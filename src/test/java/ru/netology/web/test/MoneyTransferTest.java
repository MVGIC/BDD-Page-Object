package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        val sum = 100;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        int balanceBeforeTransferFirstCard = dashboardPageBeforeTransfer.getCardBalance(getFirstCardInfo());
        int balanceBeforeTransferSecondCard = dashboardPageBeforeTransfer.getCardBalance(getSecondCardInfo());
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .addDepositTo(getSecondCardInfo())
                .transferMoney(sum, getFirstCardInfo());
        val balanceAfterTransferFirstCard = dashboardPageAfterTransfer.getCardBalance(getFirstCardInfo());
        val balanceAfterTransferSecondCard = dashboardPageAfterTransfer.getCardBalance(getSecondCardInfo());
        assertEquals(balanceBeforeTransferFirstCard - sum, balanceAfterTransferFirstCard);
        assertEquals(balanceBeforeTransferSecondCard + sum, balanceAfterTransferSecondCard);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        val sum = 100;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        int balanceBeforeTransferFirstCard = dashboardPageBeforeTransfer.getCardBalance(getFirstCardInfo());
        int balanceBeforeTransferSecondCard = dashboardPageBeforeTransfer.getCardBalance(getSecondCardInfo());
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .addDepositTo(getFirstCardInfo())
                .transferMoney(sum, getSecondCardInfo());
        val balanceAfterTransferFirstCard = dashboardPageAfterTransfer.getCardBalance(getFirstCardInfo());
        val balanceAfterTransferSecondCard = dashboardPageAfterTransfer.getCardBalance(getSecondCardInfo());
        assertEquals(balanceBeforeTransferFirstCard + sum, balanceAfterTransferFirstCard);
        assertEquals(balanceBeforeTransferSecondCard - sum, balanceAfterTransferSecondCard);
    }

    @Test
    void shouldLoginWithInvalidLogin() {
        val loginPage = new LoginPage();
        loginPage.invalidLogin(getOtherAuthInfo(getAuthInfo()));
        $("[data-test-id=error-notification] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldTransferExceedingAmountToFirst() {
        val sum = 11000;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        int balanceBeforeTransferFirstCard = dashboardPageBeforeTransfer.getCardBalance(getFirstCardInfo());
        int balanceBeforeTransferSecondCard = dashboardPageBeforeTransfer.getCardBalance(getSecondCardInfo());
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .addDepositTo(getFirstCardInfo())
                .transferMoney(sum, getSecondCardInfo());
        val balanceAfterTransferFirstCard = dashboardPageAfterTransfer.getCardBalance(getFirstCardInfo());
        val balanceAfterTransferSecondCard = dashboardPageAfterTransfer.getCardBalance(getSecondCardInfo());
        assertEquals(balanceBeforeTransferFirstCard + sum, balanceAfterTransferFirstCard);
        assertEquals(balanceBeforeTransferSecondCard - sum, balanceAfterTransferSecondCard);
    }
}