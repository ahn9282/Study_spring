package enumeration.tostring;

import enumeration.ClassGrade;
import enumeration.service.DiscountServiceV2;

public class StringGradeEx2_2 {

    public static void main(String[] args) {
        int price = 100000;
        DiscountServiceV2 discountService = new DiscountServiceV2();

        int basic = discountService.discount(ClassGrade.BASIC, price);
        int gold = discountService.discount(ClassGrade.GOLD, price);
        int diamond = discountService.discount(ClassGrade.DIAMOND, price);

        System.out.println(" 원가 : " + price);
        System.out.println("BASIC 등급 할인 금액 = " + basic);
        System.out.println("GOLD 등급 할인 금액  = " + gold);
        System.out.println("DIAMOND 등급 할인 금액  = " + diamond);

        int none = discountService.discount(null, price);
        // ENUM 경우 생성자를 private으로 하여 생성으로 인한 오류를 막는다.
       /* ClassGrade newClassGrade = new ClassGrade();
        int result =  discountService.discount(newClassGrade, price);*/
    }
}
