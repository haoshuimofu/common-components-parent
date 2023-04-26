package test.json.model;

import com.alibaba.fastjson.JSON;
import test.json.MyNameFilter;

import java.util.UUID;

/**
 * @author dewu.de
 * @date 2023-04-25 10:56 上午
 */
public class NameModel {

    private String id;
    private String content;
    private boolean isTimeSlice;
    private boolean virtual;

    public static void main(String[] args) {
        NameModel model = new NameModel();

        model.setId(UUID.randomUUID().toString());
        model.setContent("content");
        model.setTimeSlice(true);
        model.setVirtual(true);

        System.out.println(JSON.toJSONString(model, new MyNameFilter()));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getTimeSlice() {
        return isTimeSlice;
    }

    public void setTimeSlice(Boolean timeSlice) {
        isTimeSlice = timeSlice;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }
}
