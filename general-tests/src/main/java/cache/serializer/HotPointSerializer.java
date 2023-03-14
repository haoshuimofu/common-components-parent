package cache.serializer;

import cache.model.BindLink;
import cache.model.Point;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.SneakyThrows;
import org.caffinitas.ohc.CacheSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author dewu.de
 * @date 2023-03-07 11:28 上午
 */
public class HotPointSerializer implements CacheSerializer<BindLink> {

    private final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>();


    private final Kryo kryo;

    public HotPointSerializer() {
        kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.register(BindLink.class);
        kryo.register(Point.class);
        kryo.setReferences(true);
    }


    @SneakyThrows
    @Override
    public void serialize(BindLink bindLink, ByteBuffer byteBuffer) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Output output = new Output(baos);
//        kryo.writeObject(output, bindLink);
//        output.flush();
//
//        byte[] bytes = baos.toByteArray();
//        byteBuffer.put(bytes);
//        output.close();
//        baos.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, bindLink);
        output.flush();
        byte[] bytes = baos.toByteArray();
        System.out.println("serialize=" + bytes.length);
        byteBuffer.put(bytes);
    }

    @Override
    public BindLink deserialize(ByteBuffer byteBuffer) {
//        System.out.println("deserialize=" + byteBuffer.array().length);
//        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer.array());
//        Input input = new Input(bais);
//        BindLink bindLink = kryo.readObjectOrNull(input, BindLink.class);
//        input.close();
//        return bindLink;

        byte[] bytes = new byte[45];
        // 读取字节数组
        byteBuffer.get(bytes);
//        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bytes);
        BindLink bindLink = kryo.readObjectOrNull(input, BindLink.class);
        input.close();
        return bindLink;



    }

    @SneakyThrows
    @Override
    public int serializedSize(BindLink bindLink) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, bindLink);
        output.flush();
        int size = baos.toByteArray().length;
        System.out.println("serializedSize=" + size);
//        System.out.println("23-23232w|121.333,32.555|1200.0|800.0".getBytes(StandardCharsets.UTF_8).length);
        output.close();
        baos.close();
        return size;
    }

}
