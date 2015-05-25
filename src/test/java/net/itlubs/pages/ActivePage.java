package net.itlubs.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov M. on 24.05.15.
 * ver 0.9
 */
public class ActivePage extends ToDosPage {

    private ElementsCollection tasks = $$("#todo-list>.active");

    public ElementsCollection getTasks(){
        return tasks;
    }

    public void loadUrl() {
        open(getTodosMainUrl() + "/#/active");
    }
}
