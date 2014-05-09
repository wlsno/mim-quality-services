package pt.up.med.mim.fh.quality.app.inference;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pt.up.med.mim.fh.quality.app.dao.QualityModuleDAO;
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
import edu.ucla.belief.io.NetworkIO;
import edu.ucla.belief.io.PropertySuperintendent;

public class BayesianInferenceEngine implements IBayesianInference {
	
	public DataSetBean evaluate(QualityModuleDAO dao, DataSetBean inputdata) throws BayesianNetworkException {

		BeliefNetwork bn = readNetwork(dao, inputdata.getArchtypeId());

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

//		/* Calculate Pr(e) */
//		double pE = engine.probability();

		@SuppressWarnings("rawtypes")
		Set setMarginalVariables = buildMarginalVariables(bn, inputdata.getParameters());

		/* Calculate marginals */
		FiniteVariable varMarginal = null;
		Table answer = null;

		DataSetBean result = new DataSetBean();
		result.setAlgorithm(inputdata.getAlgorithm());
		result.setArchtypeId(inputdata.getArchtypeId());
		result.setNoEvidenceMarker(inputdata.getNoEvidenceMarker());
		
		for (Iterator marginVarIterator = setMarginalVariables.iterator(); marginVarIterator.hasNext();) {
			varMarginal = (FiniteVariable) marginVarIterator.next();
			answer = engine.conditional(varMarginal);

			ParameterBean parameter = new ParameterBean(varMarginal.getID(), varMarginal.getID());
//			parameter.setType(inputdata.getParameter(varMarginal.getID()).getType());
			
			for (Iterator instanceIterator = varMarginal.instances().iterator(); instanceIterator.hasNext();) {
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

	/***
	 * Reads a SamIam Bayesian Network file
	 * 
	 * @param networkFilePath - The full path for a Bayesian Network File
	 * @return a BeliefNetwork
	 * @throws BayesianNetworkLoadingException
	 */
	private BeliefNetwork readNetwork(QualityModuleDAO dao, String networkFilePath) throws BayesianNetworkException {

		BeliefNetwork retBeliefNet = null;

		try {
			/* Use NetworkIO static method to read network file. */
			retBeliefNet = NetworkIO.read(dao.getBayesianNetFilepath(networkFilePath));
		} catch (Exception e) {
			System.err.println("Error, failed to read \"" + networkFilePath + "\": " + e);
			throw new BayesianNetworkException();
		}
		return retBeliefNet;
	}
	
	private Map<FiniteVariable, Object> buildEvidenceMap(BeliefNetwork bn, List<ParameterBean> parameters) {
		Map<FiniteVariable, Object> evidence = null;
		FiniteVariable var = null;

		if (null == parameters || parameters.isEmpty()) {
			return null;
		}

		evidence = new HashMap<FiniteVariable, Object>(parameters.size());

		for (ParameterBean parameterBean : parameters) {
			if (parameterBean.getHasEvidence()) {
				var = (FiniteVariable) bn.forID(parameterBean.getAlias());
				evidence.put(var, var.instance(parameterBean.getParameterEvidence().getInstanceName()));
			}
		}

		return evidence;
	}

	private Set<Variable> buildMarginalVariables(BeliefNetwork bn, List<ParameterBean> parameters) {

		Set<Variable> marginalVariables = null;

		if (null == parameters || parameters.isEmpty()) {
			return null;
		}

		marginalVariables = new HashSet<Variable>(parameters.size());

		for (ParameterBean parameter : parameters) {
			if (!parameter.getHasEvidence()) {
				marginalVariables.add(bn.forID(parameter.getAlias()));
			}
		}

		return marginalVariables;
	}
}