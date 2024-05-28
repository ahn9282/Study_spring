public class LambdaAndStreamStudy1 {
    //함수 하나가 온다
    //
    @FunctionalInterface
    interface Printable2{
        void print(String s);
    }
    @FunctionalInterface
    interface Addable{
        int add(int num1, int num2);
    } 
    public static void main(String[] args) {
        Printable2 pt2 = (String s) -> System.out.println("프린트합니다33" + s);
        pt2.print("sd");

        Addable adder = (int a, int b) ->{ return (a + b);};
        System.out.println(adder.add(1, 4));
    }
}
