package com.wzh.viewpager.indicator;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.tmall.ultraviewpager.UltraViewPagerAdapter;

/**
 * @Author: wenzhihao
 * @Time: 2019/5/19
 * @Description: 通用ViewPager指示器
 */
public class UIndicator extends View implements ViewPager.OnPageChangeListener {

    private static final String TAG = "UIndicator";

    //指示器样式一 选中未选中都是圆点
    public static final int STYLE_CIRCLR_CIRCLE = 0;
    //指示器样式二 选中未选中都是方形
    public static final int STYLE_RECT_RECT = 1;
    //指示器样式三 选中方形，未选中圆点
    public static final int STYLE_CIRCLR_RECT = 2;

    //横向排列
    public static final int HORIZONTAL = 0;
    //纵向排列
    public static final int VERTICAL = 1;

    private Context mContext;

    //指示器之间的间距
    private int spacing;
    //指示器排列方向
    private int orientation = HORIZONTAL;
    //选中与为选中的颜色
    private ColorStateList selectedColor, normalColor;

    //指示器样式，默认都是圆点
    private int mStyle = STYLE_CIRCLR_CIRCLE;

    //样式一 圆点半径大小
    private int circleCircleRadius = 0;

    //样式二 方形大小及圆角
    private int rectRectItemWidth = 0, rectRectItemHeight = 0, rectRectCorner = 0;

    //样式三 选中的方形大小及圆角
    private int circleRectItemWidth = 0, circleRectItemHeight = 0, circleRectCorner = 0;
    //样式三 未选中的圆点半径
    private int circleRectRadius = 0;

    //画笔
    private Paint normalPaint, selectedPaint;

    //指示器item的区域
    private RectF mRectF;
    //指示器大小
    private int width, height;
    //指示器item个数
    private int itemCount = 0;
    //当前选中的位置
    private int selection = 0;

    private ViewPager viewPager;

    public UIndicator(Context context) {
        this(context, null);
    }

    public UIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
        intPaint();
        checkItemCount();
    }

    /**
     * 加载自定义属性
     */
    private void init(AttributeSet attrs) {
        // 加载自定义属性集合
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.Indicator);
        // 第二个参数是默认设置颜色
        selectedColor = ta.getColorStateList(R.styleable.Indicator_selected_color);
        normalColor = ta.getColorStateList(R.styleable.Indicator_normal_color);
        spacing = ta.getDimensionPixelSize(R.styleable.Indicator_spacing, dip2px(6));
        orientation = ta.getInt(R.styleable.Indicator_orientation, HORIZONTAL);
        mStyle = ta.getInt(R.styleable.Indicator_style, STYLE_CIRCLR_CIRCLE);

        circleCircleRadius = ta.getDimensionPixelSize(R.styleable.Indicator_circle_circle_radius, dip2px(3));

        rectRectCorner = ta.getDimensionPixelSize(R.styleable.Indicator_rect_rect_corner, 0);
        rectRectItemHeight = ta.getDimensionPixelSize(R.styleable.Indicator_rect_rect_itemHeight, dip2px(3));
        rectRectItemWidth = ta.getDimensionPixelSize(R.styleable.Indicator_rect_rect_itemWidth, dip2px(15));

        circleRectCorner = ta.getDimensionPixelSize(R.styleable.Indicator_circle_rect_corner, 0);
        circleRectRadius = ta.getDimensionPixelSize(R.styleable.Indicator_circle_rect_radius, dip2px(3));
        circleRectItemHeight = ta.getDimensionPixelSize(R.styleable.Indicator_circle_rect_itemHeight, dip2px(3));
        circleRectItemWidth = ta.getDimensionPixelSize(R.styleable.Indicator_circle_rect_itemWidth, dip2px(15));

        // 解析后释放资源
        ta.recycle();
    }

    private void intPaint() {
        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.FILL);
        normalPaint.setAntiAlias(true);
        normalPaint.setColor(normalColor == null ? Color.GRAY : normalColor.getDefaultColor());

        selectedPaint = new Paint();
        selectedPaint.setStyle(Paint.Style.FILL);
        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(selectedColor == null ? Color.RED : selectedColor.getDefaultColor());

        mRectF = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (mStyle) {
            case STYLE_CIRCLR_CIRCLE:
                width = 2 * circleCircleRadius * itemCount + (itemCount - 1) * spacing;
                height = Math.max(heightSize, 2 * circleCircleRadius);
                break;
            case STYLE_RECT_RECT:
                width = rectRectItemWidth * itemCount + (itemCount - 1) * spacing;
                height = Math.max(heightSize, rectRectItemHeight);
                break;
            case STYLE_CIRCLR_RECT:
                int normalItemWidth = circleRectRadius * 2;
                width = (itemCount - 1) * normalItemWidth + circleRectItemWidth + (itemCount - 1) * spacing;
                int tempHeight = Math.max(circleRectItemHeight, circleRectRadius * 2);
                height = Math.max(heightSize, tempHeight);
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mStyle) {

            case STYLE_CIRCLR_CIRCLE:
                float cy = height / 2;
                for (int i = 0; i < itemCount; i++) {
                    int cx = (i + 1) * circleCircleRadius + i * spacing;
                    //全部绘制圆点，画笔的区别
                    canvas.drawCircle(cx, cy, circleCircleRadius, i == selection ? selectedPaint : normalPaint);
                }
                break;

            case STYLE_RECT_RECT:
                for (int i = 0; i < itemCount; i++) {
                    int left = i * rectRectItemWidth + i * spacing;
                    mRectF.set(left, 0, left + rectRectItemWidth, rectRectItemHeight);
                    //全部绘制圆角矩形，画笔的区别
                    canvas.drawRoundRect(mRectF, rectRectCorner, rectRectCorner, i == selection ? selectedPaint : normalPaint);
                }
                break;

            case STYLE_CIRCLR_RECT:
                for (int i = 0; i < itemCount; i++) {
                    int left = selection * (circleRectRadius * 2 + spacing);
                    int top;
                    if (selection == i) {
                        //选中的绘制圆角矩形
                        top = (height - circleRectItemHeight) / 2;
                        mRectF.set(left, top, left + circleRectItemWidth, circleRectItemHeight + top);
                        canvas.drawRoundRect(mRectF, circleRectCorner, circleRectCorner, selectedPaint);
                    } else {
                        //未选中的绘制圆点，距离需要判断position在选中的左边或者右边，从而确定cx
                        top = (height - circleRectRadius * 2) / 2;
                        int cx = 0;
                        float cy1 = circleRectRadius + top;
                        if (selection < i) {
                            cx = (i - 1) * circleRectRadius * 2 + i * spacing + circleRectItemWidth + circleRectRadius;
                        } else {
                            cx = i * (circleRectRadius * 2) + i * spacing + circleRectRadius;
                        }
                        canvas.drawCircle(cx, cy1, circleRectRadius, normalPaint);
                    }

                }
                break;
        }
    }

    /**
     * 关联ViewPager
     * @param viewPager
     */
    public void attachToViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter != null) {
            //TODO 如果项目使用了阿里开源库，UltraViewPager，想要兼容需要用以下方式获取 itemCount，否则去除这个if条件
            if (pagerAdapter instanceof UltraViewPagerAdapter){
                //从UltraViewPagerAdapter获取真实的个数
                itemCount = ((UltraViewPagerAdapter)pagerAdapter).getRealCount();
            } else {
                itemCount = pagerAdapter.getCount();
            }
            selection = viewPager.getCurrentItem() % itemCount;
            checkItemCount();
        }

        viewPager.addOnPageChangeListener(this);
    }

    /**
     * 设置选中的值，当ViewPager只有一个item不显示指示器
     */
    private void checkItemCount() {
        if (selection >= itemCount) {
            selection = itemCount - 1;
        }
        setVisibility((itemCount <= 1) ? GONE : VISIBLE);
    }

    @Override
    public void onPageSelected(int i) {
        if (viewPager != null) {
            PagerAdapter pagerAdapter = viewPager.getAdapter();
            if (pagerAdapter != null) {
                selection = viewPager.getCurrentItem() % itemCount;
            }
        }
        postInvalidate();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }


    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * dp to px
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
