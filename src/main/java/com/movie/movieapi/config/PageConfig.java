package com.movie.movieapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration(proxyBeanMethods=false)
public class PageConfig {

    /**
     * 페이징 설정
     * @return PageableHandlerMethodArgumentResolverCustomizer
     */
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizer() {
        return p -> {
            p.setOneIndexedParameters(true);
            p.setMaxPageSize(100);
            p.setPageParameterName("currentPageNo");
            p.setSizeParameterName("recordsPerPage");
        };
    }


}
