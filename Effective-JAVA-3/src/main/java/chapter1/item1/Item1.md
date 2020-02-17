> 객체의 생성과 파괴를 다루는 챕터에서 알려주는 방법 중 아이템 1. 생성자 대신 정적 팩토리 메서드를 고려하라에 대해 알아보겠습니다.

---

# 아이템 1. 생성자 대신 정적 팩토리 메서드를 고려하라

-   클래스는 클라이언트에게 public 생성자 대신 static 팩토리 메서드를 제공할 수 있습니다.
-   여기서 말하는 팩토리 메서드는 디자인 패턴에서 나오는 팩토리 메서드 패턴과 다른 의미입니다.

### 장점

#### 1) 이름을 가질 수 있다.

```java
public class Book {
    private String name;

    private Book(String name) {
        this.name = name;
    }

    public static Book createBook(String name){
        return new Book(name);
    }
}
```

-   위와 같이 생성자는 private으로 설정하고 static을 통해 객체를 생성할 수 있도록 하면 createBook이라는 이름을 가지게 되어 명시적인 선언이 가능합니다.

```
public class Temp{
    public static void main(String[] args) {
        BigInteger bigInteger = BigInteger.probablePrime(1, new Random());
    }
}
```

-   BigInteger은 해당 방법을 통해 probablePrime로 가능한 소수를 생성해 줍니다.
-   만약 new BigInteger(int i, Random r)로 생성하였다면 클라이언트에서는 가능한 소수를 생성하는지 명시적으로 이해하기가 힘들게 됩니다.

#### 2) 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.

-   보통 간단한 싱글톤 객체를 만들 때 이 방법을 사용하기도 합니다.
-   혹은 값은 캐싱하여 재활용할 수 있습니다.
-   Boolean.valueOf(boolean)는 해당 방법을 이용하여 객체를 생성하지 않고 캐싱한 값을 돌려줍니다.

```
public class Book {

    private static Book book = new Book();

    private Book() {
    }

    public static Book getBookInstance(){
        return book;
    }
}
```

-   위와 같이 정적 메서드를 통해 객체를 반환하게 하면 간단하게 싱글톤 객체를 만들 수 있습니다.

#### 3) 반환 타입의 하위 타입 객체를 반환할 수 있다.

-   이러한 방법을 사용하면 구현 클래스를 공개하지 않고 원하는 객체를 반환하게 할 수 있어 API를 작게 유지할 수 있습니다.
-   자바 8부터는 인터페이스에서도 정적 메서드 선언이 가능해졌으므로 인터페이스에서도 간단하게 이 방법을 구현할 수 있습니다.

```java
public interface Item {

    void print();

    static Item createItem(String name){
        return new Book(name);
    }
}

class Book implements Item
{
    String name;

    Book(String name){
        this.name = name;
    }

    @Override
    public void print() {
        System.out.println(name);
    }
}
```

-   Item Interface와 이를 구현한 Book이 있을 때 Book을 디폴트 접근자로 지정하면 다른 패키지에서는 생성이 불가능할 것입니다.
-   외부에서는 아래와 같이 Item.createItem으로 해당 Item을 사용할 수 있게 됩니다.
-   이로 인해 Book은 캡슐화되어 API를 작게 유지할 수 있습니다.

```java
public class Main {
    public static void main(String[] args) {
        Item item = Item.createItem("Dexter");

        // Item item1 = new Book("Dexter");  생성 불가
    }
}
```

#### 4) 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.

-   매개변수에 따라 원하는 클래스 객체를 반환할 수 있으므로 상황에 따라 효율적인 객체를 만들 수 있습니다.

```java
public interface Number {

    static Number createNumber(int num){
        if (num > -1000 && num < 1000){
            return new SmallNum();
        }
        return new BigNum();
    }
}

class SmallNum implements Number{

}

class BigNum implements Number{

}
```

-   Number란 인터페이스가 있을 때 매개변수의 값이 작으면 SmallNum을 반환해주고 크면 BigNum을 반환해주어 효율적으로 메모리를 사용하게 할 수도 있습니다.

#### 5) 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

-   이러한 유연함은 service provider framework를 만들 때 매우 유용하게 사용됩니다.
-   JDBC가 대표적인 service provider framework입니다.

---

### 단점

#### 1) 상속을 하려면 public, protected 생성자가 필요한데 정적 팩토리 메서드만 제공 시 하위 클래스를 만들 수 없게 됩니다.

-   이러한 단점은 아이템 17(불변 타입), 아이템 18(상속보다는 컴포지션 사용)의 측면에서는 오히려 장점이 될 수도 있습니다.

#### 2) 정적 팩토리 메서드는 프로그래머가 찾기 어렵다.

-   생성자와 같이 API문서에 명확히 드러내지 않기 때문에 API문서를 잘 작성해야 하며 널리 사용되는 메서드 명명법을 지켜 이러한 단점을 극복할 수 있습니다.
-   대표적인 메서드 명명 방식에 대해 알아보겠습니다.

#### 메서드 명명 방식

1) from

-   매개 변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 메서드에 주로 사용합니다.
-   Date date = Date.from(instnace);

2) of

-   여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드에서 주로 사용합니다.
-   List list = List.of(1, 2, 3);

3) valueOf

-   from과 of와 비슷한 의미를 갖습니다.
-   Integer i = Integer.valueOf(10);

4) instance or getInstance

-   해당 요청에 맞는 인스턴스를 반환하는 메서드로 주로 사용합니다.

5) create or newInstance

-   instance, getInstance와 같은 의미이나 매번 새로운 인스턴스 생성을 보장할 때 주로 사용하는 메서드입니다.
-   Object newArray = Array.newInstance(Integer.class, 10);

6) getType

-   getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메서드를 정의할 때 사용합니다.
-   Type은 팩토리 메서드가 반환할 객체의 타입이 됩니다.
-   FileStore fileStore = Files.getFileStore(path);

7) newType

-   newInstnace와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메서드를 정의할 때 사용합니다.
-   BufferedReader bufferedReader = Files.newBufferedReader(path);

8) type

-   getType, newType을 간결하게 사용할 때 사용할 수 있습니다.

---

### 마무리

-   정적 팩토리 메서드와 Public 생성자는 서로서로 장단점이 존재하지만 일반적으로 정적 팩토리를 사용하는 게 장점이 더 많습니다.
-   그러므로 필요에 따라 Public 생성자보다 정적 팩토리 메서드를 사용해보는 게 좋을 수 있습니다.
