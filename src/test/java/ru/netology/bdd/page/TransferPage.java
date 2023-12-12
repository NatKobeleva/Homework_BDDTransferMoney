package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class TransferPage {
    private SelenideElement headingTransfer = $(byText("Пополнение карты"));
    private SelenideElement fieldAmount = $("[data-test-id=amount] input");
    private SelenideElement fieldFromCard = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public TransferPage() {
        headingTransfer.shouldBe(visible);
    }

    public void transferMoney(String amountOfTransfer, DataGenerator.CardInfo cardInfo) {
        fieldAmount.setValue(amountOfTransfer);
        fieldFromCard.setValue(cardInfo.getNumber());
        transferButton.click();
    }

    public void findErrorMessage(String expectedText) {
        error.shouldHave(text(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public DashboardPage validTransfer(String amountOfTransfer, DataGenerator.CardInfo cardInfo) {
        transferMoney(amountOfTransfer, cardInfo);
        return new DashboardPage();
    }
}
