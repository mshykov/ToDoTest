package net.itlubs;

import com.codeborne.selenide.ElementsCollection;
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
public class ToDosTest {

    private void addTask(String text) {
        $("#new-todo").setValue(text).pressEnter();
    }
        
    @Test
    public void testCreateTask() {
        String t1 = "abcdABCD1234!@#$";
        String t2 = "_+)({}|\"¥¨›„¨ŽººÒ><>?:|}{";
        String t3 = "text text text text TEXT TEXT TEXT";
        String t4 = "\'kashdkjahdkjqhekjahkjahdkjhzkjchkjahsdkjwhekja\'";

        ElementsCollection listOfTasks = $$("#todo-list>li");

        open("http://todomvc.com/examples/troopjs_require/#/");
        addTask(t1);
        addTask(t2);
        addTask(t3);
        addTask(t4);
        listOfTasks.shouldHave(exactTexts(t1, t2, t3, t4));

        listOfTasks.get(1).hover();
        listOfTasks.get(1).find(".destroy").click();

        listOfTasks.get(2).find(".toggle").click();
        $("#clear-completed").click();

        $("#toggle-all").click();
        $("#clear-completed").click();
        listOfTasks.shouldBe(empty);
    }
}