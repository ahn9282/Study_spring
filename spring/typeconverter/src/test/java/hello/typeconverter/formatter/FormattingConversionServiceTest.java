package hello.typeconverter.formatter;

import hello.typeconverter.converter.IpProtToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.*;

public class FormattingConversionServiceTest {
    @Test
    void formattingConversionService(){
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        //컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpProtToStringConverter());
        //포메터 등록
        conversionService.addFormatter(new MyNumberFormatter());
        
        //DefaultFormattingConversionService은 컨버터 포매터 둘다 등록하여 사용이 가능

        //컨버터 사용
        IpPort ipPort = conversionService.convert("127.0.0.1:9282", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 9282));

        ///포매터 사용
        String convert = conversionService.convert(1000, String.class);
        assertThat(convert).isEqualTo("1,000");
        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);
        
    }
}
