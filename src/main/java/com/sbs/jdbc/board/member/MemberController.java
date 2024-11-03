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

    if(rq.isLogined()) {
      System.out.println("로그아웃 후 이용해주세요.");
      return;
    }

    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.scanner.nextLine();

      member = memberService.findByMemberLoginId(loginId);

      if (member != null) {
        System.out.println("현재 입력하신 로그인 아이디는 이미 존재하는 로그인 아이디입니다.");
        continue;
      }

      if (loginId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      break;
    }

    // 로그인 패스워드 입력
    while (true) {
      System.out.print("로그인 패스워드 : ");
      loginPw = Container.scanner.nextLine();

      if (loginPw.trim().isEmpty()) {
        System.out.println("로그인 패스워드를 입력해주세요.");
        continue;
      }

      while (true) {
        System.out.print("로그인 패스워드 확인 : ");
        loginPwConfirm = Container.scanner.nextLine();

        if (loginPwConfirm.trim().isEmpty()) {
          System.out.println("로그인 패스워드확인을 입력해주세요.");
          continue;
        }

        if (!loginPwConfirm.equals(loginPw)) {
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

      if (name.trim().isEmpty()) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    int id = memberService.join(loginId, loginPw, name);

    System.out.printf("%d번 회원이 생성되었습니다.\n", id);
    System.out.printf("'%s'님 회원 가입 되었습니다\n", name);
  }

  public void doLogin(Rq rq) {
    String loginId;
    String loginPw;
    Member member;

    if(rq.isLogined()) {
      System.out.println("로그아웃 후 이용해주세요.");
      return;
    }

    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.scanner.nextLine();

      if (loginId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      member = memberService.findByMemberLoginId(loginId);

      if(member == null) {
        System.out.println("입력하신 로그인 아이디는 존재하지 않습니다.");
        continue;
      }

      break;
    }

    int loginPwTryCount = 0;
    int loginPwTryMaxCount = 3;

    // 로그인 패스워드 입력
    while (true) {
      if(loginPwTryCount >= loginPwTryMaxCount) {
        System.out.println("비밀번호 틀린 횟수를 초과하였습니다. 확인 후 다시 입력해주세요.");
        return;
      }

      System.out.print("로그인 패스워드 : ");
      loginPw = Container.scanner.nextLine();

      if (loginPw.trim().isEmpty()) {
        System.out.println("로그인 패스워드를 입력해주세요.");
        continue;
      }

      if(!member.getLoginPw().equals(loginPw)) {
        loginPwTryCount++;
        System.out.println("로그인 비밀번호가 일치하지 않습니다.");
        System.out.printf("비밀번호 틀린 횟수(%d / %d)\n", loginPwTryCount, loginPwTryMaxCount);
        continue;
      }

      break;
    }

    rq.setSessionAttr("loginedMember", member);

    System.out.printf("'%s'님 로그인 되었습니다\n", member.getName());
  }

  public void doLogout(Rq rq) {
    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    rq.removeSessionAttr("loginedMember");
    System.out.println("로그아웃 되었습니다.");
  }

  public void showMyPage(Rq rq) {
    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Member member = (Member) rq.getSessionAttr("loginedMember");

    System.out.printf("현재 로그인 한 회원 이름은 %s 입니다.\n", member.getName());
  }
}
