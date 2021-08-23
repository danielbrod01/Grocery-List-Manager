# Requirements
<ol>
  <li>
    A grocery list consists of items the users want to buy at a grocery store. The application
must allow users to add items to a list, delete items from a list, and change the quantity
of items in the list (e.g., change from one to two pounds of apples).
  </li>
  <li>
    The application must contain a database (DB) of <em>items</em> and corresponding <em>item types</em> .
  </li>
  <li>
    Users must be able to add items to a list by picking them from a hierarchical list, where
the first level is the item type (e.g., cereal), and the second level is the name of the
actual item (e.g., shredded wheat). After adding an item, users must be able to specify a
quantity for that item.
  </li>
  <li>
    Users must also be able to specify an item by typing its name. In this case, the
application must look in its DB for items with similar names and ask the users, for each
of them, whether that is the item they intended to add. If a match cannot be found, the
application must ask the user to select a type for the item and then save the new item,
together with its type, in its DB.
  </li>
  <li>
   Lists must be saved automatically and immediately after they are modified.
  </li>
  <li>
    Users must be able to check off items in a list (without deleting them).
  </li>
  <li>
    Users must also be able to clear all the check-off marks in a list at once.
  </li>
  <li>
   Check-off marks for a list are persistent and must also be saved immediately.
  </li>
  <li>
    The application must present the items in a list grouped by type, so as to allow users to
shop for a specific type of product at once (i.e., without having to go back and forth
between aisles).
  </li>
  <li>
    The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly
farmer’s market list”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete lists.
  </li>
  <li>
   The User Interface (UI) must be intuitive and responsive.
  </li>
</ol>
  
