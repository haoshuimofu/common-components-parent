package test.json.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.json.MyNameFilter;

import java.util.Date;
import java.util.UUID;

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
    @JSONField(format = "MMM dd, yyyy hh:mm:ss a")
    @JsonIgnore
    private Date now = new Date();

    private boolean isTimeSlice;

    private boolean virtual;

    public Boolean getTimeSlice() {
        return isTimeSlice;
    }

    public void setTimeSlice(Boolean timeSlice) {
        isTimeSlice = timeSlice;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("id=").append(id).append(",")
                .append("content=").append(content).append(",")
                .append("now=").append(now.toString())
                .toString();
    }

    public static void main(String[] args) {
        JSONModel model = new JSONModel();
        model.setId(UUID.randomUUID().toString());
        model.setContent("content");
        model.setTimeSlice(true);
        model.setVirtual(true);
//        model.isVirtual()

        System.out.println(JSON.toJSONString(model, true));
        System.out.println(JSON.toJSONString(model, new MyNameFilter()));

    }

}
