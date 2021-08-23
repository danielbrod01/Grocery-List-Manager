# Team UML Design

## <ins>Individual Designs</ins>

### **Design 1**

![Bahti](https://i.imgur.com/DQXFxC1.png)

Pros:

- Extremely intuitive and easy to navigate at first sight.
- GroceryListManager as an entry point for users that is in charge for multiple individual lists.
- Takes into consideration of different item having different types of units of measurement.

Cons:

- No need for a database design.
- DBconnector doesn't have to be part of the design (implied).
- Doesn't need to have the id as an attribute for the ItemType class as attribute "type" is already an unique identifier.

### **Design 2**

![Pravu](https://i.imgur.com/mPxqwP8.png)

Pros:

- This design is nicely divided up by subclasses, which makes it easier to design and even look at the UML.
- The groceryList has a super class, list which may make it easier to implement the design.
- Grocery type class is used in this design, which is needed.

Cons:

- Database of items is not necessary.
- There is no change item quantity attribute, which is important.
- A lot of attributes missing which helps to make the implementation of the design easier.
- The relationships of data model between the classes are not clear, example one-to-many etc.
- Create new grocery is invalid as we do not want to add new things to the existing database, which should not be changed.

### **Design 3**

![Pat](https://i.imgur.com/s8Zaw5U.png)


Pros:

- All the important parts that need to be addressed are there so it heads in the right idea and doesn't get too muddled in the producing process but rather has a more general outlook in terms of what is needed to be done.
    - Hierarchical list is more of a implementation process rather than a need for it to be in the design process.

Cons:

- Simplistic in terms of the outcome for the application.
-  There is also no associative class to connect between item and the list, so one person could have the amount of items saved in the actual item class itself.
    - An item such as lucky charms would have the amount intended to be purchased saved directly in the lucky charms instance.
- Wrong association from user to list, instead of a black diamond that would signify it is necessary for a user to exist in order for a list to exist, there is a white diamond meaning that the list can exist without the user which is inherently incorrect. 
- The list class is also missing a unique name or unique identifier to let the user know which list they are writing to, thus it is missing a property such as name or id. 

### **Design 4**

![Daniel](https://i.imgur.com/dOX0jKU.png)

Pros:

- All the necessary objects listed in the requirements are defined in classes in the UML.
- Clearly laid out properties and all the possible behaviors the class can do.
- Keeps check of additional properties like scalability (able to weight on a scale) and different units for the products (lbs, pc, ltrs, etc).
- Every item is a child class of a superclass called Item Type which inherits the item type property of that item.
- Association class is added to keep track of the quantity of items and property of being checked off in the list.

Cons:

- A few unnecessary methods and properties were included which either go beyond the requirements stated or are simply redundant.
- There is no method to group items by type.
- There is no interface to lay down all the multiple grocery lists that a user can have.

### **Design 5**

![Oscar](https://i.imgur.com/yj3KJ3s.png)

Pros:

- MultipleList as a class clearly displays the ability to create, rename, select, and delete individual lists.
- Simple to follow through interms of workflow.
- Good mention of database, although it doesnt need to be mentioned in the design.


Cons:

- User is implied. Doesn't have to be designed.
- Renaming a list should probably be done in individual lists instead of MultipleList.
- HierarchicalList is more of an abstract feature and doesnt really need to be designed.
- Doesnt have design for different units (lb, pc, liters, etc..).

## <ins>Team Designs</ins>

![teamUML](https://i.imgur.com/VfDPjCV.png)

Commonalities:

- MultipleList as a class designed as it serves as an entrance point for the user.
- Relationship between classes is more or less agreed among us, as there were some minor differences between the multiplicities used.  
- GroceryList as a class represents individual lists as instances. Each instance is able to perform the given methods displayed in the uml class diagram. The main takeaway is that setter and getter method for the grocery list name is defined in this class as opposed to in MultipleList class.
- Item class is a must have as it represents how different items are stored. More importantly, the scalable attribute will be discussed later on. 


Differences:

- Database was disregarded as it is more of an implementation over design.
- User doesn't have to be part of the design as it is implied upon entry into MultipleList class as one enters the application.
- ItemQuantity ended up being an attribute of the AddItemToList association class, as we agreed that this attribute is first used and implemented when an item is retrieved from the Item class (through the means of db) and added into a grocerylist instance.
- HierarchialList is more of an implementation as opposed to design, thus was disregarded in the uml.
- Scalable attribute allows us to determine the unit which follows the itemQuantity.
    - ex. 1.5 lb of apples, 2 cartons of milk, etc. 
- ItemType ended up being a separate class instead of staying as an attribute of the Item class as we want to highlight the fact that our Item class instances fall underneath the bigger umbrella, in this case ItemType.
    - It is easier to understand that an instance of ItemType can have multiple items.


## <ins>Summary</ins>

The lessons learnt from designing the final uml design is the need to cut down on extra properties that were unnecessary and how we can borrow ideas from one another. With all of us together in one group, we can look at a design and cut off any unnecessary fat from the design we decided upon as there are more people to contribute a vision. 

Another thing learnt from designing the final uml design is how to handle disagreements, for example, we had a disagreement on if an item type should be an attribute to item or if it should be its own class. Another example would be when there are difference in opinion as to whether or not we should include a database into our uml design. We ultimately brought down the argument by seeing the pros and cons of both design choices where the majority of team members decided on having item type as a class rather than be part of an attribute to the item class. In addition we came into a conclusion that database is more of an implementation as opposed to a design, and thus was disregarded. 

This project was a good opportunity in realizing a healthy way to determine how to agree on different opinions that a team comes up with. In regards to teamwork, it was noticeable to have a task at hand and to stay on task because during talks within the team, if there's no clear goal then there is bound to have time wasted that could've been productive when the group members are together. 