import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

//익명 클래스
interface Printable {
    void print();
}

class SPrintable implements Printable {

    @Override
    public void print() {
        System.out.println("안녕~");
    }
}

public class LambdaTest {
    public static void main(String[] args) {
        System.out.println("hello world!!");
        Printable sPrintable = new SPrintable();
        sPrintable.print();

        //=================================
        //익명 클래스
        //익명 클래스 사용 -> 일회용 함수로 재사용성 X
        Printable printable = new Printable() {
            @Override
            public void print() {
                System.out.println("printable 2!!");
            }
        };


        printable.print();

        //람다
        //========================================
        Printable prn = () -> {
            System.out.println("람다!!");
        };
        //new Printable() 을 생략하고 Override 함수를 즉시 선언 없이 초기화만 하여 사용한다.

        prn.print();

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        for (Integer i : list) {
            System.out.println("i = " + i);
        }

         list.stream()
                .filter(e -> e % 2 == 0)
                .forEach(e -> System.out.println(e+" \t"));

        List<Integer> list2 = list.stream()
                .filter(e -> e%2 ==1)
                .collect(Collectors.toUnmodifiableList());
        for (Integer i : list2) {
            System.out.println("i = " + i);
        }

    }


}

