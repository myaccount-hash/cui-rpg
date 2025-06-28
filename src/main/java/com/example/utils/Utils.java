package com.example.utils;

import java.nio.charset.Charset;

public final class Utils {
  /** 日本語文字列を指定幅でフォーマット */
  public static String format(String target, int length) {
    int byteLength = target.getBytes(Charset.forName("UTF-8")).length;
    int byteDiff = (byteLength - target.length()) / 2;
    return String.format("%-" + (length - byteDiff) + "s", target);
  }
}
