package com.jacob.waveview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Package : com.jacob.waveview
 * Author : jacob
 * Date : 15-3-30
 * Description : 这个类是用来xxx
 */
public class WaterWaveView extends View {

    //默认内环半径100dp
    private int mRadius = dpToPx(100);

    //外环的宽度
    private int mStrokeWidth = dpToPx(20);

    private Paint mPaintOutCircle;
    private Paint mPaintBorderCircle;
    private Paint mPaintInnerCircle;
    private Paint mPaintText;
    private Paint mPaintTextTips;
    private Paint mPaintWater;

    /**
     * 当前进度，或者百分比
     */
    private int progress = 20;

    /**
     * 中心文字的范围大小
     */
    private Rect mRectProgress = new Rect();

    private Rect mRectTips = new Rect();

    private String mTipString = "剩余电量";

    /**
     * View中心点的坐标
     */
    private int mCenterXY;

    public WaterWaveView(Context context) {
        this(context, null);
    }

    public WaterWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaintOutCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOutCircle.setDither(true);
        mPaintOutCircle.setColor(Color.parseColor("#8F45A348"));
        mPaintOutCircle.setStyle(Paint.Style.FILL);

        mPaintBorderCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBorderCircle.setDither(true);
        mPaintBorderCircle.setColor(Color.parseColor("#FFFFFF"));
        mPaintBorderCircle.setStyle(Paint.Style.STROKE);
        mPaintBorderCircle.setStrokeWidth(dpToPx(2));

        mPaintInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintInnerCircle.setDither(true);
        mPaintInnerCircle.setColor(Color.parseColor("#FF45A348"));
        mPaintInnerCircle.setStyle(Paint.Style.FILL);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setDither(true);
        mPaintText.setColor(Color.parseColor("#FFFFFF"));
        mPaintText.setTextSize(spToPx(40));

        mPaintTextTips = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextTips.setDither(true);
        mPaintTextTips.setColor(Color.parseColor("#FFFFFF"));
        mPaintTextTips.setTextSize(spToPx(15));
        mPaintTextTips.getTextBounds(mTipString,0,mTipString.length(),mRectTips);

        mPaintWater = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintWater.setDither(true);
        mPaintWater.setColor(Color.parseColor("#7F45A348"));
        mPaintWater.setStyle(Paint.Style.FILL);


    }

    /**
     * 布局最好是一个正方形，绘制的区域将在屏幕的中心位置
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width, height;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mRadius * 2 + mStrokeWidth * 2;
        }

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mRadius * 2 + mStrokeWidth * 2;
        }

        int size = Math.min(width, height);
        setMeasuredDimension(size, size);

        mCenterXY = size / 2;

        if (mCenterXY <= mRadius + mStrokeWidth) {
            mRadius = mCenterXY - mStrokeWidth;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //绘制最底层的外环
        canvas.drawCircle(mCenterXY, mCenterXY, mCenterXY, mPaintOutCircle);

        //绘制中间的边界的元环
        canvas.drawCircle(mCenterXY, mCenterXY, mRadius + dpToPx(2), mPaintBorderCircle);

        //绘制内圆
        canvas.drawCircle(mCenterXY, mCenterXY, mRadius, mPaintInnerCircle);

        //绘制中心的文字
        String text = progress + "%";
        mPaintText.getTextBounds(text, 0, text.length(), mRectProgress);
        canvas.drawText(text, (getWidth() - mRectProgress.width()) / 2, (getHeight() + mRectProgress.height()) / 2, mPaintText);

        //绘制tips的文字
        canvas.drawText(mTipString,(getWidth() - mRectTips.width()) / 2, (getHeight()/3 + mRectProgress.height()) / 2, mPaintTextTips);

    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

}
