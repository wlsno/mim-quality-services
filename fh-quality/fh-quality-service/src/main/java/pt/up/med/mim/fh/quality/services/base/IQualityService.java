package pt.up.med.mim.fh.quality.services.base;

import pt.up.med.mim.fh.quality.service.domain.entities.InputCase;
import pt.up.med.mim.fh.quality.service.domain.entities.OutputCase;

public interface IQualityService {
	
	OutputCase validateCase(InputCase inputData);
}
