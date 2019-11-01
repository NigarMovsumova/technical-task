package az.technical.task.technicaltask.config;

import az.technical.task.technicaltask.filter.AuthenticationTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationTokenFilter authenticationTokenFilter;

    public SecurityConfig(
            AuthenticationTokenFilter authenticationTokenFilter) {
        this.authenticationTokenFilter = authenticationTokenFilter;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationTokenFilter> registration() {
        FilterRegistrationBean<AuthenticationTokenFilter> registrationBean =
                new FilterRegistrationBean<>(authenticationTokenFilter);
        registrationBean.setEnabled(true);
        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'");

        http.cors()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers()
                .antMatchers("/accounts/**", "/mail/**")
                .and()
                .addFilterBefore(authenticationTokenFilter, FilterSecurityInterceptor.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui", "/swagger-resources/**",
                        "/configuration/security", "/swagger-ui.html/**", "/webjars/**");
    }
}
