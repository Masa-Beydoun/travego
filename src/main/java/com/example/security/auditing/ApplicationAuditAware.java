package com.example.security.auditing;

import com.example.security.Models.Author;
import com.example.security.Models.BaseUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null||!authentication.isAuthenticated()||authentication instanceof AnonymousAuthenticationToken)
        {
            return Optional.empty();
        }
        BaseUser user=(BaseUser) authentication.getPrincipal();
        return Optional.ofNullable(user.getId());
    }
}
