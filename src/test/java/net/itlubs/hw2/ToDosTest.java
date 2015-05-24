package net.itlubs.hw2;

import com.codeborne.selenide.ElementsCollection;
import net.itlubs.pages.ActivePage;
import net.itlubs.pages.AllPage;
import net.itlubs.pages.CompletedPage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static net.itlubs.pages.ToDosPage.*;


/**
 * Created by Shykov Maksym on 29.04.15.
 * Version 2.0
 */
public class ToDosTest {

    @BeforeClass
    public static void openToDoesMvc() {
        open(todosMainUrl);
    }

    @Before
    public void clearData() {
        executeJavaScript("localStorage.clear()");
        open(todosMainUrl);
    }

    @Test
    public void testAtAllFilter() {
        AllPage allPage = new AllPage();
        allPage.loadUrl();
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
        allPage.tasks.shouldHave(texts("a", "c updated", "d", "e"));
        // complete
        allPage.toggleTask("d");
        allPage.toggleTask("e");
        allPage.tasks.shouldHave(texts("a", "c updated", "d", "e"));
        allPage.itemsLeftCounter.shouldHave(exactText("2"));

        // filter
        ActivePage activePage = new ActivePage();
        activePage.loadUrl();
        activePage.tasks.shouldHave(exactTexts("a", "c updated"));
        CompletedPage completedPage = new CompletedPage();
        completedPage.loadUrl();
        completedPage.tasks.shouldHave(exactTexts("d", "e"));
        allPage.loadUrl();
        allPage.tasks.shouldHave(exactTexts("a", "c updated", "d", "e"));
        allPage.itemsLeftCounter.shouldHave(exactText("2"));

        // reopen task
        allPage.toggleTask("e");
        allPage.tasks.shouldHave(texts("a", "c updated", "d", "e"));
        allPage.itemsLeftCounter.shouldHave(exactText("3"));

        // clear completed
        allPage.clearCompletedTasks();
        allPage.tasks.shouldHave(texts("a", "c updated", "e"));

        // complete all task
        allPage.toggleAllTasks();
        allPage.tasks.shouldHave(texts("a", "c updated", "e"));
        allPage.itemsLeftCounter.shouldHave(exactText("0"));

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
        activePage.tasks.shouldHave(texts("a", "b", "c"));
        // edit task
        activePage.editTask("b", "b updated");
        activePage.tasks.shouldHave(texts("a", "b updated", "c"));
        // delete
        activePage.deleteTask("c");
        activePage.tasks.shouldHave(texts("a", "b updated"));
        // complete
        activePage.toggleTask("a");
        activePage.tasks.shouldHave(texts("b updated"));
        activePage.itemsLeftCounter.shouldHave(exactText("1"));
        AllPage allPage = new AllPage();
        allPage.loadUrl();
        allPage.tasks.shouldHave(texts("a", "b updated"));
        // ? clear completed
    }

    @Test
    public void testAtCompletedFilter() {
        // prepear test data
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
        completedPage.tasks.shouldHave(texts("b", "c", "d"));
        ActivePage activePage = new ActivePage();
        activePage.loadUrl();
        activePage.itemsLeftCounter.shouldHave(text("0"));
        activePage.tasks.shouldBe(empty);
        completedPage.loadUrl();
        //? edit task
        // reopen
        completedPage.toggleTask("b");
        completedPage.tasks.shouldHave(texts("c", "d"));
        allPage.loadUrl();
        allPage.itemsLeftCounter.shouldHave(text("1"));
        allPage.tasks.shouldHave(texts("b", "c", "d"));
        activePage.loadUrl();
        activePage.itemsLeftCounter.shouldHave(text("1"));
        activePage.tasks.shouldHave(texts("b"));
        completedPage.loadUrl();
        // clear
        completedPage.clearCompletedTasks();
        completedPage.itemsLeftCounter.shouldHave("1");
        completedPage.tasks.shouldBe(empty);
        activePage.loadUrl();
        activePage.itemsLeftCounter.shouldHave("1");
        activePage.tasks.shouldHave(texts("b"));
        allPage.loadUrl();
        allPage.itemsLeftCounter.shouldHave(text("1"));
        allPage.tasks.shouldHave(texts("b"));
    }
}