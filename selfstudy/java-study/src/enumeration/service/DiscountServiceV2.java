package enumeration.service;

import enumeration.ClassGrade;

public class DiscountServiceV2 {

    public int discount(ClassGrade grade, int price) {
        int discountPercent = 0;

        if (grade == ClassGrade.BASIC) {
            discountPercent = 10;
        }else if (grade == ClassGrade.GOLD) {
            discountPercent = 20;
        }else if (grade == ClassGrade.DIAMOND) {
            discountPercent = 30;
        }else{
            System.out.println(grade + " : 할인 X ");

        }
        return price * discountPercent / 100;
    }
}
