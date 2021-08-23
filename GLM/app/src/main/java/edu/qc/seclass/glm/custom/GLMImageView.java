package edu.qc.seclass.glm.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class GLMImageView extends androidx.appcompat.widget.AppCompatImageView {
    protected String type;

    public GLMImageView(Context context) {
        super(context);
    }

    public void setType(String type) { this.type = type; }
    public String getType() { return type; }
}
