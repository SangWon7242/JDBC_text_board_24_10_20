package com.sbs.jdbc.board.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {
  private int id;
  private LocalDateTime regDate;
  private LocalDateTime updateDate;
  private int memberId;
  private String subject;
  private String content;

  private String extra__writerName; // 작성자 이름

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");
    this.memberId = (int) articleMap.get("memberId");
    this.subject = (String) articleMap.get("subject");
    this.content = (String) articleMap.get("content");

    if(articleMap.get("extra__writerName") != null) {
      this.extra__writerName = (String) articleMap.get("extra__writerName");
    }
  }
}
