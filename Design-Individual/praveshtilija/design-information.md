1. A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).
- Create a class called groceryList. The class has methods, addGroceries(groceryName) to add items to the list, deleteGroceries(groceryName) to delete items from the list, changeGroceries(groceryName) to change the quantity of items in the list

2. The application must contain a database (DB) of ​items​ and corresponding ​item types​.
- Add a database of items and corresponding item types

3. Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type(e.g.,cereal), and the second level is the name of the actual item(e.g.,shreddedwheat). After adding an item, users must be able to specify a quantity for that item.
- When the addGroceries method is called, a class, GroceryType contains a list of groceryType which can be chosen. A class called Grocery handles the instantiation of the object grocery. When creating a grocery(object), after the grocery is specified to be added, users are asked for the quantity of that item

4. Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type, in its DB.
- SearchGroceries method in the list class wil help to search for a grocery across all lists(database). Another method addGroceryType adds grocery type when instantiating a new groceries in the GroceryTypeSet class

5. Lists must be saved automatically and immediately after they are modified.
- The groceryList class will update the database as soon as a grocery is created

6. Users must be able to check off items in a list (without deleting them).
- In the Grocery class, isCheckedOff of type boolean attribute

7. Users must also be able to clear all the check-off marks in a list at once.
- Two methods in groceryList to help, groceriesCheckOff(groceryName) and clearCheckOff(). These methods get groceries in the database and identifies it, sharing the value of belongingList in the Grocery object and resets isCheckedOff boolean value to false

8. Check-off marks for a list are persistent and must also be saved immediately.
- As soon as object is edited, isCheckedOff attribute in the Grocery class is saved and transfered to the database

9. The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of product at once(i.e.,without having to go back and forth between aisles).
- Each grocery to be grouped by their grocery type, each Grocery object has a GroceryType attribute that is saved with the grocery

10. The application must support multiple lists at a time(e.g.,“weekly grocery list”,“monthly farmer’s market list”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists.
- In the list class, objects of type listGroceries lets users to access methods to create, (re)name, select, and delete grocery lists, makeList(listName), renameList(selectList, newListName), deleteList(listName)

11. The User Interface (UI) must be intuitive and responsive.
- UI to be intuitive and responsive
