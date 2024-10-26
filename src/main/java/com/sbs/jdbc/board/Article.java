package com.sbs.jdbc.board;

// 설계도 -> 객체
// 속성과 기능을 정의 가능
// 속성(변수), 기능(함수)

import java.time.LocalDate;
import java.time.LocalDateTime;

// 모든 클래스는 Object를 상속받는다.
public class Article {
  int id;
  LocalDate regDate;
  LocalDate updateDate;
  String subject;
  String content;

  // 생성자 메서드 : 객체가 생성 될 때 딱 한번 실행!
  public Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;
  }

  public Article(int id, LocalDate regDate, LocalDate updateDate, String subject, String content) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.subject = subject;
    this.content = content;
  }

  // 메서드 오버라이딩
  /*
  @Override
  public String toString() {
    // return "{id : %d, subject : \"%s\", content: \"%s\"}".formatted(id, subject, content);
    return "{id : %d, regDate : %s, updateDate : %s, subject : \"%s\", content: \"%s\"}".formatted(id, regDate, updateDate, subject, content);
  }
  */

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", regDate=" + regDate +
        ", updateDate=" + updateDate +
        ", subject='" + subject + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
