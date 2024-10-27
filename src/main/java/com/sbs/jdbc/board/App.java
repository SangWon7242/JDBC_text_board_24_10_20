package com.sbs.jdbc.board;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  // MySQL 데이터베이스 URL, 사용자명, 비밀번호를 설정합니다.
  private static final String URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // your_database를 데이터베이스 이름으로 변경하세요.
  private static final String USER = "sbsst";
  private static final String PASSWORD = "sbs123414";

  public int lastArticleId;
  public List<Article> articles;

  public App() {
    lastArticleId = 0;
    articles = new ArrayList<>();
  }

  void run() {
    System.out.println("== 자바 텍스트 게시판 시작 ==");

    Scanner sc = new Scanner(System.in);

    try {
      while (true) {
        System.out.print("명령) ");
        String cmd = sc.nextLine();

        Rq rq = new Rq(cmd);

        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          // JDBC 드라이버 로드
          Class.forName("com.mysql.cj.jdbc.Driver");

          // 데이터베이스 연결
          conn = DriverManager.getConnection(URL, USER, PASSWORD);
          System.out.println("데이터베이스에 성공적으로 연결되었습니다.");

          // 액션 메서드 시작
          doAction(conn, pstat, rq, sc);

        } catch (ClassNotFoundException e) {
          System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
          e.printStackTrace();
        } catch (SQLException e) {
          System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
          e.printStackTrace();
        } finally {
          // 자원 해제
          try {
            if (conn != null && !conn.isClosed()) conn.close();
            System.out.println("데이터베이스 연결이 닫혔습니다.");
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    } finally {
      sc.close();
    }
  }

  private void doAction(Connection conn, PreparedStatement pstat, Rq rq, Scanner sc) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 작성 ==");

      System.out.print("제목 : ");
      String subject = sc.nextLine();

      if (subject.trim().isEmpty()) {
        System.out.println("제목을 입력해주세요.");
        return;
      }

      System.out.print("내용 : ");
      String content = sc.nextLine();

      if (content.trim().isEmpty()) {
        System.out.println("내용을 입력해주세요.");
        return;
      }

      int id = ++lastArticleId;

      Article article = new Article(id, subject, content);

      try {
        // SQL 삽입 쿼리
        String sql = "INSERT INTO article";
        sql += " SET regDate = NOW()";
        sql += ", updateDate = NOW()";
        sql += ", `subject` = \"%s\"".formatted(subject);
        sql += ", content = \"%s\";".formatted(content);

        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate();

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);

      } catch (SQLException e) {
        System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
        e.printStackTrace();
      } finally {
        // 자원 해제
        try {
          if (pstat != null && !pstat.isClosed()) pstat.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      ResultSet rs = null;

      try {
        // SQL 조회 쿼리
        String sql = "SELECT *";
        sql += " FROM article";
        sql += " ORDER BY id DESC";

        pstat = conn.prepareStatement(sql);

        // 쿼리 실행
        rs = pstat.executeQuery();

        // 결과 출력
        // rs.next() : 다음장으로 넘긴다.
        while (rs.next()) {
          int id = rs.getInt("id");
          LocalDateTime regDate = rs.getTimestamp("regDate").toLocalDateTime();
          LocalDateTime updateDate = rs.getTimestamp("updateDate").toLocalDateTime();
          String subject = rs.getString("subject");
          String content = rs.getString("content");

          Article article = new Article(id, regDate, updateDate, subject, content);
          articles.add(article);
        }

        System.out.println(articles);

      } catch (SQLException e) {
        System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
        e.printStackTrace();
      } finally {
        // 자원 해제
        try {
          if (rs != null) rs.close();
          if (pstat != null) pstat.close();
          System.out.println("데이터베이스 연결이 닫혔습니다.");
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.println("== 게시물 리스트 ==");

      System.out.println("== 번호 | 제목 | 작성 날짜 ==");

      for (Article article : articles) {
        System.out.printf(" %d | %s | %s\n", article.id, article.subject, article.regDate);
      }
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      System.out.println("== 게시물 수정 ==");
      System.out.print("제목 : ");
      String subject = sc.nextLine();

      if (subject.trim().isEmpty()) {
        System.out.println("제목을 입력해주세요.");
        return;
      }

      System.out.print("내용 : ");
      String content = sc.nextLine();

      if (content.trim().isEmpty()) {
        System.out.println("내용을 입력해주세요.");
        return;
      }

      try {
        String sql = "UPDATE article";
        sql += " SET updateDate = NOW()";
        sql += ", `subject` = \"%s\"".formatted(subject);
        sql += ", content = \"%s\"".formatted(content);
        sql += " WHERE id = %d;".formatted(id);

        System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate();

      } catch (SQLException e) {
        System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
        e.printStackTrace();
      } finally {
        // 자원 해제
        try {
          if (pstat != null && !pstat.isClosed()) pstat.close();
          System.out.println("데이터베이스 연결이 닫혔습니다.");
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("== 게시판을 종료합니다. ==");
      System.exit(0);
    } else {
      System.out.println("잘못 입력 된 명령어입니다.");
    }
  }
}
