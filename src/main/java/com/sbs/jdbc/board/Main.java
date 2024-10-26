package com.sbs.jdbc.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
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

        System.out.println("생성 된 게시물 객체 : " + article);

        articles.add(article);

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
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