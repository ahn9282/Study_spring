import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class LambdaAndStreamStudy6 {

    public static void main(String[] args) {
        Function<String, Integer> stringToInt;
        Function<String, Integer> intToString;
        intToString=(s )-> s.length();
        stringToInt=Integer::parseInt;
        System.out.println(intToString.apply("sdw"));
        System.out.println(stringToInt.apply("10"));

        ArrayList<Number> list = new ArrayList<>();

        Consumer<Collection<Number>> addElements;

        addElements = (s) -> list.addAll(s);
        addElements = list::addAll;
        addElements.accept(List.of(1, 2, 3, 4, 5));
        System.out.println(list);
        
        Function<String,Integer> size;
        size = (String s1) -> s1.length();
        size = String::length;
        System.out.println(size.apply("문자열의 길이"));

        BiFunction<Integer,Integer,Object> constructor;
        constructor = (x, y) -> new Test(x, y);
        constructor = Test::new;

        constructor.apply(4,5);
    }

}
class Test{
    private int x;
    private int y;

    Test(int x, int y){
        this.x = x;
        this.y = y;
        System.out.println(x+" 와 " + y);
    }


}
