package pt.up.med.mim.fh.quality.app.base;

import pt.up.med.mim.fh.quality.app.dao.FormDAO;
import pt.up.med.mim.fh.quality.domain.utils.FHQueryUtil;

public abstract class QualityModuleFluxBase {
	protected static final FormDAO formdao = new FormDAO();
	protected static final FHQueryUtil util = new FHQueryUtil();
	
}
