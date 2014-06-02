package pt.up.med.mim.fh.quality.domain.inference.engine;

import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.domain.utils.FHQueryUtil;

public interface IBayesianInference {

	public DataSetBean evaluate(FHQueryUtil dao, DataSetBean inputData) throws BayesianNetworkException;
	
}
