롬복 동작 원리
 - 컴파일 시점에 [애노테이션 프로세서](https://docs.oracle.com/javase/8/docs/api/javax/annotation/processing/Processor.html)를 사용하여 소스코드의 [AST(abstract syntax tree)](https://javaparser.org/inspecting-an-ast)를 조작한다.
 - AnnotationProcessor는 원래 참조만 가능하다.
 - 원래 공개된 API는 TypeElement, RountEnviornment만 사용할 수 있다. 이둘은 참조만 가능하다
 - 허나 이 클래스들의 하위 타입들은 수정할수 있는 클래스들이있는데 그 하위타입을 타입 캐스팅하여 사용한다. 

Annotation Processor 사용예
- 소스코드 레벨에서 소스코드에 붙어있는 애노테이션 정보를 읽어 컴파일러가 컴파일 하는 중에 새로운 소스코드를 생성하거나 바꿀 수 있다.
- 대표적으로 롬복, AutoService 등이있다.
- @Override도 Annotation Processor 이다.

- 장점
    - 런타임 비용이 제로
- 단점
    - 퍼블릭 API가 아니라 문제가 생길 수 있다.
    
 스프링은 IOC, DI에만 특화된 것이 아니라 AOP, PSA(Portable Service Abstraction) 다양한 기술에 대한 추상적인 레이어를 제공하므로 레이어 밑에 있는 기술을 바꿔도 소스코드를 크게 변경하지 않고 깔끔하고 객체지향적인 코드로 프로그래밍이 가능한 장점이 있다.
