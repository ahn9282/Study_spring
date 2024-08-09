package study.springboot.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@HandlesTypes(AppInit.class)
public class MyContainerInitV2 implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext context) throws ServletException {
        System.out.println("MyContainerInitV2.onStartup");
        System.out.println("set = " + set);
        System.out.println("context = " + context);


        //class study.springboot.container.AppInitV1Servlet
        for (Class<?> appInitClass : set) {
            try {
                //new AppInitV1Servlet()과 같은 코드
                AppInit appInit =(AppInit) appInitClass.getDeclaredConstructor().newInstance();
                appInit.onStartUp(context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
