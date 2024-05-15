package spring.study.self.typeconverterAndFormatter;


import org.junit.jupiter.api.Test;
import spring.study.self.typeconverterandFormatter.MyNumberFormatter;
import java.text.ParseException;
import java.util.Locale;
import static org.assertj.core.api.Assertions.*;

public class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void parse() throws ParseException {
        Object result =  formatter.parse("1,000", Locale.KOREA);
        assertThat(result).isEqualTo(1000L);

    }

    @Test
    void print(){
        String result = formatter.print(1000, Locale.KOREA);
        assertThat(result).isEqualTo("1,000");
    }
}
