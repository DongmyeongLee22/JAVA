package proxy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class UserServiceTest {


    @Test
    public void test() throws Exception {

        /*  ** 다이나믹 프록시 **
            런타임에 특정 인터페이스들을 구현하는 클래스 또는 인스턴스를 만드는 기술
            자바의 일반적인 프록시 사용법
            - 단 인터페이스가 없으면 사용 불가하다..
            - 클래스만 존재할 때 프록시 만들기 알아보자

         */
        UserService userService = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[]{UserService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                UserService userService = new UserServiceImple();
                if (method.getName().equals("hello")) {
                    System.out.println("this is hello method");
                    Object invoke = method.invoke(userService, args);
                    System.out.println("this is footer");
                    return invoke;
                }
                return method.invoke(userService, args);
            }
        });

        userService.hello();
        userService.bye();
    }

    // cglib 라이브러리를 이용해 클래스만으로 프록시 만들기
    @Test
    void proxyWithoutInterface() throws Exception {

        MethodInterceptor handler = new MethodInterceptor() {
            ItemService itemService = new ItemService();

            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if (method.getName().equals("hello")) {
                    System.out.println("this is hello method");
                    Object invoke = method.invoke(itemService, objects);
                    System.out.println("this is footer");
                    return invoke;
                }

                return method.invoke(itemService, objects);
            }
        };

        ItemService itemService = (ItemService) Enhancer.create(ItemService.class, handler);

        itemService.hello();
        itemService.bye();
    }



    @Test
    void proxyOnlyClassWithByteBuddy() throws Exception{

        Class<? extends ItemService> proxy = new ByteBuddy().subclass(ItemService.class)
                .method(named("all")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    ItemService itemService = new ItemService();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("hello")) {
                            System.out.println("this is hello method");
                            Object invoke = method.invoke(itemService, args);
                            System.out.println("this is footer");
                            return invoke;
                        }
                        return method.invoke(itemService, args);                    }
                }))
                .make().load(ItemService.class.getClassLoader()).getLoaded();
        ItemService itemService = proxy.getConstructor(null).newInstance();

        itemService.hello();
        itemService.bye();
    }



}