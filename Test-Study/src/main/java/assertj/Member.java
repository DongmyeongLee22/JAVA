package assertj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Member {
    private String name;
    private int age;
    private MemberType type;
}

enum MemberType{
    ADMIN, USER
}
