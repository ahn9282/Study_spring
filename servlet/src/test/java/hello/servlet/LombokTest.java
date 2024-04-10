package hello.servlet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class LombokTest {

    @Test
    public void LTest(){
        TestLombok tl1 = new TestLombok();
        TestLombok tl2 = new TestLombok();
        tl1.setNumber(3);
        System.out.println("tl1의 numbeer : "+tl1.getNumber());
        assertThat(tl1.getNumber()).isNotSameAs(tl2.getNumber());

    }
}
