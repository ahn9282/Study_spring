package study.springboot.container;

import jakarta.servlet.ServletContext;

public interface AppInit {
    void onStartUp(ServletContext context);
}
