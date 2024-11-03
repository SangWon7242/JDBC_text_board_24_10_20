package com.sbs.jdbc.board.article;

import com.sbs.jdbc.board.container.Container;

import java.util.List;

public class ArticleService {
  private ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }

  public int write(int memberId, String subject, String content) {
    return articleRepository.write(memberId, subject, content);
  }

  public List<Article> getArticles() {
    return articleRepository.getArticles();
  }

  public Article findByArticleId(int id) {
    return articleRepository.findByArticleId(id);
  }

  public void update(int id, String subject, String content) {
    articleRepository.update(id, subject, content);
  }

  public void delete(int id) {
   articleRepository.delete(id);
  }
}
