package cache;

import cache.model.BindLink;
import cache.model.Point;
import cache.serializer.HotPointSerializer;
import cache.serializer.PointSerializer;
import org.caffinitas.ohc.Eviction;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

/**
 * @author dewu.de
 * @date 2023-03-07 2:22 下午
 */
public class KryoTest {

    public static void main(String[] args) {
        OHCache<Point, BindLink> hotPointCache = OHCacheBuilder.<Point, BindLink>newBuilder()
                .keySerializer(new PointSerializer())
                .valueSerializer(new HotPointSerializer())
                //.hashMode(HashAlgorithm.CRC32C)
                //单位是字节，默认2GB空间
                .capacity(2 * 1024 * 1024 * 1024L)
                .timeouts(true)
                .defaultTTLmillis(600 * 1000)
                .eviction(Eviction.LRU)
                .build();

        Point point = new Point(121.333, 32.555);
        BindLink bindLink = hotPointCache.get(point);
        System.out.println("Step01: get=" + bindLink);

        bindLink = new BindLink();
        bindLink.setLinkId("23-23232w");
        bindLink.setPjNode(point);
        bindLink.setPjNodeToStartDis(1200);
        bindLink.setPjNodeToEndDis(800);
        hotPointCache.put(point, bindLink);
        System.out.println("Step02: put, key=" + point + ", value=" + bindLink);


        bindLink = hotPointCache.get(point);
        System.out.println("Step03: get after put, key=" + point + ", value=" + bindLink);

//        String val = "23_5886245424968899218|102.76661560921075,24.954512011346573|43.01807998888527|11.252225644659653";
//        String[] a = val.split("\\|");
//        for (String s : a) {
//            System.out.println(s);
//        }
//        int byteSize = val.getBytes(StandardCharsets.UTF_8).length;
//        System.out.println(val.length());
//        System.out.println(byteSize);


    }


}
