package com.justedlev.auth.configuration;

import com.justedlev.auth.configuration.entrypoint.CustomAuthenticationEntryPoint;
import com.justedlev.auth.configuration.filter.CustomAuthorizationFilter;
import com.justedlev.auth.configuration.handler.CustomLogoutHandler;
import com.justedlev.auth.constant.CookieKeyConstant;
import com.justedlev.auth.constant.EndpointConstant;
import com.justedlev.auth.enumeration.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final CustomLogoutHandler customLogoutHandler;
    private static final String[] FREE_ACCESS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html/**",
            EndpointConstant.AUTH_LOGIN,
            EndpointConstant.AUTH_SIGNUP,
            EndpointConstant.AUTH_LOGIN_REFRESH,
            EndpointConstant.ACCOUNT_CONFIRM_CODE,
            EndpointConstant.LOGOUT,
    };
    private static final String[] SUPER_ADMIN_ACCESS = {
            EndpointConstant.ROLE,
            EndpointConstant.ROLE_ROLE_TYPE,
            EndpointConstant.ROLE_ROLE_TYPE_DELETE,
            EndpointConstant.ROLE_ROLE_TYPE_UPDATE,
            EndpointConstant.HISTORY_ACCOUNT,
            EndpointConstant.HISTORY_USER,
            EndpointConstant.ACCOUNT_UPDATE_MODE
    };
    private static final String[] ADMIN_ACCESS = {
            EndpointConstant.USER,
            EndpointConstant.USER_USERNAME,
            EndpointConstant.USER_USERNAME_DELETE,
            EndpointConstant.ACCOUNT + "/all"
    };
    private static final String[] SUPER_USER_ACCESS = {
            EndpointConstant.USER_USERNAME,
            EndpointConstant.ACCOUNT_NICKNAME_DELETE
    };
    private static final String[] USER_ACCESS = {
            EndpointConstant.ACCOUNT_NICKNAME,
            EndpointConstant.ACCOUNT_NICKNAME_UPDATE,
            EndpointConstant.ACCOUNT_NICKNAME_DEACTIVATE,
            EndpointConstant.ACCOUNT_NICKNAME_UPDATE_PHOTO
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .cors().and()
                .formLogin().disable();
        httpSecurity.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);
        httpSecurity.logout()
                .logoutUrl(EndpointConstant.LOGOUT)
                .logoutRequestMatcher(new AntPathRequestMatcher(EndpointConstant.LOGOUT, HttpMethod.POST.name()))
                .deleteCookies(CookieKeyConstant.ATK, CookieKeyConstant.RTK)
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .permitAll();
        httpSecurity.authorizeHttpRequests()
                .antMatchers(FREE_ACCESS).permitAll()
                .antMatchers(SUPER_ADMIN_ACCESS)
                .hasAnyRole(RoleType.SUPER_ADMIN.getType())
                .antMatchers(ADMIN_ACCESS)
                .hasAnyRole(
                        RoleType.SUPER_ADMIN.getType(),
                        RoleType.ADMIN.getType()
                )
                .antMatchers(SUPER_USER_ACCESS)
                .hasAnyRole(
                        RoleType.SUPER_ADMIN.getType(),
                        RoleType.ADMIN.getType(),
                        RoleType.SUPER_USER.getType()
                )
                .antMatchers(USER_ACCESS)
                .hasAnyRole(
                        RoleType.SUPER_ADMIN.getType(),
                        RoleType.ADMIN.getType(),
                        RoleType.SUPER_USER.getType(),
                        RoleType.USER.getType()
                )
                .anyRequest()
                .authenticated();

        return httpSecurity.build();
    }
}
