package test.view.util;

import javax.servlet.jsp.PageContext;

public class PdFunctions {

	public static String pageContextClass(PageContext pageContext) {
		return pageContext.getClass().getName();
	}
}
