package com.mkobayashi.pointtopointdrawingtest;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Paint;
import android.graphics.Path;


public class DrawingView extends View{

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF0000FF;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    // keep track of how many times ACTION_DOWN
    private int numberActionDown = 0;


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){
        //get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();

        // set initial paint color and properties
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        // instantiate canvas
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (numberActionDown == 0){
                    drawPath.moveTo(touchX, touchY);
                    ++numberActionDown;
                }
                else if(numberActionDown == 1){
                    drawPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    numberActionDown = 0;
                }
                break;
//            case MotionEvent.ACTION_MOVE:
//                drawPath.lineTo(touchX, touchY);
//                break;
//            case MotionEvent.ACTION_UP:
//                if (numberActionDown == 0){
//                    ++numberActionDown;
//                }
//                if (numberActionDown == 1){
//                    drawCanvas.drawPath(drawPath, drawPaint);
//                    drawPath.reset();
//                    numberActionDown = 0;
//                }
//                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }


}
