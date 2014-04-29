package pt.up.med.mim.fh.quality.app.dao;

import java.util.ResourceBundle;

public class QualityModuleDAO {
	static ResourceBundle rb = null;

	static {
		rb =  ResourceBundle.getBundle("app");
	}

	public String getBayesianNetFilepath(String formID){
    	return rb.getString(formID);
    }
}
