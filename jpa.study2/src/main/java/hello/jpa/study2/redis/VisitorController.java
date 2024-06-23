package hello.jpa.study2.redis;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/visitor")
public class VisitorController {


    @Autowired
    private RedisService redisService;

    @GetMapping("/track")
    public String trackVisitor(HttpServletRequest request, HttpServletResponse response) {
        String visitorId = getVisitorIdFromCookie(request, response);
        redisService.saveVisitorCount(visitorId);
        return "Visitor tracked: " + visitorId;
    }

    @GetMapping("/count")
    public String getVisitorCount(HttpServletRequest request) {
        String visitorId = getVisitorIdFromCookie(request, null);
        Long count = redisService.getVisitorCount(visitorId);
        return "Visitor count: " + count;
    }

    private String getVisitorIdFromCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("visitor_id".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        String visitorId = UUID.randomUUID().toString();
        if (response != null) {
            Cookie newCookie = new Cookie("visitor_id", visitorId);
            newCookie.setMaxAge(60 * 60 * 24 ); // 1 year
            response.addCookie(newCookie);
        }
        return visitorId;
    }
}
