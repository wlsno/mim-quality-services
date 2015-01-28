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
import edu.ucla.belief.EliminationHeuristic;
import edu.ucla.belief.FiniteVariable;
import edu.ucla.belief.InferenceEngine;
import edu.ucla.belief.Table;
import edu.ucla.belief.Variable;
import edu.ucla.belief.inference.JoinTreeSettings;
import edu.ucla.belief.inference.ZCEngineGenerator;
import edu.ucla.belief.io.PropertySuperintendent;

public class ZCHuginInference extends SamiamInferenceBase implements IBayesianInference {

	public DataSetBean evaluate(String path, DataSetBean inputData) throws BayesianNetworkException {

		BeliefNetwork bn = readNetwork(path);

		Map<FiniteVariable, Object> evidence = Collections.emptyMap();

		/* Define evidence, by id */
		if (null != inputData && null != inputData.getParameters()) {
			evidence = buildEvidenceMap(bn, inputData.getParameters());
		}

		/*
		 * Create the Dynamator(edu.ucla.belief.inference.
		 * SynchronizedInferenceEngine$SynchronizedPartialDerivativeEngine).
		 */
		ZCEngineGenerator dynamator = new ZCEngineGenerator();

		/* Edit settings. */
		JoinTreeSettings settings = dynamator.getSettings((PropertySuperintendent) bn, true);
		
		/*
		 * Define the elimination order heuristic used to create the join tree,
		 * one of: MIN_FILL (Min Fill), MIN_SIZE (Min Size)
		 */
		settings.setEliminationHeuristic(EliminationHeuristic.MIN_FILL);

		/* Create the InferenceEngine. */
		InferenceEngine engine = dynamator.manufactureInferenceEngine(bn);

		/* Set evidence. */
		try {
			bn.getEvidenceController().setObservations(evidence);
		} catch (Exception e) {
			throw new BayesianNetworkException("Error, failed to set evidence");
		}

		Set<Variable> setMarginalVariables = buildMarginalVariables(bn, inputData.getParameters());

//		/* Calculate Pr(e). */
		double pE = engine.probability();
		System.out.println("Pr(e): " + pE);
		System.out.println();
		
		/* Calculate marginals */
		FiniteVariable varMarginal = null;
		Table answer = null;

		DataSetBean result = new DataSetBean();
		result.setAlgorithm(inputData.getAlgorithm());
		result.setArchtypeId(inputData.getArchtypeId());
		result.setNoEvidenceMarker(inputData.getNoEvidenceMarker());
		
		for (Iterator<Variable> marginVarIterator = setMarginalVariables.iterator(); marginVarIterator.hasNext();) {
			varMarginal = (FiniteVariable) marginVarIterator.next();

			long start = System.nanoTime();
			answer = engine.conditional(varMarginal);
			long end = System.nanoTime();
			System.out.println("Inference ZCH: " + TimeUnit.MILLISECONDS.convert((end - start), TimeUnit.NANOSECONDS));

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
			
			for (ParameterBean param : inputData.getParameters()){
				if(param.getHasEvidence())
					result.addToParameters(param);
			}
		}

		/* Clean up to avoid memory leaks */
		engine.die();

		return result;
	}
}
