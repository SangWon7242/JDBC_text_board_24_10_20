package com.sbs.jdbc.board.container;

import com.sbs.jdbc.board.article.ArticleController;
import com.sbs.jdbc.board.article.ArticleRepository;
import com.sbs.jdbc.board.article.ArticleService;

import java.util.Scanner;

public class Container {
  public static Scanner scanner;

  public static ArticleRepository articleRepository;

  public static ArticleService articleService;

  public static ArticleController articleController;

  static {
    scanner = new Scanner(System.in);

    articleRepository = new ArticleRepository();

    articleService = new ArticleService();

    articleController = new ArticleController();
  }
}
