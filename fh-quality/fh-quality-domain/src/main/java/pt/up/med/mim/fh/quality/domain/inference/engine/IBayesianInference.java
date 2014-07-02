package pt.up.med.mim.fh.quality.domain.inference.engine;

import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;

public interface IBayesianInference {

	public DataSetBean evaluate(String bayesianFile, DataSetBean inputData) throws BayesianNetworkException;
	
}
