package com.yhd.tools;

import android.content.Context;
import android.util.TypedValue;

public class DensityTool {
	
	private DensityTool(){
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	
	public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
	
    public static int sp2px(Context context, float spVal) {  
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,  
                spVal, context.getResources().getDisplayMetrics());  
    }
    
    public static float px2sp(Context context, float pxVal) {  
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);  
    }
    
}
