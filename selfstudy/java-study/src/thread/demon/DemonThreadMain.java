package thread.demon;

public class DemonThreadMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " : main() start");

        DemonThread demonThread = new DemonThread();
        demonThread.setDaemon(true);//데몬 쓰레드 여부 설정 start 전에 설정한다. 이후 못바꿈
        demonThread.start();
        // 데몬 쓰레드의 경우 Main 쓰레드가 종료되면 java가 종료되면서 같이 종료됨 << run end가 출력이 안되는 이유

        System.out.println(Thread.currentThread().getName() + " : main() end");
    }

    static class DemonThread extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : run()");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " : run() end");
        }
    }
}
