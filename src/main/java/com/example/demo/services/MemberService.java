package com.example.demo.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member updateMember(Long id, Member updatedMember) {

        Member existingMember =
                memberRepository.findById(id).orElse(null);

        if (existingMember == null) {
            return null;
        }

        existingMember.setName(updatedMember.getName());
        existingMember.setEmail(updatedMember.getEmail());
        existingMember.setPhone(updatedMember.getPhone());

        return memberRepository.save(existingMember);
    }

    public String deleteMember(Long id) {

        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return "Member deleted successfully";
        }

        return "Member not found";
    }
}