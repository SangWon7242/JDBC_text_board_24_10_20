package com.sbs.jdbc.board.article;

import com.sbs.jdbc.board.Rq;
import com.sbs.jdbc.board.container.Container;
import com.sbs.jdbc.board.member.Member;

import java.util.List;

public class ArticleController {
  private ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;
  }

  public void doWrite(Rq rq) {
    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    System.out.println("== 게시물 작성 ==");

    Member member = (Member) rq.getSessionAttr("loginedMember");
    int memberId = 0;

    if(member != null) {
      memberId = member.getId();
    }

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

    int id = articleService.write(memberId, subject, content);

    System.out.printf("%d번 게시물이 추가되었습니다.\n", id);
  }

  public void showList(Rq rq) {
    int page = rq.getIntParam("page", 1);
    String searchKeyword = rq.getParam("searchKeyword", "");
    int pageItemCount = 10; // 한 페이지당 보여질 게시물 개수
    pageItemCount = 5; // 임시

    List<Article> articles = articleService.getArticles(page, pageItemCount, searchKeyword);

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("== 게시물 리스트 ==");

    System.out.println("== 번호 | 제목 | 작성 날짜 | 작성자 | 조회수 ==");

    for (Article article : articles) {
      System.out.printf(" %d | %s | %s | %s | %d\n",
          article.getId(), article.getSubject(), article.getRegDate(), article.getExtra__writerName(), article.getHit());
    }
  }

  public void doModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Member member = (Member) rq.getSessionAttr("loginedMember");
    Article article = articleService.findByArticleId(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != member.getId()) {
      System.out.println("권한이 없습니다.");
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

    articleService.update(id, subject, content);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", article.getId());
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    articleService.increaseHit(id);

    Article article = articleService.findByArticleId(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("== 게시물 상세보기 ==");
    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("작성날짜 : %s\n", article.getRegDate());
    System.out.printf("수정날짜 : %s\n", article.getUpdateDate());
    System.out.printf("작성자 : %s\n", article.getExtra__writerName());
    System.out.printf("제목 : %s\n", article.getSubject());
    System.out.printf("내용 : %s\n", article.getContent());
    System.out.printf("조회수 : %s\n", article.getHit());
  }

  public void doDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    if(!rq.isLogined()) {
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    Member member = (Member) rq.getSessionAttr("loginedMember");
    Article article = articleService.findByArticleId(id);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    if(article.getMemberId() != member.getId()) {
      System.out.println("권한이 없습니다.");
      return;
    }

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
