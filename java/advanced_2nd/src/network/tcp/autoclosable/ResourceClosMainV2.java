package network.tcp.autoclosable;

public class ResourceClosMainV2 {
    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("call Exception 처리");
            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("close Exception 처리");
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CallException, CloseException {

        ResourceV1 resource1 = null;
        ResourceV1 resource2 = null;
        try {
            resource1 = new ResourceV1("resource1");
            resource2 = new ResourceV1("resource2");

            resource1.call();
            resource2.callEx();
        } catch (CallException e) {
            System.out.println("ex : " + e);
            throw e;
        } finally {
            if(resource2 != null){
                resource2.closeEx();
            }
            if(resource1 != null){
                resource1.closeEx();
            }

        }


    }
}
