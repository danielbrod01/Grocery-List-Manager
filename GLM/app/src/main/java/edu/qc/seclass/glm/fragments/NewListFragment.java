package edu.qc.seclass.glm.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMFragment;
import edu.qc.seclass.glm.datastructures.GroceryList;
import edu.qc.seclass.glm.db.Converter;

public class NewListFragment extends GLMFragment implements View.OnClickListener {

    private ImageView imageView;
    private Bitmap selectedImage;

    /**
     *
     * @return
     */
    public static NewListFragment instance(){
        return new NewListFragment();
    }

    /* Inaccessible from outside. Private to keep the "singleton" structure */
    private NewListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.add_newListFragment).setOnClickListener(this);
        view.findViewById(R.id.cancel_newListFragment).setOnClickListener(this);
        view.findViewById(R.id.selectImage_newListFragment).setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.selectImage_newListFragment);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.select_image_icon));
    }

    /**
     *
     */
    private void addNewList(){
        EditText listName = (EditText) this.getView().findViewById(R.id.inputListName_newListFragment);
        EditText listDescription = (EditText) this.getView().findViewById(R.id.inputDescription_newListFragment);

        String name = listName.getText().toString();
        if (name.length() == 0) {
            Toast.makeText(this.getView().getContext(), "List Name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        for (String past : this.glm.getGroceryListNames()){
            if (past.equalsIgnoreCase(name)){
                Toast.makeText(this.getView().getContext(), name+" already exists. Please change name", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String dir;
        String fileName = name.toLowerCase().replace(" ", "_");

        /* Will attempt to save the selected image. If it cannot, it will select a random default list icon */
        if (this.saveImage(fileName)) {
            dir = fileName + ".png";
            Log.d("BAHTI", "FILENAME: "+dir);

        }else{
            dir = this.glm.getRandomIcon();
        }

        /* Temp grocery list that needs to be inserted to the database */
        GroceryList groceryList = new GroceryList(-1, name, listDescription.getText().toString(), dir, 0);

        /* The actual GroceryList that was inserted and is recognized by the app */
        GroceryList inserted = this.glm.createList(groceryList);

        if (inserted != null) {
            /* Switches over to the Grocery List fragment and displays the newly created list*/
            Converter.viewToActivity(this.getView()).launchFragment(GroceryListFragment.instance(inserted));
        }else{
            Toast.makeText(this.getView().getContext(), "Unable to Create List", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @param view
     */
    private void invokeSelectImage(View view){
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2000);
        } else {
            Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
            cameraIntent.setType("image/*");
            startActivityForResult(Intent.createChooser(cameraIntent, "Select Picture"), 1);
        }

    }

    /**
     *
     * @param name
     * @return
     */
    private boolean saveImage(String name){
        /* No image was selected */
        if (this.selectedImage == null)
            return false;

        try {
            /* Will save the image in the apps local folder */
            FileOutputStream fileOutputStream = this.getContext().openFileOutput(name+".png", Context.MODE_PRIVATE);

            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Uri returnUri = data.getData();
            try {
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                imageView.setImageBitmap(bitmapImage);
                selectedImage = bitmapImage;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.selectImage_newListFragment)
            invokeSelectImage(view);
        if (view.getId() == R.id.add_newListFragment)
            addNewList();
        if (view.getId() == R.id.cancel_newListFragment)
            Converter.viewToActivity(view).switchToHomeFragment();
    }
}
