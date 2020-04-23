package edu.yuri.elearning.service;

import edu.yuri.elearning.entity.SysUser;
import edu.yuri.elearning.mapper.SysRoleRepository;
import edu.yuri.elearning.mapper.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final SysUserRepository userRepository;

    private final SysRoleRepository roleRepository;

    @Autowired
    public AuthService(SysUserRepository userRepository, SysRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public SysUser login(String username, String password) {
        SysUser user = userRepository.findByName(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    public List<SysUser> allUsers() {
        return userRepository.findAll();
    }


    public void recharge(Integer recharge, SysUser user) {
        user.setWallet(user.getWallet() + recharge);
        userRepository.save(user);
    }
}
