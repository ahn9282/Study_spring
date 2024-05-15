package spring.study.self.typeconverterAndFormatter;

import org.junit.jupiter.api.Test;
import spring.study.self.typeconverterandFormatter.*;

import static org.assertj.core.api.Assertions.*;

public class ConverterTest {

    @Test
    void stringToInteger() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");
        assertThat(result).isEqualTo(10);
    }
    @Test
    void integerToString() {
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(10);
        assertThat(result).isEqualTo("10");
    }

    @Test
    void stringToIpPort() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String request = "127.0.0.1:9282";
        IpPort result = converter.convert(request);
               assertThat(result).isEqualTo(new IpPort("127.0.0.1", 9282));
    }

    @Test
    void ipPortToString(){
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort request = new IpPort("127.0.0.1", 9282);

        String result = converter.convert(request);
        assertThat(result).isEqualTo("127.0.0.1:9282");

    }

}
