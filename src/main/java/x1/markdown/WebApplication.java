package x1.markdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import x1.markdown.security.Account;
import x1.markdown.security.AccountRepository;
import x1.markdown.security.SpringSecurityAuditorAware;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableMongoAuditing
public class WebApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

    @Bean
    CommandLineRunner init(final AccountRepository accountRepository) {

        return (String... arg0) -> {
            accountRepository.save(new Account("x1-user", "x1-user", AuthorityUtils.createAuthorityList("ROLE_USER")));
            accountRepository.save(new Account("x1-admin", "x1-admin", AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER")));
        };

    }

    @Bean
    public AuditorAware<String> myAuditorProvider() {
        return new SpringSecurityAuditorAware();
    }

    @Configuration
    static class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        AccountRepository accountRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        UserDetailsService userDetailsService() {
            return (String username) -> {
                Account account = accountRepository.findByUsername(username);
                if (account != null) {
                    return new User(account.getUsername(), account.getPassword(), true, true, true, true,
                            account.getRoles());
                } else {
                    throw new UsernameNotFoundException("could not find the user '"
                            + username + "'");
                }
            };
        }
    }

    @EnableWebSecurity
    @EnableWebMvcSecurity
    @Configuration
    @EnableGlobalMethodSecurity(securedEnabled = true)
    static class WebSecurityConfigBean extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/js/**", "/css/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin().defaultSuccessUrl("/resource")
                    .and().logout().and().authorizeRequests()
                    .antMatchers("/index.html", "/home.html", "/login.html", "/", "/access", "/logout").permitAll().anyRequest()
                    .authenticated()
                    .and().csrf().disable();
//            http.authorizeRequests().anyRequest().fullyAuthenticated().and().
//                    httpBasic().and().
//                    csrf().disable();
        }

    }
}
