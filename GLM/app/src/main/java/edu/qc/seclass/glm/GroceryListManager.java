package edu.qc.seclass.glm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.qc.seclass.glm.datastructures.GroceryItem;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.db.Converter;
import edu.qc.seclass.glm.datastructures.Item;
import edu.qc.seclass.glm.db.DBHelper;
import edu.qc.seclass.glm.db.GLMDataBase;
import edu.qc.seclass.glm.fragments.MainActivity;

/**
 * This class will contain all the data needed for the GroceryListManager. Since this class
 * is a Singleton class, we do not know have to query and populate the lists over and over.
 */
public class GroceryListManager {

    /* Reference to itself */
    private static GroceryListManager instance;

    /* Keeps track of default items */
    private HashMap<Integer, Item> defaultItems;
    private HashMap<String, ArrayList<Item>> typeLookup;
    private HashMap<String, String> typesToImages;

    /* Keeps track of grocery lists */
    private HashMap<Integer, GroceryList> groceryListLookup;

    /* List of all the names of all the grocery lists */
    private ArrayList<String> groceryListNames;

    /* List of all the default icons for grocery lists */
    private ArrayList<String> listIcons;

    /* Reference to the database */
    private GLMDataBase db;

    /* Reference to the current Context */
    private Context context;

    /**
     * Returns an instance of GroceryListManager class. If an instance was not instantiated, it
     * will create a new instance and return that. Promises to instantiate only once.
     * @param db needed to query initial data from the database
     * @return GroceryListManager, instance to object
     */
    public static GroceryListManager getInstance(GLMDataBase db, Context context){
        if (instance == null)
            instance = new GroceryListManager(db, context);
        instance.context = context;
        instance.db = db;
        return instance;
    }

    /**
     * TODO: explain
     * @param db
     */
    private GroceryListManager(GLMDataBase db, Context context) {
        defaultItems = new HashMap<Integer, Item>();
        groceryListLookup = new HashMap<Integer, GroceryList>();
        typeLookup = new HashMap<String, ArrayList<Item>>();
        typesToImages = new HashMap<String, String>();
        groceryListNames = new ArrayList<String>();
        listIcons = new ArrayList<String>();

        for (int i = 1; i <= 7; i++)
            listIcons.add("grocery"+i+".png");

        setTypesToImages(context);

        SQLiteDatabase readableDB = db.getReadableDatabase();

        /* the following statements will pre fill and map all the stored data */
        fillDefaultItems(readableDB);
        populateGroceryLists(readableDB);
        fillGroceryLists(readableDB);
    }

    /**
     * TODO: explain
     * @param db
     */
    private void fillDefaultItems(SQLiteDatabase db){
        /* Turns the queried data from db into a array of Item objects */
        Cursor cursor = DBHelper.queryAllItems(db);
        List<Item> items = Converter.DBtoItems(cursor);

        /* This will map every item with its item id, will help with fast lookup */
        for (Item item : items) {
            defaultItems.put(item.getItemId(), item);

            if (!typeLookup.containsKey(item.getType())) {
                typeLookup.put(item.getType(), new ArrayList<Item>());
            }
            typeLookup.get(item.getType()).add(item);
        }
    }

    /**
     * TODO: explain
     * @param db
     */
    private void populateGroceryLists(SQLiteDatabase db){
        /* Turns the queried data from db into a array of GroceryList objects */
        Cursor cursor = DBHelper.queryAllGroceryLists(db);
        List<GroceryList> groceries = Converter.DBtoGroceryLists(cursor);

        /* This will map every GroceryList with its list id, will help with fast lookup */
        for (GroceryList grocery : groceries) {
            groceryListLookup.put(grocery.getListId(), grocery);
            groceryListNames.add(grocery.getName());
        }
    }

    /**
     * TODO: Complete the function
     *
     * This method will fetch all the grocery items from the table and assign them to its correct
     * grocery list.
     * @param db
     */
    private void fillGroceryLists(SQLiteDatabase db){
        /* Turns the queried data from db into a array of GroceryList objects */
        Cursor cursor = DBHelper.queryAllGroceryItems(db);
        List<GroceryItem> groceryItems = Converter.DBtoGroceryItems(cursor);

        /* This will map every GroceryList with its list id, will help with fast lookup */
        for (GroceryItem item : groceryItems) {
            item.adoptItem(this.defaultItems.get(item.getItemId()));
            groceryListLookup.get(item.getListId()).addItem(item);
        }
    }

    private void setTypesToImages(Context context) {
        try {
            InputStream stream = context.getAssets().open("typeImages.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                String data[] = line.split(",");
                typesToImages.put(data[0], data[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setAppTitle(String title) {
        ((TextView)(((MainActivity)this.context).findViewById(R.id.action_bar_title))).setText(title);
    }

    public HashMap<Integer, Item> getDefaultItems(){
        return defaultItems;
    }
    public HashMap<Integer, GroceryList> getGroceryListLookup(){
        return groceryListLookup;
    }
    public HashMap<String, String> getTypesToImage(){
        return this.typesToImages;
    }
    public HashMap<String, ArrayList<Item>> getTypeLookup() { return this.typeLookup; }
    public ArrayList<String> getGroceryListNames(){
        return this.groceryListNames;
    }


    public String getRandomIcon() {
        return this.listIcons.get((int)(Math.random()*this.listIcons.size()));
    }

    /**
     * TODO: Requirements: The user creates a new list in the List Manager.
     */
    public GroceryList createList(GroceryList listToInsert){

        ContentValues values = Converter.groceryListsToDB(listToInsert);
        boolean success = DBHelper.insertNewList(db.getWritableDatabase(), values);

        if (!success) return null;

        Cursor cursor = DBHelper.queryLastInserted(db.getReadableDatabase());
        List<GroceryList> groceryLists = Converter.DBtoGroceryLists(cursor);

        if (groceryLists.size() != 1) return null;

        /* This is the actual GroceryList that is saved into the DB */
        GroceryList lastAdded = groceryLists.get(0);

        /* Saves the grocery list to our local mapped data structures for easy lookup */
        this.groceryListLookup.put(lastAdded.getListId(), lastAdded);
        this.groceryListNames.add(lastAdded.getName());

        return lastAdded;
    }


    public boolean deleteSelectedLists(ArrayList<GroceryList> toDelete) {

        for (GroceryList list : toDelete){
            boolean result = this.deleteList(list);
        }

        return true;
    }

    /**
     * TODO: Implement Merge after adding/deleting items is done
     *
     * @param toMerge
     */
    public boolean mergeSelectedLists(ArrayList<GroceryList> toMerge) {

        GroceryList first = toMerge.get(0);

        for (int i = 1; i < toMerge.size(); i++){

            GroceryList merge = toMerge.get(i);

            ArrayList<GroceryItem> gitems = new ArrayList<>(merge.getItems());

            for (GroceryItem gitem : gitems){
                this.swapGroceryItem(gitem, first);
            }

            this.deleteList(merge);
        }

        this.selectList(first);

        return true;
    }

    public void swapGroceryItem(GroceryItem gitem, GroceryList to){

        boolean result = DBHelper.updateGroceryItemListID(db.getWritableDatabase(), gitem.getId(), to.getListId());
        if (!result){
            Log.d("BAHTI","UNABLE TO UPDATE THE GLIST "+gitem.getId());
        }
        this.groceryListLookup.get(gitem.getListId()).getItems().remove(gitem);
        gitem.setListId(to.getListId());
        to.getItems().add(gitem);
    }

    /**
     * TODO: Requirements: The list is deleted in the List Manager.
     */
    public boolean deleteList(GroceryList list){

        this.deleteItemsFromList(list, new ArrayList<GroceryItem>(list.getItems()));

        boolean result = DBHelper.deleteGroceryListRow(db.getWritableDatabase(), list.getListId());
        /* Successfully deleted the list */
        if (result) {
            this.groceryListLookup.remove(list.getListId());
            this.groceryListNames.remove(list.getName());
        }

        /*When deleting make sure to delete the local saved image*/
        String icon = list.getIconDir();

        if (!isDefaultIcon(icon)){
            try {

                File dir = this.context.getFilesDir();
                File img = new File(dir, icon);
                boolean deleted = img.delete();
                if (deleted){
                    Log.d("BAHTI","DELETED");
                }else{
                    Log.d("BAHTI","DID not work");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }

    private boolean isDefaultIcon(String dir){
        return dir.length() == 12 && dir.substring(0, 7).equals("grocery") && dir.substring(8, dir.length()).equals(".png") && Character.isDigit(dir.charAt(7));
    }

    /**
     * TODO: Requirements: The user selects a list in the List Manager.
     */
    public void selectList(GroceryList list){
        /* Updates the local reference */
        list.setIsChecked((list.isSelectedRaw()+1) % 2);

        /* Updates the DB */
        boolean result = DBHelper.updateGroceryListChecked(db.getWritableDatabase(), list.getListId(), list.isSelectedRaw());

        if (!result){
            /* Reverts the local change... db did not sync */
            list.setIsChecked((list.isSelectedRaw()+1) % 2);
        }
    }

    /**
     * TODO: Requirements: The user renames a list in the List Manager.
     */
    public void renameList(GroceryList list, String name){

        /* Updates the DB */
        boolean result = DBHelper.updateGroceryListName(db.getWritableDatabase(), list.getListId(), name);

        if (!result){
            Log.d("BAHTI","Failed to update name");
        }else{
            this.groceryListNames.remove(list.getName());
            list.setName(name);
            this.groceryListNames.add(name);
        }

    }

    /**
     * TODO: Requirements: the user wants to merge two or more lists together
     */
    public void mergeLists(/* parameters */){

    }

    /**
     * TODO: Requirements: The user checks off a selected item on the list.
     */
    public void checkItem(GroceryItem item){
        /* Updates the local reference */
        item.setChecked((item.isSelectedRaw()+1) % 2);

        /* Updates the DB */
        boolean result = DBHelper.updateGroceryItemChecked(db.getWritableDatabase(), item.getId(), item.isSelectedRaw());

        if (!result){
            /* Reverts the local change... db did not sync */
            item.setChecked((item.isSelectedRaw()+1) % 2);
        }
    }

    /**
     * TODO: Requirements: This feature allows the user to uncheck all the items that are currently checked off in the list.
     */
    public void clearAllChecks(ArrayList<GroceryItem> gitems){
        for (GroceryItem gitem : gitems){
            this.checkItem(gitem);
        }
    }

    /**
     * TODO: Requirements: User adds an item into the grocery list.
     */
    public boolean addItemToList(String listName, Item item, int quantity){

        GroceryList list = findList(listName);

        GroceryItem rawGroceryItem = new GroceryItem(-1, item.getItemId(), list.getListId(), quantity, 0);

        ContentValues values = Converter.groceryItemToDB(rawGroceryItem);
        boolean result = DBHelper.insertNewGroceryItem(db.getWritableDatabase(), values);

        if (!result){
            Log.d("BAHTI", "Unable to insert the new grocery item");
            return false;
        }

        Cursor cursor = DBHelper.queryLastGroceryItemInserted(db.getReadableDatabase());

        List<GroceryItem> gitems = Converter.DBtoGroceryItems(cursor);

        if (gitems.size() != 1){
            Log.d("BAHTI","Did not retrieve last item well");
            return false;
        }

        GroceryItem realItem = gitems.get(0);
        realItem.adoptItem(item);

        list.getItems().add(realItem);

        return true;
    }

    /**
     * TODO: Requirements: updates the quantity of the item.
     */
    public void updateQuantity(GroceryItem item, int quantity){

        boolean result = DBHelper.updateGroceryItemQuantity(db.getWritableDatabase(), item.getId(), quantity);
        if (result){
            item.setQuantity(quantity);
        }
    }

    /**
     *
     * @param gitem
     */
    public void deleteGroceryItem(GroceryItem gitem){

        boolean result = DBHelper.deleteGroceryItemRow(db.getWritableDatabase(), gitem.getId());
        if (!result){
            Log.d("BAHTI","UNABLE TO DELETE G ITEM "+gitem.getId());
            return;
        }
        this.groceryListLookup.get(gitem.getListId()).getItems().remove(gitem);
    }

    /**
     *
     * @return
     */
    public ArrayList<GroceryList> getSelectedLists(){
        ArrayList<GroceryList> list = new ArrayList<GroceryList>();

        for (Map.Entry<Integer, GroceryList> entry : this.groceryListLookup.entrySet()) {
            if (entry.getValue().isSelected()) {
                list.add(entry.getValue());
            }
        }

        return list;
    }

    /**
     * TODO: Requirements: User deletes an item currently on the grocery list.
     */
    public void deleteItemsFromList(GroceryList list, ArrayList<GroceryItem> gitems){
        for (GroceryItem gitem : gitems) {
            boolean result = DBHelper.deleteGroceryItemRow(db.getWritableDatabase(), gitem.getId());
            if (!result){
                Log.d("BAHTI","UNABLE TO DELETE G ITEM "+gitem.getId());
                return;
            }
            list.getItems().remove(gitem);
        }
    }


    public GroceryList findList(String name){
        for (Map.Entry<Integer, GroceryList> entry : this.groceryListLookup.entrySet()){
            if (entry.getValue().getName().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }

    private int boolToInt(boolean x) {
        return x ? 1 : 0;
    }
}
