package pt.up.med.mim.fh.quality.domain.inference.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.ParameterBean;
import edu.ucla.belief.BeliefNetwork;
import edu.ucla.belief.FiniteVariable;
import edu.ucla.belief.Variable;
import edu.ucla.belief.io.NetworkIO;

public abstract class SamiamInferenceBase {
	
	/***
	 * Reads a SamIam Bayesian Network file
	 * 
	 * @param networkFilePath - The full path for a Bayesian Network File
	 * @return a BeliefNetwork
	 * @throws BayesianNetworkLoadingException
	 */
	protected BeliefNetwork readNetwork(String networkFilePath) throws BayesianNetworkException {

		BeliefNetwork retBeliefNet = null;

		try {
			/* Use NetworkIO static method to read network file. */
			retBeliefNet = NetworkIO.read(networkFilePath);
		} catch (Exception e) {
			System.err.println("Error, failed to read \"" + networkFilePath + "\": " + e);
			throw new BayesianNetworkException();
		}
		return retBeliefNet;
	}
	
	/**
	 * Creates a map for nodes containing evidence
	 * @param bn - The BeliefNetwork
	 * @param parameters - The list of received parameters
	 * @return A map containing evidence
	 */
	protected Map<FiniteVariable, Object> buildEvidenceMap(BeliefNetwork bn, List<ParameterBean> parameters) {
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
	
	protected Set<Variable> buildMarginalVariables(BeliefNetwork bn, List<ParameterBean> parameters) {

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
