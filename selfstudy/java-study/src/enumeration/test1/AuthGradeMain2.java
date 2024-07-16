package enumeration.test1;

import java.util.Scanner;

public class AuthGradeMain2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String grade = "";
        while (true) {
            try {
         grade = grade = inputGrade(sc);
                System.out.println("당신은 " + AuthGrade.valueOf(grade).getDescription() + " 입니다.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("없는 등급입니다. 다시 입력하세요.");
            }
        }
        AuthGrade authGrade = AuthGrade.valueOf(grade);
        System.out.println("===메뉴 목록===");
        if (authGrade.getLevel() > 0) {
            System.out.println("메인화면");
        }
        if (authGrade.getLevel() > 1) {
            System.out.println("이메일 관리 화면");
        }
        if (authGrade.getLevel() > 2) {
            System.out.println("-  관리자 화면");
        }

    }

    public static String inputGrade(Scanner sc) {
        System.out.print("당신의 등급을 입력하세요[GUEST, LOGIN, ADMIN] : ");
        String grade = sc.nextLine();
        return grade;
    }

}
