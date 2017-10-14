package mil.emp3.api.utils.kmz;

import java.io.File;
import mil.emp3.api.interfaces.IEmpExportToTypeCallBack;
import mil.emp3.api.interfaces.IFeature;
import mil.emp3.api.interfaces.IMap;
import mil.emp3.api.interfaces.IOverlay;

/**
 * Exports Map, Overlay, or Feature to a KMZ file.
 * @author Jenifer Cochran
 */

public final class EmpKMZExporter
{
    /**
     * Private to prevent from calling this class
     * in a non-static fashion
     */
    private EmpKMZExporter()
    {

    }

    /**
     * This exports the map's overlays, and features displayed on the map
     * to a KMZ file given the directory where the KMZ file should be stored as
     * well as the desired name of the KMZ file.
     * @param map the map that contains the overlays and feature data to be exported.
     * @param extendedData whether or not extended data should be exported.
     * @param callback the callback which will provide the KMZ file created when the thread is finished or report a failure
     * @param temporaryDirectoryLocation the temporary directory location( it is highly recommended to use
     *                                   Context.getExternalFilesDir() as the temporary location).
     *                                   The contents of this directory will be removed after export.
     * @param kmzFileName  the name of the exported KMZ File Name (i.e. kmz_file_export.kmz or kmz_file_export).
     */
    public static void exportToKMZ(final IMap                           map,
                                   final boolean                        extendedData,
                                   final IEmpExportToTypeCallBack<File> callback,
                                   final String                         temporaryDirectoryLocation,
                                   final String                         kmzFileName)
    {

        if ((null == map)                        ||
            (null == temporaryDirectoryLocation) ||
            (null == callback)                   ||
            (null == kmzFileName))
        {
            throw new IllegalArgumentException("Parameters can't be null.");
        }

        if(temporaryDirectoryLocation.isEmpty())
        {
            throw new IllegalArgumentException("The temporaryDirectoryLocation cannot be an empty string.");
        }

        if(kmzFileName.isEmpty())
        {
            throw new IllegalArgumentException("The kmzFileName cannot be an empty string.");
        }

        final KMZExportThread kmzExportThread = new KMZExportThread(map,
                                                                    extendedData,
                                                                    callback,
                                                                    temporaryDirectoryLocation,
                                                                    kmzFileName);
        kmzExportThread.run();
    }

    /**
     * This exports the overlay specified that is displayed on the map
     * to a KMZ file given the directory where the KMZ file should be stored as
     * well as the desired name of the KMZ file.
     * @param map the map that contains the overlay to be exported.
     * @param overlay the overlay to be exported.
     * @param extendedData whether or not extended data should be exported
     * @param callback the callback which will provide the KMZ file created when the thread is finished or report a failure
     * @param temporaryDirectoryLocation the temporary directory location( it is highly recommended to use
     *                                   Context.getExternalFilesDir() as the temporary location).
     *                                   The contents of this directory will be removed after export.
     * @param kmzFileName the name of the exported KMZ File Name (i.e. kmz_file_export.kmz or kmz_file_export).
     */
    public static void exportToKMZ(final IMap                           map,
                                   final IOverlay                       overlay,
                                   final boolean                        extendedData,
                                   final IEmpExportToTypeCallBack<File> callback,
                                   final String                         temporaryDirectoryLocation,
                                   final String                         kmzFileName)
    {


        if ((null == map)                        ||
            (null == overlay)                    ||
            (null == temporaryDirectoryLocation) ||
            (null == callback)                   ||
            (null == kmzFileName))
        {
            throw new IllegalArgumentException("Parameters can't be null.");
        }

        if(temporaryDirectoryLocation.isEmpty())
        {
            throw new IllegalArgumentException("The temporaryDirectoryLocation cannot be an empty string.");
        }

        if(kmzFileName.isEmpty())
        {
            throw new IllegalArgumentException("The kmzFileName cannot be an empty string.");
        }
   
        final KMZExportThread kmzExportThread = new KMZExportThread(map,
                                                                    overlay,
                                                                    extendedData,
                                                                    callback,
                                                                    temporaryDirectoryLocation,
                                                                    kmzFileName);
        kmzExportThread.run();
    }

    /**
     * This exports the feature specified that is displayed on the map
     * to a KMZ file given the directory where the KMZ file should be stored as
     * well as the desired name of the KMZ file.
     * @param map the map that contains the overlay to be exported.
     * @param feature the feature to be exported.
     * @param extendedData whether or not extended data should be exported
     * @param callback the callback which will provide the KMZ file created when the thread is finished or report a failure
     * @param temporaryDirectoryLocation the temporary directory location( it is highly recommended to use
     *                                   Context.getExternalFilesDir() as the temporary location).
     *                                   The contents of this directory will be removed after export.
     * @param kmzFileName the name of the exported KMZ File Name (i.e. kmz_file_export.kmz or kmz_file_export).
     */
    public static void exportToKMZ(final IMap                           map,
                                   final IFeature                       feature,
                                   final boolean                        extendedData,
                                   final IEmpExportToTypeCallBack<File> callback,
                                   final String                         temporaryDirectoryLocation,
                                   final String                         kmzFileName)
    {

        if ((null == map)                        ||
            (null == feature)                    ||
            (null == temporaryDirectoryLocation) ||
            (null == callback)                   ||
            (null == kmzFileName))
        {
            throw new IllegalArgumentException("Parameters can't be null.");
        }

        if(temporaryDirectoryLocation.isEmpty())
        {
            throw new IllegalArgumentException("The temporaryDirectoryLocation cannot be an empty string.");
        }

        if(kmzFileName.isEmpty())
        {
            throw new IllegalArgumentException("The kmzFileName cannot be an empty string.");
        }

        final KMZExportThread kmzExportThread = new KMZExportThread(map,
                                                                    feature,
                                                                    extendedData,
                                                                    callback,
                                                                    temporaryDirectoryLocation,
                                                                    kmzFileName);
        kmzExportThread.run();
    }
}
