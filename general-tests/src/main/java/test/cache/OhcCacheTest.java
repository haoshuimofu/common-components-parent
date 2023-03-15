package test.cache;

import test.cache.model.User;
import test.cache.serializer.OHCacheProtobufSerializer;
import test.cache.serializer.OhcStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.caffinitas.ohc.Eviction;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

@Slf4j
public class OhcCacheTest {

    private static OHCache<String, String> ohCache = OHCacheBuilder.<String, String>newBuilder()
            .keySerializer(new OhcStringSerializer())
            .valueSerializer(new OhcStringSerializer())
            //.hashMode(HashAlgorithm.CRC32C)
            //单位是字节，默认2GB空间
            .capacity(2 * 1024 * 1024 * 1024L)
            .timeouts(true)
            .defaultTTLmillis(600 * 1000)
            .eviction(Eviction.LRU)
            .build();

    private static OHCache<String, User> userOHCache = OHCacheBuilder.<String, User>newBuilder()
            .keySerializer(new OhcStringSerializer())
            .valueSerializer(new OHCacheProtobufSerializer())
            //.hashMode(HashAlgorithm.CRC32C)
            //单位是字节，默认2GB空间
            .capacity(2 * 1024 * 1024 * 1024L)
            .timeouts(true)
            .defaultTTLmillis(600 * 1000)
            .eviction(Eviction.LRU)
            .build();

    public static void main(String[] args) throws Exception {
        long start;

//        写入耗时:701
//        读取命中耗时:1111
//        读取未命中耗时:1303
//        总耗时:1303

//        写入耗时:23078
//        读取命中耗时:23304
//        读取未命中耗时:23481
//        总耗时:23481

//        写入耗时:713
//        读取命中耗时:1256
//        读取未命中耗时:1470
//        总耗时:1470

//        写入耗时:542
//        读取命中耗时:773
//        读取未命中耗时:949
//        总耗时:949
//

//        while (true) {
//            start = System.currentTimeMillis();
//            //只测试写入
//            for (int j = 0; j < 500000; j++) {
//                ohCache.putIfAbsent("" + j, new User(j + "", j, j + "").format());
//            }
//            System.out.println("写入耗时:" + (System.currentTimeMillis() - start));
//            //测试读写
//            for (int j = 0; j < 500000; j++) {
//                ohCache.get("" + j);
//            }
//            System.out.println("读取命中耗时:" + (System.currentTimeMillis() - start));
//            //读写+读为命中
//            for (int j = 0; j < 500000; j++) {
//                ohCache.get("" + j + "noHits");
//            }
//            System.out.println("读取未命中耗时:" + (System.currentTimeMillis() - start));
//            System.out.println("总耗时:" + (System.currentTimeMillis() - start));
//        }


//        写入耗时:945
//        读取命中耗时:1351
//        读取未命中耗时:1647
//        总耗时:1647

//        写入耗时:23610
//        读取命中耗时:24055
//        读取未命中耗时:24284
//        总耗时:24284


//        写入耗时:1014
//        读取命中耗时:1442
//        读取未命中耗时:1768
//        总耗时:1768

//        写入耗时:456
//        读取命中耗时:722
//        读取未命中耗时:885
//        总耗时:886

        while (true) {
            start = System.currentTimeMillis();
            //只测试写入
            for (int j = 0; j < 500000; j++) {
                userOHCache.putIfAbsent("" + j, new User("" + j, j, "" + j));
            }
            System.out.println("写入耗时:" + (System.currentTimeMillis() - start));
            //测试读写
            for (int j = 0; j < 500000; j++) {
                userOHCache.get("" + j);
            }
            System.out.println("读取命中耗时:" + (System.currentTimeMillis() - start));
            //读写+读为命中
            for (int j = 0; j < 500000; j++) {
                userOHCache.get("" + j + "noHits");
            }
            System.out.println("读取未命中耗时:" + (System.currentTimeMillis() - start));
            System.out.println("总耗时:" + (System.currentTimeMillis() - start));
        }
    }

}