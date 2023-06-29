package test.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dewu.de
 * @date 2023-06-29 5:23 下午
 */
public class MurmurHash3Test {

    public static void main(String[] args) {
        int max = 17;
        Map<Integer, Integer> group = new HashMap<>();
        Map<Integer, Integer> hashGroup = new HashMap<>();

        for (int i = 1; i <= 17; i++) {
            int hash = Hashing.murmur3_32().hashBytes(String.valueOf(i).getBytes()).asInt();
            int md5Hash = Hashing.murmur3_32().hashBytes(DigestUtils.md5(i + "")).asInt();
            System.out.println(i + "=" + hash + ", shardid=" + Math.abs(hash) % 4 + " - " + Math.abs(md5Hash) % 4);
            group.put(i,  Math.abs(hash) % 4);
            hashGroup.put(i,  Math.abs(md5Hash) % 4);
        }

        System.out.println(JSON.toJSONString(group));
        System.out.println(JSON.toJSONString(hashGroup));
    }

}
