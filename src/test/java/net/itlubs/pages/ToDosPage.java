package net.itlubs.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by Shykov M. on 24.05.15.
 * ver 0.9
 */
public class ToDosPage {

    private String todosMainUrl = "http://todomvc.com/examples/troopjs_require";
    private SelenideElement newTask = $("#new-todo");
    private SelenideElement itemsLeftCounter = $("#todo-count>strong");
    private SelenideElement clearCompletedButton = $("#clear-completed");
    private SelenideElement toggleAllCheckbox = $("#toggle-all");
    private ElementsCollection tasks = $$("#todo-list>li");
    private ElementsCollection footerFilters = $$("#filters>li");

    public String getTodosMainUrl() {
        return todosMainUrl;
    }

    public ElementsCollection getTasks() {
        return tasks;
    }

    public void loadUrl() {
        open(todosMainUrl);
    }

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

    @Step
    public void clearCompletedTasks() {
        clearCompletedButton.click();
    }

    @Step
    public void assertItemsLeftCounter(int leftItemsCounter) {
        itemsLeftCounter.shouldHave(exactText(Integer.toString(leftItemsCounter)));
    }

    @Step
    public void visibleItemOnPage(String... tasksToCheck) {
        for (String i : tasksToCheck) {
            tasks.findBy(exactText(i)).shouldBe(visible);
        }
    }

    @Step
    public void hiddenItemOnPage(String... tasksToCheck) {
        for (String i : tasksToCheck) {
            tasks.findBy(exactText(i)).shouldBe(hidden);
        }
    }

}
