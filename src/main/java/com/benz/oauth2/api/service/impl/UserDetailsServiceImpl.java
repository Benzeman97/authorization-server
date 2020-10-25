package com.benz.oauth2.api.service.impl;

import com.benz.oauth2.api.dao.UserDAO;
import com.benz.oauth2.api.entity.User;
import com.benz.oauth2.api.model.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl  implements UserDetailsService {

    private UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDAO)
    {
        this.userDAO=userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= userDAO.findByUserName(username)
                .orElseThrow(()->new UsernameNotFoundException("User Not Available with "+username));

          return AuthUserDetails.builder(user);
    }
}
