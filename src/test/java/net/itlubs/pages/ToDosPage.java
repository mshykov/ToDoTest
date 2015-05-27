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

    public String todosMainUrl = "http://todomvc.com/examples/troopjs_require";
    private SelenideElement newTask = $("#new-todo");
    private SelenideElement itemsLeftCounter = $("#todo-count>strong");
    private SelenideElement clearCompletedButton = $("#clear-completed");
    private SelenideElement toggleAllCheckbox = $("#toggle-all");
    public ElementsCollection tasks = $$("#todo-list>li");
    private ElementsCollection footerFilters = $$("#filters>li");
    private enum footerFilterElements = {ALL("All"), ACTIVE("Active"), COMPLETED("Completed")};

    @Step
    public void loadUrl() {
        open(todosMainUrl);
    }

    public void addTask(String text) {
        newTask.setValue(text).pressEnter();
    }

    @Step
    public void editTask(String from, String to) {
        WebElement taskToEdit = tasks.find(exactText(from)).find("label");

        actions().moveToElement(taskToEdit).doubleClick().perform();
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

    public String getItemsLeftCount() {
        return itemsLeftCounter.getSelectedText();
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

    private void clickOnFilterElement(String pageName) {
        footerFilters.findBy(exactText(pageName)).find("a").click();
    }

    @Step
    public ActivePage openActivePage() {
        clickOnFilterElement(footerFilterElements.ACTIVE);
        return new ActivePage();
    }

    @Step
    public CompletedPage openCompletedPage() {
        clickOnFilterElement(footerFilterElements.COMPLETED);
        return new CompletedPage();
    }

    @Step
    public AllPage openAllPage() {
        clickOnFilterElement(footerFilterElements.ALL);
        return new AllPage();
    }

}
