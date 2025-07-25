package net.sf.esfinge.greenframework.spring.starter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.esfinge.greenframework.core.identity.GreenIdentityHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public abstract class GreenIdentityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String currentIdentityRequest = processIdentityRequest(request, response);
            GreenIdentityHolder.set(() -> currentIdentityRequest);
            filterChain.doFilter(request, response);
        } finally {
            GreenIdentityHolder.clear();
        }

    }

    public abstract String processIdentityRequest(HttpServletRequest request, HttpServletResponse response);
}
