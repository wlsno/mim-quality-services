package pt.up.med.mim.fh.quality.app;

import pt.up.med.mim.fh.quality.app.base.QualityModuleFluxBase;
import pt.up.med.mim.fh.quality.app.inference.SamiamInferenceEngine;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.utils.QualityConstants;

public class QualityModuleFlux extends QualityModuleFluxBase {

	private final SamiamInferenceEngine engine = new SamiamInferenceEngine();
	
	public QualityModuleFlux() {
		// TODO Auto-generated constructor stub
	}
	
	public DataSetBean evaluate(DataSetBean input) throws BayesianNetworkException {
		String path = util.buildBayesianNetworkPath(QualityConstants.getRepositoryPath(), input.getArchtypeId(), QualityConstants.getFileExtension());
		
		return engine.getEvaluationResult(path, input);
	}

}
