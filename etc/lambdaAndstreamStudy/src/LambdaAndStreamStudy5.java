import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;

public class LambdaAndStreamStudy5 {

    public static void main(String[] args) {

        // IntBinaryOperator operator = (x,y) -> Math.max(x,y);
        //메서드 참조(Method Reference) == 람다식을 좀 더 줄임
        IntBinaryOperator operator =  Math::max;
        System.out.println(operator.applyAsInt(12,42));


        Consumer<String> consumer= System.out::println;
        consumer.accept("메서드 참조 해봄");


        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, -2, 3, -4, 5));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, -2, 3, -4, 5));

        System.out.println(list1);
        for (Integer i : list1) {
            if(i>0) System.out.print("i = " + i+ "\t");
        }
        System.out.println( );

        list1.stream()
                .filter(e -> e > 0)
                .forEach(e -> System.out.print(e + "\t"));
        System.out.println();
        list2.removeIf(i -> i<0);
        for (Integer i : list2) {
            System.out.print("i = " + i+ "\t");
        }

    }
}
