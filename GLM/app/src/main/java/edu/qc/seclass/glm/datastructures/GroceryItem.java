package edu.qc.seclass.glm.datastructures;

public class GroceryItem {

    /* Primary Key */
    private int id;

    /* Foreign Key */
    private int itemId;

    /* Foreign Key */
    private int listId;

    private Item item;

    private int quantity;

    private int isChecked;

    public GroceryItem(int id, int itemId, int listId, int quantity, int isChecked){
        this.id = id;
        this.itemId = itemId;
        this.listId = listId;
        this.quantity = quantity;
        this.isChecked = isChecked;
    }

    public void adoptItem(Item item){
        this.item = item;
    }

    public Item getItem(){
        return this.item;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public int getListId() {
        return listId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int isSelectedRaw() {
        return isChecked;
    }

    public boolean isSelected() { return isChecked == 1; }

    public void setChecked(int x){
        this.isChecked = x;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}
