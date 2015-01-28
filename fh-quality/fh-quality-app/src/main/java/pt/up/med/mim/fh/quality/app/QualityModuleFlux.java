package pt.up.med.mim.fh.quality.app;

import pt.up.med.mim.fh.quality.app.base.QualityModuleFluxBase;
import pt.up.med.mim.fh.quality.app.inference.SamiamInferenceEngine;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.entities.beans.FormBean;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;

public class QualityModuleFlux extends QualityModuleFluxBase {

	private final SamiamInferenceEngine engine = new SamiamInferenceEngine();
	
	public DataSetBean evaluate(DataSetBean input) throws BayesianNetworkException {
		FormBean form = null;
		String path = null;
		
		form = formdao.getByBayesianNetworkName(input.getArchtypeId());
		path = form.getBayesianNetworkFileName();

		return engine.getEvaluationResult(path, input);
	}

}
