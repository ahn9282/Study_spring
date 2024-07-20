package nested.anonymous.ex;

public class Ex0Main {
    public static void helloJava() {
        System.out.println("프로그램 시작");
        System.out.println("hello JAva");
        System.out.println("프로그램 종료");

    }

    public static void helloSpring() {
        System.out.println("프로그램 시작");
        System.out.println("hello Spring");
        System.out.println("프로그램 종료");

    }
    private interface Hello{
        void hello(String obj);
    }

    public static void main(String[] args) {
        Hello hello = new Hello() {
            @Override
            public void hello(String obj) {
                System.out.println("프로그램 시작");
                System.out.println("hello " + obj);
                System.out.println("프로그램 종료");
            }

        };
        hello.hello("Java");
        hello.hello("Spring");
    }

}
