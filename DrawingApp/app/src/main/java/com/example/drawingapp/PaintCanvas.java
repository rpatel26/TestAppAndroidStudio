package com.example.drawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class PaintCanvas extends View {

    Paint paintBrush = new Paint();
    Path paintPath = new Path();

    public PaintCanvas(Context context) {
        super(context);
        paintBrush.setAntiAlias(true);
        paintBrush.setColor(Color.MAGENTA);
        paintBrush.setStrokeWidth(7f);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
    }

    public PaintCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBrush.setAntiAlias(true);
        paintBrush.setColor(Color.MAGENTA);
        paintBrush.setStrokeWidth(15f);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            paintPath.moveTo(pointX, pointY);
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            paintPath.lineTo(pointX, pointY);
        }

        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(paintPath, paintBrush);
    }

    public void clearPaint(){
        paintPath.reset();
        postInvalidate();
    }
}
