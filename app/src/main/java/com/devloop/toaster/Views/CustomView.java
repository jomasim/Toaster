package com.devloop.toaster.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class CustomView extends View {
    Paint paint;
    Rect rect;

    public CustomView(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.set(20, 20, getWidth(), getHeight());
        canvas.drawRect(rect, paint);
    }
}
