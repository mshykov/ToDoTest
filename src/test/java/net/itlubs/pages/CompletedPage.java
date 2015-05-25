package net.itlubs.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov M. on 24.05.15.
 * ver 0.9
 */
public class CompletedPage extends ToDosPage {

    private ElementsCollection tasks = $$("#todo-list>.completed");

    public ElementsCollection getTasks(){
        return tasks;
    }

    public void loadUrl() {
        open(getTodosMainUrl() + "/#/completed");
    }

}
