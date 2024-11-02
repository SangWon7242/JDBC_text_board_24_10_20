package com.sbs.jdbc.board.member;

import com.sbs.jdbc.board.Rq;
import com.sbs.jdbc.board.article.Article;
import com.sbs.jdbc.board.container.Container;
import com.sbs.jdbc.board.util.MysqlUtil;
import com.sbs.jdbc.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;

public class MemberController {
  private List<Member> members;

  public MemberController() {
    members = new ArrayList<>();
  }

  public void doJoin(Rq rq) {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;
    
    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.scanner.nextLine();
      
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

    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", `name` = ?", name);

    int id = MysqlUtil.insert(sql);

    System.out.printf("%d번 회원이 생성되었습니다.\n", id);
    System.out.printf("'%s'님 회원 가입 되었습니다\n", name);
  }
}
