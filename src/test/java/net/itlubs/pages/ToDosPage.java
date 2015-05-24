package net.itlubs.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.actions;

/**
 * Created by Shykov M. on 24.05.15.
 * ver 0.9
 */
public class ToDosPage {

    public static String todosMainUrl = "http://todomvc.com/";
    public SelenideElement newTask = $("#new-todo");
    public SelenideElement itemsLeftCounter = $("#todo-count>strong");
    public SelenideElement clearCompletedButton = $("#clear-completed");
    public SelenideElement toggleAllCheckbox = $("#toggle-all");
    public ElementsCollection tasks = $$("#todo-list>li");
    public ElementsCollection footerFilters = $$("#filters>li");

    //tasks.filter(visible).shouldHave("a", "b"); // visible text on the screen

    public void addTask(String text) {
        newTask.setValue(text).pressEnter();
    }

    @Step
    public void editTask(String from, String to) {
        doubleClick(tasks.find(exactText(from)).find("label"));
        $(".editing").find(".edit").setValue(to).pressEnter();
    }

    private void doubleClick(WebElement element) {
        actions().moveToElement(element).doubleClick().perform();
    }

    @Step
    public void deleteTask(String taskToDelete) {
        tasks.find(exactText(taskToDelete)).hover().find(".destroy").click();
    }

    @Step
    public void toggleTask(String task) {
        tasks.find(exactText(task)).find(".toggle").click();
    }

    @Step
    public void toggleAllTasks() {
        toggleAllCheckbox.click();
    }

    public int getCounterValue() {
        return Integer.valueOf(itemsLeftCounter.toString());
    }

    @Step
    public void clearCompletedTasks() {
        clearCompletedButton.click();
    }
}
