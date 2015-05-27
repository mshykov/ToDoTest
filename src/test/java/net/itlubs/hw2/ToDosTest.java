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

    @Before
    public void clearData() {
        ToDosPage todosPage = new ToDosPage();
        todosPage.loadUrl();
        executeJavaScript("localStorage.clear()");
        AllPage allPage = new AllPage();
        allPage.addTask("a");
    }

    @After
    public void closeBrowser() {
        close();
    }

    @Test
    public void testAtAllFilter() {

        // create task
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
        assertEquals("2", allPage.getItemsLeftCount());

        // filter
        ActivePage activePage = allPage.openActivePage();
        activePage.visibleItemOnPage("a", "c updated");
        activePage.hiddenItemOnPage("d", "e");
        assertEquals("2", activePage.getItemsLeftCount());
        CompletedPage completedPage = allPage.openCompletedPage();
        completedPage.visibleItemOnPage("d", "e");
        completedPage.hiddenItemOnPage("a", "c updated");
        assertEquals("2", completedPage.getItemsLeftCount());
        allPage.openAllPage();
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "d", "e"));
        assertEquals("2", allPage.getItemsLeftCount());

        // reopen task
        allPage.toggleTask("e");
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "d", "e"));
        assertEquals("3", allPage.getItemsLeftCount());

        // clear completed
        allPage.clearCompletedTasks();
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "e"));

        // complete all task
        allPage.toggleAllTasks();
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "e"));
        assertEquals("0", allPage.getItemsLeftCount());

        // clear completed task
        allPage.clearCompletedTasks();
        allPage.tasks.shouldBe(empty);
    }

    @Test
    public void testAtActiveFilter() {

        // create task on Active Page
        ActivePage activePage = allPage.openActivePage();
        activePage.addTask("b");
        activePage.addTask("c");
        activePage.visibleItemOnPage("a", "b", "c");
        assertEquals("3", activePage.getItemsLeftCount());

        // edit task
        activePage.editTask("b", "b updated");
        activePage.visibleItemOnPage("a", "b updated", "c");

        // delete
        activePage.deleteTask("c");
        activePage.visibleItemOnPage("a", "b updated");
        assertEquals("2", activePage.getItemsLeftCount());

        // complete
        activePage.toggleTask("a");
        activePage.visibleItemOnPage("b updated");
        assertEquals("1", activePage.getItemsLeftCount());
        
        // complete all
        activePage.toggleAllTasks();
        activePage.tasks.shouldBe(empty);
        assertEquals("0", activePage.getItemsLeftCount());

        // undo complete all
        activePage.toggleAllTasks();
        activePage.visibleItemOnPage("a", "b updated");
        assertEquals("2", activePage.getItemsLeftCount());
    }

    @Test
    public void testAtCompletedFilter() {
        
        // create tasks on Completed Page
        CompletedPage completedPage = allPage.openCompletedPage();
        completedPage.addTask("b");
        completedPage.addTask("c");
        completedPage.addTask("d");
        completedPage.toggleAllTasks();

        // delete task
        completedPage.deleteTask("a");
        completedPage.visibleItemOnPage("b", "c", "d");
        assertEquals("0", completedPage.getItemsLeftCount());
        ActivePage activePage = completedPage.openActivePage();
        assertEquals("0", activePage.getItemsLeftCount());
        activePage.tasks.shouldBe(empty);
        activePage.openCompletedPage();

        // reopen task
        completedPage.toggleTask("b");
        completedPage.visibleItemOnPage("c", "d");
        completedPage.hiddenItemOnPage("b");
        assertEquals("1", completedPage.getItemsLeftCount());
        completedPage.openAllPage();
        assertEquals("1", allPage.getItemsLeftCount());
        allPage.tasks.shouldHave(exactTexts("b", "c", "d"));
        allPage.openActivePage();
        assertEquals("1", activePage.getItemsLeftCount());
        activePage.visibleItemOnPage("b");
        activePage.hiddenItemOnPage("c", "d");
        activePage.openCompletedPage();

        // clear
        completedPage.clearCompletedTasks();
        assertEquals("1", completedPage.getItemsLeftCount());
        completedPage.tasks.shouldBe(empty);
        completedPage.openActivePage();
        assertEquals("1", activePage.getItemsLeftCount());
        activePage.visibleItemOnPage("b");
        activePage.openAllPage();
        assertEquals("1", allPage.getItemsLeftCount());
        allPage.tasks.shouldHave(exactTexts("b"));
    }
}