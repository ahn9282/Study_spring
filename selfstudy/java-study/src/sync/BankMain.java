package sync;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class BankMain {
    public static void main(String[] args) throws InterruptedException {
        BankAccountV1 account = new BankAccountV1(1000);

        Thread t1 = new Thread(new WithDrawTask(account, 800), "t1");
        Thread t2 = new Thread(new WithDrawTask(account, 800), "t2");
        t1.start();
        t2.start();
        log("t1.state : " + t1.getState());
        log("t2.state : " + t2.getState());


        t1.join();
        sleep(500);
        t2.join();

        log("최종 잔액 : " + account.getBalance());
    }
}
