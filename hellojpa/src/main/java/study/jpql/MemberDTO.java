package study.jpql;

public class MemberDTO {

    private String username;

    public MemberDTO() {
    }

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
