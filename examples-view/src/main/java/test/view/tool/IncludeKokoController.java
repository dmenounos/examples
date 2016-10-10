package test.view.tool;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class IncludeKokoController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger LOGGER = LoggerFactory.getLogger(IncludeKokoController.class);

	@Inject
	private IncludeController parentBean;

	@PostConstruct
	public void postConstruct() {
		LOGGER.debug("postConstruct: ");
	}

	public void preRenderView() throws Exception {
		if (IncludeController.INCLUDE_KOKO.equals(parentBean.getIncludeFile())) {
			// Initialize on-demand.
			initialize();
		}
	}

	public void initialize() throws Exception {
		LOGGER.debug("initialize: ");
	}
}
