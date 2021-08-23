package edu.qc.seclass.glm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.qc.seclass.glm.datastructures.Item;

public class GLMDataBase extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public static final String TAG = "GLMDB Tag";

    public static final String DB_NAME = "glm.db";

    public static final String ITEMS_TABLE = "items_table";
    public static final String[] ITEMS_COLS = {"ItemID", "Name", "Type", "IconDir", "Custom"};

    public static final String GROCERY_LISTS_TABLE = "grocery_lists_table";
    public static final String[] GROCERY_LISTS_COLS = {"ListID", "ListName", "ListDescription", "IconDir", "Checked"};

    public static final String GROCERY_ITEMS_TABLE = "grocery_items_table";
    public static final String[] GROCERY_ITEMS_COLS = {"G_ID", "ItemID", "ListID", "Quantity", "Checked"};

    private final Context context;

    public GLMDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
//        this.context.deleteDatabase(DB_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    /**
     * This method will be executed if the database does not exist. It will create a new table for
     * default items, and then fill in the table with provided data. This will only be executed once
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createDefaultItemsTable(db);
        populateWithDefaultItems(db);

        createGroceryListsTable(db);
        createGroceryItemsTable(db);
    }

    private void createDefaultItemsTable(SQLiteDatabase db){
        String sql = "CREATE TABLE IF NOT EXISTS " + ITEMS_TABLE + " (";
        sql += ITEMS_COLS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += ITEMS_COLS[1] + " TEXT, " + ITEMS_COLS[2] + " TEXT, ";
        sql += ITEMS_COLS[3] + " TEXT, " + ITEMS_COLS[4] + " INTEGER)";

        db.execSQL(sql);
    }

    private void createGroceryListsTable(SQLiteDatabase db){
        String sql = "CREATE TABLE IF NOT EXISTS " + GROCERY_LISTS_TABLE + " (";
        sql += GROCERY_LISTS_COLS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += GROCERY_LISTS_COLS[1] + " TEXT, " + GROCERY_LISTS_COLS[2] + " TEXT, ";
        sql += GROCERY_LISTS_COLS[3] + " TEXT, " + GROCERY_LISTS_COLS[4] + " INTEGER)";

        db.execSQL(sql);
    }

    private void createGroceryItemsTable(SQLiteDatabase db){
        String sql = "CREATE TABLE IF NOT EXISTS " + GROCERY_ITEMS_TABLE + " (";
        sql += GROCERY_ITEMS_COLS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += GROCERY_ITEMS_COLS[1] + " INTEGER, " + GROCERY_ITEMS_COLS[2] + " INTEGER, ";
        sql += GROCERY_ITEMS_COLS[3] + " INTEGER, " + GROCERY_ITEMS_COLS[4] + " INTEGER)";

        db.execSQL(sql);
    }

    /**
     * TODO: Explain well
     * for time being, goes through the items in default items and turns each one into db object and inserts
     * @param db
     */
    private void populateWithDefaultItems(SQLiteDatabase db) {
        try {
            InputStream stream = this.context.getAssets().open("items/default_types.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0 || line.contains("#"))
                    continue;
                boolean result = insertItem(db, line);
                if (!result) {
                    Log.d(TAG, "Could not insert: " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Given a raw string that is in format of id,name,type,icon it will convert it
     * into a content values so that it can be inserted into the database
     *
     * TODO: finish explaining
     * @param db
     * @param raw
     * @return
     */
    private boolean insertItem(SQLiteDatabase db, String raw){
        String data[] = raw.split(",");
        String dir = "items/" + data[1].toLowerCase().replace(" ","_") + "/"  + data[2];
        Item item = new Item(-1, data[0], data[1], dir, 0);
        ContentValues values = Converter.itemToDB(item);
        long result = db.insert(ITEMS_TABLE, null, values);
        return result == -1 ? false : true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ITEMS_TABLE);
        onCreate(db);
    }
}
