package edu.qc.seclass.glm.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class will keep all interactions with the database here. Used for
 * querying data or inserting data
 */
public class DBHelper {

    /* TODO: Quick explain */
    public static Cursor queryAllItems(SQLiteDatabase db){
        Cursor cursor = db.query(GLMDataBase.ITEMS_TABLE, GLMDataBase.ITEMS_COLS,
                null, null, null, null, null, null);
        return cursor;
    }

    /* TODO: Quick explain */
    public static Cursor queryAllGroceryLists(SQLiteDatabase db) {
        Cursor cursor = db.query(GLMDataBase.GROCERY_LISTS_TABLE, GLMDataBase.GROCERY_LISTS_COLS,
                null, null, null, null, null, null);
        return cursor;
    }

    /* TODO: Quick explain */
    public static Cursor queryAllGroceryItems(SQLiteDatabase db) {
        Cursor cursor = db.query(GLMDataBase.GROCERY_ITEMS_TABLE, GLMDataBase.GROCERY_ITEMS_COLS,
                null, null, null, null, null, null);
        return cursor;
    }

    public static boolean insertToItems(SQLiteDatabase db, ContentValues values) {
        long result = db.insert(GLMDataBase.ITEMS_TABLE, null, values);
        return result > 0;
    }

    public static boolean insertNewList(SQLiteDatabase db, ContentValues values) {
        long result = db.insert(GLMDataBase.GROCERY_LISTS_TABLE, null, values);
        return result > 0;
    }

    public static Cursor queryLastInserted(SQLiteDatabase db){
        String query = "SELECT * FROM " + GLMDataBase.GROCERY_LISTS_TABLE + " WHERE " + GLMDataBase.GROCERY_LISTS_COLS[0] +
                       " = (SELECT MAX(" + GLMDataBase.GROCERY_LISTS_COLS[0] + ") FROM "+ GLMDataBase.GROCERY_LISTS_TABLE +")";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public static boolean deleteGroceryListRow(SQLiteDatabase db, int id){
        long result = db.delete(GLMDataBase.GROCERY_LISTS_TABLE, GLMDataBase.GROCERY_LISTS_COLS[0] + "=" + id, null);
        return result > 0;
    }

    public static boolean updateGroceryListChecked(SQLiteDatabase db, int listId, int isSelected) {
        ContentValues values = new ContentValues();
        values.put(GLMDataBase.GROCERY_LISTS_COLS[4], isSelected);
        long result = db.update(GLMDataBase.GROCERY_LISTS_TABLE, values, GLMDataBase.GROCERY_LISTS_COLS[0] + "=" + listId, null);
        return result > 0;
    }


    public static boolean updateGroceryListName(SQLiteDatabase db, int listId, String name) {
        ContentValues values = new ContentValues();
        values.put(GLMDataBase.GROCERY_LISTS_COLS[1], name);
        long result = db.update(GLMDataBase.GROCERY_LISTS_TABLE, values, GLMDataBase.GROCERY_LISTS_COLS[0] + "=" + listId, null);
        return result > 0;
    }

    public static boolean insertNewGroceryItem(SQLiteDatabase writableDatabase, ContentValues values) {
        long result = writableDatabase.insert(GLMDataBase.GROCERY_ITEMS_TABLE, null, values);
        return result > 0;
    }

    public static Cursor queryLastGroceryItemInserted(SQLiteDatabase readableDatabase){
        String query = "SELECT * FROM " + GLMDataBase.GROCERY_ITEMS_TABLE + " WHERE " + GLMDataBase.GROCERY_ITEMS_COLS[0] +
                " = (SELECT MAX(" + GLMDataBase.GROCERY_ITEMS_COLS[0] + ") FROM "+ GLMDataBase.GROCERY_ITEMS_TABLE +")";
        Cursor cursor = readableDatabase.rawQuery(query, null);
        return cursor;
    }

    public static boolean deleteGroceryItemRow(SQLiteDatabase db, int id){
        long result = db.delete(GLMDataBase.GROCERY_ITEMS_TABLE, GLMDataBase.GROCERY_ITEMS_COLS[0] + "=" + id, null);
        return result > 0;
    }

    public static boolean updateGroceryItemChecked(SQLiteDatabase writableDatabase, int id, int checked) {
        ContentValues values = new ContentValues();
        values.put(GLMDataBase.GROCERY_ITEMS_COLS[4], checked);
        long result = writableDatabase.update(GLMDataBase.GROCERY_ITEMS_TABLE, values, GLMDataBase.GROCERY_ITEMS_COLS[0] + "=" + id, null);
        return result > 0;
    }

    public static boolean updateGroceryItemQuantity(SQLiteDatabase writableDatabase, int id, int quantity) {
        ContentValues values = new ContentValues();
        values.put(GLMDataBase.GROCERY_ITEMS_COLS[3], quantity);
        long result = writableDatabase.update(GLMDataBase.GROCERY_ITEMS_TABLE, values, GLMDataBase.GROCERY_ITEMS_COLS[0] + "=" + id, null);
        return result > 0;
    }

    public static boolean updateGroceryItemListID(SQLiteDatabase writableDatabase, int id, int listId) {
        ContentValues values = new ContentValues();
        values.put(GLMDataBase.GROCERY_ITEMS_COLS[2], listId);
        long result = writableDatabase.update(GLMDataBase.GROCERY_ITEMS_TABLE, values, GLMDataBase.GROCERY_ITEMS_COLS[0] + "=" + id, null);
        return result > 0;
    }
}
