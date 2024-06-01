import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamStud2 {
    public static void main(String[] args) {

        //병렬 스트림 parallelStream
        //속도 향상을 위해 쓰인다.



        //리덕션 .reduce(); 스트림의 최종연산으로 쓰인다. 데이터를 축소하는 연산이다.
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum1 = numbers.reduce(0, (total, n) -> total + n);
        System.out.println("sum1 = " + sum1);
        //0에서 시작하여 total이라는 값에 스트림 인자 n을 계속적으로 더하고 total을 반환
        //(k, n ) 에서 반환할 값 k  스트림 인자 값 n 을 활용하여 반환 값을 하나로 줄인다 그래서 reduce
        //연산을 Custom한다.



        List<Grade> gradeList = new ArrayList<>();
        gradeList.add(new Grade("홍길동", 90, 75, 100));
        gradeList.add(new Grade("홍길순", 90, 75, 300));
        gradeList.add(new Grade("홍순자", 90, 75, 400));
        gradeList.add(new Grade("허길동", 70, 75, 400));


        gradeList.stream().forEach(grade -> System.out.print(grade.getName() + "\t"));
        System.out.println();
        gradeList.stream().forEach(grade -> System.out.print(grade.getAvg() + "\t"));
        System.out.println();
        gradeList.stream().forEach(grade -> System.out.print(grade.getTotal() + "\t"));
        System.out.println();
        gradeList.stream()
                .filter(e -> !e.getName().contains("허"))
                .forEach(grade -> System.out.print(grade.getTotal() + "\t"));

        System.out.println();    gradeList.stream()
                .filter(e -> e.getKor() >= 90)
                .forEach(grade -> System.out.print(grade.getName() + " : " + String.format("%.2f",grade.getAvg()) + "\t"));

        System.out.println();

    }
}