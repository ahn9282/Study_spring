package nested.inner;

public class InnerOuterMain {
    public static void main(String[] args) {
        InnerOuter outer = new InnerOuter();
        InnerOuter outer2 = new InnerOuter();
        InnerOuter outer3 = new InnerOuter();
        InnerOuter.Inner inner = outer.new Inner();
        InnerOuter.Inner inner2 = outer2.new Inner();
        inner.print();

        System.out.println("inner = " + inner);
        System.out.println("inner.getClass() = " + inner.getClass());
        System.out.println("inner2 = " + inner2);
        System.out.println("inner2.getClass() = " + inner2.getClass());
    }
}
