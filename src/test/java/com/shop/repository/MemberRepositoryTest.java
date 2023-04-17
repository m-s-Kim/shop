package com.shop.repository;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("TEST@naver.com");
        memberFormDto.setAddress("우리집");
        memberFormDto.setName("ㅋㅋㅋㅋ");
        memberFormDto.setPassword("1231231231");
        return Member.createMember(memberFormDto, passwordEncoder);
    }


    @Test
    @DisplayName("JOIN TEST")
    public void saveMemBerTest(){
        Member member = createMember();

        Member saveMember = memberService.saveMember(member);

        assertEquals(member.getEmail(),saveMember.getEmail());
        assertEquals(member.getName(),saveMember.getName());
        assertEquals(member.getAddress(),saveMember.getAddress());
        assertEquals(member.getPassword(),saveMember.getPassword());
        assertEquals(member.getRole(),saveMember.getRole());
    }


    @Test
    @DisplayName("JOIN TEST2")
    public void saveDuplicateMemBerTest(){
        Member member1 = createMember();
        Member member2 = createMember();

        Member saveMember = memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class,()->{
            memberService.saveMember(member2);
        });
        assertEquals("가입된 정보가 있습니다.", e.getMessage());

    }
}
