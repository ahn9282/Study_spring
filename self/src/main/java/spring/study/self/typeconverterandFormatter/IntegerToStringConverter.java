package spring.study.self.typeconverterandFormatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IntegerToStringConverter implements Converter<Integer, String> {
    @Override
    public String convert(Integer request) {
        log.info("convert source={}", request);
        return String.valueOf(request);
    }
}
