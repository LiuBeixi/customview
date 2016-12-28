package com.github.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * Created by Administrator on 2016/8/29.
 */
public class MyButton extends Button {
    /**
     * layer-list
     */
    private GradientDrawable layerDrawable;//最底层
    private GradientDrawable layerGradientDrawableNormal;//最上层
    private GradientDrawable layerGradientDrawablePress;//最上层

    private LayerDrawable layerDrawablePress;//layerDrawable+layerGradientDrawablePress
    private LayerDrawable layerDrawableNormal;//layerDrawable+layerGradientDrawableNormal
    /**
     * shape和selector
     */
    private GradientDrawable gradientDrawablePress;
    private GradientDrawable gradientDrawableNormal;
    /**
     * view最后设置的背景
     */
    private StateListDrawable stateListDrawableForShape;
    private StateListDrawable stateListDrawableForLayer;
    /*** 是否显示边框*/
    private boolean allLine;
    /***左边框*/
    private boolean leftLine;
    /*** 顶部边框*/
    private boolean topLine;
    /*** 右边框*/
    private boolean rightLine;
    /***底部边框*/
    private boolean bottomLine;
    /***边框宽度*/
    private float borderWidth;
    /***填充色*/
    private int solidColor;
    /***触摸颜色*/
    private int pressColor;
    /***边框颜色*/
    private int borderColor;
    /***虚线长度*/
    private float dashWidth;
    /***虚线间隔*/
    private float dashGap;
    /***圆角半径*/
    private float radius;
    /***左上圆角半径*/
    private float topLeftRadius;
    /***右上圆角半径*/
    private float topRightRadius;
    /***左下圆角半径*/
    private float bottomLeftRadius;
    /***右下圆角半径*/
    private float bottomRightRadius;
    /***左边框*/
    private int[] lineType;
    private boolean lineFlag;


    public MyButton(Context context) {
        super(context);
        init(null);
    }
    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    private void init(AttributeSet attrs){
        if(attrs==null){
            return;
        }
        Drawable background = getBackground();
        if (background!=null) {
            return;
        }
        TypedArray viewNormal = this.getContext().obtainStyledAttributes(attrs, R.styleable.MyButton);
        allLine = viewNormal.getBoolean(R.styleable.MyButton_my_bt_all_line, false);
        leftLine = viewNormal.getBoolean(R.styleable.MyButton_my_bt_left_line, false);
        topLine = viewNormal.getBoolean(R.styleable.MyButton_my_bt_top_line,false);
        rightLine = viewNormal.getBoolean(R.styleable.MyButton_my_bt_right_line,false);
        bottomLine = viewNormal.getBoolean(R.styleable.MyButton_my_bt_bottom_line, false);
        borderWidth = viewNormal.getDimension(R.styleable.MyButton_my_bt_border_width, 0);
        solidColor = viewNormal.getColor(R.styleable.MyButton_my_bt_solid, getDefColor());
        pressColor = viewNormal.getColor(R.styleable.MyButton_my_bt_press, getDefColor());
        borderColor = viewNormal.getColor(R.styleable.MyButton_my_bt_border_color, getDefColor());
        dashWidth = viewNormal.getDimension(R.styleable.MyButton_my_bt_border_dashWidth, 0);
        dashGap = viewNormal.getDimension(R.styleable.MyButton_my_bt_border_dashGap, 0);
        radius = viewNormal.getDimension(R.styleable.MyButton_my_bt_corner_radius, 0);
        if(radius<=0){
            topLeftRadius = viewNormal.getDimension(R.styleable.MyButton_my_bt_corner_topLeftRadius, 0);
            topRightRadius = viewNormal.getDimension(R.styleable.MyButton_my_bt_corner_topRightRadius, 0);
            bottomLeftRadius = viewNormal.getDimension(R.styleable.MyButton_my_bt_corner_bottomLeftRadius, 0);
            bottomRightRadius = viewNormal.getDimension(R.styleable.MyButton_my_bt_corner_bottomRightRadius, 0);
        }
        complete();
        viewNormal.recycle();
    }

    /**
     * 设置各个自定义属性之后调用此方法设置background
     * 这里有必要说明一下,为什么设置属性了还需要调用这个方法才能生效?
     * 这个方法是将代码设置的各个属性收集转成一个Drawable,然后将它设置为background,简单点这个方法就是用来设置背景的,等价于setBackground方法
     */
    public void complete(){
        lineType = new int[4];
        lineFlag = false;
        if(leftLine){
            lineFlag =true;
            lineType[0]=(int) borderWidth ==0?1:(int) borderWidth;
        }
        if(topLine){
            lineFlag =true;
            lineType[1]=(int) borderWidth ==0?1:(int) borderWidth;
        }
        if(rightLine){
            lineFlag =true;
            lineType[2]=(int) borderWidth ==0?1:(int) borderWidth;
        }
        if(bottomLine){
            lineFlag =true;
            lineType[3]=(int) borderWidth ==0?1:(int) borderWidth;
        }
        if(lineFlag &&!allLine){//layer-list
            if(borderColor ==getDefColor()){
                borderColor = getDefBorderColor();
            }
            if(solidColor ==getDefColor()){
                solidColor = Color.parseColor("#ffffff");
            }
            layerDrawable = new GradientDrawable();
            layerDrawable.setColor(borderColor);
            layerDrawable.setCornerRadius(radius);

            layerGradientDrawableNormal = new GradientDrawable();
            layerGradientDrawableNormal.setColor(solidColor);
            layerGradientDrawableNormal.setCornerRadius(radius);

            Drawable[] layers = new Drawable[2];
            layers[0] = layerDrawable;
            layers[1] = layerGradientDrawableNormal;

            layerDrawableNormal = new LayerDrawable(layers);
            layerDrawableNormal.setLayerInset(1, lineType[0], lineType[1], lineType[2], lineType[3]);//第一层的偏移量

            layerGradientDrawablePress = new GradientDrawable();
            layerGradientDrawablePress.setColor(pressColor == getDefColor() ? solidColor : pressColor);
            layerGradientDrawablePress.setCornerRadius(radius);

            Drawable[] layerPress = new Drawable[2];
            layerPress[0] = layerDrawable;
            layerPress[1] = layerGradientDrawablePress;

            layerDrawablePress = new LayerDrawable(layerPress);
            layerDrawablePress.setLayerInset(1, lineType[0], lineType[1], lineType[2], lineType[3]);//pres状态第一层的偏移量

            stateListDrawableForLayer = new StateListDrawable();
            stateListDrawableForLayer.addState(new int[]{-android.R.attr.state_pressed}, layerDrawableNormal);
            stateListDrawableForLayer.addState(new int[]{android.R.attr.state_pressed}, layerDrawablePress);
            stateListDrawableForLayer.addState(new int[]{}, layerDrawableNormal);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackground(stateListDrawableForLayer);
            }else{
                this.setBackgroundDrawable(stateListDrawableForLayer);
            }
        }else{//shape--selector
            if(allLine && borderWidth ==0){
                borderWidth =1;
            }
            if(borderColor ==getDefColor()){
                borderColor = getDefBorderColor();
            }
            gradientDrawableNormal = new GradientDrawable();
            gradientDrawableNormal.setStroke((int) borderWidth, borderColor, dashWidth, dashGap);
            gradientDrawableNormal.setColor(solidColor);

            gradientDrawablePress = new GradientDrawable();
            gradientDrawablePress.setStroke((int) borderWidth, borderColor, dashWidth, dashGap);
            gradientDrawablePress.setColor(pressColor == getDefColor() ? solidColor : pressColor);
            if(radius >0){
                gradientDrawableNormal.setCornerRadius(radius);
                gradientDrawablePress.setCornerRadius(radius);
            }else{
                float[] fourRadius = new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
                gradientDrawableNormal.setCornerRadii(fourRadius);
                gradientDrawablePress.setCornerRadii(fourRadius);
            }
            stateListDrawableForShape = new StateListDrawable();
            stateListDrawableForShape.addState(new int[]{-android.R.attr.state_pressed}, gradientDrawableNormal);
            stateListDrawableForShape.addState(new int[]{android.R.attr.state_pressed}, gradientDrawablePress);
            stateListDrawableForShape.addState(new int[]{}, gradientDrawableNormal);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackground(stateListDrawableForShape);
            }else{
                this.setBackgroundDrawable(stateListDrawableForShape);
            }
        }
    }

    /**
     * 是否显示上下左右边框
     * @param allLine
     */
    public void setAllLine(boolean allLine) {
        this.allLine = allLine;
    }

    /**
     * 设置显示左边框
     * @param leftLine
     */
    public void setLeftLine(boolean leftLine) {
        this.leftLine = leftLine;
    }

    /**
     * 设置显示顶部边框
     * @param topLine
     */
    public void setTopLine(boolean topLine) {
        this.topLine = topLine;
    }

    /**
     * 设置显示右边框
     * @param rightLine
     */
    public void setRightLine(boolean rightLine) {
        this.rightLine = rightLine;
    }

    /**
     * 设置显示底部边框
     * @param bottomLine
     */
    public void setBottomLine(boolean bottomLine) {
        this.bottomLine = bottomLine;
    }

    /**
     * 设置边框宽度
     * @param borderWidth
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * 设置填充色
     * @param solidColor
     */
    public void setSolidColor(int solidColor) {
        this.solidColor = solidColor;
    }

    /**
     * 设置触摸颜色
     * @param pressColor
     */
    public void setPressColor(int pressColor) {
        this.pressColor = pressColor;
    }

    /**
     * 设置边框颜色
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * 设置虚线长度
     * @param dashWidth
     */
    public void setDashWidth(float dashWidth) {
        this.dashWidth = dashWidth;
    }

    /**
     * 设置虚线间隔
     * @param dashGap
     */
    public void setDashGap(float dashGap) {
        this.dashGap = dashGap;
    }

    /**
     * 设置上下左右圆角半径
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * 设置左上圆角半径
     * @param topLeftRadius
     */
    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    /**
     * 设置右上圆角半径
     * @param topRightRadius
     */
    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    /**
     * 设置左下圆角半径
     * @param bottomLeftRadius
     */
    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }
    /***
     * 设置右下圆角半径
     * @param bottomRightRadius
     */
    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }
    /**
     * 默认边框颜色-灰色
     * @return
     */
    private int getDefBorderColor() {
        return Color.parseColor("#E2E2E2");
    }
    /**
     * 透明
     * @return
     */
    private int getDefColor() {
        return Color.parseColor("#00000000");
    }
}
