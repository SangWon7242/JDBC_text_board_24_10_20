package com.sbs.jdbc.board.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
  private int id;
  private LocalDateTime regDate;
  private LocalDateTime updateDate;
  private String loginId;
  private String loginPw;
  private String name;

  public Member(Map<String, Object> MemberMap) {
    this.id = (int) MemberMap.get("id");
    this.regDate = (LocalDateTime) MemberMap.get("regDate");
    this.updateDate = (LocalDateTime) MemberMap.get("updateDate");
    this.loginId = (String) MemberMap.get("loginId");
    this.loginPw = (String) MemberMap.get("loginPw");
    this.name = (String) MemberMap.get("name");
  }
}
