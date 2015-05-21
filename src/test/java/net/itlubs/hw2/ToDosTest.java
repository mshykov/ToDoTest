package net.itlubs.hw2;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static net.itlubs.helper.ToDosActions.*;

/**
 * Created by Shykov Maksym on 29.04.15.
 * Version 2.0
 */
public class ToDosTest {

    @BeforeClass
    public static void openToDoesMvc() {
        open("http://todomvc.com/examples/troopjs_require/#/");
    }

   // @Before
    public void clearData() {
        executeJavaScript("localStorage.clear()");
        open("http://todomvc.com/");
        open("http://todomvc.com/examples/troopjs_require/#/");
    }

    @Test
    public void testAtAllFilter() {
        open("http://todomvc.com/examples/troopjs_require/#/");
        // create task
        addTask("a");
        addTask("b");
        addTask("c");
        addTask("d");
        tasks.shouldHave(exactTexts("a", "b", "c", "d"));
        // edit
        editTask("c", "c updated");
        // delete
        tasks.find(text("b")).hover().find(".destroy").click();
        tasks.shouldHave(texts("a", "c", "d"));
        // complete
        toggleTask("d");
        tasks.shouldHave(texts("a", "c", "d")); //?? think about
        // filter
        // clear completed
        clearCompleted.click();
        // reopen task
        // complete all task
        $("#toggle-all").click();
        // clear completed task
        clearCompleted.click();
        tasks.shouldBe(empty);
    }

    @Test
    public void testAtActiveFilter() {
        // create task
        // edit task
        // delete
        // complete
        // ? clear completed
    }

    @Test
    public void testAtCompletedFilter() {
        //delete task
        //? edit task
        // reopen
        // clear
    }
}