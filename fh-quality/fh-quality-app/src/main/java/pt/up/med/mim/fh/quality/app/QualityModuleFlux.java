package pt.up.med.mim.fh.quality.app;

import pt.up.med.mim.fh.quality.app.dao.QualityModuleDAO;
import pt.up.med.mim.fh.quality.app.inference.BayesianInferenceEngine;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;

public class QualityModuleFlux {

	private static final QualityModuleDAO dao = new QualityModuleDAO();
	
	public QualityModuleFlux() {
		// TODO Auto-generated constructor stub
	}
	
	public DataSetBean evaluate(DataSetBean input) throws BayesianNetworkException {
		return new BayesianInferenceEngine().evaluate(dao, input);
	}

}
