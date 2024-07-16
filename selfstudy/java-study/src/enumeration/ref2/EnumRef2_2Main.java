package enumeration.ref2;



public class EnumRef2_2Main {

    //ENUM 내부 메서드 활용
    public static void main(String[] args) {
        int price = 100000;
        DiscountServiceV6 discountService = new DiscountServiceV6();

        System.out.println(" 원가 : " + price);

                 //ENUM 내부 메서드로 할인가격 적용 Grade.BASIC.discount(price);
        System.out.println("BASIC 등급 할인 금액 = " + Grade.BASIC.discount(price));
        System.out.println("GOLD 등급 할인 금액  = " + Grade.GOLD.discount(price));
        System.out.println("DIAMOND 등급 할인 금액  = " + Grade.DIAMOND.discount(price));


    }
}
