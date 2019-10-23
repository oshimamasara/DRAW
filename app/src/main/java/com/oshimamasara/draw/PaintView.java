package com.oshimamasara.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

public class PaintView extends View {
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANSE=5;
    Context context;


    public PaintView(Context context, AttributeSet attra) {
        super(context, attra);
        this.context = context;

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(12f);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    //@Override
    //protected void onSizeChanged(int w, int h, int oldw, int oldh){
    protected void onSizeChanged(DisplayMetrics metrics){
        //super.onSizeChanged(w,h,oldw,oldh);
        //Log.d(TAG, "onSizeChanged  w & h::" + mBitmap);
        //Log.d(TAG, "oldw & oldh::" + mBitmap);
        int height = metrics.widthPixels;
        int width = metrics.widthPixels;
        Log.d(TAG, "onSizeChanged:height:" + height);
        Log.d(TAG, "onSizeChanged:width:" + width);

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Log.d(TAG, "Bitmap=" + mBitmap);
        mCanvas = new Canvas(mBitmap);
        Log.d(TAG, "Canvas=" + mCanvas);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        Log.d(TAG, "onDraw=" + mPath);
        Log.d(TAG, "onDraw=" + mPaint);
    }

    private void startTouch(float x,float y){
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        Log.d(TAG, "startTouch  X:" + mX + "  Y:" + mY);
    }

    private void moveTouch(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        Log.d(TAG, "moveTouch::" + dx);
        Log.d(TAG, "moveTouch::" + dy);
        if(dx >= TOLERANSE || dy >= TOLERANSE){
            mPath.quadTo(mX, mY, (x+mX) / 2 , (y+mY) / 2);
            mX = x;
            mY = y;
            Log.d(TAG, "move_X:" + mX + "  move_Y:" + mY);
        }
    }

    public void clearCanvas(){
        mPath.reset();
        invalidate();
    }

    private void upTouch(){
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }

        return true;
    }
}
