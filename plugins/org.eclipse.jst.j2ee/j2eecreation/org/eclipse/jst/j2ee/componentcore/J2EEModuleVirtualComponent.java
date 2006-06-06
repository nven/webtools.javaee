/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.componentcore;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jem.util.logger.proxy.Logger;
import org.eclipse.jst.j2ee.commonarchivecore.internal.helpers.ArchiveManifest;
import org.eclipse.jst.j2ee.commonarchivecore.internal.helpers.ArchiveManifestImpl;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.common.classpath.J2EEComponentClasspathUpdater;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.internal.resources.VirtualComponent;
import org.eclipse.wst.common.componentcore.internal.util.IComponentImplFactory;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.componentcore.resources.IVirtualReference;

public class J2EEModuleVirtualComponent extends VirtualComponent implements IComponentImplFactory {

	public J2EEModuleVirtualComponent() {
		super();
	}

	public J2EEModuleVirtualComponent(IProject aProject, IPath aRuntimePath) {
		super(aProject, aRuntimePath);
	}

	public IVirtualComponent createComponent(IProject aProject) {
		return new J2EEModuleVirtualComponent(aProject, new Path("/")); //$NON-NLS-1$
	}

	public IVirtualReference[] getReferences() {
		IVirtualReference[] hardReferences = super.getReferences();
		List dynamicReferences = getManifestReferences(this, hardReferences);

		IVirtualReference[] references = null;
		if (dynamicReferences == null) {
			references = hardReferences;
		} else {
			references = new IVirtualReference[hardReferences.length + dynamicReferences.size()];
			System.arraycopy(hardReferences, 0, references, 0, hardReferences.length);
			for (int i = 0; i < dynamicReferences.size(); i++) {
				references[hardReferences.length + i] = (IVirtualReference) dynamicReferences.get(i);
			}
		}
		return references;
	}


	private static List getManifestReferences(IVirtualComponent moduleComponent, IVirtualReference[] hardReferences) {
		List dynamicReferences = null;
		String[] manifestClasspath = null;
		IVirtualFile vManifest = moduleComponent.getRootFolder().getFile(J2EEConstants.MANIFEST_URI);
		if (vManifest.exists()) {
			IFile manifestFile = vManifest.getUnderlyingFile();
			J2EEComponentClasspathUpdater.getInstance().trackManifest(manifestFile);
			InputStream in = null;
			try {
				in = manifestFile.getContents();
				ArchiveManifest manifest = new ArchiveManifestImpl(in);
				manifestClasspath = manifest.getClassPathTokenized();
			} catch (IOException e) {
				Logger.getLogger().logError(e);
			} catch (CoreException e) {
				Logger.getLogger().logError(e);
			} finally {
				if (in != null) {
					try {
						in.close();
						in = null;
					} catch (IOException e) {
						Logger.getLogger().logError(e);
					}
				}
			}
		}

		if (manifestClasspath != null && manifestClasspath.length > 0) {
			IProject[] earProjects = J2EEProjectUtilities.getAllProjectsInWorkspaceOfType(J2EEProjectUtilities.ENTERPRISE_APPLICATION);
			IVirtualReference[] earRefs = null;
			for (int i = 0; i < earProjects.length && null == earRefs; i++) {
				IVirtualComponent tempEARComponent = ComponentCore.createComponent(earProjects[i]);
				IVirtualReference[] tempEarRefs = tempEARComponent.getReferences();
				for (int j = 0; j < tempEarRefs.length && earRefs == null; j++) {
					if (tempEarRefs[j].getReferencedComponent().equals(moduleComponent)) {
						earRefs = tempEarRefs;
					}
				}
			}

			if (null != earRefs) {
				for (int i = 0; i < manifestClasspath.length; i++) {
					boolean found = false;
					for (int j = 0; j < earRefs.length && !found; j++) {
						String archiveName = earRefs[j].getArchiveName();
						if (null != archiveName && archiveName.equals(manifestClasspath[i])) {
							found = true;
							boolean shouldInclude = true;
							IVirtualComponent dynamicComponent = earRefs[j].getReferencedComponent();
							for (int k = 0; k < hardReferences.length && shouldInclude; k++) {
								if (hardReferences[k].getReferencedComponent().equals(dynamicComponent)) {
									shouldInclude = false;
								}
							}
							if (shouldInclude) {
								IVirtualReference dynamicReference = ComponentCore.createReference(moduleComponent, dynamicComponent);
								if (null == dynamicReferences) {
									dynamicReferences = new ArrayList();
								}
								dynamicReferences.add(dynamicReference);
							}
						}
					}
				}
			}
		}
		return dynamicReferences;
	}

}
