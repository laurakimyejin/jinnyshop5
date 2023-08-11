package com.jinnyshop5.member.controller;

import com.jinnyshop5.member.dto.MemberFormDto;
import com.jinnyshop5.member.dto.request.AddMemberRequestDto;
import com.jinnyshop5.member.dto.request.MemberLoginRequestDto;
import com.jinnyshop5.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입 뷰
    @GetMapping("/members/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "/member/memberForm";
    }

    //회원가입 요청
    @PostMapping("/members/new")
    public String memberForm(AddMemberRequestDto addMemberRequest){
        memberService.memberSave(addMemberRequest);
        return "redirect:/";
    }

    //로그인 뷰
    @GetMapping("/members/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    //로그인 에러 뷰
    @GetMapping("/members/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인하세요");
        return "/member/memberLoginForm";
    }

    @PostMapping("/members/login")
    public String formLoginMember(MemberLoginRequestDto requestDto) {

//        memberService.memberLogin(requestDto);

        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/members/login";
    }


}
