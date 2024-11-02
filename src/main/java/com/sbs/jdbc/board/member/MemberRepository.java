package com.sbs.jdbc.board.member;

import com.sbs.jdbc.board.article.Article;
import com.sbs.jdbc.board.util.MysqlUtil;
import com.sbs.jdbc.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberRepository {
  private List<Member> members;

  public MemberRepository() {
    members = new ArrayList<>();
  }

  public Member findByMemberLoginId(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

    if(memberMap.isEmpty()) {
      return null;
    }

    return new Member(memberMap);
  }

  public int join(String loginId, String loginPw, String name) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", `name` = ?", name);

    int id = MysqlUtil.insert(sql);

    return id;
  }
}
