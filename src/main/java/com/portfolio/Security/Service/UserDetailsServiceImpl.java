
package com.portfolio.Security.Service;

import com.portfolio.Security.Entity.PersonUser;
import com.portfolio.Security.Entity.DefaultUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonUser personUser = userService.getByUsername(username).get();
        return DefaultUser.build(personUser);
    }
    
}
