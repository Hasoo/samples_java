package com.github.hasoo.spam;

import com.github.hasoo.trie.Trie;
import java.util.HashMap;
import java.util.Optional;

public class Spam {

  private Trie defaultSpamPhone = new Trie();
  private HashMap<String, Trie> spamPhone = new HashMap<>();

  public synchronized boolean isSpamPhone(String username, String phone) {
    Trie trie = Optional.ofNullable(spamPhone.get(username)).orElse(this.defaultSpamPhone);
    return trie.containsNode(phone);
  }

  public synchronized void add(String phone) {
    defaultSpamPhone.insert(phone);
  }

  public synchronized void add(String username, String phone) {
    Trie trie = Optional.ofNullable(spamPhone.get(username))
        .orElseGet(() -> createUserTrie(username));
    trie.insert(phone);
  }

  public synchronized void remove(String phone) {
    defaultSpamPhone.delete(phone);
  }

  public synchronized void remove(String username, String phone) {
    Trie trie = spamPhone.get(username);
    trie.delete(phone);
  }

  private Trie createUserTrie(String username) {
    Trie trie = new Trie();
    spamPhone.put(username, trie);
    return trie;
  }

  public synchronized void clear() {
    defaultSpamPhone = new Trie();
    spamPhone.clear();
  }
}
