package br.com.anderson.blog.utils;

import java.util.Objects;

public class PersistentUtils {

  private PersistentUtils() { }

  public static boolean equals(BasePersistent one, BasePersistent two) {
    return Objects.nonNull(one) && Objects.nonNull(two)
      && Objects.nonNull(one.getId()) && Objects.nonNull(two.getId())
      && one.getId().equals(two.getId());
  }

  public static boolean notEquals(BasePersistent one, BasePersistent two) {
    return !equals(one, two);
  }

}
