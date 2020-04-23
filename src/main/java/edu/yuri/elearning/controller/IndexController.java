package edu.yuri.elearning.controller;

import edu.yuri.elearning.entity.SysUser;
import edu.yuri.elearning.mapper.SysUserRepository;
import edu.yuri.elearning.security.IsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    private final SysUserRepository userRepository;

    @Autowired
    public IndexController(SysUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    @IsUser
    public String index(Authentication authentication, Model model) {
        if (authentication == null) return "index/login";
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null) return "index/login";
        model.addAttribute("name", user.getUsername());
        model.addAttribute("user", user);
        return "index/index";
    }

    @GetMapping("/login")
    public String login() {
        return "index/login";
    }

    @GetMapping("/user/header")
    public String header(Authentication authentication, Model model) {
        SysUser user = (SysUser) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user/header";
    }

    @GetMapping("/sign_up")
    public String signUp(Model model) {
        return "index/sign_up";
    }

    @PostMapping("/signUp")
    public String sign(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (userRepository.existsByName(username)) {
            return "redirect:sign_up";
        }

        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        if (!password.equals(password2)) {
            return "redirect:sign_up";
        }
        userRepository.save(new SysUser(username, password));

        return "index/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "index/logout";
    }
}
