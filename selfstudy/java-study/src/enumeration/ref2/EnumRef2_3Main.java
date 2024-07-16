package enumeration.ref2;



public class EnumRef2_3Main {

    //ENUM 내부 메서드 활용
    public static void main(String[] args) {
        int price = 100000;
        DiscountServiceV6 discountService = new DiscountServiceV6();
        
        System.out.println(" 원가 : " + price);

                 //ENUM 내부 메서드로 할인가격 적용 Grade.BASIC.discount(price);
       /* System.out.println("BASIC 등급 할인 금액 = " + Grade.BASIC.discount(price));
        System.out.println("GOLD 등급 할인 금액  = " + Grade.GOLD.discount(price));
        System.out.println("DIAMOND 등급 할인 금액  = " + Grade.DIAMOND.discount(price));*/

        printDiscount(Grade.BASIC, price);
        printDiscount(Grade.GOLD, price);
        printDiscount(Grade.DIAMOND, price);

        Grade[] values = Grade.values();
        for (Grade value : values) {
            printDiscount(value, price);
        }

    }

    private static void printDiscount(Grade grade, int price) {
        System.out.println(grade.name() + " 등급 할인 금액 : " + grade.discount(price));
    }

}
