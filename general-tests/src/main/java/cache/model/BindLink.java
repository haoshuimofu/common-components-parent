package cache.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author dewu.de
 * @date 2023-03-07 2:23 下午
 */
@Getter
@Setter
public class BindLink implements Serializable {

    private String linkId;
    private double pjNodeToStartDis;
    private double pjNodeToEndDis;
    private Point pjNode;

    @Override
    public String toString() {
        return linkId +
                "|" + pjNode +
                "|" + pjNodeToStartDis +
                "|" + pjNodeToEndDis;
    }

}
