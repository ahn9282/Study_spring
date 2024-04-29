package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

 public class ConverterTest {

    @Test
    void stringToInteger(){
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");
        assertThat(result).isNotEqualTo("10");
    }

     @Test
     void IntegerToString() {
         IntegerToStringConverter converter = new IntegerToStringConverter();
         String result = converter.convert(10);
         assertThat(result).isEqualTo("10");
     }

     @Test
     void StringToIpPort(){
         IpProtToStringConverter converter = new IpProtToStringConverter();
         IpPort source = new IpPort("127.0.0.1", 9282);
         String result = converter.convert(source);
         assertThat(result).isEqualTo("127.0.0.1:9282");
     }

     @Test
     void IpPortToString(){
         StringToIpPortConverter converter = new StringToIpPortConverter();
         String source = "127.0.0.1:9282";
         IpPort result = converter.convert(source);
         assertThat(result).isEqualTo(new IpPort("127.0.0.1", 9282));

     }
}
