package cache.serializer;

import cache.model.User;
import cache.model.UserOuter;
import com.google.protobuf.InvalidProtocolBufferException;
import org.caffinitas.ohc.CacheSerializer;

import java.nio.ByteBuffer;

/**
 * @author dewu.de
 * @date 2023-03-14 4:00 下午
 */
public class OHCacheProtobufSerializer implements CacheSerializer<User> {


    @Override
    public void serialize(User value, ByteBuffer buf) {
        buf.put(bytes(value));
    }

    @Override
    public User deserialize(ByteBuffer buf) {
        byte[] bytes = new byte[buf.limit()];
        buf.get(bytes);
        try {
            UserOuter.User protoUser = UserOuter.User.parseFrom(bytes);
            return User.builder()
                    .name(protoUser.getName())
                    .age(protoUser.getAge())
                    .address(protoUser.getAddress())
                    .build();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int serializedSize(User value) {
        return bytes(value).length;
    }

    private byte[] bytes(User value) {
        UserOuter.User user = UserOuter.User.newBuilder()
                .setName(value.getName())
                .setAge(value.getAge())
                .setAddress(value.getAddress())
                .build();
        return user.toByteArray();
    }

}
