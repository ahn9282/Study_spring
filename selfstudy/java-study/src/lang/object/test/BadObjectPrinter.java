package lang.object.test;

public class BadObjectPrinter {
    public static void print(Car car) {
        String str = "객체 정보 출력 : " + car.carInfo();
        System.out.println("str = " + str);
    }

    public static void print(Dog dog) {
        String str = "객체 정보 출력 : " + dog.dogInfo();
        System.out.println("str = " + str);
    }
}
