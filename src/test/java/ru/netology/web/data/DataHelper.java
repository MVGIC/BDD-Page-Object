package ru.netology.web.data;

import lombok.Value;
import ru.netology.web.page.TransferPage;

public class DataHelper {
  private DataHelper() {
  }

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  public static AuthInfo getOtherAuthInfo(AuthInfo original) {
    return new AuthInfo("petya", "123qwerty");
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }

  @Value
  public static class TransferInfo {
    private String amount;
    private String from;
  }

  public static TransferInfo getTransferInfoFor() {
    return new TransferInfo("100", "0000_0000_0000_0002");
  }

  public static TransferInfo getAnotherTransferInfoFor() {
    return new TransferInfo("200", "0000_0000_0000_0001");
  }
}
