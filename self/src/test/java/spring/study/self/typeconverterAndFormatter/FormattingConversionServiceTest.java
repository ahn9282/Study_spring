package spring.study.self.typeconverterAndFormatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;
import spring.study.self.typeconverterandFormatter.IpPort;
import spring.study.self.typeconverterandFormatter.IpPortToStringConverter;
import spring.study.self.typeconverterandFormatter.MyNumberFormatter;
import spring.study.self.typeconverterandFormatter.StringToIpPortConverter;

import static org.assertj.core.api.Assertions.*;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionServiceTest() {

        DefaultFormattingConversionService conversionService = new
                DefaultFormattingConversionService();
        //컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        //포맷터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        //컨버터 사용
        IpPort ipPort = conversionService.convert("127.0.0.1:9282", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 9282));
        //포맷터 사용
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);

    }
}
