package edu.qc.seclass.glm.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.qc.seclass.glm.datastructures.GroceryItem;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.datastructures.Item;
import edu.qc.seclass.glm.fragments.MainActivity;

/**
 * Helper class to convert data structure object into a database object and vice versa
 */
public class Converter {

    public static int GLOBAL_WIDTH = 0;

    public static MainActivity viewToActivity(View view){
        return (MainActivity) view.getContext();
    }

    /**
     * Converts an Item object into a ContentValues needed to insert
     * into the database
     * @param item The item object that needs to be converted
     * @return ContentValues for db insertion
     */
    public static ContentValues itemToDB(Item item){
        ContentValues values = new ContentValues();
        values.put(GLMDataBase.ITEMS_COLS[1], item.getName());
        values.put(GLMDataBase.ITEMS_COLS[2], item.getType());
        values.put(GLMDataBase.ITEMS_COLS[3], item.getIconDir());
        values.put(GLMDataBase.ITEMS_COLS[4], item.isCustom());
        return values;
    }

    /**
     * This method will Convert DataBase object, Cursor, into a List
     * of Item's
     *
     * @param cursor the data from the query
     * @return List of Item's
     */
    public static List<Item> DBtoItems(Cursor cursor){
        List<Item> list = new ArrayList<Item>();

        /* Starts from the beginning of the query */
        if (cursor.moveToFirst()){
            /* Makes sure to stay in bounds */
            while (!cursor.isAfterLast()){
                /* Extracts all the key data from the row */
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String dir = cursor.getString(3);
                int custom = cursor.getInt(4);
                /* Saves the item object into the list */
                list.add(new Item(id, name, type, dir, custom));

                /* Moves on to the next row of the query */
                cursor.moveToNext();
            }
        }
        return list;
    }

    /**
     * TODO: fill explanation
     * @param groceryList
     * @return
     */
    public static ContentValues groceryListsToDB(GroceryList groceryList){
        ContentValues values = new ContentValues();
        values.put(GLMDataBase.GROCERY_LISTS_COLS[1], groceryList.getName());
        values.put(GLMDataBase.GROCERY_LISTS_COLS[2], groceryList.getDescription());
        values.put(GLMDataBase.GROCERY_LISTS_COLS[3], groceryList.getIconDir());
        values.put(GLMDataBase.GROCERY_LISTS_COLS[4], groceryList.isSelected());
        return values;
    }

    /**
     * TODO: fill explanation
     * @param cursor
     * @return
     */
    public static List<GroceryList> DBtoGroceryLists(Cursor cursor) {
        List<GroceryList> groceryLists = new ArrayList<GroceryList>();

        /* Starts from the beginning of the query */
        if (cursor.moveToFirst()){
            /* Makes sure to stay in bounds */
            while (!cursor.isAfterLast()){
                /* Extracts all the key data from the row */
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getColumnName(2);
                String dir = cursor.getString(3);
                int checked = cursor.getInt(4);
                /* Saves the item object into the list */
                groceryLists.add(new GroceryList(id, name, description, dir, checked));

                /* Moves on to the next row of the query */
                cursor.moveToNext();
            }
        }
        return groceryLists;
    }

    /**
     * TODO: fill explanation
     * @param groceryItem
     * @return
     */
    public static ContentValues groceryItemToDB(GroceryItem groceryItem){
        ContentValues values = new ContentValues();
        values.put(GLMDataBase.GROCERY_ITEMS_COLS[1], groceryItem.getItemId());
        values.put(GLMDataBase.GROCERY_ITEMS_COLS[2], groceryItem.getListId());
        values.put(GLMDataBase.GROCERY_ITEMS_COLS[3], groceryItem.getQuantity());
        values.put(GLMDataBase.GROCERY_ITEMS_COLS[4], groceryItem.isSelectedRaw());
        return values;
    }

    /**
     * TODO: fill explanation
     * @param cursor
     * @return
     */
    public static List<GroceryItem> DBtoGroceryItems(Cursor cursor) {
        List<GroceryItem> groceryItems = new ArrayList<GroceryItem>();

        /* Starts from the beginning of the query */
        if (cursor.moveToFirst()){
            /* Makes sure to stay in bounds */
            while (!cursor.isAfterLast()){
                /* Extracts all the key data from the row */
                int gid = cursor.getInt(0);
                int itemId = cursor.getInt(1);
                int listId = cursor.getInt(2);
                int quantity = cursor.getInt(3);
                int checked = cursor.getInt(4);
                /* Saves the item object into the list */
                groceryItems.add(new GroceryItem(gid, itemId, listId, quantity, checked));

                /* Moves on to the next row of the query */
                cursor.moveToNext();
            }
        }
        return groceryItems;
    }
}
