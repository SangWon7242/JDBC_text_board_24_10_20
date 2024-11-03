package com.sbs.jdbc.board;

import com.sbs.jdbc.board.container.Container;
import com.sbs.jdbc.board.session.Session;
import com.sbs.jdbc.board.util.Util;
import lombok.Getter;

import java.util.Map;

public class Rq {
  public String url;
  @Getter
  public String urlPath;
  @Getter
  public Map<String, String> params;
  public Session session;
  public String loginedMember;

  public Rq(String url) {
    this.url = url;
    session = Container.session;
    loginedMember = "loginedMember";

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

  // 로그인 되었는지 묻는 메서드
  public boolean isLogined() {
    return hasSessionAttr(loginedMember);
  }

  public Object getSessionAttr(String attrName) {
    return session.getAttribute(attrName);
  }

  public void setSessionAttr(String attrName, Object value) {
    session.setAttribute(attrName, value);
  }

  public boolean hasSessionAttr(String attrName) {
    return session.hasAttribute(attrName);
  }

  public void removeSessionAttr(String attrName) {
    session.removeAttribute(attrName);
  }
}
