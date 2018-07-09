package com.ghb_software.wms.view;


import com.ghb_software.wms.security.UserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.inject.Scope;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Named(value = "userProfile")
@SessionScoped
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;


    @Setter
    private String localeCode;

    private static Map<String, String> countries;

    static {
        countries = new LinkedHashMap<String, String>();
        countries.put("English", "en"); //label, value
        countries.put("Romana", "ro");
    }

    public String getLocaleCode() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
            return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLanguage();
        else
            return "en";
    }

    public Map<String, String> getCountriesInMap() {
        return countries;
    }

    public List<String> getRoles() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
            return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
        return Collections.emptyList();
    }

//    public String verifyToken(@RequestParam("token") Optional<String> token) {
//        if (!token.isPresent())
//            return VERIFICATION_TEMPLATE;
//
//        Optional<User> user = verificationTokenService.activateUserForToken(token.get());
//        return MessageFormat.format("redirect:/{0}?{1}", VERIFICATION_TEMPLATE, user.isPresent() ? "success" : "invalid");
//    }

}
