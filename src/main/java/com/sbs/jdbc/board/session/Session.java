package com.sbs.jdbc.board.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
  private Map<String, Object> dataStore;

  public Session() {
    dataStore = new HashMap<>();
  }

  // get(읽기), set(저장), has(포함여부), remove(삭제)
  public Object getAttribute(String key) {
    return dataStore.get(key);
  }

  public void setAttribute(String key, Object value) {
    dataStore.put(key, value);
  }

  public boolean hasAttribute(String key) {
    return dataStore.containsKey(key);
  }

  public void removeAttribute(String key) {
    dataStore.remove(key);
  }
}
