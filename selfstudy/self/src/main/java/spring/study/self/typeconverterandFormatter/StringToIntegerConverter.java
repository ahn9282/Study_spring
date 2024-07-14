package spring.study.self.typeconverterandFormatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String request) {
        log.info("convert source={}", request);
        return Integer.valueOf(request);
    }
}
