package uk.co.iceroad.notemaker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        super(context);
        setCustomFont(this, context);

    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(this, context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(this, context);
    }

    private void setCustomFont(CustomFontTextView customFontButton, Context context) {
        if(!isInEditMode()) {
            Typeface font = Typeface.createFromAsset(context.getAssets(), "CaviarDreams.ttf");
            customFontButton.setTypeface(font);
        }
    }
}
