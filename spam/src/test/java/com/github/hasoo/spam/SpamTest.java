package com.github.hasoo.spam;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SpamTest {

  @Test
  void isSpamPhone() {
    Spam spam = new Spam();

    String id1 = "test1";
    String id2 = "test2";

    String phone1 = "01022222222";
    String phone2 = "01033333333";
    String phone3 = "01044444444";
    String phone4 = "01055555555";

    String phone5 = "01066666666";
    String phone6 = "01077777777";
    String phone7 = "01088888888";
    String phone8 = "01099999999";

    spam.add(phone1);
    spam.add(phone2);
    spam.add(phone3);
    spam.add(phone4);

    spam.add(id2, phone5);
    spam.add(id2, phone6);
    spam.add(id2, phone7);
    spam.add(id2, phone8);

    Assertions.assertTrue(spam.isSpamPhone(id1, phone1));
    Assertions.assertTrue(spam.isSpamPhone(id1, phone2));
    Assertions.assertTrue(spam.isSpamPhone(id1, phone3));
    Assertions.assertTrue(spam.isSpamPhone(id1, phone4));

    Assertions.assertFalse(spam.isSpamPhone(id1, phone5));
    Assertions.assertFalse(spam.isSpamPhone(id1, phone6));
    Assertions.assertFalse(spam.isSpamPhone(id1, phone7));
    Assertions.assertFalse(spam.isSpamPhone(id1, phone8));

    Assertions.assertTrue(spam.isSpamPhone(id2, phone5));
    Assertions.assertTrue(spam.isSpamPhone(id2, phone6));
    Assertions.assertTrue(spam.isSpamPhone(id2, phone7));
    Assertions.assertTrue(spam.isSpamPhone(id2, phone8));

    spam.remove(phone4);
    Assertions.assertFalse(spam.isSpamPhone(id1, phone4));

    spam.remove(id2, phone8);
    Assertions.assertFalse(spam.isSpamPhone(id2, phone8));
  }
}