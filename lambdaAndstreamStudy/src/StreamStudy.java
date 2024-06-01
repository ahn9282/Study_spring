import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamStudy {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        ;

        System.out.println(list);

        Stream<Integer> stream = list.stream();
        stream.forEach(n -> System.out.println(n));

        Stream<Integer> stream2 = list.stream();
        stream2.forEach(n -> {
            if (n % 2 == 0) {
                System.out.println(n);
            }
        });

        // System.out.println(stream);

        stream2 = list.stream();
        stream2.filter(i -> i % 2 == 0).forEach(i -> System.out.println(i));

        // IntStream sum = IntStream.range(0, 10);
        int sum = IntStream.range(0, 10).sum();

        // System.out.println(sum.sum());
        System.out.println(sum);

        int sum2 = IntStream.rangeClosed(0, 100).filter(i -> i % 2 == 0).sum();
        System.out.println(sum2);

        System.out.println(IntStream.rangeClosed(0, 100).filter(i -> i % 2 == 1).sum());

        System.out.println(IntStream.rangeClosed(0, 100).filter(i -> i % 2 == 0).map(i -> i * 2).sum());
        IntStream.rangeClosed(0, 100).filter(i -> i % 2 == 0).map(i -> i * 2).forEach(System.out::println);
        IntStream.rangeClosed(0, 100).filter(i -> i % 2 == 0).map(i -> i * 2).forEach(i -> System.out.println(i));

        System.out.println(IntStream.rangeClosed(0, 10).filter(i -> i % 2 == 0).map(i -> i * 2).sum());

        int[] arr = { 1, 3, 5, 7, 9 };

        System.out.println(Arrays.stream(arr).sum());
        System.out.println(Arrays.stream(arr).average());
        System.out.println(Arrays.stream(arr).average().orElse(0.));


        //중간 연산 map
        IntStream.rangeClosed(1, 10).filter(i -> i % 2 == 0).map(i -> i * 2).forEach(System.out::print);

       sum = IntStream.rangeClosed(1,10).filter(i -> i%2 == 0).map(i -> i*2).sum();
        System.out.println("sum = " + sum);


        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {

            list1.add(i);
        }
        for (Integer i : list1) {
            System.out.print("i = " + i + "\t");
        }
        System.out.println();
        list1.stream()
                .forEach(e -> System.out.print("e = " + e + "\t"));
        System.out.println();
        list1.stream()
                .filter(e -> e%2 == 1)
                .forEach(e -> System.out.print("e = " + e + "\t"));
    }
}
