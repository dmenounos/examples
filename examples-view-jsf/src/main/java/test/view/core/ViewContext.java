package test.view.core;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class ViewContext implements Context {

	@Override
	public Class<? extends Annotation> getScope() {
		return ViewScoped.class;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	/**
	 * Find or Create bean.
	 */
	@Override
	public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
		T obj = get(contextual);

		if (obj == null) {
			Bean<T> bean = (Bean<T>) contextual;
			obj = bean.create(creationalContext);
			getViewMap().put(bean.getName(), obj);

		}

		return obj;
	}

	/**
	 * Find bean.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Contextual<T> contextual) {
		Bean<T> bean = (Bean<T>) contextual;
		return (T) getViewMap().get(bean.getName());
	}

	private Map<String, Object> getViewMap() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		UIViewRoot root = ctx.getViewRoot();
		return root.getViewMap(true);
	}
}
