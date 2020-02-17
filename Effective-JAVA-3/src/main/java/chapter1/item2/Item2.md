> 이펙티브 자바 3판에서 객체의 생성과 파괴를 다루는 챕터에서 알려주는 방법 중 아이템 2. 생성자에 매개변수가 많다며 빌더를 고려하라에 대해 알아보겠습니다.

---

# 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라

-   아이템 1의 정적 팩토리 메서드와 생성자로 객체를 생성할 때는 매개변수가 많을 때 적절한 대응이 어려워집니다.

```java
public class Member {
    private String name;
    private String email;
    private String address;
    private String number;
    private String hobby;
    private String job;
    private int age;

    // 최소 요구조건
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Member(String name, String email, String address, String number) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.number = number;
    }

    public Member(String name, String email, String address, String number, String hobby, String job, int age) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.number = number;
        this.hobby = hobby;
        this.job = job;
        this.age = age;
    }
}
```

-   여러 개의 변수를 가지는 Member객체를 생성할 때 위와 같이 상황에 맞게 하나하나 생성자를 명시해 주어야 합니다.
-   뿐만 아니라 생성자를 통해 인스턴스를 생성하여도 아래와 같이 해당 값이 어디에 주입되는지 알 수 없습니다.
-   이는 시스템이 복잡해질수록 가독성이 떨어지며 실수할 여지가 많습니다.

```
public class Temp{
    public static void main(String[] args){
      Member member = new Member("temp1", "temp2", "temp3", "temp4");
    }
}
```

-   이러한 문제를 해결하기 위해 setter를 활용할 수 있지만 이 또한 인스턴스가 생성된 후에 값을 변경하는 등의 일관성을 피할 수 없습니다.
-   이럴 때 가장 사용하기 좋은 방법 중 하나가 **빌더 패턴을** 활용하는 것입니다.

#### 빌더 패턴 사용

```java
public class Member {
    private String name;
    private String email;
    private String address;
    private String number;
    private int age;

    // 좀더 간단하게 하기위해 변수 몇개는 제거하였습니다.

    public Member(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.address = builder.address;
        this.number = builder.number;
        this.age = builder.age;
    }

    public static class Builder{
        private String name;
        private String email;

        private int age = 0;
        private String address;
        private String number;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder address(String address){
            this.address = address;
            return this;
        }

        public Builder number(String number){
            this.number = number;
            return this;
        }

        public Builder age(int age){
            this.age = age;
            return this;
        }

        public Member build(){
            if (name == null || email == null){
                throw new IllegalArgumentException("이름 혹은 이메일은 반드시 필요합니다.");
            }
            return new Member(this);
        }
    }
}

public class Temp {
    public static void main(String[] args) {
        Member member = new Member.Builder()
                .name("name")
                .email("email")
                .build();

    }
}
```

-   빌더 패턴을 통해 인스턴스를 생성하면 위와 같이 명시적으로 인스턴스를 생성할 수 있습니다.
-   그리고 필요에 따라 위와 같이 필수로 들어가야 하는 변수들을 빌드 시 검증할 수도 있습니다.

---

### 마무리

-   빌더 패턴은 보통 4개 이상의 매개변수가 존재할 때 값이 치를 합니다.
-   API는 시간이 지날수록 점점 복잡해지고 또한 롬복을 활용하면 매우 간단하게 빌더를 구현할 수 있습니다.
-   그러므로 애초에 빌더 패턴을 통해 객체를 생성하도록 하는 것이 코드 가독성을 높이고, 안전한 객체 생성이 가능하도록 할 수 있습니다.
