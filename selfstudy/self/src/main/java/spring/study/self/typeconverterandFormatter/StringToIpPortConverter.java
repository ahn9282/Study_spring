package spring.study.self.typeconverterandFormatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {
    @Override
    public IpPort convert(String request) {
        log.info("convert source={}", request);
        String[] splitRequest = request.split(":");
        String ip = splitRequest[0];
        int port = Integer.parseInt(splitRequest[1]);

        return new IpPort(ip, port);
    }

}
