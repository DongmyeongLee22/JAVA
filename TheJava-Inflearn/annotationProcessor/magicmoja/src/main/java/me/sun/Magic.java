package me.sun;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// SOURCE가 적당
// CLASS까지 유지할 필요가 없다. 바이트 코드에서 유지하겠다는 뜻인데 바이트 코드가 될떄는 이미 적용되었기 때문에 필요없다.

@Target(ElementType.TYPE) // interface, class , enum에 붙일 수 있다.
@Retention(RetentionPolicy.SOURCE)
public @interface Magic {
}
