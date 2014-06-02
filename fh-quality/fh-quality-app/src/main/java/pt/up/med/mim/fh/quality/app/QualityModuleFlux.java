package pt.up.med.mim.fh.quality.app;

import pt.up.med.mim.fh.quality.app.inference.SamiamInferenceEngine;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.utils.FHQueryUtil;

public class QualityModuleFlux {

	private static final FHQueryUtil dao = new FHQueryUtil();
	private final SamiamInferenceEngine engine = new SamiamInferenceEngine();
	
	public QualityModuleFlux() {
		// TODO Auto-generated constructor stub
	}
	
	public DataSetBean evaluate(DataSetBean input) throws BayesianNetworkException {
		return engine.getEvaluationResult(dao, input);
		//return new LoopyBeliefPropagationInference().evaluate(dao, input);
	}

}
