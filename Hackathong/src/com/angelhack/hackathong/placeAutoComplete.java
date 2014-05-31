package com.angelhack.hackathong;
 
import java.util.HashMap;
 
import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
 
public class placeAutoComplete extends AutoCompleteTextView {
 
    public placeAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    @Override
    protected CharSequence convertSelectionToString(Object selectedItem) {
        
        HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
        return hm.get("description");
    }
}