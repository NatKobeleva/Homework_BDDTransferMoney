package ru.netology.bdd.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.DataGenerator;
import ru.netology.bdd.data.DataGenerator.CardInfo;
import ru.netology.bdd.page.DashboardPage;
import ru.netology.bdd.page.LoginPage;
import ru.netology.bdd.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.bdd.data.DataGenerator.*;


public class AuthTest {
    DashboardPage dashboardPage;
    CardInfo firstCardInfo;
    CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;


    @BeforeEach
    void setUp() {
        open("http://localhost:7777");
        var LoginPage = new LoginPage();
        var authInfo = DataGenerator.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = DataGenerator.getVerificationCode(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var amount = validAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.validTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }
    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        var amount = validAmount(secondCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = transferPage.validTransfer(String.valueOf(amount), secondCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldErrorMessageIfAmountMoreBalance(){
        var amount = invalidAmount(firstCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        transferPage.transferMoney(String.valueOf(amount), firstCardInfo);
        transferPage.findErrorMessage("Операция не может быть выполнена. Сумма превышает остаток на балансе");
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }
}
