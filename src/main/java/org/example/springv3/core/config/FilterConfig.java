package org.example.springv3.core.config;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import lombok.Builder;
import org.example.springv3.core.filter.JwtAuthorizationFilter;
import org.example.springv3.user.User;
import org.hibernate.metamodel.mapping.FilterRestrictable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/*
 * @Controller, @RestController, @Service, @Repository, @Component, @Configuration
 * 이거 붙이면 IOC 에 뜬다. new 뜸
 * IOC 가 뭐임? 제어의 역전 -> 원래는 개발자가 new 하는건데, 어노테이션만 붙여놓으면 스프링에서 자동으로 new 됨
 * 제어권이 바뀌는 것, 스프링이 IoC 컨테이너에 담아놓는다.
 * 필요할 때마다 가져쓰면 됨 @Autowired 혹은 final 뭐시기 ?
 */

@Configuration // IOC 등록, 이 친구가 스프링실행 될 때 메모리에 떠야 한다.
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<?> jwtAuthorizaionFilter() {
        FilterRegistrationBean<JwtAuthorizationFilter> bean
                = new FilterRegistrationBean<>(new JwtAuthorizationFilter());
        bean.addUrlPatterns("/api/*"); // 발동시점 정해줌
        bean.setOrder(0);
        return bean;
    }
}
