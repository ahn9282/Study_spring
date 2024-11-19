package charset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class EncodingMain2 {

    private static final Charset EUC_KR = Charset.forName("EUC-KR");
    private static final Charset MS_949 = Charset.forName("MS949");

    public static void main(String[] args) {

        System.out.println("== 영문 ASCII 인코딩 == ");
        test("A", US_ASCII, US_ASCII);
        test("A", US_ASCII, UTF_8);
        test("A", US_ASCII, ISO_8859_1);
        test("A", US_ASCII, MS_949);
        //여기까지 ASCII 포함
        test("A", US_ASCII, UTF_16BE);
        //UTF-16의 경우 1Byte씩 디코딩하기 때문에 실패

        System.out.println("== 한글 인코딩 - 기본 ==");
        //한글은 ASCII에 없기 때문에 인코딩에서 실패하여 ? 라는 결과만 나온다.
        test("가", US_ASCII, US_ASCII);
        test("가", US_ASCII, ISO_8859_1);
        test("가", US_ASCII, UTF_8);

        System.out.println("=== 한글 인코딩 - 복잡한 문자 ==");
        test("뷁", EUC_KR, EUC_KR);
        test("뷁", MS_949, MS_949);
        test("뷁", UTF_8, UTF_8);
        test("뷁", UTF_16BE, UTF_16BE);

        System.out.println("=== 한글 인코딩 - 디코딩이 다른 경우 ==");
        test("가", EUC_KR, MS_949);
        test("뷁", MS_949, EUC_KR);
        test("가", UTF_8, US_ASCII);
    }

    private static void test(String text, Charset encodingCharset, Charset decodingCharset) {
        byte[] encoded = text.getBytes(encodingCharset);
        String decoded = new String(encoded, decodingCharset);
        System.out.printf("%s -> [%s] 인코딩 -> %s %sbyte -> [%s] 디코딩 -> %s\n",
                text, encodingCharset, Arrays.toString(encoded), encoded.length,
                decodingCharset, decoded);
    }

}
