package net.itlubs;

import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov Maksym on 29.04.15.
 * Version 0.1
 * Verifications have not developed yet
 */
public class ToDosTests {

    @Test
    public void testCreateTask() {
        String textToVerify1 = "text 1";
        String textToVerify2 = "text 2";
        String textToVerify3 = "text 3";
        String textToVerify4 = "text 4";

        //Step 1: Open todomvc.com in the web broweser
        open("http://todomvc.com/examples/troopjs_require/#/");
        //[VERIFICATION]: list is empty
        $$("#todo-list>li").shouldBe(empty);

        //Step 2: Add all items to the list
        $("#new-todo").setValue(textToVerify1).pressEnter();
        $("#new-todo").setValue(textToVerify2).pressEnter();
        $("#new-todo").setValue(textToVerify3).pressEnter();
        $("#new-todo").setValue(textToVerify4).pressEnter();

        //Step 3: Remove 2nd task from the list
        SelenideElement el = $$("#todo-list>li").get(1);
        el.hover();
        el.find(".destroy").click();

        //Step 4: Check the last one as completed and clear all completed tasks
        $$("#todo-list>li").get(2).find("[type=checkbox]").click();
        $("#clear-completed").click();

        //Step 5: Mark all task as completed and clear all completed tasks
        $("#toggle-all").click();
        $("#clear-completed").click();

    }
}
