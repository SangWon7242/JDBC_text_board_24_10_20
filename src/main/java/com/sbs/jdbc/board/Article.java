package com.sbs.jdbc.board;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

// 모든 클래스는 Object를 상속받는다.
public class Article {
  int id;
  LocalDateTime regDate;
  LocalDateTime updateDate;
  String subject;
  String content;

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");;
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");;
    this.subject = (String) articleMap.get("subject");;
    this.content = (String) articleMap.get("content");;
  }

  public Article(int id, LocalDateTime regDate, LocalDateTime updateDate, String subject, String content) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.subject = subject;
    this.content = content;
  }

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
