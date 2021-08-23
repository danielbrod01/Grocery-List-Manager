package edu.qc.seclass.glm.datastructures;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TableLayout;

import java.io.InputStream;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMTableRow;

public class Constant {

    public static String lastTypeSelected;

    public static String lastGroceryListAddition;

    public static int GLOBAL_WIDTH;

    public static int HOME_IMAGE_WIDTH;
    public static int HOME_PADDING;

    public static void calculateWidth(int width){
        GLOBAL_WIDTH = width;
        HOME_IMAGE_WIDTH = (int) (GLOBAL_WIDTH * 0.25);
        HOME_PADDING = ((int)(GLOBAL_WIDTH * 0.08)) / 4;
    }

    public static boolean isIconDefault(String icon) {
        return icon.length() == 12 && icon.substring(0, 7).equals("grocery") && icon.substring(8, icon.length()).equals(".png") && Character.isDigit(icon.charAt(7));
    }

    /**
     *
     * @return
     */
    public static TableLayout clearAndReturnTable(View view, int id){
        TableLayout myTable = (TableLayout) view.findViewById(id);

        /* Loops through the all the child views the table has and extracts each view */
        for (int i = 0; i < myTable.getChildCount(); i++) {
            View v = myTable.getChildAt(i);
            /* If the current child view in Table is a TableRow, then de reference the ImageView's from the Row */
            if (v instanceof GLMTableRow) ((GLMTableRow)v).removeAllViewsInLayout();
        }
        /* This will de reference all the TableRows from Table*/
        myTable.removeAllViewsInLayout();
        return myTable;
    }

    public static Bitmap getLocalImage(View view, String dir, int size){
        try {
            return getImage(view.getContext().openFileInput(dir), size);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getAssetsImage(View view, String icon, int size){
        try {
            return getImage(view.getContext().getAssets().open("listsIcons/"+icon), size);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Bitmap getImage(InputStream stream, int size) {
        try {
            Bitmap bm = BitmapFactory.decodeStream(stream);
            bm = Bitmap.createScaledBitmap(bm, size, size, true);
            return bm;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
