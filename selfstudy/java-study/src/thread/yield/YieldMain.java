package thread.yield;

import thread.runnable.HelloRunnable;

public class YieldMain {
    static final int THREAD_COUNT = 1000;
    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                // 1. empty -> 스레드 하나당 묶여서(연달아서) 하는 경우가 보임 : 순서를 어느정도 지킴
                // 2. sleep -> 스레드가 무작위로 수행 : 순서가 없음
                //3. yield  -> 자바의 스레드가 RUNNABLE(RUNNING) 상태일 때 운영체제는 잠깐만 실행하고 실행 대기 상태로 만든다.
                // 현재 실행 중인 스에드가 자발적으로 CPU를 양보하여 다른 스레드가 실행될 수 있도록 양보한다.
                //yield() 메서드를 호출한 스레드는 RUNNABLE 상태를 유지하며 양보한다 그 후 다시 스케쥴링 큐에 들어가면서 CPU 사용 기회를 넘김
                //양보할 것이 없다면 본인 스레드를 마저 실행할 수 있다.  -> 유연성 있는 수행
                Thread.yield();
            }
        }
    }
}
