package test.view.tool;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class IncludeController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger LOGGER = LoggerFactory.getLogger(IncludeController.class);

	public static final String INCLUDE_KOKO = "INCLUDE_KOKO";
	public static final String INCLUDE_LOLO = "INCLUDE_LOLO";

	private List<String> includeFiles;
	private String includeFile;

	@PostConstruct
	public void postConstruct() {
		LOGGER.debug("postConstruct: ");
		includeFiles = Arrays.asList(INCLUDE_KOKO, INCLUDE_LOLO);
	}

	public void preRenderView() throws Exception {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		LOGGER.debug("preRenderView: ");
		includeFile = INCLUDE_KOKO;
	}

	public List<String> getIncludeFiles() {
		return includeFiles;
	}

	public void setIncludeFiles(List<String> includeFiles) {
		this.includeFiles = includeFiles;
	}

	public String getIncludeFile() {
		return includeFile;
	}

	public void setIncludeFile(String includeFile) {
		this.includeFile = includeFile;
	}
}
