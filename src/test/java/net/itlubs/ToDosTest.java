package net.itlubs;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov Maksym on 29.04.15.
 * Version 2.0
 */
public class ToDosTest {

    ElementsCollection tasks = $$("#todo-list>li");
    SelenideElement clearCompleted = $("#clear-completed");

    private void addTask(String text) {
        $("#new-todo").setValue(text).pressEnter();
    }
        
    @Test
    public void testCreateTask() {
        String t1 = "abcdABCD1234!@#$";
        String t2 = "_+)({}|\"¥¨›„¨ŽººÒ><>?:|}{";
        String t3 = "text text text text TEXT TEXT TEXT";
        String t4 = "\'kashdkjahdkjqhekjahkjahdkjhzkjchkjahsdkjwhekja\'";

        open("http://todomvc.com/examples/troopjs_require/#/");

        //create tasks
        addTask(t1);
        addTask(t2);
        addTask(t3);
        addTask(t4);
        tasks.shouldHave(texts(t1, t2, t3, t4));

        //edit tasks

        //delete task
        tasks.find(text(t2)).hover();
        tasks.find(text(t2)).find(".destroy").click();
        tasks.shouldHave(texts(t1, t3, t4));

        //complete & clear
        tasks.find(text(t4)).find(".toggle").click();
        clearCompleted.click();
        tasks.shouldHave(texts(t1, t3));

        //complete all & clear
        $("#toggle-all").click();
        clearCompleted.click();
        tasks.shouldBe(empty);
    }
}