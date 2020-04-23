package edu.yuri.elearning.service;

import edu.yuri.elearning.entity.Course;
import edu.yuri.elearning.entity.Purchase;
import edu.yuri.elearning.entity.SysUser;
import edu.yuri.elearning.mapper.CourseRepository;
import edu.yuri.elearning.mapper.PurchaseRepository;
import edu.yuri.elearning.mapper.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    private final PurchaseRepository purchaseRepository;

    private final SysUserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, PurchaseRepository purchaseRepository, SysUserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getPurchasedCourses(Integer id) {
        List<Course> list = new ArrayList<>();
        purchaseRepository.findAllByUserId(id).forEach(purchase -> {
            Optional<Course> course = courseRepository.findById(purchase.getCourseId());
            course.ifPresent(list::add);
        });
        return list;
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    public boolean bought(Integer courseId, Integer userId) {
        return purchaseRepository.existsByCourseIdAndUserId(courseId, userId);
    }

    public Course getCourseById(Integer courseId) {

        Optional<Course> course = courseRepository.findById(courseId);
        return course.orElse(null);
    }

    public List<MyCourse> getCourses(Integer uid) {
        List<MyCourse> list = new LinkedList<>();
        getAllCourses().forEach(course -> list.add(new MyCourse(course, bought(course.getId(), uid))));
        return list;
    }

    public String buyCourse(Integer userId, Integer courseId) {
        if (bought(courseId, userId)) {
            return "Already Bought";
        }
        Optional<SysUser> userOp = userRepository.findById(userId);
        Optional<Course> courseOp = courseRepository.findById(courseId);
        if (userOp.isPresent() && courseOp.isPresent()) {
            SysUser user = userOp.get();
            Course course = courseOp.get();
            if (user.getWallet() > course.getPrice()) {
                purchaseRepository.save(new Purchase(courseId, userId));
                user.setWallet(user.getWallet() - course.getPrice());
                userRepository.save(user);
                return "Success";
            } else {
                return String.format("You need %d yuan, but you only have %d", course.getPrice(), user.getWallet());
            }
        }
        return "Not Found";
    }

    public static class MyCourse extends Course {
        private boolean bought;

        public MyCourse(Course course, boolean bought) {
            this.bought = bought;
            setId(course.getId());
            setName(course.getName());
            setPrice(course.getPrice());
        }

        public boolean isBought() {
            return bought;
        }

        public void setBought(boolean bought) {
            this.bought = bought;
        }
    }
}
