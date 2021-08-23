package edu.qc.seclass.glm.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.List;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMFragment;
import edu.qc.seclass.glm.datastructures.Constant;
import edu.qc.seclass.glm.datastructures.GroceryItem;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.datastructures.Item;
import edu.qc.seclass.glm.db.Converter;

public class EditItemFragment extends GLMFragment implements View.OnClickListener{


    private GroceryItem gitem;

    private int quantity;

    private TextView quantityView;
    private TextView listName;

    private int IMAGE_DIMEN;

    private Spinner spinner;

    public static EditItemFragment instance(GroceryItem gitem){
        return new EditItemFragment(gitem);
    }

    private EditItemFragment(GroceryItem gitem) {
        this.gitem = gitem;
        this.quantity = this.gitem.getQuantity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_item, container, false);


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.glm.setAppTitle("Editing "+gitem.getItem().getName());

        this.quantityView = (TextView) view.findViewById(R.id.editTextNumber_editItemFragment);
        this.quantityView.setText(quantity+"");

        List<String> lists = this.glm.getGroceryListNames();

        spinner = (Spinner) view.findViewById(R.id.spinner_editItemFragment);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, lists);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        GroceryList list = this.glm.getGroceryListLookup().get(gitem.getListId());
        spinner.setSelection(lists.indexOf(list.getName()));

        view.findViewById(R.id.delete_editItemFragment).setOnClickListener(this);
        view.findViewById(R.id.save_editItemFragment).setOnClickListener(this);
        view.findViewById(R.id.cancel_editItemFragment).setOnClickListener(this);
        view.findViewById(R.id.increaseQuantity_editItemFragment).setOnClickListener(this);
        view.findViewById(R.id.decreaseQuantity_editItemFragment).setOnClickListener(this);

        IMAGE_DIMEN = (int) (Converter.GLOBAL_WIDTH * 0.5);

        Bitmap image = getImage(view, gitem.getItem());
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_editItemFragment);
        imageView.setImageBitmap(image);

        TextView itemName = (TextView) view.findViewById(R.id.itemName_editItemFragment);
        itemName.setText(gitem.getItem().getName());
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
        quantity++;
        if (quantity > 10) quantity = 10;
        quantityView.setText("" + quantity);
    }

    public void decrement(){
        quantity--;
        if(quantity < 1) quantity = 1;
        quantityView.setText("" + quantity);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_editItemFragment){
            Converter.viewToActivity(v).launchFragment(GroceryListFragment.instance(this.glm.findList(Constant.lastGroceryListAddition)));
        }
        if (v.getId() == R.id.delete_editItemFragment){
            this.glm.deleteGroceryItem(gitem);
            Converter.viewToActivity(v).launchFragment(GroceryListFragment.instance(this.glm.findList(Constant.lastGroceryListAddition)));
        }
        if (v.getId() == R.id.save_editItemFragment){
            String selected = (String) this.spinner.getSelectedItem();
            this.glm.updateQuantity(gitem, quantity);
            if (!this.glm.getGroceryListLookup().get(gitem.getListId()).getName().equals(selected)){
                this.glm.swapGroceryItem(gitem, this.glm.findList(selected));
            }
            Converter.viewToActivity(v).launchFragment(GroceryListFragment.instance(this.glm.findList(selected)));
        }
        if (v.getId() == R.id.increaseQuantity_editItemFragment)
            increment();

        if (v.getId() == R.id.decreaseQuantity_editItemFragment)
            decrement();
    }
}
