# Test Plan

**Author**: CSCI 370 Spring 21 Section 34 Team 6

## 1 Testing Strategy

### 1.1 Overall strategy

Since this application consists of a simple system, testing strategy will be kept simple

- Manual testing: Mainly executed by our QA tester. Also performing black-box testing and white-box testing to catch and fix bugs.
- Unit testing: Individual units or components of code will be tested.
- Integration testing: Test will involve how multiple components mash with one another.
- System Testing: Testing as a complete and fully integrated app and evaluate the requirements.
- Regression testing: Should there be changes/adaptations in terms of implementations, prior tests will be re-ran to see if components are still functioning.

### 1.2 Test Selection

We will be using black-box testing to test each of the internal components to make sure that they are functioning correctly. White-box testing will be performed for each individual function.


- Unit testing: white-box (Control Flow & Statement Coverage)
- Integration testing: white-box (Control Flow & Branch Coverage) / black-box
- System testing: black-box (Error Guessing & Functional Testing)
- Regression testing: black-box (Error Guessing & Functional Testing)

### 1.3 Adequacy Criterion

Test cases should be documented thoroughly, as small units without combining as much functional parts.

Since we are implementing custom class objects with a supplied input to the methods testing, we will know what the expected output should be. There will hence be two sets of tests.

- Testing the validity of data at object creation or modification
- Testing for bad data types supplied to methods or constructors to assess any error catching

### 1.4 Bug Tracking

As we go through unit tests, we will mark down in a table whether that specific test cases has passed or failed. Developers will be able to look at the table the determine their functionality and what needs to be modified to fix them. When a failed test cases is flagged at build time, we will take note of the unit that failed and will create a task to address that unit to our team.

### 1.5 Technology

We will be utilizing **JUnit** for manual testing.

We will always test for regression everytime we modify a class. This is to ensure that when an old test fails, we will know which modification affected the failed unit and address the error accordingly.

## 2 Test Cases

For each test case row, we will describe them in the following columns:

1. Test Case
2. Activity in which the unit resides
3. Purpose of the test
4. Procedure
5. Input required
6. Expected result
7. Actual result
8. Status (passed or failed)


| Test Case | Test Source  | Purpose | Procedure | Input | Expected result | Actual result | Pass/Fail| 
| -------- | -------- | -------- |------- |-------- | -------- | ------- | ------ |
| Start application     | None     | Test that the application opens    | Open the application on your device    | None | User is in MainActivity |
| Create List Button    | MainActivity  | The user is able to create a list     | Press the "+" button     | "+" button pressed     | Brings user to NewListActivity     |
| Input List Name    | NewListActivity  | The user is able to input a name for the list     | Press on list name field and insert name   | String     | Name of list should be displayed     |
| Add to list Button    | NewListActivity  | The user is able to add the list onto GroceryListManager     | Press the "Add" button   | "Add" button pressed   | Brings user to GroceryListActivity     |
| Cancel Button    | NewListActivity  | The user is able to cancel the creation of a new list   | Press "Cancel" button   | "Cancel" button pressed   | Bring user back to MainActivity  |
| Rename List     | GroceryListActivity  | The user is able to rename a list     | Press the edit button, and provide a string input to text field     | String     | List is renamed|
| Select List     | MainActivity  | The user is able to select a list     | Press the List name    | None     | Brings user to GroceryListActivity |
| Delete List     | MainActivity  | The user is able to delete a list     | User presses on the delete button     | Radio button(s) selected    |List is deleted and longer visible in MainActivity  |
| Search Item Button   | GroceryListActivity     | The user is able to search an item from an individual list    | User presses the "+" button     | '+' button is pressed     |App is redirected to the Search Activity    |
| Search Item Button (Navbar)  | In every Activity     | The user is able to search an item from any activity    | User presses the search icon on the navigation bar     | '+' button is pressed     |App is redirected to the Search Activity     |
| Add Item     | SearchActivity     | The user is able to add an item     | User presses the "+" button at Search Activity and an add item pop up comes out     | 'Add' button is pressed     |Item is added     |
| Cancelling the Add Item pop-up     | SearchActivity     | The user is able to cancel the add item procedure    | User presses the "+" button at Search Activity and an add item pop up comes out. User presses the 'cancel' button     | 'Cancel' button is pressed     | Brings user back to SearchActivity     |
| Delete Item   | GroceryListActivity  | The user is able to delete an item     | User presses the delete button at Grocery List Activity     | Item(s) are selected and 'Delete' button is pressed     |Item(s) are deleted and no longer visible in the list  |
| Check off Item  | GroceryListActivity   | The user is able to check off an item     | User presses the uncheck button beside the list at Main/Grocery List Activity     | Checkbox is checked      |Item is checked off(not selected) and the item's text name is striked off     |
| Clear all checks  | GroceryListActivity     | The user is able to clear off all checks     | User presses the uncheck all button at Grocery List Activity     | 'Uncheck all' button is pressed     |All List/item(s) are checked off(not selected)    |
| Set Quantity  | GroceryListActivity   | The user is able to set a quantity to an item     | User supplies a number quantity presses the set quantity button on items     | Number is supplied     |The quantity of the item is set/changed    |
| Group Items  | GroceryListActivity  | The user is able to group items by itemType alphabetically    | Press "filter" button and select group by type     | "filter" button pressed and group by type selected   |Items are grouped together   |
| Create Item Button    | SearchActivity   | The user is able to create an item     | Press the Create button  | Create button pressed   |Brings user to CreateItemActivity    |
| Input Item Name  | CreateItemActivity   | The user is able to type the item name   | Press on item name field and insert name    | String     |Name of item should be displayed  |
| Input ItemType Name  | CreateItemActivity   | The user is able to type the itemType name    | Press on itemType name field and insert name     | String    |Name of itemType should be displayed     |
| Add to Warehouse Button | CreateItemActivity   | The user is able to add the item into the warehouse    | Press the "Add" button   | "Add" button pressed    |Item is created and added into DB   |
| Cancel Button  | CreateItemActivity   | The user is able to cancel the action    | Press the "Cancel" button    | "Cancel" button pressed   | Cancels any ongoing activity   |
| Home Button (navbar) | In every Activity   | The user is able to return back to GroceryListManager    | Press the "Home" button    | "Home" button pressed   | Brings user back to MainActivity  |