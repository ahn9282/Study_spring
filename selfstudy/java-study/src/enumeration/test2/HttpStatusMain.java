package enumeration.test2;

import java.util.Scanner;

public class HttpStatusMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("HTTP CODE : ");

        int httpCodeInput = sc.nextInt();

        HttpStatus findStatus = HttpStatus.findByCode(httpCodeInput);
        if (findStatus == null) {
            System.out.println("정의 되지 않은 코드");

        }else{
            System.out.println(findStatus.getStatusCode() + " " + findStatus.getMessage());
            System.out.println("isSuccess " + findStatus.isSuccess());

        }
    }
}
