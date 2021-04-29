package picle.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationConfiguration {

    /**
     * Define how a bean of the AuthorizationFilter should be initialized.
     * @return an initialized bean
     */
    @Bean
    public FilterRegistrationBean authorizationRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        filterRegistrationBean.setFilter(authorizationFilter);

        // Most crucial part: specifies which endpoints created Filter bean will act on
        filterRegistrationBean.addUrlPatterns(
                "/user/*",
                "/activity/*",
                "/auth",
                "/score/*",
                "/social/*");
        return filterRegistrationBean;
    }
}
