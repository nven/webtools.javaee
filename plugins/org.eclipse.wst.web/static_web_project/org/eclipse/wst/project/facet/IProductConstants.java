/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.project.facet;

/**
 * These constants define the set of properties that this pluging expects to
 * be available via <code>IProduct.getProperty(String)</code>. The status of
 * this interface and the facilities offered is highly provisional. 
 * Productization support will be reviewed and possibly modified in future 
 * releases.
 * 
 * @see org.eclipse.core.runtime.IProduct#getProperty(String)
 */

public interface IProductConstants {   
    
    public static final String APPLICATION_CONTENT_FOLDER = "earContent"; //$NON-NLS-1$
	public static final String WEB_CONTENT_FOLDER = "webContent"; //$NON-NLS-1$
	public static final String EJB_CONTENT_FOLDER = "ejbContent"; //$NON-NLS-1$
	public static final String APP_CLIENT_CONTENT_FOLDER = "appClientContent"; //$NON-NLS-1$
	public static final String JCA_CONTENT_FOLDER = "jcaContent"; //$NON-NLS-1$
	public static final String DEFAULT_SOURCE_FOLDER = "defaultSource"; //$NON-NLS-1$
	public static final String ADD_TO_EAR_BY_DEFAULT = "addToEarByDefault"; //$NON-NLS-1$
	public static final String OUTPUT_FOLDER = "outputFolder"; //$NON-NLS-1$
	public static final String USE_SINGLE_ROOT_STRUCTURE = "useSingleRootStructure"; //$NON-NLS-1$
	public static final String ID_PERSPECTIVE_HIERARCHY_VIEW = "idPerspectiveHierarchyView"; //$NON-NLS-1$
	
	/**
     * Alters the final perspective used by the following new project wizards
     */
	public static final String FINAL_PERSPECTIVE_WEB = "finalPerspectiveWeb"; //$NON-NLS-1$
	public static final String FINAL_PERSPECTIVE_EJB = "finalPerspectiveEjb"; //$NON-NLS-1$
	public static final String FINAL_PERSPECTIVE_EAR = "finalPerspectiveEar"; //$NON-NLS-1$
	public static final String FINAL_PERSPECTIVE_APPCLIENT = "finalPerspectiveAppClient"; //$NON-NLS-1$
	public static final String FINAL_PERSPECTIVE_JCA = "finalPerspectiveJca"; //$NON-NLS-1$
	public static final String FINAL_PERSPECTIVE_UTILITY = "finalPerspectiveUtility"; //$NON-NLS-1$
	public static final String FINAL_PERSPECTIVE_STATICWEB = "finalPerspectiveStaticWeb"; //$NON-NLS-1$
	
	/**
	 * Ability to default initial runtimes chosen in wizards
	 */
	public static final String DEFAULT_RUNTIME_1 = "defaultRuntime1"; //$NON-NLS-1$
	public static final String DEFAULT_RUNTIME_2 = "defaultRuntime2"; //$NON-NLS-1$
	public static final String DEFAULT_RUNTIME_3 = "defaultRuntime3"; //$NON-NLS-1$
	public static final String VIEWER_SYNC_FOR_WEBSERVICES = "viewerSyncForWebservices"; //$NON-NLS-1$
	
}