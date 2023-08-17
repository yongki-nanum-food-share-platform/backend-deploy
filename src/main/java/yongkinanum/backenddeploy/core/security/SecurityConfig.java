package yongkinanum.backenddeploy.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import yongkinanum.backenddeploy.core.error.exception.Exception401;
import yongkinanum.backenddeploy.core.error.exception.Exception403;
import yongkinanum.backenddeploy.core.utils.FilterResponseUtils;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 권한에 따라 허용하는 url 설정
        // /login, /signup 페이지는 모두 허용, 다른 페이지는 인증된 사용자만 허용

        // 1. CSRF 해제
        http.csrf().disable(); // postman 접근해야 함!! - CSR 할때!!

        http
                .authorizeRequests()
                .antMatchers("/users/login", "/users/regist", "/users/regist/check").permitAll()
                .anyRequest().authenticated();

        // 2. iframe 거부
        http.headers().frameOptions().sameOrigin();

        // 3. cors 재설정
        http.cors().configurationSource(configurationSource());

        // 4. jSessionId 사용 거부 (5번을 설정하면 jsessionId가 거부되기 때문에 4번은 사실 필요 없다)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 5. form 로긴 해제 (UsernamePasswordAuthenticationFilter 비활성화)
        http.formLogin().disable();

        // 6. 로그인 인증창이 뜨지 않게 비활성화
        http.httpBasic().disable();

        // 7. 커스텀 필터 적용 (시큐리티 필터 교환)
        //http.apply(new CustomSecurityFilterManager());

        // 8. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint(
                (request, response, authException) -> FilterResponseUtils.unAuthorized(response, new Exception401("인증되지 않았습니다")));

        // 9. 권한 실패 처리
        http.exceptionHandling().accessDeniedHandler(
                (request, response, accessDeniedException) -> FilterResponseUtils.forbidden(response, new Exception403("권한이 없습니다")));

//        // 11. 인증, 권한 필터 설정
//        http.authorizeRequests(
//                authorize -> authorize.antMatchers("/carts/**", "/options/**", "/orders/**", "/users/**").authenticated()
//                        .antMatchers("/admin/**")
//                        .access("hasRole('ADMIN')")
//                        .anyRequest().permitAll()
//        );

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
