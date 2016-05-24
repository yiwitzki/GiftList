package com.lxsj.sdk.giftlist.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by TP on 16/4/26.
 */
public class StrokeTextView extends TextView
{
    private final String TAG = "StrokeTextView";
    private TextView outlineTextView = null;

    public StrokeTextView(Context context)
    {
        super(context);
        outlineTextView = new TextView(context);
        init();
    }

    public StrokeTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        outlineTextView = new TextView(context, attrs);
        init();
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        outlineTextView = new TextView(context, attrs, defStyle);
        init();
    }
    public void init()
    {
        Log.d(TAG, "init: ");
        TextPaint paint = outlineTextView.getPaint();
        paint.setStrokeWidth(5);// 描边宽度
        paint.setStyle(Paint.Style.STROKE);
        outlineTextView.setTextColor(0xFFFFFF00);// 描边颜色
        outlineTextView.setGravity(getGravity());
    }

    @Override
    public void setLayoutParams (ViewGroup.LayoutParams params)
    {
        super.setLayoutParams(params);
        outlineTextView.setLayoutParams(params);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 设置轮廓文字
        CharSequence outlineText = outlineTextView.getText();
        if (outlineText == null || !outlineText.equals(this.getText()))
        {
            outlineTextView.setText(getText());
            postInvalidate();
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        super.setText(text, type);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        outlineTextView.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        outlineTextView.draw(canvas);
        super.onDraw(canvas);
    }
}
