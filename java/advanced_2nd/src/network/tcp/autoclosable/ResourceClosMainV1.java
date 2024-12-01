package network.tcp.autoclosable;

public class ResourceClosMainV1 {
    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("call Exception 처리");
            throw new RuntimeException(e);
        }catch (CloseException e) {
            System.out.println("close Exception 처리");
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CallException, CloseException {
        ResourceV1 resource1 = new ResourceV1("resource1");
        ResourceV1 resource2 = new ResourceV1("resource2");


        resource1.call();
        resource2.callEx();


        System.out.println("자원 정리");
        resource1.closeEx();
        resource2.closeEx();

    }
}
