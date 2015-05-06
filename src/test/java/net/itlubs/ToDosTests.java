package net.itlubs;

import com.codeborne.selenide.SelenideElement;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Shykov Maksym on 29.04.15.
 * Version 0.2
 * Verifications have not developed yet
 */
public class ToDosTests {

	private void addTask(String str) {
        todoBlock.setValue(str).pressEnter();
	}
	
	private void clearCompletedTasks() {
        $("#clear-completed").click();
	}
	
	@Test
    public void testCreateTask() {
        String textToVerify1 = "abcdABCD1234!@#$";
        String textToVerify2 = "_+)({}|\"¥¨›„¨ŽººÒ><>?:|}{";
        String textToVerify3 = "text text text text TEXT TEXT TEXT";
        String textToVerify4 = "\'kashdkjahdkjqhekjahkjahdkjhzkjchkjahsdkjwhekja\'";
		
        ElementsCollection todoBlock = $$("#todo-list>li");
		
        //Step 1: Open todomvc.com in the web broweser
        open("http://todomvc.com/examples/troopjs_require/#/");
        //[VERIFICATION]: list is empty
        todoBlock.shouldBe(empty);

        //Step 2: Add all items to the list
        addTask(textToVerify1);
        addTask(textToVerify2);
        addTask(textToVerify3);
        addTask(textToVerify4);

        //Step 3: Remove 2nd task from the list
        todoBlock.get(1).hover().find(".destroy").click();

        //Step 4: Check the last one as completed and clear all completed tasks
        todoBlock.get(2).find("#toggle").click();
        clearCompletedTasks();

        //Step 5: Mark all task as completed and clear all completed tasks
        $("#toggle-all").click();
        clearCompletedTasks();
    }
}
