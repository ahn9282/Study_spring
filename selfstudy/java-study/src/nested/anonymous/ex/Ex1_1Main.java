package nested.anonymous.ex;

import java.util.Random;

public class Ex1_1Main {

    public static void hello(Logic logic) {
        System.out.println("프로그램 시작");

        //코드 조각  싲가
        logic.process();
        //코드 조각 종료

        System.out.println("프로그램 종료");
    }

    public interface Logic {
        void process();
    }

    public static void main(String[] args) {

        hello(() -> {
            int randomValue = new Random().nextInt(6) + 1;
            System.out.println("주사위  = " + randomValue);
        });

        hello(new Logic() {
            @Override
            public void process() {
                for (int i = 0; i < 3; i++) {
                    System.out.println("i = " + i);
                }
            }
        });
    }
}
