package classes;

/**
 * Created by quentineono on 24/11/2017.
 */

public class Stop {
    private String stop_id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private boolean wheelchair_boarding;

    public Stop(String stop_id, String name, String description, Double latitude, Double longitude, boolean wheelchair_boarding) {
        this.stop_id = stop_id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.wheelchair_boarding = wheelchair_boarding;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isWheelchair_boarding() {
        return wheelchair_boarding;
    }

    public void setWheelchair_boarding(boolean wheelchair_boarding) {
        this.wheelchair_boarding = wheelchair_boarding;
    }
}
