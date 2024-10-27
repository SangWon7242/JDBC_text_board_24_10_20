package com.sbs.jdbc.board;

import com.sbs.jdbc.board.util.Util;

import java.util.Map;

public class Rq {
  public String url;
  public String urlPath;
  public Map<String, String> params;

  public String getUrlPath() {
    return urlPath;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public Rq(String url) {
    this.url = url;
    urlPath = Util.getUrlPathFromUrl(this.url);
    params = Util.getParamsFromUrl(this.url);
  }

  public int getIntParam(String paramName, int defaultValue) {
    if (!params.containsKey(paramName)) return defaultValue;

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {
    if (!params.containsKey(paramName)) return defaultValue;

    return params.get(paramName);
  }
}
