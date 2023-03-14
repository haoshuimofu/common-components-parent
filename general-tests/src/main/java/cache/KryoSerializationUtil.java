package cache;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author dewu.de
 * @date 2023-03-14 10:39 上午
 */
public class KryoSerializationUtil {
    private static final Kryo kryo = new Kryo();

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, obj);
        output.close();
        return baos.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bais);
        Object obj = kryo.readClassAndObject(input);
        input.close();
        return obj;
    }
}

