package lang.object.test;

public class Dog {
    private String name;
    String dogInfo() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
    }
}
