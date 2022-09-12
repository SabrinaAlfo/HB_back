package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

 @Override

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //tiene mas importancia en el orden que coloque los permisos. Lo + restringido en las ultimas lineas//
                .antMatchers("/web/index.html","/web/css/**","/web/img/**","/web/js/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                .antMatchers("/web/clients/current/accounts","/web/clients/current/cards", "/web/clients/current/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clients/accounts","/api/clients/current/cards","/api/clients/current/loans" ).permitAll()
                .antMatchers("/api/clients/current/cards", "/api/clients/current/accounts","/api/clients/current/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/web/clients/current/cards","/web/clients/current/loans" ).permitAll()
                .antMatchers("/ api/loans").hasAuthority("CLIENT")
                .antMatchers("/api/transactions").hasAuthority("CLIENT")
                .antMatchers("/").hasAuthority("CLIENT")
                .antMatchers("/**").hasAuthority("CLIENT");
                /*.antMatchers("/web/index/**", "/web/css/", "/web/img/", "/web/js/").permitAll()
                //.antMatchers("/manager.html").hasAuthority("ADMIN")//
                  .antMatchers("/").hasAuthority("CLIENT")
                .antMatchers("/**").hasAuthority("CLIENT");*/


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
