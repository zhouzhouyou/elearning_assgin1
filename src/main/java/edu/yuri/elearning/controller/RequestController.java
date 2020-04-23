package edu.yuri.elearning.controller;

import edu.yuri.elearning.entity.SysUser;
import edu.yuri.elearning.security.IsUser;
import edu.yuri.elearning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RequestController {
    private final CourseService courseService;
    private static final String LOGIN = "index/login";

    @Autowired
    public RequestController(CourseService courseService) {
        this.courseService = courseService;
    }

    private static class BuyCourse {
        Integer user;
        Integer course;

        public BuyCourse(int user, int course) {
            this.user = user;
            this.course = course;
        }
    }

    @PostMapping("/buy")
    @IsUser
    @CrossOrigin
    public ResponseEntity<String> buyCourse(@RequestBody BuyCourse buyCourse,
                                    Authentication authentication)
    {
        if (authentication == null) {
            return new ResponseEntity<>(LOGIN, HttpStatus.UNAUTHORIZED);
        }
        SysUser user = (SysUser) authentication.getPrincipal();
        if (user == null || !user.getId().equals(buyCourse.user)) {
            return new ResponseEntity<>(LOGIN, HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(courseService.buyCourse(buyCourse.user, buyCourse.course));
    }
}
