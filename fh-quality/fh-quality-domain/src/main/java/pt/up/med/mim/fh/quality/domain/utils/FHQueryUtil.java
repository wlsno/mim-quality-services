package pt.up.med.mim.fh.quality.domain.utils;

import java.util.ResourceBundle;

public class FHQueryUtil {
	static ResourceBundle rb = null;

	static {
		rb = ResourceBundle.getBundle("app");
	}

	/**
	 * Gets the Path for the given Bayesian Network File Name
	 * 
	 * @param filename
	 *            The name of the file for the Bayesian Network
	 * @return The path for the given Bayesian Network (using the configurations
	 *         on the app properties file)
	 */
	public String buildBayesianNetworkPathFromProperties(String formID) {
		return rb.getString(formID);
	}

	/**
	 * Gets the Path for the given Bayesian Network File Name
	 * 
	 * @param filename
	 *            The name of the file for the Bayesian Network
	 * @return The path for the given Bayesian Network (using the configurations
	 *         on the reference data table)
	 */
	public String buildBayesianNetworkPath(String filename) {
		return String.format("%s%s%s", QualityConstants.getRepositoryPath(), filename, QualityConstants.getFileExtension());
	}

	/**
	 * Gets the Path for the given Bayesian Network File Name
	 * 
	 * @param repositoryPath
	 * @param filename
	 * @param fileExtension
	 * @return The path for the given Bayesian Network
	 */
	public String buildBayesianNetworkPath(String repositoryPath,
			String filename, String fileExtension) {
		return String.format("%s%s%s", repositoryPath, filename, fileExtension);
	}
}
