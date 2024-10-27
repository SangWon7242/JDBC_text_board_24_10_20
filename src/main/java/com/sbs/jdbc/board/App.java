package com.sbs.jdbc.board;

import com.sbs.jdbc.board.article.Article;
import com.sbs.jdbc.board.article.ArticleController;
import com.sbs.jdbc.board.container.Container;
import com.sbs.jdbc.board.util.MysqlUtil;
import com.sbs.jdbc.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
  public Scanner sc;
  public ArticleController articleController;

  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    // true는 개발자 모드이다.(개발할 때 좋다.)
    return true;
  }

  public App() {
    sc = Container.scanner;
    articleController = Container.articleController;
  }

  void run() {
    System.out.println("== 자바 텍스트 게시판 시작 ==");

    try {
      while (true) {
        System.out.print("명령) ");
        String cmd = sc.nextLine();

        Rq rq = new Rq(cmd);

        // DB 세팅
        MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "text_board");

        MysqlUtil.setDevMode(isDevMode());
        // DB 끝

        // 액션 메서드 시작
        doAction(rq);
      }
    } finally {
      sc.close();
    }
  }

  private void doAction(Rq rq) {
    switch (rq.getUrlPath()) {
      case "/usr/article/write" -> articleController.doWrite();
      case "/usr/article/list" -> articleController.showList();
      case "/usr/article/modify" -> articleController.doModify(rq);
      case "/usr/article/detail" -> articleController.showDetail(rq);
      case "/usr/article/delete" -> articleController.doDelete(rq);
      case "exit" -> {
        System.out.println("== 게시판을 종료합니다. ==");
        System.exit(0);
      }
      default -> System.out.println("잘못 입력 된 명령어입니다.");
    }
  }
}
