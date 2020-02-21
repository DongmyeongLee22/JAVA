package second;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Stranger on 2020/02/22
 */
public class V2SuperTypeToken {
    static class Sup<T> {
        T value;
    }
    /* V1
    public static void main(String[] args) throws NoSuchFieldException {
        Sup<String> s = new Sup<>();


        // ------------ New Method ------------- 새로운 메서드에서 위를 받아왔다. 해당 타입이 뭔지 알고 싶어 한다.
        Class<?> value = s.getClass().getDeclaredField("value").getType();
        System.out.println(value);
        // String이 나올거 같지만 Object가 나온다.
        // 리플렉션을 통해도 알 수 없다. 왜냐하면 이레이저를 통해 런타임 시 String정보는 사라지고 다 Object가 되기 때문이다.
        // 이를 가져올 수 있는 방법이 있다.
    }
    */

    static class Sub extends Sup<String> {
    }

    static class SubList extends Sup<List<String>> {
    }

    /* V2
    public static void main(String[] args) {
        Sub b = new Sub();
        Type t = b.getClass().getGenericSuperclass();
        ParameterizedType ptype = (ParameterizedType) t;
        System.out.println(ptype.getActualTypeArguments()[0]);
        // 이런 방식을 사용하면 String이 출력된다.
        // 왜냐하면 새로운 타입(Sub)를 정의하면서 슈퍼 클래스가 제네릭스일 때 타입을 지정해서 상속받으면 리플렉션을 통해 런타임시 접근할 수 있도록
        // 바이트 코드에 정보가 남아있다. 이 타입은 ParameterizedType으로 받을 수 있다.

        ParameterizedType listPType = (ParameterizedType) new SubList().getClass().getGenericSuperclass();
        System.out.println(listPType.getActualTypeArguments()[0]);
        // List<String>수준 까지 받아올 수 있다.

        class SubTemp extends Sup<List<String>> {
        }
        // 지역 클래스로도 위와 같이 동작된다.

        Sup b2 = new Sup<String>() {
        };
        // 임의의 익명 클래스를 만들 수 있다.
        Type t2 = b2.getClass().getGenericSuperclass();
        ParameterizedType ptype2 = (ParameterizedType) t2;
        System.out.println(ptype2.getActualTypeArguments()[0]);


        // 익명 클래스는 이렇게 한번에 사용할 수도 있다.
        // 지네릭 타입에 타입 파라미터를 주면서, 타입 파라미터를 런타임시에 가져올 수 있는 코드를 작성할 수 있다.
        Type t3 = (new Sup<String>() {
        }).getClass().getGenericSuperclass();
        ParameterizedType ptype3 = (ParameterizedType) t3;
        System.out.println(ptype3.getActualTypeArguments()[0]);

        // 이제 이를 통해 슈퍼타입토큰을 만들 수 있다.
    }
    */


    static class TypeReference<T> { // same Sup
        Type type;

        public TypeReference() {
            Type stype = getClass().getGenericSuperclass();

            // 타입 정보가 들어있다.
            if (stype instanceof ParameterizedType) {
                this.type = ((ParameterizedType) stype).getActualTypeArguments()[0];
            } else {
                throw new RuntimeException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            // 상속받은거니 superclass로 비교를 해야한다.
            if (o == null || getClass().getSuperclass() != o.getClass().getSuperclass()) return false;
            TypeReference<?> that = (TypeReference<?>) o;
            return type.equals(that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

    public static void main(String[] args) {
        // TypeReference<String> t = new TypeReference<>(); 런타임 예외 발생. <>에 넣은건 런타임에 남는 정보가 아니다!

        System.out.println(new TypeReference<String>() {
        }.type);
        // 이렇게 익명 클래스를 통해 가져올 수 있다.
        // 이를 통해 TypesafeMapp을 만들 수 있다.

        TypesafeMap m = new TypesafeMap();
        m.put(new TypeReference<String>() {
        }, "HELLO");
        System.out.println(m.get(new TypeReference<String>() {
        }));

        m.put(new TypeReference<List<String>>() {
        }, List.of("HELLO", "WORLD"));
        System.out.println(m.get(new TypeReference<List<String>>() {
        }));
    }

    static class TypesafeMap {

        Map<TypeReference<?>, Object> map = new HashMap<>();

        <T> void put(TypeReference<T> tr, T value) {
            map.put(tr, value);
        }

        <T> T get(TypeReference<T> tr) {
            // TypeReference<String>은 받아준다. 하지만 이코드는 List<String>을 해결할 수 없다.
            //return ((Class<T>) tr.type).cast(map.get(tr));
            if (tr.type instanceof Class<?>)
                return ((Class<T>) tr.type).cast(map.get(tr));
            else
                return ((Class<T>) ((ParameterizedType) tr.type).getRawType()).cast(map.get(tr));
            // List<String>을 해결할 수 있다.
        }
    }
}
