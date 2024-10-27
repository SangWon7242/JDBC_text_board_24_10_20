package com.sbs.jdbc.board.container;

import com.sbs.jdbc.board.article.ArticleController;

import java.util.Scanner;

public class Container {
  public static Scanner scanner;

  public static ArticleController articleController;

  static {
    scanner = new Scanner(System.in);

    articleController = new ArticleController();
  }
}
