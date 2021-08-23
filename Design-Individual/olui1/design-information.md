# Software Design - UML Class Diagram

1. A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).

In order to fulfill this requirements, I added a class "List" with the attributes listName and itemList. An instance of this class represents one particular list, in this case grocery, and there exists a composition relationship with another class, "MultipleList". This means that should class MultipleList not exist, neither can class List. Through class List, one is able to add, delete, and change the quantity of an item through the operations, addItem (discuessed later), deleteItem, and editQuantityItem, respectively. In addition, functions toggleCheckItem, clearAllCheckItem, and groupByType clear requirements 6,7,8, and 9.

2. The application must contain a database (DB) of items and corresponding item types.

This is realized by implementing a database class, which should more accurately be displayed as a module but will suffice as a class for this project. The sole attribute for this class is the itemList which returns a dictionary of <String, Item> as the key-value pair. The operation fetchItems allows for users to retrieve a list of items from the database through queries. Function createItems, on the other hand, allows users to create an Item and input it into the database. These two operations fulfill requirement 4.

3. Users must be able to add items to a list by picking them from a hierarchical list, where
the first level is the item type (e.g., cereal), and the second level is the name of the
actual item (e.g., shredded wheat). After adding an item, users must be able to specify a
quantity for that item.

This is realized by implementing class HierarchicalList with no attributes. This class is intended to act as a medium between one instance of the List class and the database to search whether an item exists in the database with the searchItem function in tandem with the fetchItem function as mentioned earlier. Should the item not exist in the database, the createItem function is utilized to create one with the createItems function via queries in the database.

I decided to add the function editQuantityItem into class List instead of hierarchicalList because the attribute itemList is constructed via an ArrayList that contains several objects, in this case an item along with the quantity, hence quantity is not an attribute of the class List nor class Item.

4. Users must also be able to specify an item by typing its name. In this case, the
application must look in its DB for items with similar names and ask the users, for each
of them, whether that is the item they intended to add. If a match cannot be found, the
application must ask the user to select a type for the item and then save the new item,
together with its type, in its DB.

Explained in design of requirement 2.

5. Lists must be saved automatically and immediately after they are modified.

Not considered because it does not affect the design directly.

6. Users must be able to check off items in a list (without deleting them).

Explained in design of requirement 1.

7. Users must also be able to clear all the check-off marks in a list at once.

Explained in design of requirement 1.

8. Check-off marks for a list are persistent and must also be saved immediately.

Explained in design of requirement 1.

9. The application must present the items in a list grouped by type, so as to allow users to
shop for a specific type of product at once (i.e., without having to go back and forth
between aisles).

Explained in design of requirement 1.

10. The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly
farmer’s market list”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete lists.

Implemented by creating class MultipleList, with attribute list, which returns an ArrayList. Through the operations, createList, renameList, selectList, and deleteList, the requirement is satisfied.

Function selectList is extremely important as it allows users to choose between various amounts of lists.

11. The User Interface (UI) must be intuitive and responsive.

Not considered because it does not affect the design directly.

In addition to the requirements, class User is created in order to utilize the function retrieve which is invoked in order to create an instance of MultipleList.