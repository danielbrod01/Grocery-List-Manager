# GroceryListManager

This is a software design for **GrocerListManager**, an application to manage grocery lists. The **UML** design can be found [here][1]. 

# Assumptions
- There are DB's with all the Items available at any grocery.
- There is a DB with the user's grocery lists and all the items.
- The app is responsive and handles all functionalities such as creating and adding items to grocery lists.

# Requirements

1. A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).
	> To realize this requirement, GroceryList has functions to add, remove, and modify items. The class Item has attributes for quantity and unit (pounds vs pack). Directly modifying and changing the quantity of items is not considered in the design directly because it is a UI and app functionality.
	
2. The application must contain a database (DB) of items and corresponding item types.
	> Not considered because it does not directly affect the design directly

3. Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.
	> To partially realize this, the Item class has attributes type (ItemType) and quantity (int). The UI and hierarchical list is not considered because it does not affect the design directly.
	
4. Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type, in its DB.
	> Not considered because it does not directly affect the design directly. This is a functionality within the app.
	
5. Lists must be saved automatically and immediately after they are modified. 
	> To realize this requirement, GroceryList has a addItem(item), modifyItem(item), and deleteItem(item) function that will allow the interact with an item. Once interacted with, the item will be updated in the DB to allow persistency.
	 
6. Users must be able to check off items in a list (without deleting them).
	> To realize this requirement, the class Item has attribute isChecked (boolean). 

7. Users must also be able to clear all the check-off marks in a list at once.
	> To realize this requirement, the class GroceryList contains the method unCheckAll() which will set the attribute isChecked for all items in that list to false. It will also update the DB so that check-off being cleared is saved. How the app and UI allow the user to clear all check-off's does not affect the design directly.

8. Check-off marks for a list are persistent and must also be saved immediately.
	> To realize this requirement, Each Item has a attribute isChecked (boolean), which is also stored in the DB. When an item is checked or not, it will update the DB for that item.
	 
9. The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of product at once (i.e., without having to go back and forth between aisles). 
	> To realize this requirement, the class Item has a attribute for it's type (ItemType). GroceryList has a function sort(type) which will sort the list of items. The app can have a functionality to allow the user to sort.  
	 
10. The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly farmer’s market list”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists. 
	> To realize this requirement, the class GroceryListManager is able to arrogate class GroceryList and also contains methods such as create(), select(), rename() and delete() to directly modify GroceryList. GroceryList has the attribute name (string) which supports multiple lists with custom names. 
	
11. The User Interface (UI) must be intuitive and responsive.
	> Not considered because it does not affect the design directly

[1]: https://github.com/qc-se-spring2021/370Spring21Bakhtiyorjon-Rasulov/blob/main/Assignment5/design.pdf
