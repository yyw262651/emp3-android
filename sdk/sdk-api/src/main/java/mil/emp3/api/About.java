package mil.emp3.api;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mil.emp3.api.interfaces.IMap;
import mil.emp3.api.interfaces.core.IStorageManager;
import mil.emp3.api.utils.ManagerFactory;
import mil.emp3.mapengine.interfaces.IMapInstance;

/**
 * Retrieves version information from all available BuildConfig classes. BuildConfig class is generated by the build process
 * for each Android Library and Android APK. emp3-android-common has been modified to included the fields listed in buildConfigFields
 * listed below. Map Engines have one additional field which shows the map engine version and Id. IMapInstance is modified
 * to print the engine related version information.
 *
 * emp3-android-common was also updated to set versionCode and versionName which are standard android fields in the application
 * manifest. Since that information will be lost when Application using EMP API is built (manifests are merged) we don't use that
 * information here. That information is visible for the sdk-apk and mapengine-apk when they are installed on the device.
 */
public class About {
    private static String TAG = About.class.getSimpleName();

    static final private IStorageManager storageManager = ManagerFactory.getInstance().getStorageManager();

    /*
        If you add any other android libraries or APKs please update the following array. Not that it doesn't include
        map engines. We will invoke a method on MapInstance to get map engine information.
     */
    private static String[] buildConfigClasses = {
            "mil.emp3.apk.BuildConfig",
            "mil.emp3.view.BuildConfig",
            "mil.emp3.mirrorcache.api.BuildConfig",
            "mil.emp3.mirrorcache.mirrorables.BuildConfig"
    };

    /*
        Following information will be printed per component. If you want to change this then you will need to visit
        emp3-android-common and each of the map engine build scripts.
     */
    private static List<String> buildConfigFields = new ArrayList<>();
    static
    {
        buildConfigFields.add("Implementation_Id");
        buildConfigFields.add("Implementation_Title");
        buildConfigFields.add("Implementation_Vendor");
        buildConfigFields.add("Implementation_Version");
        buildConfigFields.add("Implementation_Map_Engine_Id");
        buildConfigFields.add("Implementation_Sec_Renderer_Id");
        buildConfigFields.add("Implementation_Sec_Cmapi_Id");
    }

    /**
     * For each available BuildConfig object print out the above properties.
     * Get the MapInstance associated with the client map and print its properties.
     * Note that all BuildConfig classes may not be available in each application as it depend on how application has
     * chosen to compile/link these packages (static load, dynamic load etc).
     * @param clientmap
     * @return
     */
    public static String getVersionInformation(IMap clientmap) {
        StringBuilder builder = new StringBuilder();

        for(String bc: buildConfigClasses) {
            builder.append("\n");
            try {
                Class<?> clazz = null;

                /**
                 * Adjusted for PluggableMap and WorldWindPlugin (a plugin for PluggableMap) loading
                 */
                try {
                    clazz = About.class.getClassLoader().loadClass(bc);
                } catch (ClassNotFoundException e) {
                    Log.e(TAG, "getVersionInformation " + bc + " not available via About class loader");
                }

                if(null == clazz) {
                    if(null == clientmap) {
                        throw new ClassNotFoundException("bc");
                    }
                    clazz = clientmap.getClass().getClassLoader().loadClass(bc);
                }

                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                        if(buildConfigFields.contains(field.getName())) {
                            try {
                                builder.append("\n\t\t" + (String)field.get(null));
                            } catch (IllegalAccessException e) {
                                Log.e(TAG, "getVersionInformation " + field.getName(), e);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "getVersionInformation " + bc + " not available");
            }
        }

        IMapInstance mapInstance = storageManager.getMapInstance(clientmap);
        if(null != mapInstance) {
            builder.append("\n");
            mapInstance.getVersionInformation(builder, buildConfigFields);
        }

        builder.append("\n\n");
        Log.d(TAG, builder.toString());

        return builder.toString();
    }
}