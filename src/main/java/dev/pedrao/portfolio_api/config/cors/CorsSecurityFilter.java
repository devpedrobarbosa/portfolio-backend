package dev.pedrao.portfolio_api.config.cors;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.List;

@Component
@Profile("!dev")
public class CorsSecurityFilter implements Filter {

    private static final List<String> ALLOWED_ORIGINS = List.of(
            "https://devpedrobarbosa.com.br", "https://www.devpedrobarbosa.com.br", "https://portfolio-api.devpedrobarbosa.com.br", "http://localhost:5173", "http://localhost:8080"
    );

    private static final List<String> BLOCKED_USER_AGENTS = List.of();

    private static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS";

    private static final String ALLOWED_HEADERS = "Authorization,Content-Type,Accept,Origin,X-Requested-With";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String origin = req.getHeader("Origin");
        String userAgent = req.getHeader("User-Agent");

        if(origin != null && ALLOWED_ORIGINS.contains(origin)) {
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
            res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
            res.setHeader(HttpHeaders.VARY, "Origin");
        }

        if("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            if(origin == null || !ALLOWED_ORIGINS.contains(origin)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: Origem não permitida.");
                return;
            }

            res.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        if("GET".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        if(origin == null || !ALLOWED_ORIGINS.contains(origin)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: Origem não permitida.");
            return;
        }

        if(userAgent != null && BLOCKED_USER_AGENTS.stream().anyMatch(userAgent::contains)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado: Ferramenta bloqueada.");
            return;
        }

        chain.doFilter(request, response);
    }
}