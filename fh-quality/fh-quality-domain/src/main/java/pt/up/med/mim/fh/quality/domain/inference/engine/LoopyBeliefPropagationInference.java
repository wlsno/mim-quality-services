package pt.up.med.mim.fh.quality.domain.inference.engine;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterInstanceBean;
import edu.ucla.belief.BeliefNetwork;
import edu.ucla.belief.FiniteVariable;
import edu.ucla.belief.InferenceEngine;
import edu.ucla.belief.Table;
import edu.ucla.belief.Variable;
import edu.ucla.belief.approx.BeliefPropagationSettings;
import edu.ucla.belief.approx.PropagationEngineGenerator;
import edu.ucla.belief.io.PropertySuperintendent;

public class LoopyBeliefPropagationInference extends SamiamInferenceBase implements IBayesianInference {
	
	public DataSetBean evaluate(String path, DataSetBean inputdata) throws BayesianNetworkException {

		BeliefNetwork bn = readNetwork(path);

		Map<FiniteVariable, Object> evidence = Collections.emptyMap();

		/* Define evidence, by id */
		if (null != inputdata && null != inputdata.getParameters()) {
			evidence = buildEvidenceMap(bn, inputdata.getParameters());
		}

		/* Create the Dynamator(edu.ucla.belief.inference.SynchronizedInferenceEngine) */
		PropagationEngineGenerator dynamator = new PropagationEngineGenerator();

		/* Edit settings */
		BeliefPropagationSettings settings = PropagationEngineGenerator.getSettings((PropertySuperintendent) bn);
		settings.setTimeoutMillis(10000);
		settings.setMaxIterations(100);
		settings.setScheduler(edu.ucla.belief.approx.MessagePassingScheduler.TOPDOWNBOTTUMUP);
		settings.setConvergenceThreshold(1.0E-7);

		/* Create the InferenceEngine */
		InferenceEngine engine = dynamator.manufactureInferenceEngine(bn);

		/* Set evidence */
		try {
			bn.getEvidenceController().setObservations(evidence);
		} catch (Exception e) {
			throw new BayesianNetworkException("Error, failed to set evidence");
		}

		/* Calculate Pr(e) */
		double pE = engine.probability();
		System.out.println("Pr(e): " + pE);
		System.out.println();
		

		Set<Variable> setMarginalVariables = buildMarginalVariables(bn, inputdata.getParameters());

		/* Calculate marginals */
		FiniteVariable varMarginal = null;
		Table answer = null;

		DataSetBean result = new DataSetBean();
		result.setAlgorithm(inputdata.getAlgorithm());
		result.setArchtypeId(inputdata.getArchtypeId());
		result.setNoEvidenceMarker(inputdata.getNoEvidenceMarker());
		
		for (Iterator<Variable> marginVarIterator = setMarginalVariables.iterator(); marginVarIterator.hasNext();) {
			varMarginal = (FiniteVariable) marginVarIterator.next();

			long start = System.nanoTime();
			answer = engine.conditional(varMarginal);
			long end = System.nanoTime();
			System.out.println("Inference LBP: " + TimeUnit.MILLISECONDS.convert((end - start), TimeUnit.NANOSECONDS));

			ParameterBean parameter = new ParameterBean(varMarginal.getID(), varMarginal.getID());
//			parameter.setType(inputdata.getParameter(varMarginal.getID()).getType());
			
			for (Iterator<String> instanceIterator = varMarginal.instances().iterator(); instanceIterator.hasNext();) {
				String instanceName = (String) instanceIterator.next();
				double[] data = answer.dataclone();
				double value = data[varMarginal.instances().indexOf(instanceName)];

				ParameterInstanceBean instance = new ParameterInstanceBean(instanceName, value);
				parameter.addToInstances(instance);
			}
			result.addToParameters(parameter);
			
			for (ParameterBean param : inputdata.getParameters()){
				if(param.getHasEvidence())
					result.addToParameters(param);
			}
		}

		/* Clean up to avoid memory leaks */
		engine.die();

		return result;
	}
}