package edu.qc.seclass.glm.custom;

import android.content.Context;
import android.widget.TableRow;

import edu.qc.seclass.glm.datastructures.GroceryItem;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.datastructures.Item;

public class GLMTableRow extends TableRow {

    private GroceryList list;
    private Item item;

    public GLMTableRow(Context context) {
        super(context);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setGroceryList(GroceryList list) { this.list = list; }

    public GroceryList getGroceryList() { return this.list; }

    public Item getItem() {
        return this.item;
    }
}
