import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


class A{}

public class LambdaAndStreamStudy4 {
    public static void main(String[] args) {
    Supplier<String> sup = () -> {return "아침 인사";};

    //Supplier
        System.out.println(sup.get());
        Supplier<A> supplier2 = () -> new A();

        Supplier<Integer> spr = () -> {
            Random random = new Random();
            return random.nextInt(50);
        };

        List<Integer> list = makeIntList(spr, 5);
        System.out.println("list = " + list);
        List<Integer> list2 = makeIntList(spr, 10);
        System.out.println("list2 = " + list2);

        //Consumer
    //Consumer<T>
    //메서드 void accept(T t) : 너가 알아서 객체 받아서 Custom 해라
        //ObjConsumer<T>  -> accpet(T t, (Integer, Double 등등))
        //BiConsumer<T t, U u>
        Consumer<String> consumer = (String s) -> {
            System.out.println("consumer = " + s);
        };
        consumer.accept("컨슈머");
        consumer = (String s) -> {
            System.out.print("원본  :  " + s+ "\t");
            System.out.print("대문자 : " + s.toUpperCase()+ "\t");
            System.out.println("소문자 : " + s.toLowerCase());
        };
        consumer.accept("AbCdEfGhIjK");


        //Function<T,R> : 익명으로 출력반환 가공(Custom)해서 반환하도록 할 수 있다.
        // T타입을 받아 R타입으로 반환
        //R apply(T t);  매개변수와는 다른 타입으로 반환 가능 Ex) 숫자를 받아 list<Integer>로 반환
        //BiFunction<T, U, R >  -> R apply(T t, U u)
        Function<String, Integer> f = s -> s.length();//s -> String, R-> Integer (숫자 : 문자열 길이)
        System.out.print(f.apply("Robot") + "\t");
        System.out.println(f.apply("System"));

    }



    //Supplier<T> : 인자를 받지 않고 제네릭에 속한 타입의 값을 반환
    //메서드 : T get();
    public static List<Integer> makeIntList(Supplier<Integer> s, int n){
           // s = () -> {return n;};
        List<Integer> list = new ArrayList<>();
        for(int i = 0 ; i < n; i++){
            list.add(s.get());
        }
        return list;
    }

    //Predicate <T> 안에 클래스들을 토대로 조건에 부합 여부를 반환 해준다.
    //함수형 메서드 boolean test(T t);
    //BiPredicate<T t, U u> 는 매개 변수를 2개 받을 수 있다.
    public static int sum(Predicate<Integer> p, List<Integer> list) {

        int s = 0;
        for (Integer n : list) {
            if (p.test(n))    s +=n;
        }
            return s;
    }

}
