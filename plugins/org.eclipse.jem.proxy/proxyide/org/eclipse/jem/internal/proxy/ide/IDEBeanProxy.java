package org.eclipse.jem.internal.proxy.ide;
/*******************************************************************************
 * Copyright (c)  2001, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/*
 *  $RCSfile: IDEBeanProxy.java,v $
 *  $Revision: 1.2 $  $Date: 2004/01/12 21:44:26 $ 
 */

import org.eclipse.jem.internal.proxy.core.*;

public abstract class IDEBeanProxy implements IBeanProxy, IIDEBeanProxy {

	protected Object fBean;
	protected final ProxyFactoryRegistry fProxyFactoryRegistry;

	protected IDEBeanProxy(ProxyFactoryRegistry aProxyFactoryRegistry) {
		fProxyFactoryRegistry = aProxyFactoryRegistry;
	}
	protected IDEBeanProxy(ProxyFactoryRegistry aProxyFactoryRegistry, Object anObject) {
		fProxyFactoryRegistry = aProxyFactoryRegistry;
		fBean = anObject;
	}
	public boolean isValid() {
		return true;
	}
	public boolean isNullProxy() {
		return fBean == null;
	}
	public ProxyFactoryRegistry getProxyFactoryRegistry() {
		return fProxyFactoryRegistry;
	}
	/**
	 * USE with extreme care
	 */
	public final Object getBean() {
		return fBean;
	}
	/**
	 * Return the toString() of the bean
	 */
	public String toBeanString() {
		return (fBean != null ? fBean.toString() : "null"); //$NON-NLS-1$
	}
	/**
	 Append the bean's to string to our own name if we have one
	 */
	public String toString() {

		if (fBean == null)
			return super.toString();
		else
			return super.toString() + "(" + fBean.toString() + ")"; //$NON-NLS-2$//$NON-NLS-1$

	}
	/**
	 * equals: If there are identical or if they wrapper the same bean. In the IDE VM this
	 * can happen if bean proxies are created from a Bean, since proxies aren't cached in
	 * the beanproxy factory, more than one proxy can be created for the same bean.
	 */
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		if (obj instanceof IIDEBeanProxy) {
			return fBean.equals(((IIDEBeanProxy) obj).getBean());
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jem.internal.proxy.core.IBeanProxy#sameAs(org.eclipse.jem.internal.proxy.core.IBeanProxy)
	 */
	public boolean sameAs(IBeanProxy aBeanProxy) {
		if (this == aBeanProxy)
			return true;
		if (aBeanProxy instanceof IIDEBeanProxy)
			return getBean() == ((IIDEBeanProxy) aBeanProxy).getBean();
		return false;
	}

}