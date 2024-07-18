package exception.ex4;


import java.util.Scanner;

public class MainV5 {

    public static void main(String[] args)  {
        NetworkServiceV5 networkService = new NetworkServiceV5();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("전송할 문자 : ");
            String input = sc.nextLine();
            if (input.equals("exit")) {
                break;
            }
            try{

            networkService.sendMessage(input);
            }catch(Exception e){
                exceptionHandler(e);
            }

            System.out.println();
        }
        System.out.println("프로그램을 정상 종료합니다.");

    }

    //공통 예외 처리
    private static void exceptionHandler(Exception e) {
        System.out.println("사용자 메시지 : 죄송합니다. 알 수 없는 문제가 발생했습니다.");
        System.out.println("== 개발자용 디버깅 메시지 ==");
        e.printStackTrace(System.out);


    }
}
