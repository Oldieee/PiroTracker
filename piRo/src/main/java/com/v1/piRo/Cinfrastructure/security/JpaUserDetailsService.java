package com.v1.piRo.Cinfrastructure.security;

import com.v1.piRo.Cinfrastructure.repository.SpringUserJpaRepository;
import com.v1.piRo.Cinfrastructure.repository.dbo.UserDbo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class JpaUserDetailsService implements UserDetailsService {
    private  final SpringUserJpaRepository userJpaRepository;
    public  JpaUserDetailsService(SpringUserJpaRepository userJpaRepository){
        this.userJpaRepository=userJpaRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserDbo userDbo= userJpaRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Utilizatorul nu a fost gasit."+username));
        return new User(
                userDbo.getUsername(),
                userDbo.getPassword(),
                userDbo.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
