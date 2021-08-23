package edu.qc.seclass.glm.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMFragment;
import edu.qc.seclass.glm.custom.GLMImageView;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.datastructures.Item;
import edu.qc.seclass.glm.db.Converter;

public class SearchFragment extends GLMFragment implements View.OnClickListener, TextWatcher{

    /* The instance of this class to keep this object an Singleton */
    private static SearchFragment instance;

    /* Padding and Dimensions for the TableRow and Table in the ui */
    private static int PADDING = 25;
    private static int IMAGE_DIMEN = 0;

    /* Keeps track of all the GLMImageView for sorting and searching items */
    private ArrayList<GLMImageView> imageViews;

    /* Reference to the GroceryList that was passed in */
    private GroceryList attachedList;

    public static SearchFragment instance(GroceryList list){
        if (instance == null)
            instance = new SearchFragment();
        instance.attachedList = list;
        return instance;
    }

    /* Unavailable for any other class to have a instance of this object */
    private SearchFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EditText)view.findViewById(R.id.input_searchFragment)).addTextChangedListener(this);
        view.findViewById(R.id.clear_searchFragment).setOnClickListener(this);
        this.glm.setAppTitle("Search items");
        this.populateTable(view);
    }

    /**
     * This function will open the image from assets and resize is to IMAGE_DIMEN
     *
     * @param view Access to the current ui
     * @param entry Information about the item Type to extract
     * @return Bitmap, the image needed for ImageView
     */
    private Bitmap getImage(View view, Map.Entry<String, String> entry){
        try {
            InputStream stream = view.getContext().getAssets().open("types/" + entry.getValue());
            Bitmap bm = BitmapFactory.decodeStream(stream);
            bm = Bitmap.createScaledBitmap(bm, IMAGE_DIMEN, IMAGE_DIMEN, true);
            Log.d("BAHTI","GOOD IMAGE "+entry.getKey());
            return bm;
        } catch(Exception e) {
            Log.d("BAHTI","NULL IMAGE "+entry.getKey());
            return null;
        }
    }

    /**
     * Will create and set GLMImageView needed for TableRow. Custom ImageView contains information
     * about the item type associated with the image
     *
     * @param view Access to the current ui
     * @param entry Information about the item type
     * @return GLMImageView, the custom ImageView for TableRow
     */
    private GLMImageView getImageView(View view, Map.Entry<String, String> entry) {
        GLMImageView image = new GLMImageView(view.getContext());
        image.setPadding(PADDING, PADDING, PADDING, PADDING);
        image.setImageBitmap(getImage(view, entry));
        image.setOnClickListener(this);
        image.setType(entry.getKey());
        return image;
    }

    /**
     * When the SearchFragment is opened, the fragment will go through all the Item Type's and create ImageView's associated
     * with that type to allow for searching, sorting, and displaying the table
     *
     * @param view, reference to the View
     */
    private void populateTable(View view){
        /* Dynamically creates dimensions for the images based on the app width */
        PADDING =  ((int)(this.getActivity().getWindow().getDecorView().getWidth() * 0.08)) / 4;
        IMAGE_DIMEN = (this.getActivity().getWindow().getDecorView().getWidth() - (PADDING * 6) ) / 2;

        TableLayout myTable = clearAndReturnTable();

        /* Iterator to loop through the general item types and create ImageViews for each type */
        Iterator<Map.Entry<String, String>> iterator = this.glm.getTypesToImage().entrySet().iterator();

        imageViews = new ArrayList<GLMImageView>();

        while (iterator.hasNext()) {
            /* The row that will contain two image views side by side */
            TableRow tableRow = new TableRow(view.getContext());

            /* Takes the first item type and creates a type icon image */
            Map.Entry<String, String> entry = iterator.next();
            GLMImageView image = getImageView(view, entry);

            /* Adds the image to the table, and allows us to search later */
            tableRow.addView(image);
            imageViews.add(image);

            Log.d("BAHTI","IMAGE ENTRY1 "+entry.getKey()+ " "+imageViews.size());

            /* Adds the second item type to the row */
            entry = iterator.next();
            image = getImageView(view, entry);

            /* Adds the image to the table, and allows us to search later */
            tableRow.addView(image);
            imageViews.add(image);

            //tableRow.setBackgroundColor(Color.BLUE);

            Log.d("BAHTI","IMAGE ENTRY2 "+entry.getKey()+ " "+imageViews.size());

            /* Adds the row to the table so it is visible to the user */
            myTable.addView(tableRow);
        }

        Log.d("BAHTI", "TOTAL "+myTable.getChildCount());

        //showAllImages();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof GLMImageView){
            /* Opens the SearchTypeFragment to display the item type that was clicked */
            GLMImageView clickedImage = (GLMImageView) v;
            Converter.viewToActivity(v).launchFragment(SearchTypeFragment.instance(clickedImage.getType()));
        }
        /* Will clear the search text box in search fragment */
        if (v.getId() == R.id.clear_searchFragment){
            ((EditText)this.getView().findViewById(R.id.input_searchFragment)).setText("");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        /* What the user has typed in the search bar, in lower case */
        String search = s.toString().toLowerCase();

        /* Makes sure to only check if something is typed in the search bar, else display everything */
        if (search.length() >= 1) {

            /* Keeps track of the item types that match with what was searched */
            Set<String> accepted = new HashSet<String>();

            /* This will loop through all the items the app supports and checks if what was searched is part of an item */
            for (Map.Entry<Integer, Item> entry : this.glm.getDefaultItems().entrySet()) {
                if (entry.getValue().getName().toLowerCase().contains(search)) {
                    accepted.add(entry.getValue().getType());
                }
            }

            /* This will loop through all the item types and sees if what was searched exists as a type */
            for (String type : this.glm.getTypesToImage().keySet()){
                if (type.toLowerCase().contains(search)){
                    accepted.add(type);
                }
            }

            /* Will clear the current Table so the types can be sorted by search type */
            TableLayout myTable = clearAndReturnTable();

            TableRow tableRow = new TableRow(this.getView().getContext());
            boolean two = false;

            /* Goes through all the available ImageView's and finds the type that matches with what was searched */
            for (GLMImageView image : imageViews) {
                if (accepted.contains(image.getType())){
                    /* Adds the ImageView to the TableRow and once it has 2 images, it will add it to the Table */
                    tableRow.addView(image);
                    if (two) {
                        myTable.addView(tableRow);
                        tableRow = new TableRow(this.getView().getContext());
                    }
                    two = !two;
                }
            }

            /* In the case there is a row with only 1 ImageView, it will add the trailing row */
            if (two) myTable.addView(tableRow);

            /* This will refresh the Table so that the user can see the updated rows */
            myTable.invalidate();
            myTable.refreshDrawableState();
        }

        /* The user cleared their search, will re display all the rows on the Table */
        else showAllImages();

    }

    /**
     * Will de reference all the ImageViews from the TableRows. Then de reference all the TableRows from the Table
     *
     * @return TableLayout, the reference to an empty Table
     */
    private TableLayout clearAndReturnTable(){
        TableLayout myTable = (TableLayout) this.getView().findViewById(R.id.table_searchFragment);

        /* Loops through the all the child views the table has and extracts each view */
        for (int i = 0; i < myTable.getChildCount(); i++) {
            View v = myTable.getChildAt(i);
            /* If the current child view in Table is a TableRow, then de reference the ImageView's from the Row */
            if (v instanceof TableRow) ((TableRow)v).removeAllViewsInLayout();
        }
        /* This will de reference all the TableRows from Table*/
        myTable.removeAllViewsInLayout();
        return myTable;
    }

    /**
     *
     */
    private void showAllImages(){

        /* Will clear the current Table so we can add all the rows back on to it safely */
        TableLayout myTable = clearAndReturnTable();

        /* Creates a starter TableRow */
        TableRow tableRow = new TableRow(this.getView().getContext());
        boolean two = false;

        /* Loops through all the ImageViews we have, and puts two IV into TableRow */
        for (GLMImageView image : imageViews) {
            tableRow.addView(image);
            /* Once there are two images in TableRow, the Row is added on to the table and a new TableRow object is created*/
            if (two) {
                myTable.addView(tableRow);
                tableRow = new TableRow(this.getView().getContext());
            }
            two = !two;
        }

        /* This will refresh the Table so that the user can see the updated rows */
        myTable.invalidate();
        myTable.refreshDrawableState();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
}
