package com.yuntian.viewstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yuntian.viewstudy.R;

/**
 * description  .
 * Created by ChuYingYan on 2017/6/3.
 */

public class DrawView extends View {

    private Rect mBound;
    private Paint p;

    /**
     * 文本
     */
    private String mTitleText="";
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private float mTitleTextSize;


    private int offY=0;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DrawView, defStyleAttr, 0);

        mTitleText = a.getString(R.styleable.DrawView_drawView_titleText);
        // 默认颜色设置为黑色
        mTitleTextColor = a.getColor(R.styleable.DrawView_drawView_titleTextColor, Color.BLACK);
        // 默认设置为16sp，TypeValue也可以把sp转化为px
        mTitleTextSize = a.getDimension(R.styleable.DrawView_drawView_titleTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        a.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        p = new Paint();
        mBound = new Rect();

        p.setTextSize(mTitleTextSize);
        //mPaint.setColor(mTitleTextColor);

        //设置字的区域
        //p.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
         * 方法 说明
         * drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔
        p.setColor(Color.RED);// 设置红色

        drawCirlce(canvas);
//        drawLine(canvas);
//        drawRect(canvas);
//        drawArcAndOval(canvas);
//        drawPolygon(canvas);
//        drawRoundRect(canvas);
//        drawBezierPath(canvas);
//        drawPoint(canvas);
//        drawBitmap(canvas);
    }

    /**
     * 画圆
     *
     * @param canvas
     */
    public void drawCirlce(Canvas canvas) {
        String text="画圆：";
        p.getTextBounds(text, 0, text.length(), mBound);
        canvas.drawText(text,10, mBound.height(), p);// 画文本

        canvas.drawCircle(140, 20, 20, p);// 小圆
        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，一看效果就明白了
        canvas.drawCircle(240, 40, 40, p);// 大圆

    }

    /**
     * 画线及弧线
     *
     * @param canvas
     */
    public void drawLine(Canvas canvas) {
        canvas.drawText("画线及弧线：", 10, offY+=80, p);
        p.setColor(Color.GREEN);// 设置绿色
        canvas.drawLine(100, 40, 140, 40, p);// 画线
        canvas.drawLine(140, 40, 200, 80, p);// 斜线
        //画笑脸弧线
        p.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval1 = new RectF(150, 20, 180, 40);
        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
        oval1.set(190, 20, 220, 40);
        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
        oval1.set(160, 30, 210, 60);
        canvas.drawArc(oval1, 0, 180, false, p);//小弧形
    }


    /**
     * 画矩形
     *
     * @param canvas
     */
    public void drawRect(Canvas canvas) {
        canvas.drawText("画矩形：", 10, offY+=80, p);
        p.setColor(Color.GRAY);// 设置灰色
        p.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(60, 60, 80, 80, p);// 正方形
        canvas.drawRect(60, 90, 160, 100, p);// 长方形

    }

    /**
     * 画扇形和椭圆
     *
     * @param canvas
     */
    public void drawArcAndOval(Canvas canvas) {
        canvas.drawText("画扇形和椭圆:", 10, offY+=80, p);
        /* 设置渐变色 这个正方形的颜色是改变的 */
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY}, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        p.setShader(mShader);
        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, 200, 130, true, p);
        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是true的时候画扇形，是false的时候画弧线
        //画椭圆，把oval改一下
        oval2.set(210, 100, 250, 130);
        canvas.drawOval(oval2, p);
    }

    public void drawPolygon(Canvas canvas) {
        canvas.drawText("画三角形：", 10, offY+=80, p);
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);

        //你可以绘制很多任意多边形，比如下面画六连形
        p.reset();//重置
        p.setColor(Color.LTGRAY);
        p.setStyle(Paint.Style.STROKE);//设置空心
        Path path1 = new Path();
        path1.moveTo(180, 200);
        path1.lineTo(200, 200);
        path1.lineTo(210, 210);
        path1.lineTo(200, 220);
        path1.lineTo(180, 220);
        path1.lineTo(170, 210);
        path1.close();//封闭
        canvas.drawPath(path1, p);

    }

      /*
         * Path类封装复合(多轮廓几何图形的路径
         * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
         * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
         */

    /**
     * 画圆角矩形
     *
     * @param canvas
     */
    public void drawRoundRect(Canvas canvas) {
        p.setStyle(Paint.Style.FILL);//充满
        p.setColor(Color.LTGRAY);
        p.setAntiAlias(true);// 设置画笔的锯齿效果
        canvas.drawText("画圆角矩形:", 10, offY+=80, p);
        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径
    }



    /**
     * 画贝塞尔曲线
     *
     * @param canvas
     */
    public void drawBezierPath(Canvas canvas) {
        canvas.drawText("画贝塞尔曲线:", 10, offY+=80, p);
        p.reset();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GREEN);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, p);//画出贝塞尔曲线

    }

    /**
     * 画点
     *
     * @param canvas
     */
    public void drawPoint(Canvas canvas) {
        p.setStyle(Paint.Style.FILL);
        canvas.drawText("画点：", 10, offY+=80, p);
        canvas.drawPoint(60, 390, p);//画一个点
        canvas.drawPoints(new float[]{60,400,65,400,70,400}, p);//画多个点
    }

   /**
     * 画图片，就是贴图
     * @param canvas
     */
    public void drawBitmap(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap, 250,offY+=80, p);
    }


}