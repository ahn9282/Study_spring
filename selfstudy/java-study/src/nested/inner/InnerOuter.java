package nested.inner;

import java.util.stream.DoubleStream;

public class InnerOuter {
    
    private static int outClassValue = 3;
    
    private int outInstanceValue = 2;
    
    class Inner{
        private int innerInstanceValue = 1;

        public void print() {
            System.out.println("innerInstanceValue = " + innerInstanceValue);
            System.out.println("outInstanceValue = " + outInstanceValue);
            System.out.println("outClassValue = " + outClassValue);
        }
    }
}
