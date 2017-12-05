package classes;

/**
 * Created by quentineono on 24/11/2017.
 */

public class Trip {
    private int trip_id;
    private int route_id;
    private int service_id;
    private String headsign;
    private int direction_id;
    private String block_id;
    private boolean wheelchair_accessible;

    public Trip(int trip_id, int route_id, int service_id, String headsign, int direction_id, String block_id, boolean wheelchair_accessible) {
        this.trip_id = trip_id;
        this.route_id = route_id;
        this.service_id = service_id;
        this.headsign = headsign;
        this.direction_id = direction_id;
        this.block_id = block_id;
        this.wheelchair_accessible = wheelchair_accessible;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getHeadsign() {
        return headsign;
    }

    public void setHeadsign(String headsign) {
        this.headsign = headsign;
    }

    public int getDirection_id() {
        return direction_id;
    }

    public void setDirection_id(int direction_id) {
        this.direction_id = direction_id;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public boolean isWheelchair_accessible() {
        return wheelchair_accessible;
    }

    public void setWheelchair_accessible(boolean wheelchair_accessible) {
        this.wheelchair_accessible = wheelchair_accessible;
    }
}
