package study.spring.aop.order.aop;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;

public class JoinPoint implements org.aspectj.lang.JoinPoint {

    @Override//프록시 객체 반환
    public Object getThis() {
        return null;
    }

    @Override// 대상 객체 반환
    public Object getTarget() {
        return null;
    }

    @Override// 메서드 인수 반환
    public Object[] getArgs() {
        return new Object[0];
    }

    @Override//조언되는 메서드에 대한 설명(정보) 반환
    public Signature getSignature() {
        return null;
    }
    //toString  : 조언 되는 방법에 대한 유용한 설명을 인쇄
    @Override
    public String toShortString() {
        return "";
    }

    @Override
    public String toLongString() {
        return "";
    }


    @Override
    public SourceLocation getSourceLocation() {
        return null;
    }

    @Override
    public String getKind() {
        return "";
    }

    @Override
    public StaticPart getStaticPart() {
        return null;
    }
}
