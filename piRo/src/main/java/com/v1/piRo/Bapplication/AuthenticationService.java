package com.v1.piRo.Bapplication;

import com.v1.piRo.Aexposition.AuthenticationRequest;
import com.v1.piRo.Aexposition.AuthenticationResponse;
import com.v1.piRo.Aexposition.RegisterRequest;
import com.v1.piRo.Cinfrastructure.repository.SpringUserJpaRepository;
import com.v1.piRo.Cinfrastructure.repository.dbo.UserDbo;
import com.v1.piRo.Cinfrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final SpringUserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public  AuthenticationService(SpringUserJpaRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager){
    this.authenticationManager=authenticationManager;
    this.jwtService=jwtService;
    this.passwordEncoder=passwordEncoder;
    this.userRepository=userRepository;
}
 public AuthenticationResponse register(RegisterRequest request){
     UserDbo user=new UserDbo();
     user.setUsername(request.username());
     user.setPassword(request.password());
     Set<String>roles=new HashSet<>();
     roles.add("ROLE_USER");
     user.setRoles(roles);
     userRepository.save(user);
     UserDetails userDetails=new User(
             user.getUsername(),
             user.getPassword(),
             user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
     );
     String jwtToken=jwtService.generateToken(userDetails);
     return new AuthenticationResponse(jwtToken);
 }
 public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        UserDbo user= userRepository.findByUsername(request.username()).orElseThrow();
        UserDetails userDetails=new User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
     String jwtToken = jwtService.generateToken(userDetails);

     return new AuthenticationResponse(jwtToken);
 }
    }