package collection.simple;

import collection.simple.list.BasicList;
import collection.simple.list.SimpleList;
import collection.simple.list.SyncList;
import collection.simple.list.SyncProxyList;

import static thread.util.MyLogger.log;

public class SimpleListMainV4 {


    public static void main(String[] args) throws InterruptedException {
        SimpleList list = new SyncProxyList(new BasicList());

        test(list);

        System.out.println("list: " + list);
    }

    private static void test(SimpleList list) throws InterruptedException {
        log(list.getClass().getSimpleName());

        Runnable taskA = new Runnable() {

            @Override
            public void run() {
                list.add("A");
                log("Thread-1 : list.add(A)");
            }
        };
        Runnable taskB = new Runnable() {

            @Override
            public void run() {
                list.add("B");
                log("Thread-2 : list.add(B)");
            }
        };

        Thread thread1 = new Thread(taskA, "Thread-1");
        Thread thread2 = new Thread(taskB, "Thread-2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        log("완료");
    }
}


