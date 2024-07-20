package nested.anonymous;

import nested.local.Printer;

public class AnonymousOuter {
    private int outInstanceVar = 3;

    public void process(int param) {
        int localVar = 1;



        Printer printer = new Printer() {
            int value = 0;

            @Override
            public void print() {
                System.out.println("value = " + value);
                System.out.println("localVar = " + localVar);
                System.out.println("mainVar = " + param);
                System.out.println("outInstanceVar = " + outInstanceVar);
            }
        };
        printer.print();
    }

    public static void main(String[] args) {
        AnonymousOuter outer = new AnonymousOuter();
        outer.process(2);

    }
}
