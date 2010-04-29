/******************************************************************************
 * Copyright (c) 2009 Red Hat
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rob Stryker - initial implementation and ongoing maintenance
 ******************************************************************************/
package org.eclipse.jst.servlet.ui.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.ui.J2EEModuleDependenciesPropertyPage;
import org.eclipse.jst.j2ee.internal.ui.preferences.Messages;
import org.eclipse.wst.common.componentcore.internal.impl.TaskModel;
import org.eclipse.wst.common.componentcore.ui.internal.propertypage.DependencyPageExtensionManager;
import org.eclipse.wst.common.componentcore.ui.internal.propertypage.DependencyPageExtensionManager.ReferenceExtension;
import org.eclipse.wst.common.componentcore.ui.propertypage.IReferenceWizardConstants;
import org.eclipse.wst.common.componentcore.ui.propertypage.ModuleAssemblyRootPage;

public class WebDependencyPropertyPage extends J2EEModuleDependenciesPropertyPage {

	public WebDependencyPropertyPage(IProject project,
			ModuleAssemblyRootPage page) {
		super(project, page);
	}

	
	@Override
	protected void createPushButtons() {
		super.createPushButtons();
	}

	@Override
	protected void setCustomReferenceWizardProperties(TaskModel model) {
		model.putObject(IReferenceWizardConstants.DEFAULT_LIBRARY_LOCATION, J2EEConstants.WEB_INF_LIB);
	}

	@Override
	protected String getModuleAssemblyRootPageDescription() {
		return Messages.WebDependencyPropertyPage_1;
	}

	@Override
	protected ReferenceExtension[] filterReferenceTypes(ReferenceExtension[] defaults) {
		// Replace the default one with our own custom one, in class CustomWebProjectReferenceWizardFragment
		for( int i = 0; i < defaults.length; i++ ) {
			if( defaults[i].getId().equals("org.eclipse.wst.common.componentcore.ui.newProjectReference")) { //$NON-NLS-1$
				defaults[i] = DependencyPageExtensionManager.getManager().findReferenceExtension("org.eclipse.jst.servlet.ui.internal.CustomWebProjectReferenceWizardFragment"); //$NON-NLS-1$
			}
		}
		return defaults;
	}
}