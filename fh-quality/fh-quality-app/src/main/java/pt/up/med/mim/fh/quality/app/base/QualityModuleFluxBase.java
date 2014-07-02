package pt.up.med.mim.fh.quality.app.base;

import pt.up.med.mim.fh.quality.app.dao.QualityModuleDAO;
import pt.up.med.mim.fh.quality.domain.utils.FHQueryUtil;

public abstract class QualityModuleFluxBase {
	protected static final QualityModuleDAO dao = new QualityModuleDAO();
	protected static final FHQueryUtil util = new FHQueryUtil();
	
}
