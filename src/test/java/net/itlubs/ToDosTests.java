package net.itlubs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov Maksym on 29.04.15.
 * Version 1.0
 */
public class ToDosTests {

    private void addTask(String str) {
        $("#new-todo").setValue(str).pressEnter();
    }
        
    private void clearCompletedTasks() {
        SelenideElement clearCompleted = $("#clear-completed");

        if (clearCompleted.exists()) {
            clearCompleted.click();
        }
    }
        
    @Test
    public void testCreateTask() {
        String t1 = "abcdABCD1234!@#$";
        String t2 = "_+)({}|\"¥¨›„¨ŽººÒ><>?:|}{";
        String t3 = "text text text text TEXT TEXT TEXT";
        String t4 = "\'kashdkjahdkjqhekjahkjahdkjhzkjchkjahsdkjwhekja\'";

        ElementsCollection liCollection = $$("#todo-list>li");

        //Open todomvc.com in the web browser
        open("http://todomvc.com/examples/troopjs_require/#/");

        //Add all items to the list
        addTask(t1);
        addTask(t2);
        addTask(t3);
        addTask(t4);
        liCollection.shouldHave(exactTexts(t1, t2, t3, t4));

        //Remove 2nd task from the list
        liCollection.get(1).hover();
        liCollection.get(1).find(".destroy").click();
        liCollection.shouldHaveSize(3);

        //Check the last one as completed and clear all completed tasks
        liCollection.get(2).find(".toggle").click();
        clearCompletedTasks();
        liCollection.shouldHaveSize(2);

        //Mark all task as completed and clear all completed tasks
        $("#toggle-all").click();
        clearCompletedTasks();
        liCollection.shouldBe(empty);
    }
}