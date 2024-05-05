package hello.jdbc.exception.basic;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class CheckedTest {


    @Test
    void checked_cach(){
        Service service = new Service();
        service.callCatch();

    }
    @Test
    void checked_Throw0(){
        Service service = new Service();
        try {
            service.callThrow();
        } catch (MyCheckedException e) {
            log.info("callThrow 예외 캐치 = {}", e.getMessage(), e);
        }

    }

    @Test
    void checked_Throw(){
        Service service = new Service();
        /*
        * 람다 내 메서드를 호출하면 해당 예외가 호출되는지에 대한 여부 검증
        * 예외 발생 시 true
        * */
        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);


    }

    /*
    * Exception을 상속받은 예외는 체크 예외가 된다.
    * 예외를 커스텀마이장하여 생성
    * */
    static class MyCheckedException extends Exception {

        public MyCheckedException(String message) {
            super(message);
        }
    }
/*
* Chekced 예외는
* 예외를 잡아서 처리하거나 던져아한다.
* */
    static class Service{
        Repository repository = new Repository();

        /*
        * 예외를 잡아서 처리하는 코드
        * */
        public void callCatch(){
            try {
                repository.call();
            } catch (MyCheckedException e) {
                //예외 처리 로직
                log.info("에외 처리, message = {}", e.getMessage(),e);
                }
        }
        /*
        * 체크 예외를 밖으로 던지는 코드
        * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws로 예외를 메서드에 필수로 선언해야한다.
        * */
        public void callThrow() throws MyCheckedException {
            repository.call();


        }

    }

    static class Repository{

        public void call() throws MyCheckedException {

                throw new MyCheckedException("ex");

        }

    }
}
