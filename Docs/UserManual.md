# User Manual for the Grocery List Manager App

**Author**: Daniel Rodriguez

Welcome to the Grocery List Manager App! This application hopes to give an easy and user friendly experience to planning out your groceries.

Documented below is a detailed description of the various UI screens present this application. We have segmented the descriptions based on the current screen the user will see. We will call these screens "fragments". The user will first see the Main Activity Screen that views the various fragments on bootup.

## MainActivity

The Main Activity views numerous fragments that will be displayed based on the user's interaction.

MainActivity consists of the following Fragments:
- HomeFragment
- NewListFragment
- GroceryListFragment
- SearchFragment
- SearchTypeFragment
- AddItemFragment
- CreateItemFragment
- SettingsFragment
- Navigation Bar

On bootup, the Main Activity page will display the Grocery List Manager (**HomeFragment**) 

<!--include an image of the navigation bar and the main activity-->

There is also a **Nagivation Bar** present at the bottom of the MainActivity which will allow the user to return back **Home** to the **HomeFragment** page, a **Search** feature to allow the user to search up items in the database in the **SearchFragment** to add to grocery lists, and a **Settings** option to tweak settings in the **SettingsFragment**.

## HomeFragment

This screen displays the Grocery List Manager, and the user will be able to see and manage all of their created Grocery Lists.

Each Grocery List consist of the following:
- A **radio** button to select the Grocery List.
- A displayed **icon image**.
- The **name** of the Grocery List.
- A **preview** of some of the **items** in the Grocery List.

Users are able to select existing lists by pressing the **radio** buttons next to them, delete selected lists using the "**Delete**" button, create a new list using the "**New**" button (bringing up the **NewListFragment** to the screen) or merge existing grocery lists with the "**Merge List**" button.

## NewListFragment
This screen is accessed through the "**New**" button from GLMFragment.

This screen displays the UI to create a new list. Users will be prompted to provide a **name** for the Grocery List, an option to **upload** an icon for that Grocery List, and to provide a **description**.

User will then have two options:
- To "**Add**" that described Grocery List to the existing GroceryListManager.
- To "**Cancel**" this action.

## GroceryListActivity

This screen displays the current Grocery List that the user chose to view, and will be able to see and manage all of their Grocery Items stored in this list.

<!--needs refinement-->
Each Grocery Item in the list consist of the following:
- A **checkbox** to select the Grocery Item.
- A displayed **icon image**.
- The **name** of the Grocery Item.
- The **quantity** of the Grocery Item and the **unit** of the quantity.

<!-- needs clarification -->
Users are able to select existing Grocery Items by pressing the **checkboxes** next to them, delete selected items using the "**Delete**" button, and add more items using the "**New**" button (bringing up the **SearchFragment** to the screen).

## SearchFragment

<!-- Needs refinement -->
This screen is accessed through the "**New**" button in GroceryListFragment or the "**Search**" button on the **Navigation Bar**.

This screen displays the search UI for the user to interact with. User can look up items to add into their desired Grocery Lists.

There are two search features here:
- A search bar to look up the item's name or type.
- A set of preloaded item types/categories user can select from.
-- i.e. Entering in the search bar "cereal" or selecting "Cereal" will bring up the **SearchTypeFragment**

## SearchTypeFragment

This screen is accessed through the preset categories in the SearchFragment screen.

This screen displays all the item results of the selected type/category.

The search bar remains in this fragment with an additional filter option.
- User will be able to continue searching for other items and filter them.
- User can also return back to the SearchFragment through the "**<-**" arrow button on the top left of the screen.

Each item result consist of the following:
- A displayed **icon image**.
- The **name** of the Grocery Item.
- A "**+**" button to add to your Grocery List.
-- i.e. Pressing on this "**+**" button brings up the **AddItemFragment**.

In the event that the user could not find their desired results, there is a feature at the bottom that allows the user to **Create** a new item in the database. Pressing this button brings up the **CreateItemFragment**.

## AddItemFragment

This screen is accessed through the search bar or the preset categories in the **SearchFragment**.

This screen displays the UI for the user to set the necessary information to **add** the desired item to the user's Grocery List.
- User can also return back to the SearchFragment through the "**<-**" arrow button on the top left of the screen.

The UI consist of the following:
- An option to upload an icon image for the item.
- An option to set the quantity for the item (default value is 1).
- A dropdown selector for the desired Grocery List to add the item to.

User will then have two options:
- To "**Add**" that described Grocery Item to the existing Grocery List.
- To "**Cancel**" this action.

## CreateItemFragment

This screen is accessed through the **SearchTypeFragment** when user clicks on the "*Item not found? Click here*" option.

This screen displays the UI for the user to **create** a new item to the user's search results (this also stores the new entry to the database).
- User can also return back to the SearchFragment through the "**<-**" arrow button on the top left of the screen.

The UI consist of the following:
- A **textfield** to set the **Item's Name**.
- A **textfield** to set the **Item's Category**.
- An **option** to upload an icon image for the item.

User will then have two options:
- To "**Save**" that described Grocery Item to the database.
- To "**Cancel**" this action.

## SettingsFragment

This screen is accessed through the "**Settings**" button on the **Navigation Bar**.

This screen displays the UI for the user to tweak various settings for the application and access various links to information such as the to **Meet the team**, **Terms and Conditions**, and **Privacy** matters.

<!-- to do -->
The UI consist of the following:
- A **toggled** option to enable **Dark Mode**.
- A **link** to the **team's information**.
- A **link** to the **COVID-19 safety information**.
- A **link** to the **Terms and Conditions**.
- A **link** to the **Privacy** matters.
- A **button** to redirect the user back to their previous screen to where they came from.



