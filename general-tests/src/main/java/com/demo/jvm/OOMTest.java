package com.demo.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wude
 * @date 2020/10/8 14:37
 */
public class OOMTest {

    // -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }

    static class OOMObject {

    }





}