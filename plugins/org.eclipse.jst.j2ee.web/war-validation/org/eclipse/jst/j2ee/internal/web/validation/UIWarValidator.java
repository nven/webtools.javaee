/*******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.web.validation;



import org.eclipse.core.internal.resources.Resource;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jst.j2ee.application.Module;
import org.eclipse.jst.j2ee.application.WebModule;
import org.eclipse.jst.j2ee.internal.earcreation.EARNatureRuntime;
import org.eclipse.jst.j2ee.internal.web.operations.IBaseWebNature;
import org.eclipse.jst.j2ee.internal.web.operations.J2EEWebNatureRuntime;
import org.eclipse.jst.j2ee.internal.web.operations.J2EEWebNatureRuntimeUtilities;
import org.eclipse.jst.j2ee.model.internal.validation.WarValidator;
import org.eclipse.wst.validation.core.IFileDelta;
import org.eclipse.wst.validation.core.IHelper;
import org.eclipse.wst.validation.core.IReporter;
import org.eclipse.wst.validation.core.ValidationException;
import org.eclipse.wst.validation.internal.operations.IWorkbenchHelper;
import org.eclipse.wst.web.internal.operation.ILibModule;


/**
 * Insert the type's description here. Creation date: (10/2/2001 6:46:56 PM)
 * 
 * @author: Administrator
 */
public class UIWarValidator extends WarValidator {
	/**
	 * Method validateLibModules.
	 */
	protected void validateLibModules(J2EEWebNatureRuntime webNature) {
		if (webNature != null) {
			ILibModule[] libModules = webNature.getLibModules();
			IProject project = webNature.getProject();

			for (int i = 0; i < libModules.length; i++) {
				ILibModule lib = libModules[i];
				IProject tProject = lib.getProject();
				if (tProject != null) { // Project could be null if JavaProject is deleted, for
										// example
					if (!tProject.exists() || !tProject.isOpen()) {
						addWarning(WAR_CATEGORY, MESSAGE_WAR_VALIDATION_MISSING_WLP_PROJECT, new String[]{tProject.getName()}, webNature.getProject());
					}
				}

				String jarName = lib.getJarName();
				IPath jarPath = webNature.getLibraryFolder().getFullPath().append(jarName);
				if (((Resource) project).findExistingResourceVariant(jarPath) != null)
					addWarning(WAR_CATEGORY, MESSAGE_WAR_VALIDATION_CONFLICTING_WLP_PROJECT, new String[]{jarName}, webNature.getProject());
			}
		}
	}

	protected org.eclipse.jst.j2ee.internal.web.validation.UIWarHelper warHelper;

	/**
	 * UIWarValidator constructor comment.
	 */
	public UIWarValidator() {
		super();
	}

	/**
	 * Insert the method's description here. Creation date: (10/2/2001 8:01:45 PM)
	 * 
	 * @return org.eclipse.wst.validation.internal.core.core.war.workbenchimpl.UIWarHelper
	 */
	public org.eclipse.jst.j2ee.internal.web.validation.UIWarHelper getWarHelper() {
		return warHelper;
	}

	/**
	 * Insert the method's description here. Creation date: (10/2/2001 8:01:45 PM)
	 * 
	 * @param newWarHelper
	 *            org.eclipse.wst.validation.internal.core.core.war.workbenchimpl.UIWarHelper
	 */
	public void setWarHelper(org.eclipse.jst.j2ee.internal.web.validation.UIWarHelper newWarHelper) {
		warHelper = newWarHelper;
	}

	/**
	 * Insert the method's description here. Creation date: (10/2/2001 6:49:26 PM)
	 */
	public void validate() throws ValidationException {
		// First remove all previous msg. for this project
		//	_reporter.removeAllMessages(this,null);
		super.validate();
		validateEARForContextRoot();
	}

	/**
	 * Insert the method's description here. Creation date: (10/2/2001 6:49:26 PM)
	 */
	public void validate(IHelper inHelper, IReporter inReporter, IFileDelta[] inChangedFiles) throws org.eclipse.wst.validation.core.ValidationException {
		setWarHelper((UIWarHelper) inHelper);

		IProject proj = ((IWorkbenchHelper) inHelper).getProject();
		IBaseWebNature webNature = null;
		if (proj != null) {
			webNature = J2EEWebNatureRuntimeUtilities.getRuntime(proj);
		}

		// if this is a static project, it has no WAR, so do not validate.
		if (webNature != null && !webNature.isStatic()) {
			super.validate(inHelper, inReporter, inChangedFiles);
			validateLibModules((J2EEWebNatureRuntime) webNature);
		}

	}

	/**
	 * Insert the method's description here. Creation date: (10/2/2001 6:49:26 PM)
	 */
	public void validateEARForContextRoot() {

		IProject project = getWarHelper().getProject();
		J2EEWebNatureRuntime webNature = J2EEWebNatureRuntime.getRuntime(project);
		String contextRoot = webNature.getContextRoot();

		if (webNature == null)
			return;

		EARNatureRuntime earRuntimeNature[] = webNature.getReferencingEARProjects();

		for (int i = 0; i < earRuntimeNature.length; i++) {
			// for each Ear Project that contains this web project update the context root.
			// hold the model and validate the context root & release the model
			org.eclipse.jst.j2ee.internal.earcreation.EAREditModel editModel = null;
			try {
				editModel = earRuntimeNature[i].getEarEditModelForRead(this);
				if (editModel != null) {
					Module module = null;
					if ((module = earRuntimeNature[i].getModule(project)) != null) {
						String webModcontextRoot = ((WebModule) module).getContextRoot();
						if (webModcontextRoot != null && !webModcontextRoot.equals(contextRoot)) {
							String[] params = new String[3];
							params[0] = project.getName();
							params[1] = ((WebModule) module).getContextRoot();
							params[2] = earRuntimeNature[i].getProject().getName();
							addError(WAR_CATEGORY, ERROR_EAR_INVALID_CONTEXT_ROOT, params);
						}
					}
				}
			} finally {
				if (editModel != null) {
					editModel.releaseAccess(this);
					editModel = null;
				}
			}

		}

	}
}