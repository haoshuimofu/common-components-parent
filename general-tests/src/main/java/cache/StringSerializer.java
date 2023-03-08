package cache;

import org.caffinitas.ohc.CacheSerializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author dewu.de
 * @date 2023-03-07 11:46 上午
 */
public class StringSerializer implements CacheSerializer<String> {

    @Override
    public void serialize(String value, ByteBuffer buf) {
        buf.put(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String deserialize(ByteBuffer buf) {
        if (buf != null && buf.hasArray()) {
            return new String(buf.array());
        }
        return null;
    }

    @Override
    public int serializedSize(String value) {
        return value.getBytes(StandardCharsets.UTF_8).length;
    }
}
