package edu.qc.seclass.glm.custom;

import androidx.fragment.app.Fragment;
import edu.qc.seclass.glm.GroceryListManager;

public class GLMFragment extends Fragment {

    /* Reference to everything that has to do with the app */
    protected GroceryListManager glm;

    public void setGLM(GroceryListManager glm){
        this.glm = glm;
    }
}
