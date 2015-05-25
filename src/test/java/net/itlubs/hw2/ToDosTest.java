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

    ToDosPage todoesPage = new ToDosPage();

    //@BeforeClass

    @Before
    public void clearData() {
        todoesPage.loadUrl();
        executeJavaScript("localStorage.clear()");
    }

    @After
    public void closeBroweser() {
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
        ActivePage activePage = new ActivePage();
        activePage.loadUrl();
        activePage.visibleItemOnPage("a", "c updated");
        activePage.hiddenItemOnPage("d", "e");
        activePage.assertItemsLeftCounter(2);
        CompletedPage completedPage = new CompletedPage();
        completedPage.loadUrl();
        activePage.visibleItemOnPage("d", "e");
        activePage.hiddenItemOnPage("a", "c updated");
        activePage.assertItemsLeftCounter(2);
        allPage.loadUrl();
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
        ActivePage activePage = new ActivePage();
        activePage.loadUrl();

        // create task
        activePage.addTask("a");
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
        allPage.loadUrl();
        allPage.addTask("a");
        allPage.addTask("b");
        allPage.addTask("c");
        allPage.addTask("d");
        allPage.toggleAllTasks();
        CompletedPage completedPage = new CompletedPage();
        completedPage.loadUrl();

        //delete task
        completedPage.deleteTask("a");
        completedPage.visibleItemOnPage("b", "c", "d");
        completedPage.assertItemsLeftCounter(0);
        ActivePage activePage = new ActivePage();
        activePage.loadUrl();
        activePage.assertItemsLeftCounter(0);
        activePage.tasks.shouldBe(empty);
        completedPage.loadUrl();

        // reopen
        completedPage.toggleTask("b");
        completedPage.visibleItemOnPage("c", "d");
        completedPage.hiddenItemOnPage("b");
        completedPage.assertItemsLeftCounter(1);
        allPage.loadUrl();
        allPage.assertItemsLeftCounter(1);
        allPage.tasks.shouldHave(exactTexts("b", "c", "d"));
        activePage.loadUrl();
        activePage.assertItemsLeftCounter(1);
        activePage.visibleItemOnPage("b");
        activePage.hiddenItemOnPage("c", "d");
        completedPage.loadUrl();

        // clear
        completedPage.clearCompletedTasks();
        completedPage.assertItemsLeftCounter(1);
        completedPage.tasks.shouldBe(empty);
        activePage.loadUrl();
        activePage.assertItemsLeftCounter(1);
        activePage.visibleItemOnPage("b");
        allPage.loadUrl();
        allPage.assertItemsLeftCounter(1);
        allPage.tasks.shouldHave(exactTexts("b"));
    }
}