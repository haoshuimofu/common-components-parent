package test.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author dewu.de
 * @date 2023-06-29 5:23 下午
 */
public class MurmurHash3Test {

    public static void main(String[] args) {
        int max = 17;

        List<Group> groups = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            int hash = Hashing.murmur3_32().hashBytes(String.valueOf(i).getBytes()).asInt();
            groups.add(new Group(Math.abs(hash) % 4, i));
        }
        System.out.println(JSON.toJSONString(groups.stream().collect(Collectors.groupingBy(Group::getNodeId, Collectors.counting())), true));

        groups.clear();
        for (int i = 1; i <= max; i++) {
            int md5Hash = Hashing.murmur3_32().hashBytes(DigestUtils.md5(i + "")).asInt();
            groups.add(new Group(Math.abs(md5Hash) % 4, i));
        }
        System.out.println(JSON.toJSONString(groups.stream().collect(Collectors.groupingBy(Group::getNodeId, Collectors.counting())), true));




    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Group {
        private int nodeId;
        private int shards;
    }

}
