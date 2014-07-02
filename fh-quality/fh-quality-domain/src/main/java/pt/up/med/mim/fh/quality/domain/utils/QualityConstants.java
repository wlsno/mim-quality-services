package pt.up.med.mim.fh.quality.domain.utils;

import pt.up.med.mim.fh.infrastructure.rdata.dao.ReferenceDataDAO;
import pt.up.med.mim.fh.infrastructure.rdata.entities.ReferenceDataType;

public abstract class QualityConstants {
	static ReferenceDataDAO dao = new ReferenceDataDAO();
	
	// Configurations
	public final static String BAYESIAN_REPOSITORY_EXT = "BAYESIAN_REPOSITORY_EXT";
	public final static String BAYESIAN_REPOSITORY_PATH = "BAYESIAN_REPOSITORY_PATH";
	
	
	public static String getFileExtension(){
		return dao.getValue(ReferenceDataType.QUALITYSERVICE, BAYESIAN_REPOSITORY_EXT);
	}
	
	public static String getRepositoryPath(){
		return dao.getValue(ReferenceDataType.QUALITYSERVICE, BAYESIAN_REPOSITORY_PATH);
	}
	
	
}
