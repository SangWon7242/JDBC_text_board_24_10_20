package com.sbs.jdbc.board.member;

import com.sbs.jdbc.board.Rq;
import com.sbs.jdbc.board.container.Container;

public class MemberController {
  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;
  }

  public void doJoin(Rq rq) {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;
    Member member;
    
    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.scanner.nextLine();

      member = memberService.findByMemberLoginId(loginId);

      if(member != null) {
        System.out.println("현재 입력하신 로그인 아이디는 이미 존재하는 로그인 아이디입니다.");
        continue;
      }

      if(loginId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      break;
    }

    // 로그인 패스워드 입력
    while (true) {
      System.out.print("로그인 패스워드 : ");
      loginPw = Container.scanner.nextLine();

      if(loginPw.trim().isEmpty()) {
        System.out.println("로그인 패스워드를 입력해주세요.");
        continue;
      }

      while (true) {
        System.out.print("로그인 패스워드 확인 : ");
        loginPwConfirm = Container.scanner.nextLine();

        if(loginPwConfirm.trim().isEmpty()) {
          System.out.println("로그인 패스워드확인을 입력해주세요.");
          continue;
        }

        if(!loginPwConfirm.equals(loginPw)) {
          System.out.println("로그인 비밀번호가 일치하지 않습니다.");
          continue;
        }

        break;
      }

      break;
    }

    // 이름 입력
    while (true) {
      System.out.print("이름 : ");
      name = Container.scanner.nextLine();

      if(name.trim().isEmpty()) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    int id = memberService.join(loginId, loginPw, name);

    System.out.printf("%d번 회원이 생성되었습니다.\n", id);
    System.out.printf("'%s'님 회원 가입 되었습니다\n", name);
  }
}
