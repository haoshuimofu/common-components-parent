package leetcode;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test_20230721 {

    public static void main(String[] args) {
        //[[1,3],[3,0,1],[2],[0]]
        List<List<Integer>> rooms = new ArrayList<>();
        rooms.add(Lists.newArrayList(1, 3));
        rooms.add(Lists.newArrayList(3, 0, 1));
        rooms.add(Lists.newArrayList(2));
        rooms.add(Lists.newArrayList(0));
        System.out.println(canVisitAllRooms(rooms));
        System.out.println(canVisitAllRooms1(rooms));
        System.out.println(canVisitAllRooms2(rooms));

    }

    /**
     * 钥匙和房间
     * 低效的暴力解法
     *
     * @param rooms
     * @return
     */
    public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int[] visit = new int[rooms.size()];
        ArrayDeque<List<Integer>> queue = new ArrayDeque<>();
        queue.addLast(rooms.get(0));
        visit[0] = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                List<Integer> openRooms = queue.removeFirst();
                for (Integer room : openRooms) {
                    visit[room] = 1;
                    List<Integer> nextRooms = rooms.get(room).stream()
                            .filter(r -> visit[r] == 0)
                            .collect(Collectors.toList());
                    if (!nextRooms.isEmpty()) {
                        queue.addLast(nextRooms);
                    }
                }
            }
        }
        for (int i : visit) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean canVisitAllRooms1(List<List<Integer>> rooms) {
        int[] visit = new int[rooms.size()];
        visit[0] = 1;
        visitRoom1(rooms.get(0), rooms, visit);
        for (int i : visit) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void visitRoom1(List<Integer> curr, List<List<Integer>> rooms, int[] visit) {
        for (Integer room : curr) {
            visit[room] = 1;
            List<Integer> nextRooms = rooms.get(room).stream()
                    .filter(r -> visit[r] == 0)
                    .collect(Collectors.toList());
            if (!nextRooms.isEmpty()) {
                visitRoom1(nextRooms, rooms, visit);
            }
        }
    }


    public static boolean canVisitAllRooms2(List<List<Integer>> rooms) {
        // 声明数组标记房间被访问过
        int[] visit = new int[rooms.size()];
        // 0=房间是入口，首先访问
        visit[0] = 1;
        for (Integer room : rooms.get(0)) {
            // 去访问房间room
            visitRoom2(rooms, room, visit);
        }
        // 看是否有未被访问的房间
        for (int i : visit) {
            if (i == 0) {
                return false;
            }
        }
        // 没有被访问的房间
        return true;
    }

    public static void visitRoom2(List<List<Integer>> rooms, int room, int[] visit) {
        // 房间room被访问了, 标记一下
        visit[room] = 1;
        // 当前房间有钥匙对应的房间未被访问，继续往下走, 相当于DFS
        for (Integer integer : rooms.get(room)) {
            if (visit[integer] == 0) {
                visitRoom2(rooms, integer, visit);
            }
        }
    }

}
