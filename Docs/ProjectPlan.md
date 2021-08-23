# Project Plan

**Author**: CSCI 370 Spring 21 Section 34 Team 6

## 1 Introduction

*Here you introduce the product. Keep this  clean and simple, one or two sentences at most.*

An application that allows users to create multiple grocery lists, where one can add grocery items and have each checked off at any given time. 

## 2 Process Description

*Process description as a set of activities; for each activity, provide the following:*

#### MainActivity
- This activity will display all the Grocery Lists that are available. Also allows user to add, edit, merge, or delete any lists. This activity will be invoked when the app starts or the user requests to go to the home page. 
- No **Entrance criteria**
- No **Exit criteria**

#### NewListActivity
- Invokable via MainActivity. Allows the user to enter information about a new GroceryList they are trying to create. If a GroceryList is provided upon entrance, the user can edit information about the list.
- **optional** Entrance criteria: GroceryList object
- Exit criteria: if **successful**, returns a GroceryList

#### GroceryListActivity
- Displays all the items in the GroceryList. Allows user to search, filter, or filter items. 
- **Mandatory** Entrance criteria: GroceryList object
- No **Exit criteria**

#### SearchActivity
- Allows the user to search for the list of items that are available to add to cart. Can filter/search based on requirements When item is desired, upon clicking the *+* button, a pop up will appear to enter quantity and which GroceryList to add to. 
- **optional** Entrance criteria: GroceryList object
- Exit criteria: if **successful**, returns a GroceryListItem

#### AppSettingsActivity
- Allows user to customize the app‘s UI and theme.
- No **Entrance criteria**
- No **Exit criteria**

## 3 Team

|Name| Role|
|---|----|
|Bakhtiyorjon Rasulov|Frontend|
|Patricio Tapia|Backend|
|Daniel Rodriguez|Database|
|Pravesh Tilija|QA|
|Oscar Lui|Team Manager|

- Team Manager: In charge of the project overview along with assisting other roles.
- Frontend: In charge of the visual aspect of the application.
- Backend: In charge of connecting the database with the frontend, and making sure requests and data are sent both ways.
- Database: In charge of creation of tables and retrival of data through queries.
- QA: In charge of test cases to ensure bugs are dealt with.

Members of the team will assist each other depending on the priority of which part of the application needs to be implemented first.
- This allows for everyone to learn what each role does with practical experience.
