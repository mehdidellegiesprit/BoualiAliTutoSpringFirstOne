package com.bouali.gestiondestock.services.auth;

import com.bouali.gestiondestock.Repository.UtilisateurRepository;
import com.bouali.gestiondestock.dto.RolesDto;
import com.bouali.gestiondestock.dto.UtilisateurDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.model.Utilisateur;
import com.bouali.gestiondestock.model.auth.ExtendedUser;
import com.bouali.gestiondestock.services.RolesService;
import com.bouali.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurService service ;
    @Autowired
    private RolesService rolesService ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UtilisateurDto utilisateur = service.findByEmail(email);
//        System.out.println("***************Roles******************");
//        System.out.println(service.findByEmail(email).getRoles());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // TODO njib liste des roles emte3 l user hetha utilisateur
        // TODO get roles by utilisateur
        // TODO Create repository de role et implementer les methodes
        List<RolesDto> rolesUser = rolesService.findRolesByUtilisateur(utilisateur.getId());
//        System.out.println("/n ***********rolesUser*************");
//        System.out.println(rolesUser);
//        System.out.println("/n ***********rolesUserSIZE*************");
 //      System.out.println(rolesUser.size());
        rolesUser
                .forEach
                        (role->authorities.add(new SimpleGrantedAuthority(role.getRoleName())));


        return new ExtendedUser(utilisateur.getEmail(), utilisateur.getMotDePasse(), utilisateur.getEntreprise().getId(),authorities) ;
    }
}
