package edu.qc.seclass.glm;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.qc.seclass.glm.fragments.MainActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GroceryListManagerTest {

    @Rule
    public String gList1, gList2, editGList;
    public int doritosQuantity = 1, yogurtQuantity, cannedTomatoSauce;

    public GroceryListManagerTest() {
        super();
    }

    @Before
    public void initItems() {
        gList1 = "Maxim's List";
        gList2 = "Paul's List";
        editGList = "Edited List";

        yogurtQuantity = 3;
        cannedTomatoSauce = 5;
        doritosQuantity = 6;
    }

    @Test
    public void testCreateList() {
        //Create a new list
        openContextualActionModeOverflowMenu();
        onView(withText("NEW LIST"))
                .perform(click());
        onView(withId(R.id.inputListName_newListFragment))
                .perform(typeText(gList1), closeSoftKeyboard());
        onView(withText("ADD LIST"))
                .perform(click());

        //Test if the new list is created
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .check(matches(withText(gList1)));

    }

    @Test
    public void testDeleteSelectedList() {
        //Delete a list
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .perform(click());


        openContextualActionModeOverflowMenu();
        onView(withText("DELETE"))
                .perform(click());
        onView(withText("DELETE"))
                .perform(click());

        //Test list has been deleted
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .check(matches(withText(gList2)));
    }

    @Test
    public void testMergeSelectedList() {
        //Select both lists and merge
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment)).atPosition(0)
                .onChildView(withId(R.id.table_homeFragment))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment)).atPosition(1)
                .onChildView(withId(R.id.table_homeFragment))
                .perform(click());
        openContextualActionModeOverflowMenu();
        onView(withText("MERGE"))
                .perform(click());

        //Test if lists are merged
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .check(matches(withText(gList1)));
    }

    @Test
    public void testSelectList() {
        //Select list
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment)).atPosition(0)
                .onChildView(withId(R.id.table_homeFragment))
                .perform(click());

        //Test that list has been selected
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .check(matches(isChecked()));
    }

    @Test
    public void testRenameList() {
        //Rename a list
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment)).atPosition(0)
                .onChildView(withId(R.id.table_homeFragment))
                .perform(click());
        openContextualActionModeOverflowMenu();
        onView(withText("RENAME"))
                .perform(click());
        onView(withId(R.id.table_homeFragment)) //TODO fix this id, rename prompt has no id, isn't created on xml file
                .perform(typeText(editGList), closeSoftKeyboard());
        onView(withText("SAVE"))
                .perform(click());

        //Test that list has been renamed
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .check(matches(withText(editGList)));
    }

    //The user checks off a selected item on the list
    @Test
    public void testCheckItem() {
        //Check Doritos and Lemonade from list
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(0).onChildView(withId(R.id.table_groceryListFragment))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(1).onChildView(withId(R.id.table_groceryListFragment))
                .perform(click());

        //Test Doritos and Lemonade are checked
        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(0).onChildView(withId(R.id.table_groceryListFragment))
                .check(matches(isChecked()));

        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(1).onChildView(withId(R.id.table_groceryListFragment))
                .check(matches(isChecked()));
    }

    //This feature allows the user to uncheck all the items that are currently checked off in the list.
    @Test
    public void testClearAllChecks() {
        //Clear all checks on the list
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .perform(click());
        onView(withResourceName("CLEAR ALL"))
                .perform(click());
        onView(withResourceName("CLEAR ALL"))
                .perform(click());

        //Test all items are unchecked
        onView(withResourceName("CLEAR ALL"))
                .check(matches(isNotChecked()));
    }

    @Test
    public void testAddItemToList() {
        //Add  5 Yogurt and 3 Canned Tomato Sauce to Gopal's List
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .perform(click());
        openContextualActionModeOverflowMenu();
        onView(withText("ADD NEW ITEM"))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.table_searchFragment))
                .atPosition(1)
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(4)
                .perform(click());
        onView(withId(R.id.increaseQuantity_addItemFragment))
                .perform((typeText(String.valueOf(yogurtQuantity))), closeSoftKeyboard());
        onView(withText("ADD"))
                .perform(click());

        openContextualActionModeOverflowMenu();
        onView(withText("ADD NEW ITEM"))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.table_searchFragment)).atPosition(4)
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment)).atPosition(9)
                .perform(click());
        onView(withId(R.id.increaseQuantity_addItemFragment))
                .perform((typeText(String.valueOf(cannedTomatoSauce))), closeSoftKeyboard());
        onView(withText("ADD"))
                .perform(click());

        //Test that Selected Items have been added
        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(0).onChildView(withId(R.id.table_groceryListFragment))
                .check(matches(withText("Yogurt")));

        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(1).onChildView(withId(R.id.table_groceryListFragment))
                .check(matches(withText("Canned Tomato Sauce")));
    }

    @Test
    public void testUpdateQuantity() {
        //Update quantity for an item
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(0).onChildView(withId(R.id.table_groceryListFragment))
                .perform(click());

        onView(withId(R.id.increaseQuantity_editItemFragment))
                .perform((typeText(String.valueOf(doritosQuantity))), closeSoftKeyboard());
        onView(withText("SAVE"))
                .perform(click());

        //Test that the right quantity is displayed
        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(0).onChildView(withId(R.id.table_groceryListFragment))
                .check(matches(withText("6")));
    }

    @Test
    public void testDeleteGroceryItem() {
        //Delete Doritos and Lemonade from list
        onData(anything()).inAdapterView(withId(R.id.table_homeFragment))
                .atPosition(0).onChildView(withId(R.id.table_homeFragment))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(0).onChildView(withId(R.id.table_groceryListFragment))
                .perform(click());

        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(1).onChildView(withId(R.id.table_groceryListFragment))
                .perform(click());

        openContextualActionModeOverflowMenu();
        onView(withText("DELETE"))
                .perform(click());

        //Test if items are deleted
        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(0).onChildView(withId(R.id.table_groceryListFragment))
                .check(matches(withText("Doritos")));
        onData(anything()).inAdapterView(withId(R.id.table_groceryListFragment))
                .atPosition(1).onChildView(withId(R.id.table_groceryListFragment))
                .check(matches(withText("Lemonade")));
    }
}