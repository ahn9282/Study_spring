package sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static thread.util.MyLogger.log;
import static thread.util.ThreadUtils.sleep;

public class BankAccountV2 implements BankAccount {

    private int balance;
     List<String> failRecord;

    public BankAccountV2(int init) {
        this.balance = init;
    }

    @Override
    synchronized public int getBalance() {
        return balance;
    }

    @Override
   synchronized public boolean withDraw(int amount) {
        log("거래 시작 : " + getClass().getSimpleName());

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
        log("거래 종료  ");
        return true;
    }
}
