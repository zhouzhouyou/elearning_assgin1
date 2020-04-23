package edu.yuri.elearning.security;

import edu.yuri.elearning.entity.SysUser;
import edu.yuri.elearning.mapper.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SysUserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(SysUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userRepository.findByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        log.info(String.valueOf(user.getAuthorities()));
        return user;
    }
}
