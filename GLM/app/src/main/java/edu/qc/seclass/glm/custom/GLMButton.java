package edu.qc.seclass.glm.custom;

import android.content.Context;
import android.widget.Button;

import edu.qc.seclass.glm.datastructures.GroceryItem;
import edu.qc.seclass.glm.datastructures.GroceryList;

public class GLMButton extends Button {

    private GroceryList list;
    private GroceryItem gitem;

    public GLMButton(Context context) {
        super(context);
    }

    public void setGroceryList(GroceryList list) {
        this.list = list;
    }

    public GroceryList getGroceryList(){
        return this.list;
    }


    public void setGroceryItem(GroceryItem gitem){
        this.gitem = gitem;
    }

    public GroceryItem getGroceryItem(){
        return gitem;
    }
}
