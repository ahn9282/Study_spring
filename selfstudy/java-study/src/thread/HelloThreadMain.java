package thread;

public class HelloThreadMain {
    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " : main() start");

        HelloThread helloThread = new HelloThread();
        System.out.println(Thread.currentThread().getName() + " : start() 호출 전");
        helloThread.start();//start를 통해서만 stack 메모리 Area에 할당됨 다른 쓰레드의 메서드로 할당
        //helloThread.run(); // 이 경우에는 main 클래스에서  run 메서드 호출이기에 main 쓰레드에서 helloThread 인스턴스의 메서드 호출임 그냥
        System.out.println(Thread.currentThread().getName() + " : start() 호출 후");

        System.out.println(thread.getName() + " : main() end");


    }
}
