package mil.emp3.mapengine.events;

import mil.emp3.api.events.Event;
import mil.emp3.api.interfaces.ICamera;
import mil.emp3.api.enums.MapEventEnum;
import mil.emp3.api.enums.MapViewEventEnum;
import mil.emp3.api.interfaces.ILookAt;
import mil.emp3.mapengine.interfaces.IMapInstance;
import org.cmapi.primitives.IGeoBounds;

/**
 * This class implements the Map Instance View Change Event. It must be generated by all map engine
 * instances when the viewing area of the map changes.
 * All events of this type are handled by the EMP core code.
 */
public class MapInstanceViewChangeEvent extends Event<MapViewEventEnum, IMapInstance> {
    private final ICamera oCamera;
    private final ILookAt oLookAt;
    private final IGeoBounds oBounds;
    private final int iViewWidth;
    private final int iViewHeight;

    /**
     * This constructor must be called by the map engines to create a MapInstanceViewChangeEvent event
     * @param oMapInstance The actual map instance. The this property of the object that implements the IMapInstance interface.
     * @param viewEventEnum The view event generated. See {@link MapViewEventEnum}
     * @param camera The new camera position of the view of the map. See {@link ICamera}.
     * @param bounds The new view bounds.
     */
    public MapInstanceViewChangeEvent(IMapInstance oMapInstance,
            MapViewEventEnum viewEventEnum,
            ICamera camera,
            ILookAt lookAt,
            IGeoBounds bounds,
            int iWidth,
            int iHeight) {
        super(viewEventEnum, oMapInstance);
        this.oCamera = camera;
        this.oBounds = bounds;
        this.iViewHeight = iHeight;
        this.iViewWidth = iWidth;
        this.oLookAt = lookAt;
    }

    /**
     * Returns the updated Camera object after the event.
     * @return @See ICamera
     */
    public ICamera getCamera() {
        return this.oCamera;
    }

    /**
     * Returns the updated lookAt object after the event.
     * @return @See ILookAt
     */
    public ILookAt getLookAt() { return this.oLookAt; }

    /**
     * Returns the map bounds after the event, it could be null based camera settings.
     * @return bounds
     */

    public IGeoBounds getBounds() {
        return this.oBounds;
    }

    /**
     * Returns width of the map in pixels
     * @return width
     */
    public int getMapViewWidth() {
        return this.iViewWidth;
    }

    /**
     * Returns height of the map in pixels
     * @return height
     */

    public int getMapViewHeight() {
        return this.iViewHeight;
    }
}