package enumeration.ref1;


public class DiscountServiceV4 {

    public int discount(ClassGrade grade, int price) {
        int discountPercent = 0;

        discountPercent = grade.getDiscountPercent();

        return price * discountPercent / 100;
    }
}
