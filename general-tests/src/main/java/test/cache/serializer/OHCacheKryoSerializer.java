package test.cache.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.SneakyThrows;
import org.caffinitas.ohc.CacheSerializer;
import test.cache.model.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * @author dewu.de
 * @date 2023-03-07 11:28 上午
 */
public class OHCacheKryoSerializer implements CacheSerializer<User> {

    private final Kryo kryo;

    public OHCacheKryoSerializer() {
        kryo = new Kryo();
        kryo.setRegistrationRequired(true);
        kryo.register(User.class);
        kryo.setReferences(false);
    }


    @SneakyThrows
    @Override
    public void serialize(User user, ByteBuffer byteBuffer) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, user);
        output.flush();
        byte[] bytes = baos.toByteArray();
//        System.out.println("serialize=" + bytes.length);
        byteBuffer.put(bytes);
    }

    @Override
    public User deserialize(ByteBuffer buf) {
        byte[] bytes = new byte[buf.limit()];
        buf.get(bytes);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bais);
        User user = kryo.readObject(input, User.class);
        input.close();
        return user;
    }

    @SneakyThrows
    @Override
    public int serializedSize(User user) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, user);
        output.flush();
        int size = baos.toByteArray().length;
//        System.out.println("serializedSize=" + size);
        output.close();
        baos.close();
        return size;
    }

}
