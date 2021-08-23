package edu.qc.seclass.glm.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.ArrayList;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMFragment;
import edu.qc.seclass.glm.custom.GLMTableRow;
import edu.qc.seclass.glm.datastructures.Constant;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.datastructures.Item;
import edu.qc.seclass.glm.db.Converter;

public class SearchTypeFragment extends GLMFragment implements View.OnClickListener{

    private static SearchTypeFragment instance;
    private static int IMAGE_DIMEN = 100;
    private static int PADDING = 20;
    public static String lastType;
    private String type;

    private ArrayList<GLMTableRow> rows;

    public static SearchTypeFragment instance(String type){
        if (instance == null)
            instance = new SearchTypeFragment();
        Constant.lastTypeSelected = type;
        instance.type = type;
        return instance;
    }

    private SearchTypeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.back_searchTypeFragment).setOnClickListener(this);
        view.findViewById(R.id.done_searchTypeFragment).setOnClickListener(this);
        this.glm.setAppTitle("Item Type: "+type);

        this.populateTable(view);
    }

    private Bitmap getImage(View view, Item item){
        try {
            InputStream stream = view.getContext().getAssets().open(item.getIconDir());
            Bitmap bm = BitmapFactory.decodeStream(stream);
            bm = Bitmap.createScaledBitmap(bm, IMAGE_DIMEN, IMAGE_DIMEN, true);
            return bm;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ImageView getImageView(View view, Item item) {
        ImageView image = new ImageView(view.getContext());
        image.setPadding(PADDING, PADDING, PADDING, PADDING);
        image.setImageBitmap(getImage(view, item));
        return image;
    }

    public void populateTable(View view){
        IMAGE_DIMEN = (int) (this.getActivity().getWindow().getDecorView().getWidth() * 0.25);
        PADDING =  ((int)(this.getActivity().getWindow().getDecorView().getWidth() * 0.08)) / 4;

        ArrayList<Item> items = this.glm.getTypeLookup().get(this.type);

        rows = new ArrayList<GLMTableRow>();

        TableLayout myTable = (TableLayout) view.findViewById(R.id.table_homeFragment);
        //myTable.setBackgroundColor(Color.BLUE);
        myTable.setPadding(PADDING, PADDING, PADDING, PADDING);

        TableRow.LayoutParams textViewParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1.0f);

        for (Item item : items){

            GLMTableRow row = new GLMTableRow(view.getContext());

            row.setOnClickListener(this);
            row.setItem(item);

            TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(tableRowParams);

            //row.setBackgroundColor(Color.GREEN);

            ImageView image = getImageView(view, item);
            image.setId(View.generateViewId());
            row.addView(image);

            TextView text = new TextView(view.getContext());
            text.setText(item.getName());
            //text.setBackgroundColor(Color.RED);
            text.setLayoutParams(textViewParam);
            text.setGravity(Gravity.CENTER);
            text.setPadding(PADDING, PADDING, PADDING, PADDING);
            text.setTextSize(16);
            text.setTypeface(text.getTypeface(), Typeface.BOLD);
            row.addView(text);

            myTable.addView(row);
            rows.add(row);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_searchTypeFragment){
            Converter.viewToActivity(v).launchFragment(SearchFragment.instance(null));
        }
        else if (v.getId() == R.id.done_searchTypeFragment) {
            GroceryList list = this.glm.findList(Constant.lastGroceryListAddition);
            if (list == null){
                Converter.viewToActivity(v).switchToHomeFragment();
            }else{
                Converter.viewToActivity(v).launchFragment(GroceryListFragment.instance(list));
            }
        }
        else {
            if (v instanceof GLMTableRow) {
                GLMTableRow row = (GLMTableRow) v;
                Converter.viewToActivity(v).launchFragment(AddItemFragment.instance(row.getItem()));
            }
        }
    }
}
