package study.springboot.container;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import study.springboot.spring.HelloConfig;

public class AppInitV2Spring implements AppInit {
    @Override
    public void onStartUp(ServletContext context) {
        System.out.println("AppInitV2Spring.onStartUp");


        //스프링 컨테이너 생성
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(HelloConfig.class);

        //스프링 MVC 디스패처 서블릿 생성, 스프링 컨테이너 연결
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);

        //디스패처 서블릿을 서블릿 컨테이너에 등록
        ServletRegistration.Dynamic servlet = context.addServlet("dispatcherV2", dispatcher);

        //   /spring/*요청이 디스패처 서블릿을 통하도록 설정
        servlet.addMapping("/spring/*");


    }
}
