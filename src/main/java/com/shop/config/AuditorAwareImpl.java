package com.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware {
    @Override
    public Optional getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if(authentication !=null){
            userId= authentication.getName();    // 현재 로그인한 사용자 정보를 조회해 사용자의 이름을 등록자와 수정자로 저장
        }

        return Optional.of(userId);
    }
}
