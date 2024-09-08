package lock;

import sync.BankAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class BankAccountV5 implements BankAccount {

    private final Lock lock = new ReentrantLock();
    List<String> failRecord;
    private int balance;

    public BankAccountV5(int init) {
        this.balance = init;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean withDraw(int amount) {
        log("거래 시작 : " + getClass().getSimpleName());

        if(!lock.tryLock()){
            log("[진입 실패] 이미 처리 중인 작업이 있습니다.");
            return false;
        }
        try {

            log("[검증 시작] 출금액 : " + amount + ", 잔액 : " + balance);

            if (balance < amount) {
                log("[검증 실패] 출금액 : " + amount + ", 잔액 : " + balance);
                failRecord = new ArrayList<String>();
                failRecord.add("[" + new Date() + "] : 입급 실패 내역  -> 출금액 : " + amount + ", 잔액 : " + balance);
                return false;
            }

            log("[검증 완료] 출금액 : " + amount + ", 잔액 : " + balance);
            sleep(1000);
            balance = balance - amount;

            log("[출금 완료] 출금액 : " + amount + ", 잔액 : " + balance);

        } finally {
            lock.unlock();

        }

        log("거래 종료  ");
        return true;
    }
}
