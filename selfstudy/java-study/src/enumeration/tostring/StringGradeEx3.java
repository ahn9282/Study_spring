package enumeration.tostring;

import enumeration.Grade;
import enumeration.service.DiscountServiceV3;

public class StringGradeEx3 {

    public static void main(String[] args) {
        int price = 100000;
        DiscountServiceV3 discountService = new DiscountServiceV3();

        int basic = discountService.discount(Grade.BASIC, price);
        int gold = discountService.discount(Grade.GOLD, price);
        int diamond = discountService.discount(Grade.DIAMOND, price);

        System.out.println(" 원가 : " + price);
        System.out.println("BASIC 등급 할인 금액 = " + basic);
        System.out.println("GOLD 등급 할인 금액  = " + gold);
        System.out.println("DIAMOND 등급 할인 금액  = " + diamond);

        int none = discountService.discount(null, price);

    }
}
