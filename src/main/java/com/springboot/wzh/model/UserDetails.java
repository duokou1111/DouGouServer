package com.springboot.wzh.model;

import com.springboot.wzh.domain.UserInfo;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private transient String password;
    private String username;
    private String token;
    private UserInfo userInfo;
    private boolean isAccountNonExpired = true;
    private boolean isNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
    private ArrayList<GrantedAuthority> auths = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addAuth(GrantedAuthority grantedAuthority){
        auths.add(grantedAuthority);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setNonLocked(boolean nonLocked) {
        isNonLocked = nonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setAuths(ArrayList<GrantedAuthority> auths) {
        this.auths = auths;
    }
}
