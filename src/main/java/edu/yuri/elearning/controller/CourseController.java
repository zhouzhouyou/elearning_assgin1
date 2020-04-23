package edu.yuri.elearning.controller;

import edu.yuri.elearning.entity.Course;
import edu.yuri.elearning.entity.SysUser;
import edu.yuri.elearning.security.IsAdmin;
import edu.yuri.elearning.security.IsUser;
import edu.yuri.elearning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@IsUser
@Controller
public class CourseController {
    private final CourseService courseService;
    private static final String LOGIN = "index/login";

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/manage_course")
    @IsAdmin
    public String courseManagement(Authentication authentication, Model model) {
        if (authentication == null) return LOGIN;
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null) return LOGIN;
        model.addAttribute("user", user);
        List<Course> list = courseService.getAllCourses();
        model.addAttribute("list", list);
        return "user/manage_course";
    }

    @RequestMapping("/save_course")
    @IsAdmin
    public String updateCourse(@Valid Course course, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            courseService.saveCourse(course);
        }
        return "redirect:manage_course";
    }

    @RequestMapping("/delete_course")
    @IsAdmin
    public String deleteCourse(Integer id) {
        courseService.deleteCourse(id);

        return "redirect:manage_course";
    }

    @GetMapping("/user/{id}")
    @IsUser
    public String myCourses(Authentication authentication, Model model, @PathVariable Integer id) {
        if (authentication == null) {
            return LOGIN;
        }
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null || !user.getId().equals(id)) {
            return LOGIN;
        }
        model.addAttribute("user", user);
        model.addAttribute("list", courseService.getPurchasedCourses(id));
        return "user/my_course";
    }

    @GetMapping("/user/{id}/courses")
    @IsUser
    public String courses(Authentication authentication, Model model, @PathVariable Integer id) {
        if (authentication == null) {
            return LOGIN;
        }
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null || !user.getId().equals(id)) {
            return LOGIN;
        }
        model.addAttribute("user", user);
        model.addAttribute("list", courseService.getCourses(id));
        return "user/courses";
    }

    @GetMapping("/course/{id}")
    @IsUser
    public String takeCourse(@PathVariable Integer id, Authentication authentication, Model model) {
        if (authentication == null) {
            return LOGIN;
        }
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null) {
            return LOGIN;
        }
        if (!courseService.bought(id, user.getId())) {
            return String.format("/user/%d/courses", user.getId());
        }
        model.addAttribute("user", user);
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return String.format("/user/%d/courses", user.getId());
        }
        model.addAttribute("course", course);
        return "user/course";
    }

}
