package net.itlubs.helper;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Collections;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.actions;

/**
 * Created by Shykov M. on 18.05.15.
 * ToDosMVC Actions
 */
public class ToDosActions {

    public static ElementsCollection tasks = $$("#todo-list>li");
    public static SelenideElement clearCompleted = $("#clear-completed");

    @Step
    public static void editTask(String from, String to) {
        doubleClick(tasks.find(text(from)).find("label"));
        $(".editing").find(".edit").setValue(to).pressEnter();
    }

    //tasks.filter(visible).shouldHave("a", "b"); // visible text on the screen

    @Step
    public static void counterLeftItems(){

    }

    public static void addTask(String text) {
        $("#new-todo").setValue(text).pressEnter();
    }

    public static void doubleClick(WebElement element) {
        actions().moveToElement(element).doubleClick().perform();
    }

    @Step
    public static void toggleTask(String task) {
        tasks.find(text(task)).find(".toggle").click();
    }
}
