package com.paxw.phonesafe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class MyView extends TextView {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private String name = "几天" ;
    public void setMyName(String string){
        name = string ;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //按照既定点 绘制文本内容
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(this.getTextSize());
        canvas.drawText(name,0f,50f, paint);
    }
}
