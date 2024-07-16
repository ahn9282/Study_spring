package enumeration.ref2;



public class DiscountServiceV5 {

    public int discount(Grade grade, int price) {
        int discountPercent = 0;

        discountPercent = grade.getDiscountPercent();

        return price * discountPercent / 100;
    }
}
