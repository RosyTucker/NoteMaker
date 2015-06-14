package uk.co.iceroad.notemaker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

public class CustomFontEditText extends EditText {

    public CustomFontEditText(Context context) {
        super(context);
        setCustomFont(this, context);

    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(this, context);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(this, context);
    }

    private void setCustomFont(CustomFontEditText customFontButton, Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "CaviarDreams.ttf");
        customFontButton.setTypeface(font);
    }
}
