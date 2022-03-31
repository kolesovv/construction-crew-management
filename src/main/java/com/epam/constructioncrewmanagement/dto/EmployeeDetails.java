package com.epam.constructioncrewmanagement.dto;

import com.epam.constructioncrewmanagement.entity.Role;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class EmployeeDetails implements UserDetails {

  private final String email;
  private final String password;
  private final Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());
    authorities.add(authority);
    return authorities;
  }

  @Override
  public String getPassword() {

    return password;
  }

  @Override
  public String getUsername() {

    return email;
  }

  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  @Override
  public boolean isAccountNonLocked() {

    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return true;
  }

  @Override
  public boolean isEnabled() {

    return true;
  }
}
