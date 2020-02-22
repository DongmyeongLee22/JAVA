package lombok;

/**
 * Created by Stranger on 2020/02/22
 */

@NoArgsConstructor
public class Member2 {
    private String name;
    private String address;
    private int age;

    @Builder
    public Member2(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }
}
