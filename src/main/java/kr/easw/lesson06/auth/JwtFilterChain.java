package kr.easw.lesson06.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.easw.lesson06.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilterChain extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final JpaUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 만약 Authorization 헤더가 없다면, 필터 체인을 계속 진행합니다.
        if (request.getHeader("Authorization") == null) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Jwt filter chain");
        String token = request.getHeader("Authorization");
        System.out.println("Token: " + token);
        //  토큰을 검증합니다.
        switch (jwtService.validate(token)) {
            case VALID:
                // 토큰이 유효하다면, 토큰에서 유저 이름을 추출합니다.
                String userName = jwtService.extractUsername(token);
                // 유저 이름을 통해 유저 정보를 가져옵니다.
                UserDetails details = userDetailsService.loadUserByUsername(userName);
                System.out.println(details.getAuthorities());
                // 유저 정보를 통해 인증 객체를 생성합니다.
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        details,
                        details.getPassword(),
                        details.getAuthorities()
                ));
                System.out.println("Token validated / Role: " + details.getAuthorities());
                // 필터 체인을 계속 진행합니다.
                filterChain.doFilter(request, response);
                return;
            case EXPIRED:
                response.sendError(401, "Expired token");
                break;
            case UNSUPPORTED:
                response.sendError(401, "Unsupported token");
                break;
            case INVALID:
                response.sendError(401, "Invalid token");
                break;
        }
        System.out.println("Token invalid");
        System.out.println(jwtService.validate(token));
    }
}
