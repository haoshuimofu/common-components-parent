package com.demo.csv;

/**
 * @author dewu.de
 * @date 2022-12-06 4:01 下午
 */
public class Test {

    public static void main(String[] args) {
       String data =  "\"\"----\"\"";
        System.out.println(data);
        System.out.println(data.replace("\"\"", "\""));

        data = "[{\"\"dest\"\":{\"\"lat\"\":25.079569006338716\",\"\"lng\"\":102.73308204486966}\",\"\"origin\"\":{\"\"lat\"\":25.08601\",\"\"lng\"\":102.73423}}]";
        System.out.println(data);
        System.out.println(data.replace("\"\"", "\""));

    }

}
