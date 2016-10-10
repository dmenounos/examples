package test.view.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class MessagesFactory {

	public static ResourceBundle getBundle(String bundleName) {
		Locale currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		return ResourceBundle.getBundle(bundleName, currentLocale);
	}
}
