package edu.qc.seclass.glm.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMFragment;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import edu.qc.seclass.glm.datastructures.Constant;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.datastructures.Item;
import edu.qc.seclass.glm.db.Converter;

public class AddItemFragment extends GLMFragment implements View.OnClickListener{

    TextView quantity;
    int count = 1;

    private static AddItemFragment instance;
    private static int IMAGE_DIMEN = 300;
    private Item item;

    private Spinner spinner;

    public static AddItemFragment instance(Item item){
        if (instance == null)
            instance = new AddItemFragment();
        instance.item = item;
        instance.count = 1;
        return instance;
    }

    private AddItemFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_item, container, false);

        // Input values
        quantity = (TextView) v.findViewById(R.id.quantityText_addItemFragment);
        quantity.setText(count+"");

        List<String> lists = this.glm.getGroceryListNames();

        spinner = (Spinner) v.findViewById(R.id.spinner_addItemFragment);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, lists);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.glm.setAppTitle("Add Item");
        IMAGE_DIMEN = (int) (this.getActivity().getWindow().getDecorView().getWidth() * 0.5);

        view.findViewById(R.id.add_addItemFragment).setOnClickListener(this);
        view.findViewById(R.id.cancel_addItemFragment).setOnClickListener(this);
        view.findViewById(R.id.increaseQuantity_addItemFragment).setOnClickListener(this);
        view.findViewById(R.id.decreaseQuantity_addItemFragment).setOnClickListener(this);

        if (item != null) {
            ImageView image = (ImageView) view.findViewById(R.id.imageView_addItemFragment);
            image.setImageBitmap(getImage(view, item));

            TextView itemName = (TextView) view.findViewById(R.id.itemNameTextView_addItemFragment);
            itemName.setText(item.getName());
        }
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

    public void increment(){
        count++;
        if (count > 10) count = 10;
        quantity.setText("" + count);
    }

    public void decrement(){
        count--;
        if(count < 1) count = 1;
        quantity.setText("" + count);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_addItemFragment) {

            String selected = (String) spinner.getSelectedItem();
            boolean result = this.glm.addItemToList(selected, item, count);

            if (result) {
                Constant.lastGroceryListAddition = selected;
                Converter.viewToActivity(v).launchFragment(SearchTypeFragment.instance(Constant.lastTypeSelected));
            }else{
                Toast.makeText(this.getContext(), "Unable to add this item", Toast.LENGTH_LONG);
            }
        }
        if (v.getId() == R.id.cancel_addItemFragment)
            Converter.viewToActivity(v).switchToSearchFragment();
        // Add
        if (v.getId() == R.id.increaseQuantity_addItemFragment)
              increment();
        // Subtract
        if (v.getId() == R.id.decreaseQuantity_addItemFragment)
            decrement();
    }
}
