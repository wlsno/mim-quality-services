package pt.up.med.mim.fh.quality.services;

import java.util.concurrent.TimeUnit;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import pt.up.med.mim.fh.quality.app.QualityModuleFlux;
import pt.up.med.mim.fh.quality.common.exception.BayesianNetworkException;
import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceResult;
import pt.up.med.mim.fh.quality.service.domain.entities.DataEvaluationServiceRequest;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;
import pt.up.med.mim.fh.quality.services.base.IQualityService;
import pt.up.med.mim.fh.quality.services.base.QualityServiceBase;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class DataValidation extends QualityServiceBase implements IQualityService {

	QualityModuleFlux moduleflux = new QualityModuleFlux();

	@WebMethod(operationName = "validateForm")
	public DataEvaluationServiceResult validateCase(@WebParam(name = "inputData") DataEvaluationServiceRequest inputData) {	
		DataSetBean bean = new DataSetBean();
		DataEvaluationServiceResult result = new DataEvaluationServiceResult();

		try {

			System.out.println("Operation Start");
			long start = System.nanoTime();
			inferencePreAction(inputData, bean);
			bean = assessForm(bean);
			inferencePostAction(result, bean);
			long end = System.nanoTime();
			
			System.out.println("Operation Total: " + TimeUnit.MILLISECONDS.convert((end - start), TimeUnit.NANOSECONDS));
			System.out.println();
			return result;
		} catch (QualityServiceException e) {
			e.printStackTrace();
			return null;
		}

	}

	private DataSetBean assessForm(DataSetBean inputBean) {
		DataSetBean bean = new DataSetBean();

		try {
			bean = moduleflux.evaluate(inputBean);
		} catch (BayesianNetworkException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
