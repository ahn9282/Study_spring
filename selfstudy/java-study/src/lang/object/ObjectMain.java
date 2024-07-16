package lang.object;

public class ObjectMain {
    public static void main(String[] args) {
        int a = 10;
        int b = a;
        a += 5;
        System.out.println("a = " + a);//15
        System.out.println("b = " + b);//10

        Address q = new Address("123", "seoul");
        Address w = q;
        System.out.println("==========before change===================");
        System.out.println("q = " + q);
        System.out.println("w = " + w);
        System.out.println("==========after change===================");
        q.setStreet("paju");
        q.setZipcode("234");
        System.out.println("q = " + q);
        System.out.println("w = " + w);
    }

    public static class Address {


        String zipcode;
        String street;

        public Address() {
        }

        @Override
        public String toString() {
            return "Address{" +
                    "zipcode='" + zipcode + '\'' +
                    ", street='" + street + '\'' +
                    '}';
        }

        public Address(String zipcode, String street) {
            this.zipcode = zipcode;
            this.street = street;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getZipcode() {
            return zipcode;
        }

        public String getStreet() {
            return street;
        }
    }
}
