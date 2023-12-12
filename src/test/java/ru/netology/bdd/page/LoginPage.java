package ru.netology.bdd.page;

import ru.netology.bdd.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public VerificationPage validLogin(DataGenerator.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }
}
