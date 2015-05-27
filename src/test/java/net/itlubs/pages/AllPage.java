package net.itlubs.pages;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov M. on 24.05.15.
 * ver 0.9
 */
public class AllPage extends ToDosPage {

	@Override
    public void loadUrl() {
        open(todosMainUrl + "/#/");
    }

}
