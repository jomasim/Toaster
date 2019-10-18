package com.devloop.toaster.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class CustomView extends View {
    Paint paint;
    RectF rect;

    public CustomView(Context context) {
        super(context);
        /*
            Paint is used to define styles and colors for a given
            drawing.
         */
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        rect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
            Canvas - 2D drawing API/framework offered by Android.
            Canvas offers drawing methods and makes it possible to draw
            items on a give canvas.
         */
        super.onDraw(canvas);
        rect.set(20, 20, getWidth() - 20, (float) (getHeight() * 0.05));
        canvas.drawRect(rect, paint);
    }
}
