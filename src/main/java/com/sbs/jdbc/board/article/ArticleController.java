package com.sbs.jdbc.board.article;

import com.sbs.jdbc.board.Rq;
import com.sbs.jdbc.board.container.Container;
import com.sbs.jdbc.board.util.MysqlUtil;
import com.sbs.jdbc.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleController {
  public int lastArticleId;
  public List<Article> articles;

  public ArticleController() {
    lastArticleId = 0;
    articles = new ArrayList<>();
  }

  public void doWrite() {
    System.out.println("== 게시물 작성 ==");

    System.out.print("제목 : ");
    String subject = Container.scanner.nextLine();

    if (subject.trim().isEmpty()) {
      System.out.println("제목을 입력해주세요.");
      return;
    }

    System.out.print("내용 : ");
    String content = Container.scanner.nextLine();

    if (content.trim().isEmpty()) {
      System.out.println("내용을 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);

    int id = MysqlUtil.insert(sql);

    System.out.printf("%d번 게시물이 추가되었습니다.\n", id);
  }

  public void showList() {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

    if (articleListMap.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("== 게시물 리스트 ==");

    System.out.println("== 번호 | 제목 | 작성 날짜 ==");

    for (Map<String, Object> articleMap : articleListMap) {
      System.out.printf(" %d | %s | %s\n", (int) articleMap.get("id"), articleMap.get("subject"), articleMap.get("content"));
    }
  }

  public void doModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    boolean isArticleExists = MysqlUtil.selectRowBooleanValue(sql);

    if(!isArticleExists) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("== 게시물 수정 ==");
    System.out.print("제목 : ");
    String subject = Container.scanner.nextLine();

    if (subject.trim().isEmpty()) {
      System.out.println("제목을 입력해주세요.");
      return;
    }

    System.out.print("내용 : ");
    String content = Container.scanner.nextLine();

    if (content.trim().isEmpty()) {
      System.out.println("내용을 입력해주세요.");
      return;
    }

    sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    boolean isArticleExists = MysqlUtil.selectRowBooleanValue(sql);

    if(!isArticleExists) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

    Article article = new Article(articleMap);

    System.out.println("== 게시물 상세보기 ==");
    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("작성날짜 : %s\n", article.regDate);
    System.out.printf("수정날짜 : %s\n", article.updateDate);
    System.out.printf("제목 : %s\n", article.subject);
    System.out.printf("내용 : %s\n", article.content);
  }

  public void doDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    boolean isArticleExists = MysqlUtil.selectRowBooleanValue(sql);

    if(!isArticleExists) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    MysqlUtil.delete(sql);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
