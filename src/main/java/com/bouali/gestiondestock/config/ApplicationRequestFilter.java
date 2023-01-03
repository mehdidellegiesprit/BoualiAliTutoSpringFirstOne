package com.bouali.gestiondestock.config;

import com.bouali.gestiondestock.services.auth.ApplicationUserDetailsService;
import com.bouali.gestiondestock.utils.JwtUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ApplicationRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil ;
    @Autowired
    private ApplicationUserDetailsService userDetailsService;




    // TODO si la a comme header "Authorization" et start with Bearer donc elle necessite un access token donc il faut
    // TODO verfier si ce token et valide ou non
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        String userEmail = null ;
        String jwt = null ;
        String idEntreprise = null  ;


        // TODO si la a comme header "Authorization" et start with Bearer donc elle necessite un access token donc il faut
        // TODO verfier si ce token et valide ou non


        if (StringUtils.hasLength(authHeader)&&authHeader.startsWith("Bearer")){
            jwt = authHeader.substring(7) ;
            userEmail = jwtUtil.extractUsername(jwt) ;
            idEntreprise = jwtUtil.extractIdEntreprise(jwt) ;
        }

        if(StringUtils.hasLength(userEmail) && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail) ;
            if(jwtUtil.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // TODO MDC.put() fournit par slf4j lombok : elle me donne la possibilitee de stockeé un objet sans avoir cree un class comme une session ou cookies !
        MDC.put("idEntreprise",idEntreprise);
        // TODO continuer le rest des filtre !

        chain.doFilter(request,response);
    }
}














