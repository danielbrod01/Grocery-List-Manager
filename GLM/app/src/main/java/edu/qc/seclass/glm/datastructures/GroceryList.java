package edu.qc.seclass.glm.datastructures;

import java.util.ArrayList;

import edu.qc.seclass.glm.datastructures.GroceryItem;

public class GroceryList {

    /* Primary Key */
    private int listId;

    private String name;
    private String description;
    private String iconDir;
    private int isChecked;

    private ArrayList<GroceryItem> items;

    public GroceryList(int listId, String name, String description, String iconDir, int isChecked) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.iconDir = iconDir;
        this.isChecked = isChecked;

        this.items = new ArrayList<GroceryItem>();
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    public String getIconDir() {
        return iconDir;
    }

    public boolean isSelected() {
        return isChecked > 0;
    }

    public int isSelectedRaw() { return isChecked; }

    public ArrayList<GroceryItem> getItems() {
        return items;
    }

    public void addItem(GroceryItem item) {
        this.items.add(item);
    }

    public void setIsChecked(int checked) {
        this.isChecked = checked;
    }

    public String toString(){
        return this.getName();
    }

    public void setName(String name) {
        this.name = name;
    }
}
