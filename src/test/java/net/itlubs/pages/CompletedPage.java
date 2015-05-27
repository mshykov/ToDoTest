package net.itlubs.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov M. on 24.05.15.
 * ver 0.9
 */
public class CompletedPage extends ToDosPage {

    @Override
    public ElementsCollection tasks = $$("#todo-list>.completed");

    @Override
    public void loadUrl() {
        open(todosMainUrl + "/#/completed");
    }

}
