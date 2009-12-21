package org.eclipse.jst.j2ee.internal.plugin;


import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jst.common.frameworks.CommonFrameworksPlugin;
import org.eclipse.jst.common.project.facet.core.internal.FacetCorePlugin;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.J2EEVersionConstants;
import org.eclipse.wst.project.facet.IProductConstants;
import org.eclipse.wst.project.facet.ProductManager;

public class JavaEEPreferencesInitializer extends AbstractPreferenceInitializer {

	public interface Keys {
		static final String JAVA_SOURCE = "org.eclipse.jst.j2ee.preference.javaSourceName"; //$NON-NLS-1$

		static final String SERVER_TARGET_SUPPORT = "org.eclipse.jst.j2ee.preference.servertargetsupport"; //$NON-NLS-1$
		static final String CREATE_EJB_CLIENT_JAR = "org.eclipse.jst.j2ee.preference.createClientJar"; //$NON-NLS-1$
		static final String J2EE_VERSION = "org.eclipse.jst.j2ee.ui.preference.j2eeVersion"; //$NON-NLS-1$
		static final String EJB_CLIENT_JAR_CP_COMPATIBILITY = "org.eclipse.jst.j2ee.preference.clientjar.cp.compatibility"; //$NON-NLS-1$
		static final String INCREMENTAL_DEPLOYMENT_SUPPORT = "org.eclipse.jst.j2ee.ui.preference.incrementalDeployment"; //$NON-NLS-1$
		
		final static String USE_EAR_LIBRARIES = "org.eclipse.jst.j2ee.preferences.useEARLibraries";//$NON-NLS-1$
		final static String USE_WEB_APP_LIBRARIES = "org.eclipse.jst.j2ee.preferences.useWebAppLibraries";//$NON-NLS-1$
		final static String USE_EAR_LIBRARIES_JDT_EXPORT = "org.eclipse.jst.j2ee.preferences.useEARLibrariesJDTExport";//$NON-NLS-1$
		final static String ALLOW_CLASSPATH_DEP = "org.eclipse.jst.j2ee.preferences.allowClasspathDep";//$NON-NLS-1$

		static final String J2EE_WEB_CONTENT = "org.eclipse.jst.j2ee.preference.j2eeWebContentName"; //$NON-NLS-1$
		static final String STATIC_WEB_CONTENT = "org.eclipse.jst.j2ee.preference.staticWebContentName"; //$NON-NLS-1$
		static final String APPLICATION_CONTENT_FOLDER = IProductConstants.APPLICATION_CONTENT_FOLDER;
		/**
		 * @since 2.0
		 */
		static final String WEB_CONTENT_FOLDER = IProductConstants.WEB_CONTENT_FOLDER;
		/**
		 * @since 2.0
		 */
		static final String EJB_CONTENT_FOLDER = IProductConstants.EJB_CONTENT_FOLDER;
		/**
		 * @since 2.0
		 */
		static final String APP_CLIENT_CONTENT_FOLDER = IProductConstants.APP_CLIENT_CONTENT_FOLDER;
		/**
		 * @since 2.0
		 */
		static final String JCA_CONTENT_FOLDER = IProductConstants.JCA_CONTENT_FOLDER;
		
		/**
		 * @since 2.0
		 */
		static final String ADD_TO_EAR_BY_DEFAULT = IProductConstants.ADD_TO_EAR_BY_DEFAULT;
		/**
		 * @since 2.0
		 */
		static final String APPLICATION_GENERATE_DD = "application_generate_dd"; //$NON-NLS-1$
		/**
		 * @since 2.0
		 */
		static final String DYNAMIC_WEB_GENERATE_DD = "dynamic_web_generate_dd"; //$NON-NLS-1$
		/**
		 * @since 2.0
		 */
		static final String EJB_GENERATE_DD = "ejb_generate_dd"; //$NON-NLS-1$
		/**
		 * @since 2.0
		 */
		static final String APP_CLIENT_GENERATE_DD = "app_client_generate_dd"; //$NON-NLS-1$
		/**
		 * @since 3.0
		 */
		static String ID_PERSPECTIVE_HIERARCHY_VIEW = "perspective_hierarchy_view_id"; //$NON-NLS-1$
		/**
		 * @since 3.1
		 */
		static String SHOW_JAVA_EE_MODULE_DEPENDENCY_PAGE = "showJavaEEModuleDependencyPage"; //$NON-NLS-1$
		
		
		/**
		 * 
		 */
		static final String DYN_WEB_SRC_FOLDER = "dynWebSource"; //$NON-NLS-1$
		/**
		 * 
		 */
		
		static final String DYN_WEB_OUTPUT_FOLDER = IProductConstants.DYN_WEB_OUTPUT_FOLDER;
		/**
		 * 
		 */
		static final String EJB_OUTPUT_FOLDER = IProductConstants.EJB_OUTPUT_FOLDER;
		
		/**
		 * 
		 */
	    static final String APP_CLIENT_OUTPUT_FOLDER = IProductConstants.APP_CLIENT_OUTPUT_FOLDER;
	    
		/**
		 * 
		 */
	    static final String JCA_OUTPUT_FOLDER = IProductConstants.JCA_OUTPUT_FOLDER;
	    
	    /**
	     * @since 3.2
	     */
		static final String EE6_CONNECTOR_GENERATE_DD = "ee6_connector_generate_dd"; //$NON-NLS-1$

	}

	public interface Values {
		final static String J2EE_VERSION_1_2 = "J2EE_1_2"; //$NON-NLS-1$
		final static String J2EE_VERSION_1_3 = "J2EE_1_3"; //$NON-NLS-1$
		final static String J2EE_VERSION_1_4 = "J2EE_1_4"; //$NON-NLS-1$

		/**
		 * @deprecated, see initializeDefaultPreferences() it uses ProductManager
		 */
		final static String J2EE_WEB_CONTENT = ProductManager.getProperty(IProductConstants.WEB_CONTENT_FOLDER);
		/**
		 * @deprecated, see initializeDefaultPreferences() it uses ProductManager
		 */
		final static String STATIC_WEB_CONTENT = ProductManager.getProperty(IProductConstants.WEB_CONTENT_FOLDER);
		/**
		 * @deprecated, use CommonFrameworksPlugin.DEFAULT_SOURCE_FOLDER
		 */
		final static String JAVA_SOURCE = CommonFrameworksPlugin.getDefault().getPluginPreferences().getString(CommonFrameworksPlugin.DEFAULT_SOURCE_FOLDER);
	}

	public interface Defaults {

		/**
		 * @deprecated, see initializeDefaultPreferences() it uses ProductManager
		 */
		final static String J2EE_WEB_CONTENT = Values.J2EE_WEB_CONTENT;
		/**
		 * @deprecated, see initializeDefaultPreferences() it uses ProductManager
		 */
		final static String STATIC_WEB_CONTENT = Values.STATIC_WEB_CONTENT;
		/**
		 * @deprecated, see DEFAULT_SOURCE_FOLDER
		 */
		final static String JAVA_SOURCE = Values.JAVA_SOURCE;
		final static String J2EE_VERSION = Values.J2EE_VERSION_1_4;
		final static int J2EE_VERSION_ID = J2EEVersionConstants.J2EE_1_4_ID;
		final static boolean CREATE_EJB_CLIENT_JAR = false;
		final static boolean EJB_CLIENT_JAR_CP_COMPATIBILITY = true;
		final static boolean INCREMENTAL_DEPLOYMENT_SUPPORT = true;
		final static boolean USE_EAR_LIBRARIES_JDT_EXPORT = false;
		final static String ID_PERSPECTIVE_HIERARCHY_VIEW = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$
		final static boolean ALLOW_CLASSPATH_DEP = true;
		final static boolean SHOW_JAVA_EE_MODULE_DEPENDENCY_PAGE = true;
		public static final String STRING_DEFAULT_DEFAULT = ""; //$NON-NLS-1$
		public static final boolean BOOLEAN_DEFAULT_DEFAULT = false;
	}
	
	@Override
	public void initializeDefaultPreferences() {
		
		IEclipsePreferences node = new DefaultScope().getNode(J2EEPlugin.PLUGIN_ID);
		
		node.put(Keys.J2EE_VERSION, Defaults.J2EE_VERSION);
		node.putBoolean(Keys.CREATE_EJB_CLIENT_JAR, Defaults.CREATE_EJB_CLIENT_JAR);
		node.putBoolean(Keys.EJB_CLIENT_JAR_CP_COMPATIBILITY, Defaults.EJB_CLIENT_JAR_CP_COMPATIBILITY);
		node.putBoolean(Keys.INCREMENTAL_DEPLOYMENT_SUPPORT, Defaults.INCREMENTAL_DEPLOYMENT_SUPPORT);
		
		// since 2.0
		node.put(Keys.J2EE_WEB_CONTENT, ProductManager.getProperty(IProductConstants.WEB_CONTENT_FOLDER));
		node.put(Keys.STATIC_WEB_CONTENT, ProductManager.getProperty(IProductConstants.WEB_CONTENT_FOLDER));
		// since 2.0
		node.put(Keys.JAVA_SOURCE, FacetCorePlugin.getJavaSrcFolder());
		// done in CommonFrameworksPref..Initializer
		//node.put(Keys.DEFAULT_SOURCE_FOLDER, ProductManager.getProperty(IProductConstants.DEFAULT_SOURCE_FOLDER));
		node.put(Keys.APPLICATION_CONTENT_FOLDER, ProductManager.getProperty(IProductConstants.APPLICATION_CONTENT_FOLDER));
		node.put(Keys.WEB_CONTENT_FOLDER, ProductManager.getProperty(IProductConstants.WEB_CONTENT_FOLDER));
		node.put(Keys.APP_CLIENT_CONTENT_FOLDER, ProductManager.getProperty(IProductConstants.APP_CLIENT_CONTENT_FOLDER));
		node.put(Keys.EJB_CONTENT_FOLDER, ProductManager.getProperty(IProductConstants.EJB_CONTENT_FOLDER));
		node.put(Keys.JCA_CONTENT_FOLDER, ProductManager.getProperty(IProductConstants.JCA_CONTENT_FOLDER));
		node.put(Keys.ADD_TO_EAR_BY_DEFAULT, ProductManager.getProperty(IProductConstants.ADD_TO_EAR_BY_DEFAULT));
		// done in CommonFrameworksPref..Initializer
		//node.put(Keys.OUTPUT_FOLDER, ProductManager.getProperty(IProductConstants.OUTPUT_FOLDER));
		
		// since 2.0, for java ee projects
		node.putBoolean(Keys.APPLICATION_GENERATE_DD, false);
		// for ee5 jee web projects default it to true so that we can create servlets, otherwise false
		node.putBoolean(Keys.DYNAMIC_WEB_GENERATE_DD, true);
		node.putBoolean(Keys.EJB_GENERATE_DD, false);
		node.putBoolean(Keys.APP_CLIENT_GENERATE_DD, false);	
		node.putBoolean(Keys.EE6_CONNECTOR_GENERATE_DD, false);
		
		node.putBoolean(Keys.USE_EAR_LIBRARIES, true);
		node.putBoolean(Keys.USE_WEB_APP_LIBRARIES, true);
		node.putBoolean(Keys.USE_EAR_LIBRARIES_JDT_EXPORT, Defaults.USE_EAR_LIBRARIES_JDT_EXPORT);
		String perspectiveID = ProductManager.getProperty(IProductConstants.ID_PERSPECTIVE_HIERARCHY_VIEW);
		node.put(Keys.ID_PERSPECTIVE_HIERARCHY_VIEW, (perspectiveID != null) ? perspectiveID : Defaults.ID_PERSPECTIVE_HIERARCHY_VIEW);
		node.putBoolean(Keys.ALLOW_CLASSPATH_DEP, Defaults.ALLOW_CLASSPATH_DEP);
		String showJavaEEModuleDependencyPage = ProductManager.getProperty(IProductConstants.SHOW_JAVA_EE_MODULE_DEPENDENCY_PAGE);
		boolean showJavaEEModuleDependencyPageDefault = (showJavaEEModuleDependencyPage != null) ? Boolean.parseBoolean(showJavaEEModuleDependencyPage) : Defaults.SHOW_JAVA_EE_MODULE_DEPENDENCY_PAGE;
		node.putBoolean(Keys.SHOW_JAVA_EE_MODULE_DEPENDENCY_PAGE, showJavaEEModuleDependencyPageDefault);
		
		node.put(Keys.DYN_WEB_SRC_FOLDER, getDynamicWebDefaultSourceFolder());
		node.put(Keys.DYN_WEB_OUTPUT_FOLDER, getDynamicWebDefaultOuputFolderName());
		node.put(Keys.APP_CLIENT_OUTPUT_FOLDER,  getAppClientDefaultOutputFolderName() );
		node.put(Keys.EJB_OUTPUT_FOLDER, getEJBDefaultOutputFolderName() );
		node.put(Keys.JCA_OUTPUT_FOLDER, getJCADefaultOutputFolderName() );	
		}

	
	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	static String getDynamicWebDefaultSourceFolder(){
		return getDefaultJavaSrcFolder();
	}
	

	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	static String getDefaultJavaSrcFolder(){
		String srcFolder = FacetCorePlugin.getDefault().getPluginPreferences().getDefaultString(FacetCorePlugin.PROD_PROP_SOURCE_FOLDER_LEGACY);
		if( srcFolder == null || srcFolder.equals("") ){ //$NON-NLS-1$
			if( Platform.getProduct() != null ){
				srcFolder = Platform.getProduct().getProperty( "defaultJavaSourceFolder" ); //$NON-NLS-1$
			    if( srcFolder == null || srcFolder.equals("")){ //$NON-NLS-1$
			    	srcFolder = Platform.getProduct().getProperty( FacetCorePlugin.PROD_PROP_SOURCE_FOLDER_LEGACY );
			    }      			
			}
	    	if( srcFolder == null || srcFolder.equals("") ){ //$NON-NLS-1$
	    		srcFolder = FacetCorePlugin.DEFAULT_SOURCE_FOLDER;
	    	}
	
		}
	    return srcFolder;
	}
	
	
	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	static String getDynamicWebDefaultOuputFolderName(){
		if ( ProductManager.shouldUseSingleRootStructure() ){
			return ProductManager.getProperty(IProductConstants.WEB_CONTENT_FOLDER) + "/"+ J2EEConstants.WEB_INF_CLASSES; //$NON-NLS-1$
		}
		return getDefaultOutputFolderName();
	}
	
	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	static String getAppClientDefaultOutputFolderName(){
		if (ProductManager.shouldUseSingleRootStructure())
			return getString(Keys.APP_CLIENT_CONTENT_FOLDER);
		return getDefaultOutputFolderName();
	}
	
	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	static String getEJBDefaultOutputFolderName(){
		if (ProductManager.shouldUseSingleRootStructure())
			return getString(Keys.EJB_CONTENT_FOLDER);
		return getDefaultOutputFolderName();
	}
	
	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	static String getJCADefaultOutputFolderName(){
		if (ProductManager.shouldUseSingleRootStructure())
			return getString(Keys.JCA_CONTENT_FOLDER);
		return getDefaultOutputFolderName();
	}
	
	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	static String getString(String name) {
		IPreferencesService preferencesService = Platform.getPreferencesService();
		IScopeContext[] lookupOrder = new IScopeContext[]{new InstanceScope(), new DefaultScope()};
		return preferencesService.getString(J2EEPlugin.PLUGIN_ID, name, Defaults.STRING_DEFAULT_DEFAULT, lookupOrder);
		}

	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	static String getDefaultOutputFolderName(){
		if (ProductManager.shouldUseSingleRootStructure())
			return getDefaultJavaSrcFolder();
	    String outputFolder = getProductProperty( "defaultJavaOutputFolder" ); //$NON-NLS-1$
	    if( outputFolder == null ){
	        outputFolder = getProductProperty( "outputFolder" ); //$NON-NLS-1$
	    }
	    
	    if( outputFolder == null )
	   {
	        outputFolder = FacetCorePlugin.DEFUALT_OUTPUT_FOLDER;
	    }
	    return outputFolder;
	}
	
	/**
	 * This method should not intended to be used anywhere outside this class. 
	 * This will be made private once the deprecated J2EEPreferences class is deleted 
	 */
	private static String getProductProperty( final String propName ){
	    String value = null;
	    if( Platform.getProduct() != null ){
	        value = Platform.getProduct().getProperty( propName );
	    }
	    return value;
	}
}