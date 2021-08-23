package edu.qc.seclass.glm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import edu.qc.seclass.glm.datastructures.Item;
import edu.qc.seclass.glm.db.GLMDataBase;

import static org.junit.Assert.*;

public class GLMDataBaseTest {

    private GLMDataBase dB;

    @Before
    public void preCondition(){
        dB = new GLMDataBase(null);
    }

    @After
    public void postCondition(){
        dB = null;
    }

    @Test
    public void testInsertItem() { //TODO fix this
        //dB.insertItem(dB,  "Plunger");
        ArrayList<String> myArray = new ArrayList<>();
        //ArrayList<Item> nb = dB.getItemsByName();
        /*
        for(int i = 0; i < nb.size(); i++){
            myArray.add(nb.get(i).getName());
        }*/
        assertTrue(myArray.contains("Plunger"));
    }
}
