package uk.co.iceroad.notemaker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomFontButton extends Button {

    public CustomFontButton(Context context) {
        super(context);
        setCustomFont(this, context);

    }

    public CustomFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(this, context);
    }

    public CustomFontButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(this, context);
    }

    private void setCustomFont(CustomFontButton customFontButton, Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "CaviarDreams_Bold.ttf");
        customFontButton.setTypeface(font);
    }
}
