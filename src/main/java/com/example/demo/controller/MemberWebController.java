package com.example.demo.controller;

import com.example.demo.model.Member;
import com.example.demo.services.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberWebController {

    private final MemberService memberService;

    public MemberWebController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String showMembers(Model model) {
        model.addAttribute("members", memberService.getMembers());
        model.addAttribute("member", new Member());
        return "members";
    }

    @PostMapping
    public String addMember(@ModelAttribute("member") Member member) {
        memberService.addMember(member);
        return "redirect:/members";
    }

    @PostMapping("/save")
    public String saveMember(@ModelAttribute("member") Member member) {
        memberService.addMember(member);
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String showEditMemberForm(@PathVariable Long id, Model model) {
        Member member = memberService.getMemberById(id);
        if (member == null) {
            return "redirect:/members";
        }
        model.addAttribute("member", member);
        return "edit-member";
    }

    @PostMapping("/update/{id}")
    public String updateMember(@PathVariable Long id, @ModelAttribute("member") Member member) {
        memberService.updateMember(id, member);
        return "redirect:/members";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "redirect:/members";
    }
}
