package edu.qc.seclass.glm.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMButton;
import edu.qc.seclass.glm.custom.GLMFragment;
import edu.qc.seclass.glm.custom.GLMTableRow;
import edu.qc.seclass.glm.datastructures.Constant;
import edu.qc.seclass.glm.datastructures.GroceryItem;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.db.Converter;

public class GroceryListFragment extends GLMFragment implements View.OnClickListener{

    //private static GroceryListFragment instance;

    private GroceryList groceryList;

    private static int IMAGE_DIMEN = 100;
    private static int PADDING = 20;

    private Set<Integer> selectableButtons;
    private Set<Integer> settingButtons;

    private Button clearAll;

    public static GroceryListFragment instance(GroceryList list){
        GroceryListFragment instance = new GroceryListFragment();
        instance.groceryList = list;
        return instance;
    }

    private GroceryListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocery_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.addNewItem_groceryListFragment).setOnClickListener(this);
        this.clearAll = view.findViewById(R.id.clearAll_groceryListFragment);
        this.clearAll.setOnClickListener(this);

        this.glm.setAppTitle("Grocery List: "+this.groceryList.getName());

        this.populateTable(view);
    }

    private TableLayout clearAndReturnTable(){
        TableLayout myTable = (TableLayout) this.getView().findViewById(R.id.table_groceryListFragment);

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

    private Bitmap getImage(View view, GroceryItem item){
        try {
            InputStream stream = view.getContext().getAssets().open(item.getItem().getIconDir());
            Bitmap bm = BitmapFactory.decodeStream(stream);
            bm = Bitmap.createScaledBitmap(bm, IMAGE_DIMEN, IMAGE_DIMEN, true);
            return bm;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ImageView getImageView(View view, GroceryItem item) {
        ImageView image = new ImageView(view.getContext());
        image.setPadding(PADDING, PADDING, PADDING, PADDING);
        image.setImageBitmap(getImage(view, item));
        return image;
    }

    public void populateTable(View view){

        IMAGE_DIMEN = (int) (Converter.GLOBAL_WIDTH * 0.20);
        PADDING =  ((int)(Converter.GLOBAL_WIDTH * 0.08)) / 4;

        TableLayout table = clearAndReturnTable();
        //table.setPadding(PADDING, PADDING, PADDING, PADDING);

        TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1.0f);
        TableRow.LayoutParams buttonParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1.0f);

        ArrayList<GroceryItem> gitems = this.glm.getGroceryListLookup().get(this.groceryList.getListId()).getItems();

        if (gitems.size() == 0){
            TextView itemName = new TextView(view.getContext());
            itemName.setText("Grocery List: "+groceryList.getName() +" is empty. Click Add New Item to add items to this list");
            itemName.setLayoutParams(textViewParam);
            itemName.setGravity(Gravity.CENTER);
            itemName.setPadding(PADDING, PADDING, PADDING, PADDING);
            itemName.setTextSize(24);
            itemName.setTypeface(itemName.getTypeface(), Typeface.BOLD);

            GLMTableRow row = new GLMTableRow(view.getContext());
            row.setGravity(Gravity.CENTER);
            row.setPadding(PADDING, PADDING, PADDING, PADDING);

            row.addView(itemName);

            table.addView(row);
        }

        selectableButtons = new HashSet<Integer>();
        settingButtons = new HashSet<Integer>();

        for (GroceryItem item : this.glm.getGroceryListLookup().get(this.groceryList.getListId()).getItems()){

            GLMTableRow row = new GLMTableRow(view.getContext());
            row.setGravity(Gravity.CENTER);
            //row.setPadding(PADDING, PADDING, PADDING, PADDING);
            //row.setBackgroundColor(Color.GREEN);

            ImageView image = getImageView(view, item);
            //image.setBackgroundColor(Color.RED);
            row.addView(image);

            TextView itemName = new TextView(view.getContext());
            itemName.setText(item.getItem().getName());
            itemName.setLayoutParams(textViewParam);
            itemName.setGravity(Gravity.CENTER);
            //itemName.setPadding(PADDING, PADDING, PADDING, PADDING);
            itemName.setTextSize(20);
            itemName.setTypeface(itemName.getTypeface(), Typeface.BOLD);
            //itemName.setBackgroundColor(Color.BLUE);
            row.addView(itemName);

            TextView quantity = new TextView(view.getContext());
            quantity.setText("x "+item.getQuantity());
            quantity.setLayoutParams(textViewParam);
            quantity.setGravity(Gravity.CENTER);
            //quantity.setPadding(PADDING, PADDING, PADDING, PADDING);
            quantity.setTextSize(20);
            quantity.setTypeface(itemName.getTypeface(), Typeface.BOLD);
            //quantity.setBackgroundColor(Color.YELLOW);

            row.addView(quantity);



            GLMButton checked = new GLMButton(view.getContext());
            checked.setLayoutParams(buttonParam);
            if (item.isSelectedRaw() == 1)
                checked.setBackgroundResource(R.drawable.ic_baseline_check_box_24);
            else
                checked.setBackgroundResource(R.drawable.ic_baseline_check_box_outline_blank_24);
            checked.setOnClickListener(this);
            checked.setGroceryItem(item);
            checked.setId(View.generateViewId());

            selectableButtons.add(checked.getId());
            row.addView(checked);

            GLMButton setting = new GLMButton(view.getContext());
            setting.setLayoutParams(buttonParam);
            setting.setBackgroundResource(R.drawable.ic_baseline_settings_24);
            setting.setOnClickListener(this);
            setting.setGroceryItem(item);
            setting.setId(View.generateViewId());
            settingButtons.add(setting.getId());
            row.addView(setting);

            table.addView(row);
        }

        updateClearButton();
    }

    private void updateClearButton() {
        int size = getAllSelected().size();
        if (size != 0){
            clearAll.setVisibility(View.VISIBLE);
        }else{
            clearAll.setVisibility(View.GONE);
        }
    }

    private ArrayList<GroceryItem> getAllSelected(){
        ArrayList<GroceryItem> gitems = new ArrayList<>();
        for (GroceryItem item : this.glm.getGroceryListLookup().get(this.groceryList.getListId()).getItems()){
            if (item.isSelected()) {
                gitems.add(item);
            }
        }
        return gitems;
    }

    private void updateChecked(GLMButton button){

        /* Inverts the checked/unchecked */
        if (!(button.getGroceryItem().isSelectedRaw() == 1))
            button.setBackgroundResource(R.drawable.ic_baseline_check_box_24);
        else
            button.setBackgroundResource(R.drawable.ic_baseline_check_box_outline_blank_24);

        this.glm.checkItem(button.getGroceryItem());

        updateClearButton();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addNewItem_groceryListFragment) {
            Constant.lastGroceryListAddition = this.groceryList.getName();
            Converter.viewToActivity(v).switchToSearchFragment();
        }

        if (selectableButtons.contains(v.getId())){
            GLMButton selectable = (GLMButton) v;
            this.updateChecked(selectable);

        }else if (v.getId() == clearAll.getId()) {

            ArrayList<GroceryItem> gitems = getAllSelected();
            this.glm.clearAllChecks(gitems);
            this.populateTable(this.getView());

        }else if (settingButtons.contains(v.getId())){
            GLMButton settings = (GLMButton)v;
            Constant.lastGroceryListAddition = this.groceryList.getName();
            Converter.viewToActivity(v).launchFragment(EditItemFragment.instance(settings.getGroceryItem()));
        }



    }
}
