package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement sumField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement endDepositButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorSumBallance = $("[data-test-id=error-balance] .notification__content");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage transferMoney(int amount, DataHelper.CardInfo number) {
        sumField.setValue(Integer.toString(amount));
        fromField.setValue(number.getNumber());
        endDepositButton.click();
        return new DashboardPage();
    }

    public void transferMaxMoney (int amount, DataHelper.CardInfo number){
        sumField.setValue(Integer.toString(amount));
        fromField.setValue(number.getNumber());
        endDepositButton.click();
        errorSumBallance.shouldBe(visible)
                .shouldHave(Condition.text("Ошибка! Сумма перевода не может превышать актуальный баланс списываемой карты"));
    }
}