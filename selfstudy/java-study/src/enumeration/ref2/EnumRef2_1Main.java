package enumeration.ref2;



public class EnumRef2_1Main {

    public static void main(String[] args) {
        int price = 100000;
        DiscountServiceV5 discountService = new DiscountServiceV5();

        int basic = discountService.discount(Grade.BASIC, price);
        int gold = discountService.discount(Grade.GOLD, price);
        int diamond = discountService.discount(Grade.DIAMOND, price);

        System.out.println(" 원가 : " + price);
        System.out.println("BASIC 등급 할인 금액 = " + basic);
        System.out.println("GOLD 등급 할인 금액  = " + gold);
        System.out.println("DIAMOND 등급 할인 금액  = " + diamond);


    }
}
