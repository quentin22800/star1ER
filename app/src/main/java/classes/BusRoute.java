package classes;

/**
 * Created by quentineono on 24/11/2017.
 */

public class BusRoute {
    private int route_id;
    private String short_name;
    private String long_name;
    private String description;
    private String type;
    private String color;
    private String text_color;

    public BusRoute(int route_id, String short_name, String long_name, String description, String type, String color, String text_color) {
        this.route_id = route_id;
        this.short_name = short_name;
        this.long_name = long_name;
        this.description = description;
        this.type = type;
        this.color = color;
        this.text_color = text_color;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }
}
