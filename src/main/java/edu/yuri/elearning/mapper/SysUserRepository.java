package edu.yuri.elearning.mapper;

import edu.yuri.elearning.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

    SysUser findByName(String name);

    @Transactional
    void deleteAllByNameLike(String name);

    boolean existsByName(String name);
}
