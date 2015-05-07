package net.itlubs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
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

    ElementsCollection liCollection = $$("#todo-list>li");
    SelenideElement footer = $("#footer");
    SelenideElement numberOfTasks = $("#todo-count");

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
        int taskIndex = size - 1;

        liCollection.shouldHaveSize(size);
        liCollection.get(taskIndex).shouldHave(text(str));
    }

    public void assertFooterExists(String str) {
        footer.shouldBe(present);
        numberOfTasks.shouldHave(text(str));
    }
        
    @Test
    public void testCreateTask() {
        String t1 = "abcdABCD1234!@#$";
        String t2 = "_+)({}|\"¥¨›„¨ŽººÒ><>?:|}{";
        String t3 = "text text text text TEXT TEXT TEXT";
        String t4 = "\'kashdkjahdkjqhekjahkjahdkjhzkjchkjahsdkjwhekja\'";

        //Open todomvc.com in the web browser
        open("http://todomvc.com/examples/troopjs_require/#/");

        //Add all items to the list
        addTask(t1);
        addTask(t2);
        addTask(t3);
        addTask(t4);
        liCollection.shouldHave(exactTexts(t1, t2, t3, t4));
        //assertTextAddedToList(4, t4);
        //assertFooterExists("4");

        //Remove 2nd task from the list
        liCollection.get(1).hover();
        liCollection.get(1).find(".destroy").click();
        liCollection.shouldHaveSize(3);
        assertFooterExists("3");
        
        //Check the last one as completed and clear all completed tasks
        liCollection.get(2).find(".toggle").click();
        clearCompletedTasks();
        liCollection.shouldHaveSize(2);
        assertFooterExists("2");

        //Mark all task as completed and clear all completed tasks
        $("#toggle-all").click();
        clearCompletedTasks();
        liCollection.shouldBe(empty);
        footer.shouldBe(hidden);
    }
}