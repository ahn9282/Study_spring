package enumeration.tostring;

import enumeration.service.DiscountService;

public class StringGradeEx0_1 {

    public static void main(String[] args) {
        int price = 100000;
        DiscountService discountService = new DiscountService();
        int basic = discountService.discount("BASIC", price);
        int gold = discountService.discount("GOLD", price);
        int diamond = discountService.discount("DIAMOND", price);
        int none = discountService.discount("NONE", price);

        System.out.println(" 원가 : " + price);
        System.out.println("BASIC 등급 할인 금액 = " + basic);
        System.out.println("GOLD 등급 할인 금액  = " + gold);
        System.out.println("DIAMOND 등급 할인 금액  = " + diamond);
    }
}
