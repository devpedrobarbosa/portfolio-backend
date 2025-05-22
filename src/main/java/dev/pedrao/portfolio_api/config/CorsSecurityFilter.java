package dev.pedrao.portfolio_api.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CorsSecurityFilter implements Filter {

    private static final List<String> ALLOWED_ORIGINS = List.of(
            "https://www.pedrao.tech", "http://localhost:5173", "http://localhost:8080"
    );

    private static final List<String> BLOCKED_USER_AGENTS = List.of();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String origin = req.getHeader("Origin");
        String userAgent = req.getHeader("User-Agent");
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        if (origin != null && !ALLOWED_ORIGINS.contains(origin)) {
            System.out.println(origin);
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: Origem não permitida.");
            return;
        }
        if (userAgent != null && BLOCKED_USER_AGENTS.stream().anyMatch(userAgent::contains)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: Ferramenta bloqueada.");
            return;
        }
        chain.doFilter(request, response);
    }
}