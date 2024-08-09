package study.springboot.container;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import study.springboot.servlet.HelloServlet;

public class AppInitV1Servlet implements AppInit{
    @Override
    public void onStartUp(ServletContext context) {
        System.out.println("AppInitV1Servlet.onStartUp");

        ServletRegistration.Dynamic helloServlet =
                context.addServlet("helloServlet", new HelloServlet());
        helloServlet.addMapping("/hello-servlet");
    }
}
