package cache.serializer;

import cache.model.Point;
import com.alibaba.fastjson.JSON;
import org.caffinitas.ohc.CacheSerializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author dewu.de
 * @date 2023-03-07 11:46 上午
 */
public class PointSerializer implements CacheSerializer<Point> {

    @Override
    public void serialize(Point value, ByteBuffer buf) {
        buf.put(value.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Point deserialize(ByteBuffer buf) {
        return JSON.parseObject(new String(buf.array()), Point.class);
    }

    @Override
    public int serializedSize(Point value) {
        return value.toString().getBytes(StandardCharsets.UTF_8).length;
    }
}
