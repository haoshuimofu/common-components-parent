package cache.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author dewu.de
 * @date 2023-03-07 2:22 下午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Point implements Serializable {

    private double lng;
    private double lat;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Point point = (Point)o;
            return Double.compare(point.lng, this.lng) == 0 && Double.compare(point.lat, this.lat) == 0;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 31 * Double.hashCode(this.lng) + Double.hashCode(this.lat);
    }

    public String toString() {
        return this.lng + "," + this.lat;
    }

}
