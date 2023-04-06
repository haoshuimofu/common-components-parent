package test.json.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author dewu.de
 * @date 2023-04-06 2:50 下午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JSONModel {

    @Expose
    private String id;
    @Expose
    private String content;
    @Expose(serialize = false, deserialize = false)
    private Date now = new Date();

    @Override
    public String toString() {
        return new StringBuffer()
                .append("id=").append(id).append(",")
                .append("content=").append(content).append(",")
                .append("now=").append(now.toString())
                .toString();
    }

}
