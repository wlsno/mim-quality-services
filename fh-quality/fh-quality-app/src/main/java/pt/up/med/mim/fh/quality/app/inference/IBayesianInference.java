package pt.up.med.mim.fh.quality.app.inference;

import pt.up.med.mim.fh.quality.app.dao.QualityModuleDAO;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;

public interface IBayesianInference {

	public DataSetBean evaluate(QualityModuleDAO dao, DataSetBean inputData) throws BayesianNetworkException;
	
}
