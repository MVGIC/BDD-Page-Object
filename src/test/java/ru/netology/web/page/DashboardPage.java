package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final String cardIdStart = "[data-test-id=\"";
    private final String cardIdFinish = "\"]";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.CardId cardId) {
        String id = cardId.getId();
        String text2 = $(cardIdStart + id + cardIdFinish).text();
        val text = cards.find(text(text2)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), +finish);
        return Integer.parseInt(value);
    }

    public TransferPage addDepositTo(DataHelper.CardId cardId) {
        String id = cardId.getId();
        $(cardIdStart + id + cardIdFinish + " button").click();
        return new TransferPage();
    }
}