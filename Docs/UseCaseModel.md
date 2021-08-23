# Use Case Model

**Authors**: CSCI 370 Spring 21 Section 34 Team 6

## 1. Use Case Diagram

*This section should contain a use case diagram with all the actors and use cases for the system, suitably connected.*

![useCaseDiagram](https://i.imgur.com/dfE5FAI.png)


## 2. Use Case Descriptions

**Use Case: Manage List(s)**
- Create List
- Delete List
- Select List
- Rename List

**Use Case: Create List**

- *Requirements: The user creates a new list in the List Manager.*
- *Pre-conditions: When there is an available slot to create a list and user presses the '+' button.*
- *Post-conditions: A new list is created, where user can now select, delete, or rename list.*
- *Scenarios: When the user requires a new list for organization (i.e Weekly grocery list, Monthly grocery list, etc.).*


**Use Case: Delete List**

- *Requirements: The list is deleted in the List Manager.*
- *Pre-conditions: The user has to select the current list to be deleted and presses the delete button or swipes to delete it.*
- *Post-conditions: List is deleted from the List Manager.*
- *Scenarios: User feels list is no longer needed or is unnecessary.*

**Use Case: Select List**

- *Requirements: The user selects a list in the List Manager.*
- *Pre-conditions: A list has to exist.*
- *Post-conditions: The list is selected and can view the items that exist in that selected list.*
- *Scenarios: User wants to manage/rename/delete a list.*

**Use Case: Rename List**

- *Requirements: The user renames a list in the List Manager.*
- *Pre-conditions: A list has to exist and selected and user presses the edit button.*
- *Post-conditions: The list is renamed.*
- *Scenarios: User wants to rename a list for organization purposes.*


**Use Case: Manage Item(s)**
- Check off item
- Clear all checks at once
- Set quantity
- Delete item

**Use Case: Check off item**

- *Requirements: The user checks off a selected item on the list.*
- *Pre-conditions: There must be an existing item in the list to be selected.*
- *Post-conditions: Item will remain in the list but strikethrough.*
- Scenarios: When the user walks down an aisle, grabs the item and places it into the bin, the item can then be checked off on the list. 

**Use Case: Clear all checks at once**

- *Requirements: This feature allows the user to uncheck all the items that are currently checked off in the list.*
- *Pre-conditions: There must exist at least an item that is checked off on the list.*
- *Post-conditions: All items on the list will be unchecked.*
- Scenarios: After purchase of the items, the list can be reused for another time and thus instead of recreating a list with the same items, a simple uncheck all items will do.*



**Use Case: Set quantity**

- *Requirements: Sets the quantity of the item.*
- *Pre-conditions: The item is selected.*
- *Post-conditions: The quantity of the item is changed.*
- *Scenarios: The user has changed his/her mind of how much of the item to get.*



**Use Case: Delete item**

- *Requirements: User deletes an item currently on the grocery list.*
- *Pre-conditions: There must exists at least an item on the grocery list.*
- *Post-conditions: The selected item is removed from the grocery list.*
- *Scenarios: The user wants to remove an item from the grocery list.*

**Use Case: Create new item**

- *Requirements: This case enables the user to create a new item.*
- *Pre-conditions: The new item must not already exists in the list.*
- *Post-conditions: The new item is created.*
- *Scenarios: When the current item is not in the list.*


**Use Case: Add item**

- *Requirements: User adds an item into the grocery list.*
- *Pre-conditions: The item must exist in database and user presses the '+' button next to the item.*
- *Post-conditions: The item now exists in the list.*
- *Scenarios: The user wants to add an item into their grocery list.*


**Use Case: Group items by type**

- *Requirements: User sorts the list by item type and is displayed by type.*
- *Pre-conditions: Item(s) must exist in List and user presses the group item by type button.*
- *Post-conditions: Item(s) are now sorted and displayed by type.*
- *Scenarios: The user wants to organize and view items grouped by specific types.*

**Use Case: Search for item**

- *Requirements: User searches for an item in the item warehouse.*
- *Pre-conditions: User presses the search icon and type item name.*
- *Post-conditions: SearchActivity is brought up where users can search through a search bar to find a specific item by name.*
- *Scenarios: In the case that user wants to add an item onto the grocery list.*
