import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//람다가 필요한 상황
//인스턴스보다 기능하나가 필요한 상황
class SlenComp implements Comparator<String>{
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
}
public class LambdaAndStreamStudy2 {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Robot");
        list.add("Box");
        list.add("Lambda");
        //자바는 함주자체를 전달 못함
        Collections.sort(list, new SlenComp());//옛날 방식
        
        //함수포인터 -> C언어
        //인스턴스보다 기능 하나가 필요한 상황 = 자바는 함수 자체을 전달을 못했음
        Collections.sort(list,(String s1, String s2) -> {
            return s1.length() - s2.length();
        });

        list.stream()
                .forEach(e -> {
                    if (e.equals("Robot")) {
                        System.out.println("같음");
                    } else {
                        System.out.println("다름");
                    }
                });

        for (String s : list) {
            System.out.println("s = " + s);
        }
    }
}
