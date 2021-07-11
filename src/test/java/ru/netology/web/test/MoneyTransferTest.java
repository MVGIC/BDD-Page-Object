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
        int actualAmountFirstCard = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId());
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId());
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .addDepositTo(getFirstCardId())
                .transferMoney(sum, getSecondCardInfo());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCardId());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCardId());
        assertEquals(actualAmountFirstCard, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);

    }
}