package test.view.util;

import java.lang.reflect.Type;
import java.util.Iterator;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Utility class for performing programmatic bean lookups.
 */
public class CDI {

	@SuppressWarnings("unchecked")
	public static <T> T lookup(Class<T> clazz) {
		BeanManager bm = lookupBeanManager();
		Iterator<Bean<?>> iter = bm.getBeans(clazz).iterator();
		if (!iter.hasNext()) {
			throw new IllegalStateException("CDI BeanManager cannot find an instance of requested type " + clazz.getName());
		}
		Bean<T> bean = (Bean<T>) iter.next();
		CreationalContext<T> ctx = bm.createCreationalContext(bean);
		T dao = (T) bm.getReference(bean, clazz, ctx);
		return dao;
	}

	public static Object lookup(String name) {
		BeanManager bm = lookupBeanManager();
		Iterator<Bean<?>> iter = bm.getBeans(name).iterator();
		if (!iter.hasNext()) {
			throw new IllegalStateException("CDI BeanManager cannot find an instance of requested type '" + name + "'");
		}
		Bean<?> bean = iter.next();
		CreationalContext<?> ctx = bm.createCreationalContext(bean);
		// select one beantype randomly. A bean has a non-empty set of beantypes.
		Type type = (Type) bean.getTypes().iterator().next();
		return bm.getReference(bean, type, ctx);
	}

	private static BeanManager lookupBeanManager() {
		try {
			// in an application server
			return (BeanManager) InitialContext.doLookup("java:comp/BeanManager");
		}
		catch (NamingException e) {
			// silently ignore
		}

		try {
			// in a servlet container
			return (BeanManager) InitialContext.doLookup("java:comp/env/BeanManager");
		}
		catch (NamingException e) {
			// silently ignore
		}

		throw new RuntimeException("Could not lookup beanmanager in jndi.");
	}
}
