# 이펙티브 자바 3 들어가며..
### 해당 책은 아주 핵심적인 기본 원칙 몇개에서 파생되었다고 합니다.
- 가장 중요한 두 가지는 명료성(clarity)과 단순성(simplicity)입니다.
- 컴포넌트는 사용자를 놀라게 하는 동작을 해서는 절대 안됩니다.
- 즉 정해진 동작이나 예측할 수 있는 동작만 수행하여야 합니다.

> 해당 책에서 컴포넌트는 개별 메서드부터 복합 패키지까지 재사용 가능한 모든 소프트웨어 요소를 뜻합니다.

[코드 참조](https://git.io/fAm6s)

### 자바의 타입
- 자바의 타입은 **인터페이스**, **클래스**, **배열**, **기본타입** 총 네가지 입니다.
- 애너테이션은 일종의 인터페이스이고 이늄은 일종의 클래스라고 할 수 있습니다.

### 명칭
- 해당 책에서는 상속을 서브클래싱이라고 칭할 수 있습니다.
- 그리고 인터페이스는 상속이 아닌 구현, 혹은 다른 인터페이스를 확장한다고 명시합니다.
- 공개API는 API로 칭하고 프로그래머가 클래스, 인터페이스, 패키지를 통해 접근할 수 있는 모든 클래스, 인터페이스, 생성자, 멤버, 직렬화된 형태를 말합니다.
- 자바 타입의 인터페이스와 헷갈리지 않게 API라고 부릅니다.
- 해당 API를 사용하는 프로그램 작성자를 사용자(USER)라고 하고 해당 API를 사용하는 코드는 그 API의 클라이언트라고 합니다.
- 자바9에서는 모듈시스템의 등장으로 모듈 선언을 한 패키지만 공개API로 만들 수 있습니다.

---

# Chapter 1
- 객체의 생성과 파괴를 다루는 챕터입니다.
- 객체를 만들어야할 때와 만들지 말아야할 때를 구분하고, 올바른 객체 생성 방법과 불필요한 생성을 피하는 방법을 이해하고, 제때 객체가 파괴됨을 보장하고 파괴전 수행해야할 정리 작업을 관리하는 요령을 알아봅니다.


#### [아이템1. 생성자 대신 정적 팩토리 메서드를 고려하라](./chapter1/item1/Item1.md)
#### [아이템2. 생성자에 매개변수가 많다며 빌더를 고려하라](./chapter1/item2/Item2.md)