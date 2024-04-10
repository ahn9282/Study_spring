package hellow.springMVC.basic.response;

import hellow.springMVC.basic.requestmapping.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {

        response.getWriter().write("ok");

    }


  /*
   `ResponseEntity` 엔티티는 `HttpEntity` 를 상속 받았는데,
  HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가 지고 있다.
  `ResponseEntity` 는 여기에 더해서 HTTP 응답 코드를 설정할 수 있다.
  */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() throws IOException {

        return
                new ResponseEntity<String>("ok", HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";

    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1(){
        HelloData data = new HelloData();
        data.setUsername("안준섭");
        data.setAge(23);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2(){
        HelloData helloData = new HelloData();
        helloData.setUsername("안준섭");
        helloData.setAge(23);

        return helloData;
    }

}
