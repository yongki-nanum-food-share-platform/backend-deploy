package yongkinanum.backenddeploy.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
        http.csrf().disable();

        // 2. iframe 거부
        http.headers().frameOptions().sameOrigin();

        // 3. cors 재설정
        http.cors().configurationSource(configurationSource());

        // 5. form 로긴 해제 (UsernamePasswordAuthenticationFilter 비활성화)
        http.formLogin().disable();

        // 6. 로그인 인증창이 뜨지 않게 비활성화
        http.httpBasic().disable();

        // 7. 커스텀 필터 적용 (시큐리티 필터 교환)
        http.apply(new CustomSecurityFilterManager());

        // 8. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint(
                (request, response, authException) -> FilterResponseUtils.unAuthorized(response, new Exception401("인증되지 않았습니다")));

        // 9. 권한 실패 처리
        http.exceptionHandling().accessDeniedHandler(
                (request, response, accessDeniedException) -> FilterResponseUtils.forbidden(response, new Exception403("권한이 없습니다")));

        // 11. 인증, 권한 필터 설정
        http.authorizeRequests(
                authorize -> authorize
                        .antMatchers(
                                "/users", "/users/update", "/users/unregist",
                                "/carts/**",
                                "/posts/**",
                                "/shops/{id:\\d+}")
                        .authenticated()
                        //권한 허용 테스트
                        .antMatchers("/shops/regist", "/shops/update", "/shops/unregist")
                        .hasAuthority("SHOPPER")
        );

/*        http.authorizeRequests()
                .antMatchers("/users/login", "/users/regist", "/users/regist/check").permitAll()
                .anyRequest().authenticated();*/

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 엔드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
