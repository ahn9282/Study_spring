package study.actuator.start.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

    @GetMapping("/log")
    public String Log() {
        log.trace("trace log");
        log.debug("debug log");
        log.warn("warn log");
        log.info("info log");
        log.error("error Log");
        return "ok";
    }

}
