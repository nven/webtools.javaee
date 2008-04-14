/*******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.archive.operations;

import org.eclipse.jst.j2ee.commonarchivecore.internal.strategy.SaveStrategy;
import org.eclipse.jst.j2ee.internal.archive.AppClientComponentArchiveSaveAdapter;
import org.eclipse.jst.j2ee.internal.archive.ComponentArchiveSaveAdapter;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class AppClientComponentImportOperation extends J2EEArtifactImportOperation {

	public AppClientComponentImportOperation(IDataModel model) {
		super(model);
	}

	protected SaveStrategy createSaveStrategy(IVirtualComponent virtualComponent) {
		AppClientComponentSaveStrategyImpl saveStrat = new AppClientComponentSaveStrategyImpl(virtualComponent);
		return saveStrat;
	}
	
	protected ComponentArchiveSaveAdapter getArchiveSaveAdapter(IVirtualComponent virtualComponent) {
		return new AppClientComponentArchiveSaveAdapter(virtualComponent);
	}

}