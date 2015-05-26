package net.itlubs.hw2;

import net.itlubs.pages.ActivePage;
import net.itlubs.pages.AllPage;
import net.itlubs.pages.CompletedPage;
import net.itlubs.pages.ToDosPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Created by Shykov Maksym on 29.04.15.
 * Version 2.0
 */

public class ToDosTest {

    ToDosPage todosPage = new ToDosPage();

    @Before
    public void clearData() {
        todosPage.loadUrl();
        executeJavaScript("localStorage.clear()");
    }

    @After
    public void closeBrowser() {
        close();
    }

    @Test
    public void testAtAllFilter() {
        AllPage allPage = new AllPage();

        // create task
        allPage.addTask("a");
        allPage.addTask("b");
        allPage.addTask("c");
        allPage.addTask("d");
        allPage.addTask("e");
        allPage.tasks.shouldHave(exactTexts("a", "b", "c", "d", "e"));

        // edit
        allPage.editTask("c", "c updated");
        allPage.tasks.shouldHave(exactTexts("a", "b", "c updated", "d", "e"));

        // delete
        allPage.deleteTask("b");
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "d", "e"));

        // complete
        allPage.toggleTask("d");
        allPage.toggleTask("e");
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "d", "e"));
        allPage.assertItemsLeftCounter(2);

        // filter
        ActivePage activePage = allPage.openActivePage();
        activePage.visibleItemOnPage("a", "c updated");
        activePage.hiddenItemOnPage("d", "e");
        activePage.assertItemsLeftCounter(2);
        CompletedPage completedPage = allPage.openCompletedPage();
        completedPage.visibleItemOnPage("d", "e");
        completedPage.hiddenItemOnPage("a", "c updated");
        completedPage.assertItemsLeftCounter(2);
        allPage.openAllPage();
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "d", "e"));
        allPage.assertItemsLeftCounter(2);

        // reopen task
        allPage.toggleTask("e");
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "d", "e"));
        allPage.assertItemsLeftCounter(3);

        // clear completed
        allPage.clearCompletedTasks();
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "e"));

        // complete all task
        allPage.toggleAllTasks();
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "e"));
        allPage.assertItemsLeftCounter(0);

        // clear completed task
        allPage.clearCompletedTasks();
        allPage.tasks.shouldBe(empty);
    }

    @Test
    public void testAtActiveFilter() {
        // pre-pare test data
        AllPage allPage = new AllPage();
        allPage.addTask("a");

        // create task on Active Page
        ActivePage activePage = allPage.openActivePage();
        activePage.addTask("b");
        activePage.addTask("c");
        activePage.visibleItemOnPage("a", "b", "c");
        activePage.assertItemsLeftCounter(3);

        // edit task
        activePage.editTask("b", "b updated");
        activePage.visibleItemOnPage("a", "b updated", "c");

        // delete
        activePage.deleteTask("c");
        activePage.visibleItemOnPage("a", "b updated");
        activePage.assertItemsLeftCounter(2);

        // complete
        activePage.toggleTask("a");
        activePage.visibleItemOnPage("b updated");
        activePage.assertItemsLeftCounter(1);
        
        // complete all
        activePage.toggleAllTasks();
        activePage.tasks.shouldBe(empty);
        activePage.assertItemsLeftCounter(0);

        // undo complete all
        activePage.toggleAllTasks();
        activePage.visibleItemOnPage("a", "b updated");
        activePage.assertItemsLeftCounter(2);
    }

    @Test
    public void testAtCompletedFilter() {
        // pre-pear test data
        AllPage allPage = new AllPage();
        allPage.addTask("a");
        allPage.addTask("b");
        allPage.addTask("c");
        allPage.addTask("d");
        allPage.toggleAllTasks();

        //delete task
        CompletedPage completedPage = allPage.openCompletedPage();
        completedPage.deleteTask("a");
        completedPage.visibleItemOnPage("b", "c", "d");
        completedPage.assertItemsLeftCounter(0);
        ActivePage activePage = completedPage.openActivePage();
        activePage.assertItemsLeftCounter(0);
        activePage.tasks.shouldBe(empty);
        activePage.openCompletedPage();

        // reopen
        completedPage.toggleTask("b");
        completedPage.visibleItemOnPage("c", "d");
        completedPage.hiddenItemOnPage("b");
        completedPage.assertItemsLeftCounter(1);
        completedPage.openAllPage();
        allPage.assertItemsLeftCounter(1);
        allPage.tasks.shouldHave(exactTexts("b", "c", "d"));
        allPage.openActivePage();
        activePage.assertItemsLeftCounter(1);
        activePage.visibleItemOnPage("b");
        activePage.hiddenItemOnPage("c", "d");
        activePage.openCompletedPage();

        // clear
        completedPage.clearCompletedTasks();
        completedPage.assertItemsLeftCounter(1);
        completedPage.tasks.shouldBe(empty);
        completedPage.openActivePage();
        activePage.assertItemsLeftCounter(1);
        activePage.visibleItemOnPage("b");
        activePage.openAllPage();
        allPage.assertItemsLeftCounter(1);
        allPage.tasks.shouldHave(exactTexts("b"));
    }
}