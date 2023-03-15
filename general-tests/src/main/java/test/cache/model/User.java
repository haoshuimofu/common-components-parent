package test.cache.model;

import lombok.*;

/**
 * string name =1;
 * int32 age =2;
 * string address =3;
 *
 * @author dewu.de
 * @date 2023-03-14 5:00 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

    private String name;
    private int age;
    private String address;

    public String format() {
        return name + "|" + age + "|" + address;
    }

    public User parse(String value) {
        String[] va = value.split("\\|");
        return new User(va[0], Integer.parseInt(va[1]), va[2]);
    }

}
