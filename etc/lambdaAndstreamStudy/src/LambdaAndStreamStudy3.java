import java.util.*;
import java.util.function.Predicate;

class SLenComp implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
}

@FunctionalInterface
interface Calculate {
    void cal(int num1, int num2);
}

interface Howlong {
    int len(String s);
}

interface Generator {
    int rand();
}

interface Calculator2<T> {
    T cal(Number a, Number b);
}

public class LambdaAndStreamStudy3 {

    public static void main(String[] args) {
        Predicate<Integer> predicate = (num) -> num < 10;
        System.out.println(predicate.test(3));










        List<String> list = new ArrayList<String>();

        list.add("Sahar");
        list.add("Dawn");
        list.add("Morning");

        Collections.sort(list, new SLenComp());

        for (String s : list) {
            System.out.println(s);
        }
        ;

//      Comparator<Integer> cmp = (Integer n1, Integer n2) {
//
//
//      };

        Calculate add = (n1, n2) -> System.out.println(n1 + n2);
        add.cal(8, 3);
        Calculate sub = (n1, n2) -> System.out.println(n1 - n2);
        sub.cal(8, 3);
        Calculate mul = (n1, n2) -> System.out.println(n1 * n2);
        mul.cal(8, 3);
        Calculate div = (n1, n2) -> System.out.println(n1 / n2);
        div.cal(8, 3);
        Calculate rem = (n1, n2) -> System.out.println(n1 % n2);
        rem.cal(8, 3);

        Howlong howlong = (s) -> s.length();

        System.out.println(howlong.len("Salam Sahripie"));

        Generator gen = () -> {
            Random rand = new Random();
            return rand.nextInt();
        };

        Calculator2 cal2 = (a, b) -> (int)a + (int)b;
        System.out.println(cal2.cal(1, 5));

        Calculator2<Double> cal3 = (a, b) -> (double)a + (double)b;
        System.out.println(cal3.cal(3.4, 6.325));

    }

}
