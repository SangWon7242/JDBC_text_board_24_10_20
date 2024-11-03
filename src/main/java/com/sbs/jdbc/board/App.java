package com.sbs.jdbc.board;

import com.sbs.jdbc.board.article.ArticleController;
import com.sbs.jdbc.board.container.Container;
import com.sbs.jdbc.board.member.Member;
import com.sbs.jdbc.board.member.MemberController;
import com.sbs.jdbc.board.session.Session;
import com.sbs.jdbc.board.util.MysqlUtil;

public class App {
  public MemberController memberController;
  public ArticleController articleController;

  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    // true는 개발자 모드이다.(개발할 때 좋다.)
    return true;
  }

  public App() {
    memberController = Container.memberController;
    articleController = Container.articleController;
  }

  void run() {
    System.out.println("== 자바 텍스트 게시판 시작 ==");

    try {
      while (true) {
        Session session = Container.session;
        Member member = (Member) session.getAttribute("loginedMember");

        String promptName = "명령";

        if(member != null) {
          promptName = member.getLoginId();
        }

        System.out.printf("%s) ", promptName);
        String cmd = Container.scanner.nextLine();
        Rq rq = new Rq(cmd);

        // DB 세팅
        MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "text_board");

        MysqlUtil.setDevMode(isDevMode());
        // DB 끝

        // 액션 메서드 시작
        doAction(rq);
      }
    } finally {
      Container.scanner.close();
    }
  }

  private void doAction(Rq rq) {
    switch (rq.getUrlPath()) {
      case "/usr/article/write" -> articleController.doWrite(rq);
      case "/usr/article/list" -> articleController.showList();
      case "/usr/article/modify" -> articleController.doModify(rq);
      case "/usr/article/detail" -> articleController.showDetail(rq);
      case "/usr/article/delete" -> articleController.doDelete(rq);
      case "/usr/member/join" -> memberController.doJoin(rq);
      case "/usr/member/login" -> memberController.doLogin(rq);
      case "/usr/member/logout" -> memberController.doLogout(rq);
      case "/usr/member/mypage" -> memberController.showMyPage(rq);
      case "exit" -> {
        System.out.println("== 게시판을 종료합니다. ==");
        System.exit(0);
      }
      default -> System.out.println("잘못 입력 된 명령어입니다.");
    }
  }
}
