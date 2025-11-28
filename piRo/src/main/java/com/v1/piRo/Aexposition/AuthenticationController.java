package com.v1.piRo.Aexposition;

import com.v1.piRo.Bapplication.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
private  final AuthenticationService service;
public AuthenticationController(AuthenticationService service){
    this.service=service;
}
@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>register(
            @RequestBody RegisterRequest request
){
    AuthenticationResponse response=service.register(request);
    return  ResponseEntity.ok(response);
}
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {

        AuthenticationResponse response = service.authenticate(request);


        return ResponseEntity.ok(response);
    }
}
