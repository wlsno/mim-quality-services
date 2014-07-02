package pt.up.med.mim.fh.quality.app.inference;

import java.util.HashMap;

import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.inference.common.InferenceAlgorithm;
import pt.up.med.mim.fh.quality.domain.inference.engine.IBayesianInference;
import pt.up.med.mim.fh.quality.domain.inference.engine.LoopyBeliefPropagationInference;
import pt.up.med.mim.fh.quality.domain.inference.engine.ZCHuginInference;

public class SamiamInferenceEngine {

	HashMap<InferenceAlgorithm, IBayesianInference> inferenceEngines = new HashMap<InferenceAlgorithm, IBayesianInference>();
	
	public SamiamInferenceEngine() {
		inferenceEngines.put(InferenceAlgorithm.LOOPYBELIEFPROPAGATION, new LoopyBeliefPropagationInference());
		inferenceEngines.put(InferenceAlgorithm.ZC_HUGIN, new ZCHuginInference());
	}

	public HashMap<InferenceAlgorithm, IBayesianInference> getInferenceEngines() {
		return inferenceEngines;
	}
	
	public DataSetBean getEvaluationResult(String path, DataSetBean inputData) throws BayesianNetworkException{
		return inferenceEngines.get(inputData.getAlgorithm()).evaluate(path, inputData);
	}
}
