package stduy.desingpattern.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        log.info("start");
        String result1 = target.callA();
        log.info("result = {}", result1);
    }

    @Test
    void reflection1() throws Exception  {
        Class<?> classHello = Class.forName("stduy.desingpattern.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA");
        String result1 = (String) methodCallA.invoke(target);
        log.info("result1 = {}", result1);
        assertThat(result1).isEqualTo("A");


        Method methodCallB = classHello.getMethod("callB");
        String result2 = (String) methodCallB.invoke(target);
        log.info("result2 = {}", result2);
        assertThat(result2).isEqualTo("B");
    }

    @Test
    void reflection2() throws Exception {
        Class<?> classHello = Class.forName("stduy.desingpattern.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);


        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

        static class Hello {
            public String callA() {
                log.info("call A");
                return "A";
            }

            public String callB() {
                log.info("call B");
                return "B";
            }
        }
    }
