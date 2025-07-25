package net.sf.esfinge.greenframework.spring.starter.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class GreenFilterAutoConfig {

    @Bean
    @ConditionalOnBean(GreenIdentityFilter.class)
    public FilterRegistrationBean<GreenIdentityFilter> greenFilterRegistration(GreenIdentityFilter filter) {
        FilterRegistrationBean<GreenIdentityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*"); // intercepta tudo
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // executa cedo
        return registrationBean;
    }

}
