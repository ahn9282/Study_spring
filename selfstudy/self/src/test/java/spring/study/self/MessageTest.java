package spring.study.self;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class MessageTest {
    @Autowired
    MessageSource ms;

    @DisplayName("영어를 한글화")
    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("hihihi");
    }


    @DisplayName("예외 체크")
    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    @DisplayName("한국어 국제화 ")
    void testMessageLocaleKO(){
        String result = ms.getMessage("hello", null, Locale.KOREA);
        assertThat(result).isEqualTo("hi-korea");

    }
    @Test
    @DisplayName("한국어 디폴트 테스트 ")
    void testMessageLocaleDefault(){
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("hi-korea");

    }
}
