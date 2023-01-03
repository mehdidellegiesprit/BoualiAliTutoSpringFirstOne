package com.bouali.gestiondestock.controller;


import com.bouali.gestiondestock.dto.auth.AuthenticationRequest;
import com.bouali.gestiondestock.dto.auth.AuthenticationResponse;
import com.bouali.gestiondestock.model.auth.ExtendedUser;
import com.bouali.gestiondestock.services.auth.ApplicationUserDetailsService;
import com.bouali.gestiondestock.utils.JwtUtil;
import io.swagger.annotations.Api;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.bouali.gestiondestock.utils.Constants.APP_ROOT;
import static com.bouali.gestiondestock.utils.Constants.Authentication_ENDPOINT;
@CrossOrigin("*")
@RestController
@RequestMapping(APP_ROOT)
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager ;
    @Autowired
    private ApplicationUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil ;

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request){
        //TODO elle va verfier si le user avec ce login e mdp existe dans la base ou non si non elle va lever une exception !

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        //System.out.println("im here ");
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }
}
