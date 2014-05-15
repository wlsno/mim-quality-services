package pt.up.med.mim.fh.quality.service.domain.converters;

import pt.up.med.mim.fh.quality.domain.inference.beans.DataSetBean;
import pt.up.med.mim.fh.quality.service.domain.entities.IServiceObject;
import pt.up.med.mim.fh.quality.service.domain.exceptions.QualityServiceException;

public interface IServiceObjectConverter {
	
	void convert(IServiceObject serviceObject, final DataSetBean bean) throws QualityServiceException;
	
	void convertBack(DataSetBean datasetBean, IServiceObject serviceObject) throws QualityServiceException;
}
