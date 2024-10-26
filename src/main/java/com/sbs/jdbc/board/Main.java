package com.sbs.jdbc.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  // MySQL 데이터베이스 URL, 사용자명, 비밀번호를 설정합니다.
  private static final String URL = "jdbc:mysql://localhost:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull"; // your_database를 데이터베이스 이름으로 변경하세요.
  private static final String USER = "sbsst"; // 사용자명을 입력하세요.
  private static final String PASSWORD = "sbs123414"; // 비밀번호를 입력하세요.

  public static void main(String[] args) {
    System.out.println("== 자바 텍스트 게시판 시작 ==");

    int lastArticleId = 0;
    List<Article> articles = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    while(true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      if(cmd.equals("/usr/article/write")) {
        System.out.println("== 게시물 작성 ==");

        System.out.print("제목 : ");
        String subject = sc.nextLine();

        if(subject.trim().isEmpty()) {
          System.out.println("제목을 입력해주세요.");
          continue;
        }

        System.out.print("내용 : ");
        String content = sc.nextLine();

        if(content.trim().isEmpty()) {
          System.out.println("내용을 입력해주세요.");
          continue;
        }

        int id = ++lastArticleId;

        Article article = new Article(id, subject, content);

        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          // JDBC 드라이버 로드
          Class.forName("com.mysql.cj.jdbc.Driver");

          // 데이터베이스 연결
          conn = DriverManager.getConnection(URL, USER, PASSWORD);
          System.out.println("데이터베이스에 성공적으로 연결되었습니다.");

          // SQL 삽입 쿼리
          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()";
          sql += ", updateDate = NOW()";
          sql += ", `subject` = \"%s\"".formatted(subject);
          sql += ", content = \"%s\";".formatted(content);

          System.out.println(sql);

          pstat = conn.prepareStatement(sql);

          int affectedRows = pstat.executeUpdate();
          System.out.println("affectedRows : " + affectedRows);

          System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);

        } catch (ClassNotFoundException e) {
          System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
          e.printStackTrace();
        } catch (SQLException e) {
          System.out.println("데이터베이스 작업 중 오류가 발생했습니다.");
          e.printStackTrace();
        } finally {
          // 자원 해제
          try {
            if (pstat != null && !pstat.isClosed()) pstat.close();
            if (conn != null && !conn.isClosed()) conn.close();
            System.out.println("데이터베이스 연결이 닫혔습니다.");
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
      else if(cmd.equals("/usr/article/list")) {
        if(articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("== 게시물 리스트 ==");

        System.out.println("== 번호 | 제목 ==");

        for(int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          System.out.printf(" %d | %s\n", article.id, article.subject);
        }
      }
      else if(cmd.equals("exit")) {
        System.out.println("== 게시판을 종료합니다. ==");
        break; // 이 시점에서 반복문을 빠져나옴
      }
      else {
        System.out.println("잘못 입력 된 명령어입니다.");
      }
    }

    sc.close();
  }
}

// 설계도 -> 객체
// 속성과 기능을 정의 가능
// 속성(변수), 기능(함수)

// 모든 클래스는 Object를 상속받는다.
class Article {
  int id;
  String subject;
  String content;

  // 생성자 메서드 : 객체가 생성 될 때 딱 한번 실행!
  Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;
  }

  // 메서드 오버라이딩
  @Override
  public String toString() {
    return "{id : %d, subject : \"%s\", content: \"%s\"}".formatted(id, subject, content);
  }
}