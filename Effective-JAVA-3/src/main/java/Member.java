import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/25
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member implements Serializable {
    private String name;
    private String email;
    private int age;
}
