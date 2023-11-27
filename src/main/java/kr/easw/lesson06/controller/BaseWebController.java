package kr.easw.lesson06.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// 이 클래스를 컨트롤러로 선언합니다.
@Controller
@RequiredArgsConstructor
public class BaseWebController {

    @RequestMapping("/dashboard")
    public ModelAndView onUserDashboard() {
        return new ModelAndView("user_dashboard.html");
    }

    @RequestMapping("/login")
    public ModelAndView onLogin() {
        return new ModelAndView("login.html");
    }


    @RequestMapping("/register")
    public ModelAndView onRegister() {
        return new ModelAndView("register.html");
    }

    @RequestMapping("/admin")
    public ModelAndView onAdminDashboard() {
        return new ModelAndView("admin_dashboard.html");
    }

    @RequestMapping("/management")
    public ModelAndView onManagementDashboard() {
        return new ModelAndView("management.html");
    }

    // 이 메서드의 엔드포인트를 /server-error로 설정합니다.
    @RequestMapping("/server-error")
    public ModelAndView onErrorTest() {
        // 에러 페이지로 리다이렉트합니다.
        return new ModelAndView("error.html");
    }


}



