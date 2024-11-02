package com.sbs.jdbc.board.member;

import com.sbs.jdbc.board.container.Container;

public class MemberService {
  private MemberRepository memberRepository;

  public MemberService() {
    memberRepository = Container.memberRepository;
  }

  public Member findByMemberLoginId(String loginId) {
    return memberRepository.findByMemberLoginId(loginId);
  }

  public int join(String loginId, String loginPw, String name) {
    return memberRepository.join(loginId, loginPw, name);
  }
}
