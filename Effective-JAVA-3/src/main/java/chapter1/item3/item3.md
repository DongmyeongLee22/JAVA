# 아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라
- 오직 하나의 인스턴스만을 생성하는 싱글톤 객체는 클라이언트를 테스트하기가 어려워 질 수 있습니다.
- 싱글턴의 타입을 인터페이스로 정의하고 구현한 것이 아니라면 mock 객체를 만들 수 없기 때문입니다.

### 싱글톤 대표 구현방식
#### 1)

```java
public class SingleTon{
    public static final SingleTon INSTANCE = new SingleTon();
    private SingleTon(){}
}
```
- 첫번째 방법은 생성자를 private으로 설정하고 static final 필드로 유일하게 생성된 객체에 접근하는 방법입니다.
- 이 방법은 해당 객체가 싱글톤임을 명백히 들어내게 됩니다. final로 정의되었고 오직 static 멤버로만 해당 객체에 접근할 수 있기 때문입니다.

#### 2)

```java
public class SingleTon{
    private static final SingleTon INSTANCE = new SingleTon();
    private SingleTon(){}
    
    public static SingleTon getInstance(){
        return INSTANCE;
    }   
}
```   
- 두번째 방법은 public static 메서드를 정의하여 오직 이 메서드로만 객체에 접근할 수 있게 하는 방법입니다.
- 이 방법은 해당 객체를 싱글톤으로 만들고 싶지 않을 때 getInstance API를 바꾸지 않고도 변경할 수 있습니다.

#### 두 가지 방법의 문제점
- 위의 두가지 방법은 해당 객체를 직렬화하고 역직렬화할 때 Serializable만으로는 싱글톤 객체를 보장할 수 없게 됩니다.
- 해당 객체를 직렬화하고 역직렬화할 때 새로운 인스턴스가 생성될 수 있기 때문입니다.