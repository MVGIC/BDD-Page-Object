package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.security.PrivateKey;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    //private SelenideElement fillButton = $("[data-test-id=code] button");
    private SelenideElement sumField = $("[date-test-id=amount] input");
    private SelenideElement fromField = $("[date-test-id-from] input");
    private SelenideElement endFillButton = $("[data-test-id=action-transfer] button");

    public TransferPage(){
        //fillButton.shouldBe(visible);
        endFillButton.shouldBe(visible);
    }

    public DashboardPage transferMoney (DataHelper.TransferInfo transferInfo){
        sumField.setValue(transferInfo.getAmount());
        fromField.setValue(transferInfo.getFrom());
        endFillButton.click();
        return new DashboardPage();
    }
}
