package lang.object.test;

public class Car {
    private String name;

    String carInfo() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Car() {
    }

    public Car(String name) {
        this.name = name;
    }
}
