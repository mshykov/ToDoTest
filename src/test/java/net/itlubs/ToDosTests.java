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

    public void assertTextAddedToList(int size, String str) {
        int getElement = size - 1;

        todoBlock.shouldHaveSize(size);
        todoBlock.get(getElement).shouldHave(text(str));
    }

    public void assertFooterExists(String str) {
        footer.shouldBe(present);
        countElements.shouldHave(text(str));
    }
        
    @Test
    public void testCreateTask() {
        String t1 = "abcdABCD1234!@#$";
        String t2 = "_+)({}|\"¥¨›„¨ŽººÒ><>?:|}{";
        String t3 = "text text text text TEXT TEXT TEXT";
        String t4 = "\'kashdkjahdkjqhekjahkjahdkjhzkjchkjahsdkjwhekja\'";

        //Step 1: Open todomvc.com in the web broweser
        open("http://todomvc.com/examples/troopjs_require/#/");
        //[VERIFICATION]: List is empty; footer is hidden
        todoBlock.shouldBe(empty);
        footer.shouldBe(hidden);

        //Step 2: Add all items to the list
        addTask(t1);
        assertTextAddedToList(1, t1);
        assertFooterExists("1");
        addTask(t2);
        addTask(t3);
        addTask(t4);
        assertTextAddedToList(4, t4);
        assertFooterExists("4");

        //Step 3: Remove 2nd task from the list
        todoBlock.get(1).hover();
        todoBlock.get(1).find(".destroy").click();
        //[VERIFICATION]: 2nd task deleted; footer updated
        todoBlock.shouldHaveSize(3);
        assertFooterExists("3");
        
        //Step 4: Check the last one as completed and clear all completed tasks
        todoBlock.get(2).find(".toggle").click();
        clearCompletedTasks();
        //[VERIFICATION]: 4th task removed from the list; footer updated
        todoBlock.shouldHaveSize(2);
        assertFooterExists("2");

        //Step 5: Mark all task as completed and clear all completed tasks
        $("#toggle-all").click();
        clearCompletedTasks();
        //[VERIFICATION]:List is empty; footer is hidden
        todoBlock.shouldBe(empty);
        footer.shouldBe(hidden);
    }
}