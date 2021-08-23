package edu.qc.seclass.glm.datastructures;

public class Item {

    /* Primary Key */
    private int itemId;

    private String name;
    private String type;
    private String iconDir;

    private int isCustom;

    public Item(int id, String name, String type, String dir, int isCustom){
        this.itemId = id;
        this.name = name;
        this.type = type;
        this.iconDir = dir; //
        this.isCustom = isCustom;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getIconDir() {
        return iconDir;
    }

    public boolean isCustom(){
        return isCustom > 0;
    }
}
