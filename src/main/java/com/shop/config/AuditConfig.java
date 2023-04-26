package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing   // JPA의 Auditing기능 활성화
public class AuditConfig {
    
    // 등록자와 수정자를 처리
    @Bean 
    public AuditorAware<String> auditorProvider(){     
        return new AuditorAwareImpl();
    }
}
