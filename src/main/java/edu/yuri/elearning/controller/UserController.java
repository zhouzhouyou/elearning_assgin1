package edu.yuri.elearning.controller;

import edu.yuri.elearning.entity.SysUser;
import edu.yuri.elearning.security.IsAdmin;
import edu.yuri.elearning.security.IsUser;
import edu.yuri.elearning.service.AuthService;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    private static final String LOGIN = "index/login";
    private final AuthService authService;

    @Autowired
    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/manage_user")
    @IsAdmin
    public String userManagement(Authentication authentication, Model model) {
        if (authentication == null) {
            return LOGIN;
        }
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null) {
            return LOGIN;
        }
        model.addAttribute("user", user);
        model.addAttribute("list", authService.allUsers());
        return "user/manage_user";
    }

    @GetMapping("user/profile/{id}")
    @IsUser
    public String profile(Authentication authentication, @PathVariable Integer id, Model model) {
        if (authentication == null) {
            return LOGIN;
        }
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null || !user.getId().equals(id)) {
            return LOGIN;
        }
        model.addAttribute("user", user);
        return "user/profile";
    }

    @RequestMapping("/recharge")
    @IsUser
    public String recharge(Authentication authentication, Integer recharge, Model model) {
        if (authentication == null) {
            return LOGIN;
        }
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null) {
            return LOGIN;
        }
        authService.recharge(recharge, user);
        model.addAttribute("user", user);
        return String.format("redirect:user/profile/%d", user.getId());
    }
}
