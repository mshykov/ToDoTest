package net.itlubs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.present;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov Maksym on 29.04.15.
 * Version 0.9b
 */
public class ToDosTests {

    ElementsCollection todoBlock = $$("#todo-list>li");
    SelenideElement footer = $("#footer");
    SelenideElement countElements = $("#todo-count");

    private void addTask(String str) {
        $("#new-todo").setValue(str).pressEnter();
    }
        
    private void clearCompletedTasks() {
        SelenideElement clearCompleted = $("#clear-completed");

        if (clearCompleted.exists()) {
            clearCompleted.click();
        }
    }

    public void isTextAddedToList(int size, String str) {
        int getElement = size - 1;

        todoBlock.shouldHaveSize(size);
        if (str != null) {
            todoBlock.get(getElement).shouldHave(text(str));
        }
    }

    public void isFooterExists(String str) {
        footer.shouldBe(present);
        countElements.shouldHave(text(str));
    }
        
    @Test
    public void testCreateTask() {
        String textToVerify1 = "abcdABCD1234!@#$";
        String textToVerify2 = "_+)({}|\"¥¨›„¨ŽººÒ><>?:|}{";
        String textToVerify3 = "text text text text TEXT TEXT TEXT";
        String textToVerify4 = "\'kashdkjahdkjqhekjahkjahdkjhzkjchkjahsdkjwhekja\'";

        //Step 1: Open todomvc.com in the web broweser
        open("http://todomvc.com/examples/troopjs_require/#/");
        //[VERIFICATION]: List is empty; footer is hidden
        todoBlock.shouldBe(empty);
        footer.shouldBe(hidden);

        //Step 2: Add all items to the list
        addTask(textToVerify1);
        isTextAddedToList(1, textToVerify1);
        isFooterExists("1");
        addTask(textToVerify2);
        addTask(textToVerify3);
        addTask(textToVerify4);
        isTextAddedToList(4, textToVerify4);
        isFooterExists("4");

        //Step 3: Remove 2nd task from the list
        SelenideElement lineToDelete = todoBlock.get(1);
        lineToDelete.hover();
        lineToDelete.find(".destroy").click();
        //[VERIFICATION]: 2nd task deleted; footer updated
        isTextAddedToList(3, null);
        isFooterExists("3");
        
        //Step 4: Check the last one as completed and clear all completed tasks
        todoBlock.get(2).find(".toggle").click();
        clearCompletedTasks();
        //[VERIFICATION]: 4th task removed from the list; footer updated
        isTextAddedToList(2, null);
        isFooterExists("2");

        //Step 5: Mark all task as completed and clear all completed tasks
        $("#toggle-all").click();
        clearCompletedTasks();
        //[VERIFICATION]:List is empty; footer is hidden
        todoBlock.shouldBe(empty);
        footer.shouldBe(hidden);
    }
}