package cache;

import cache.model.BindLink;
import cache.model.Point;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.SneakyThrows;
import org.caffinitas.ohc.CacheSerializer;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * @author dewu.de
 * @date 2023-03-07 11:28 上午
 */
public class HotPointSerializer implements CacheSerializer<BindLink> {


    private Kryo kryo;

    public HotPointSerializer() {
        kryo = new Kryo();
        kryo.register(BindLink.class);
        kryo.register(Point.class);
    }


    @SneakyThrows
    @Override
    public void serialize(BindLink bindLink, ByteBuffer byteBuffer) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, bindLink);
        output.flush();

        byte[] bytes = baos.toByteArray();
        byteBuffer.put(bytes);
        output.close();
        baos.close();
    }

    @Override
    public BindLink deserialize(ByteBuffer byteBuffer) {

        System.out.println(byteBuffer.position());
        if (byteBuffer != null) {

//            Input input = new Input(new ByteArrayInputStream(bytes));
            Input input = new Input(byteBuffer.array());
            return kryo.readObject(input, BindLink.class);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public int serializedSize(BindLink bindLink) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, bindLink);
        output.flush();
        output.close();
        baos.close();

        byte[] bytes = baos.toByteArray();
        return bytes.length;
    }

}
