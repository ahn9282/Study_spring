package abstract_추상;

public class Truck extends Car{
    @Override
    void stop() {
        System.out.println("브레이크 밟기");
    }

    @Override
    void move() {
        System.out.println("엑셀밟기");

    }
}
