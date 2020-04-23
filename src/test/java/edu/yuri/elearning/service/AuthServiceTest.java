package edu.yuri.elearning.service;

import edu.yuri.elearning.entity.SysUser;
import edu.yuri.elearning.mapper.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthServiceTest {
    @Autowired
    private SysUserRepository userRepository;

    @Test
    public void test1() {
        SysUser user = new SysUser();
        user.setName("test");
        user.setPassword("testP");
        userRepository.save(user);
    }

    @Test
    public void test2() {
        List<SysUser> users = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            SysUser user = new SysUser();
            user.setName("#" + i);
            user.setPassword("p" + i);
            users.add(user);
        }
        userRepository.saveAll(users);
    }

    @Test
    public void test3() {
        Optional<SysUser> optionalSysUser = userRepository.findById(1);
        if (optionalSysUser.isPresent()) {
            log.info(optionalSysUser.get().toString());
        } else {
            log.info("Not Found");
        }
    }

    @Test
    public void test4() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<SysUser> page = userRepository.findAll(pageable);
        List<SysUser> userList = page.getContent();
        userList.forEach(sysUser -> log.info(sysUser.toString()));
        int totalPages = page.getTotalPages();
        long totalElement = page.getTotalElements();
        log.info("totalPages:#{}, totalElements:#{}", totalPages, totalElement);
    }

    @Test
    @After
    public void test5() {
        userRepository.deleteAllByNameLike("#%");
    }


}