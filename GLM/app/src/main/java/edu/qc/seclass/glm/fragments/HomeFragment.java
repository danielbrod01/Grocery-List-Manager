package edu.qc.seclass.glm.fragments;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Map;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMButton;
import edu.qc.seclass.glm.custom.GLMFragment;
import edu.qc.seclass.glm.custom.GLMTableRow;
import edu.qc.seclass.glm.datastructures.Constant;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.db.Converter;

/**
 * This is the default HomeFragment page that will be displayed in the MainActivities Fragment Viewer.
 * It contains a Table in the center of the screen, with TableRows that displays the information
 * of all active GroceryLists. Upon clicking the active selectable button, user can delete or rename
 * that particular GroceryList. When more than 1 GroceryList is selected, an option to merge these
 * lists will appear.
 *
 * In the center, there is a static New List button, which redirects the user to NewListFragment page.
 *
 * Upon clicking any Row in the Table, the user will be redirected to GroceryListFragment page.
 *
 * @author bahtibyte
 * @version 1.0
 * @since 2021-04-05
 */
public class HomeFragment extends GLMFragment implements View.OnClickListener{

    /* Reference to the buttons on the ui */
    private Button newList;
    private Button delete;
    private Button merge;
    private Button rename;

    /**
     * This static method will return a reference to the HomeFragment since
     * the constructor of this class is private. Keeping the structure of a
     * singleton class, without it being singleton.
     *
     * @return HomeFragment contains all the GroceryLists and options to interact with them
     */
    public static HomeFragment getInstance(){
        return new HomeFragment();
    }

    private HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.glm.setAppTitle("Grocery List Manager");

        /* Extracts the main four buttons on the home page */
        delete = view.findViewById(R.id.deleteButton_homeFragment);
        merge = view.findViewById(R.id.mergeButton_homeFragment);
        rename = view.findViewById(R.id.renameButton_homeFragment);
        newList = view.findViewById(R.id.newListButton_homeFragment);

        /* Connects the listeners to this class so we know when it is clicked */
        newList.setOnClickListener(this);
        delete.setOnClickListener(this);
        merge.setOnClickListener(this);
        rename.setOnClickListener(this);

        /* Displays all the GroceryLists on the main Table in the center of the ui */
        this.populateTable();
    }

    /**
     * This method will clear the Table in the center of the ui and for each GroceryList, it will dynamically
     * generate new TableRow. On the TableRow, it will display the list icon, the list name, and a button to
     * show the current selectable status. TableRow will also have a listener such that when clicked on any
     * part of the row, it will open the GroceryListFragment to further show the contents of that list.
     */
    private void populateTable() {

        /* The main table that will display everything */
        TableLayout table = Constant.clearAndReturnTable(this.getView(), R.id.table_homeFragment);
        table.setPadding(Constant.HOME_PADDING, Constant.HOME_PADDING, Constant.HOME_PADDING, Constant.HOME_PADDING);

        /* Layouts for text labels and buttons so that it is in the center and evenly spread out */
        TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1.0f);
        TableRow.LayoutParams buttonParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1.0f);

        /* Loops through every GroceryList entry in the GroceryListManager */
        for (Map.Entry<Integer, GroceryList> entry : this.glm.getGroceryListLookup().entrySet()) {

            /* Dynamically creates a new TableRow with correct paddings, layout and generates a view id */
            GLMTableRow row = new GLMTableRow(this.getContext());
            row.setPadding(Constant.HOME_PADDING, Constant.HOME_PADDING, Constant.HOME_PADDING, Constant.HOME_PADDING);
            row.setGravity(Gravity.CENTER);
            row.setId(View.generateViewId());

            GroceryList groceryList = entry.getValue();

            /* Saves the reference of the GroceryList that is tied to this TableRow. Also listens for user cliccks */
            row.setGroceryList(groceryList);
            row.setOnClickListener(this);

            /* Extracts the grocery list icon from assets or local storage folder and displays it on the table row */
            ImageView image = getImageView(this.getView(), groceryList);
            row.addView(image);

            /* Dynamically creates a TextView and sets necessary styling to display the list name on the TableRow */
            TextView text = new TextView(this.getView().getContext());
            text.setPadding(Constant.HOME_PADDING, Constant.HOME_PADDING, Constant.HOME_PADDING, Constant.HOME_PADDING);
            text.setTypeface(text.getTypeface(), Typeface.BOLD);
            text.setGravity(Gravity.CENTER);
            text.setLayoutParams(textViewParam);
            text.setText(groceryList.getName());
            text.setTextSize(20);
            row.addView(text);

            /* This button allows the user to select/un select the grocery list. The icon will update depending on the status of the list */
            GLMButton selectable = new GLMButton(this.getContext());
            selectable.setGroceryList(groceryList);
            selectable.setLayoutParams(buttonParam);
            selectable.setOnClickListener(this);
            if (groceryList.isSelected())
                selectable.setBackgroundResource(R.drawable.ic_baseline_check_box_24);
            else
                selectable.setBackgroundResource(R.drawable.ic_baseline_check_box_outline_blank_24);
            row.addView(selectable);

            /* Lastly, adds the TableRow to the Table so it can be visible to the user */
            table.addView(row);
        }

        /* This will update the user options at the bottom of the list depending on the number of grocery lists selected */
        this.updateOptions();
    }

    /**
     * Dynamically creates a new ImageView with necessarry styling to match the ui's size requirements. Depending on how the
     * grocery list icon was established, it will extract and load the image icon from the correct storage location.
     *
     * @param view current ui that is being worked on
     * @param list the list's icon that needs to be displayed
     * @return
     */
    private ImageView getImageView(View view, GroceryList list) {
        ImageView image = new ImageView(view.getContext());
        image.setPadding(Constant.HOME_PADDING, Constant.HOME_PADDING, Constant.HOME_PADDING, Constant.HOME_PADDING);
        if (Constant.isIconDefault(list.getIconDir()))
            image.setImageBitmap(Constant.getAssetsImage(view, list.getIconDir(), Constant.HOME_IMAGE_WIDTH));
        else
            image.setImageBitmap(Constant.getLocalImage(view, list.getIconDir(), Constant.HOME_IMAGE_WIDTH));
        return image;
    }

    /**
     * Depending on the number of grocery lists selected, different options will be displayed.
     * If no lists are selected, all options are hidden
     * If only one list is selected, the user can only delete or rename that list
     * if more than one list is selected, the user can delete all or merge all the selected lists
     */
    private void updateOptions(){
        int count = totalSelected();

        if (count == 0){
            this.merge.setVisibility(View.GONE);
            this.delete.setVisibility(View.GONE);
            this.rename.setVisibility(View.GONE);
        }
        else if (count == 1) {
            this.delete.setVisibility(View.VISIBLE);
            this.merge.setVisibility(View.GONE);
            this.rename.setVisibility(View.VISIBLE);
        }else {
            this.delete.setVisibility(View.VISIBLE);
            this.merge.setVisibility(View.VISIBLE);
            this.rename.setVisibility(View.GONE);
        }
    }

    /**
     *
     * @param button
     */
    private void updateSelectedList(GLMButton button){
        /* Inverts the checked/unchecked */
        if (!button.getGroceryList().isSelected())
            button.setBackgroundResource(R.drawable.ic_baseline_check_box_24);
        else
            button.setBackgroundResource(R.drawable.ic_baseline_check_box_outline_blank_24);

        /* Updates the database */
        this.glm.selectList(button.getGroceryList());

        /* Updates the ui delete/merge buttons after database is up to date */
        this.updateOptions();
    }

    /**
     *
     * @return
     */
    private int totalSelected() {
        return this.glm.getSelectedLists().size();
    }

    /**
     *
     * @param toDelete
     */
    private void promptDeletion(ArrayList<GroceryList> toDelete){

        final HomeFragment home = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(Converter.viewToActivity(this.getView()));
        builder.setTitle("Deleting GroceryLists");
        builder.setMessage("Are you sure you want to delete the following grocery list(s)?"+listsToString(toDelete));

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boolean result = home.glm.deleteSelectedLists(toDelete);
                if (result){
                    home.populateTable();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { /* Do Nothing */ }
        });

        builder.create().show();
    }

    /**
     *
     * @param toMerge
     */
    private void promptMerge(ArrayList<GroceryList> toMerge){

        final HomeFragment home = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(Converter.viewToActivity(this.getView()));
        builder.setTitle("Merging GroceryLists");
        builder.setMessage("Are you sure you want to merge the following grocery list(s)?"+listsToString(toMerge));

        builder.setPositiveButton("Merge", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boolean result = glm.mergeSelectedLists(toMerge);
                if (result) {
                    home.populateTable();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { /* Do Nothing */ }
        });

        builder.create().show();
    }

    /**
     *
     * @param toRename
     */
    private void promptRename(final GroceryList toRename){

        final HomeFragment home = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Please enter a new name");

        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (home.glm.findList(input.getText().toString()) == null) {
                    home.glm.renameList(toRename, input.getText().toString());
                    home.glm.selectList(toRename);
                    home.populateTable();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { /* Do Nothing */}
        });

        builder.show();
    }

    /**
     *
     * @param lists
     * @return
     */
    private String listsToString(ArrayList<GroceryList> lists){
        String out = "";
        for (GroceryList list : lists){
            out += "\n - " + list.getName();
        }
        return out;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.newListButton_homeFragment) {
            Converter.viewToActivity(view).launchFragment(NewListFragment.instance());
        }
        if (view instanceof GLMTableRow){
            Converter.viewToActivity(view).launchFragment(GroceryListFragment.instance(((GLMTableRow)view).getGroceryList()));
        }
        if (view.getId() == delete.getId()){
            this.promptDeletion(this.glm.getSelectedLists());
        }
        if (view.getId() == merge.getId()){
            this.promptMerge(this.glm.getSelectedLists());
        }
        if (view.getId() == rename.getId()) {
            this.promptRename(this.glm.getSelectedLists().get(0));
        }
        if (view instanceof GLMButton) {
            this.updateSelectedList((GLMButton) view);
        }
    }
}