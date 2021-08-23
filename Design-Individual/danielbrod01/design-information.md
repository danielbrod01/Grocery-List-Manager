GroceryListManager UML class diagram creates users and lists with access to an item/inventory database.

There are 5 classes:
    User, GroceryList, ItemType, Item, and GroceryListItem.

User class:
    User class has the properties of having a user id, name, and password called userId, userName, and password respectively.

    User is allowed to register for an account with id and password using register() method.
    User is allowed to login to their account using their id and password using login() method.
    User is allowed to create GroceryList(s) using the addList() method.
    User is allowed to delete GroceryList(s) using the deleteList() method.
    User is allowed to select from multiple GroceryLists using the selectList() method.

GroceryList class:
    GroceryList class has the properties of having a grocery list id and name called groceryListId and groceryListName respectively.

    User is allowed to get and set the GroceryList name using getGroceryListName() and setGroceryListName() methods respectively.
    User is allowed to check and uncheck an Item in the GroceryList using the checkOrUncheckItem() method.
    User is allowed to uncheck all Items in the GroceryList using uncheckAllItems() method.
    User is allowed to add Item(s) to the GroceryList using addItem() method.
    User is allowed to delete Item(s) from the GroceryList using deleteItem() method.
    User is allowed to get and set the Item's quantity using getItemQuantity() and setItemQuantity() methods respectively.
    User is allowed to get and set the Item's volume using getItemVolume() and setItemVolume() methods respectively.
    User is allowed to get and set the Item's unit using getItemUnit() and setItemUnit() methods respectively.
    User is allowed to search for Item(s) by name using the searchItemByName() method.
    User is allowed to search for Item(s) by type using the searchItemByType() method.
    User is allowed to save a new Item's name and type to the database if there is no match in the searches using the addNewItem() method.
    GroceryList is will automatically save its data upon modifications to the GroceryList using the saveList() method.

ItemType and Item class:
    Both classes are part of the Item database.
    Both classes have the properties of having an id and name.

    ItemType class is the parent class of Item class and has the properties of having the item type's id and name called itemTypeId and itemTypeName respectively.
    ItemType class can access and modify its name using the getItemTypeName() and setItemTypeName() methods respectively.

    Item class is the child class of ItemType class and inherits all the ItemType class methods.
    Item class also has the properties of having the item's id and name called itemId and itemName respectively.
    Item class also had the boolean property of being scalable or not called scalable.
    Item class can access and modify its name using the getItemTypeName() and setItemTypeName() methods respectively.
    Item class can check if the item is scalable or not using the isScalable() method.

GroceryListItem (Item-GroceryList Association class):
    An association class on the relationship/association between Item and GroceryList class.

    GroceryListItem contains the properties of having a quantity, volume, unit called itemQuantity, itemVolume and itemQuantityUnit respectively.
    GroceryListItem also contains the boolean property of being checked off in the GroceryList or not called checked.
