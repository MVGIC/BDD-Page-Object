package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.LoginPage;

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
        loginPage.invalidLogin(getInvalidAuthInfo());
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
                .addDepositTo(getFirstCardInfo());
        dashboardPageAfterTransfer.transferMaxMoney(sum, getSecondCardInfo());
    }
}