package exception.checked;

public class CheckedThrowMain {
    public static void main(String[] args) throws MyCheckedException {
        try{
        Service service = new Service();
        service.catchThrow();
        System.out.println("정상 종료");

        }catch(Exception e){
            System.out.println("main 메서드에서 MyCheckedException 발생!!");
        }
    }
}
