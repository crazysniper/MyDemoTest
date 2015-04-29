package com.example.camerademo.mywidget;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class VTextView extends TextView{
   final boolean topDown;
   
   public VTextView(Context context){
	      super(context);
	      final int gravity = getGravity();
	      if(Gravity.isVertical(gravity) && (gravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
	         setGravity((gravity&Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.TOP);
	         topDown = false;
	      }else
	         topDown = true;
	   }

   public VTextView(Context context, AttributeSet attrs){
      super(context, attrs);
      final int gravity = getGravity();
      if(Gravity.isVertical(gravity) && (gravity&Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
         setGravity((gravity&Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.TOP);
         topDown = false;
      }else
         topDown = true;
   }

   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
      super.onMeasure(heightMeasureSpec, widthMeasureSpec);
      setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
   }

   @Override
   protected void onDraw(Canvas canvas){
	try {
	      TextPaint textPaint = getPaint(); 
	      textPaint.setColor(getCurrentTextColor());
	      textPaint.drawableState = getDrawableState();

	      canvas.save();

	      if(topDown){
	         canvas.translate(getWidth(), 0);
	         canvas.rotate(90);
	      }else {
	         canvas.translate(0, getHeight());
	         canvas.rotate(-90);
	      }


	      canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());

	      getLayout().draw(canvas);
	      canvas.restore();

	      
	      /*Paint paint = new Paint();
	      paint.setColor(android.graphics.Color.YELLOW);
	     

	      canvas.drawLine(0, 0, this.getWidth()-1, 0, paint);
	      //1、横坐标0到this.getWidth()-1，纵坐标0到0
	      canvas.drawLine(0, 0, 0, this.getHeight()-1, paint);
	      //2、横坐标0到0，纵坐标0到this.getHeight()-1
	      canvas.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight()-1, paint);
	      //3、横坐标this.getWidth()-1到this.getWidth()-1，纵坐标0到this.getHeight()-1
	      canvas.drawLine(0, this.getHeight()-1, this.getWidth()-1, this.getHeight()-1, paint);
	      //4、横坐标0到this.getWidth()-1，纵坐标this.getHeight()-1到this.getHeight()-1
	      //下面用图介绍边框的绘制
	*/ 
	} catch (Exception e) {
//		System.out.println("trying to use a recycled bitmap");  
	}
 }
}