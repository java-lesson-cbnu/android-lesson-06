package kr.easw.lesson06.configurations;

import kr.easw.lesson06.Constants;
import kr.easw.lesson06.auth.JwtFilterChain;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 스프링 웹 시큐리티를 활성화합니다.
@EnableWebSecurity
// Configuration 어노테이션을 사용하여 해당 클래스가 스프링 설정 클래스임을 선언합니다.
@Configuration
@AllArgsConstructor
public class SpringSecurityConfiguration {
    // 어플리케이션에 사용할 비밀번호 인코더를 미리 생성합니다.
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final JwtFilterChain filterChain;

    // @Bean 어노테이션을 사용하여 해당 메서드가 스프링 빈임을 선언합니다.
    @Bean
    // @SneakyThrows를 사용하여 예외를 무시합니다.
    // 이는 lombok의 API입니다.
    @SneakyThrows
    // HttpSecurity를 파라미터로 받아 SecurityFilterChain을 반환하는 메서드입니다.
    SecurityFilterChain configureHttpSecurity(HttpSecurity security) {
        security
                // csrf 보호를 비활성화합니다.
                // 활성화되었을 경우, 페이지 내의 API 호출이 실패할 수 있습니다.
                .csrf(csrf -> csrf.disable())
                .cors(AbstractHttpConfigurer::disable)
                // 모든 요청에 대해 인증을 넘깁니다.
                .authorizeHttpRequests(registry -> {
                    // /dashboard 엔드포인트에 대해, 관리자와 게스트 권한을 가진 사용자만 접근할 수 있도록 설정합니다.
                    registry.requestMatchers("/dashboard").hasAnyAuthority(Constants.AUTHORITY_ADMIN, Constants.AUTHORITY_GUEST)
                            // /admin와 /management 엔드포인트에 대해, 관리자 권한을 가진 사용자만 접근할 수 있도록 설정합니다.
                            .requestMatchers("/admin", "/management").hasAnyAuthority(Constants.AUTHORITY_ADMIN)
                            // /api/v1/data/admin 엔드포인트에 대해, 관리자 권한을 가진 사용자만 접근할 수 있도록 설정합니다.
                            .requestMatchers("/api/v1/data/admin/**").hasAnyAuthority(Constants.AUTHORITY_ADMIN)
                            // /api/v1/data 엔드포인트에 대해, 관리자와 게스트 권한을 가진 사용자만 접근할 수 있도록 설정합니다.
                            .requestMatchers("/api/v1/data/**").hasAnyAuthority(Constants.AUTHORITY_ADMIN, Constants.AUTHORITY_GUEST)
                            // /api/v1/data 엔드포인트에 대해, 관리자 권한을 가진 사용자만 접근할 수 있도록 설정합니다.
                            .requestMatchers("/api/v1/user/**").hasAnyAuthority(Constants.AUTHORITY_ADMIN)
                            // /api/v1/auth 엔드포인트에 대해, 모든 사용자가 접근할 수 있도록 설정합니다.
                            .requestMatchers("/api/v1/auth/**").permitAll()
                            // 다른 모든 링크는 허용합니다.
                            .anyRequest().permitAll()
                    ;
                })
                // 로그아웃 엔드포인트를 설정합니다.
                .logout(customizer -> {
                    customizer.logoutUrl("/logout");
                    customizer.logoutSuccessUrl("/?logout=true");
                })
                // 로그인 엔드포인트를 설정합니다.
                .formLogin(customizer -> {
                    customizer
                            // 로그인 페이지를 /login으로 설정합니다.
                            .loginPage("/login")
                            // 로그인 페이지에 대해 모든 사용자가 접근할 수 있도록 설정합니다.
                            .permitAll()
                            // 로그인 성공시 리다이렉트할 페이지를 설정합니다.
                            .defaultSuccessUrl("/dashboard")
                            // 로그인 실패시 리다이렉트할 페이지를 설정합니다.
                            .failureUrl("/login?error=true");
                })
                // JWT 필터를 추가합니다.
                // JWT 필터는 로그인 전 수행됩니다.
                .addFilterBefore(filterChain, UsernamePasswordAuthenticationFilter.class)
        ;
        return security.build();
    }


    // @Bean 어노테이션을 사용하여 해당 메서드가 스프링 빈임을 선언합니다.
    @Bean
    public BCryptPasswordEncoder encoder() {
        // 사용되는 비밀번호 인코더를 BCrypt로 설정합니다.
        return encoder;
    }

}
