package com.github.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/9/6.
 */
public class MyEditText extends EditText implements View.OnFocusChangeListener {

    /***清除文本内容的icon*/
    private Drawable mClearDrawable;
    private boolean hasFoucs,isHiddenClear;
    /***边框宽度*/
    private float borderWidth;
    /***填充色*/
    private int solidColor;
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

    public MyEditText(Context context) {
        super(context);
        init(null);
    }
    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    private void init(AttributeSet attrs){
        if(attrs==null){
            return;
        }
        Drawable background = getBackground();
        if (background instanceof ColorDrawable &&background!=null) {
            return;
        }
        TypedArray viewNormal = this.getContext().obtainStyledAttributes(attrs, R.styleable.MyEditText);
        borderWidth = viewNormal.getDimension(R.styleable.MyEditText_my_et_border_width, 0);
        solidColor = viewNormal.getColor(R.styleable.MyEditText_my_et_solid, getDefColor());
        borderColor = viewNormal.getColor(R.styleable.MyEditText_my_et_border_color, getDefColor());
        if(borderWidth >0){
            borderColor = borderColor ==getDefColor()?getDefBorderColor(): borderColor;
        }
        Drawable clearIcon = viewNormal.getDrawable(R.styleable.MyEditText_my_et_clearIcon);
        setRightDrawble(clearIcon);
        dashWidth = viewNormal.getDimension(R.styleable.MyEditText_my_et_border_dashWidth, 0);
        dashGap = viewNormal.getDimension(R.styleable.MyEditText_my_et_border_dashGap, 0);
        isHiddenClear= viewNormal.getBoolean(R.styleable.MyEditText_my_et_hiddenClear, false);
        radius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_radius, 0);
        if(radius<=0){
            topLeftRadius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_topLeftRadius, 0);
            topRightRadius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_topRightRadius, 0);
            bottomLeftRadius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_bottomLeftRadius, 0);
            bottomRightRadius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_bottomRightRadius, 0);
        }
        viewNormal.recycle();
        complete();
    }

    /**
     * 设置各个自定义属性之后调用此方法设置background
     * 这里有必要说明一下,为什么设置属性了还需要调用这个方法才能生效?
     * 这个方法是将代码设置的各个属性收集转成一个Drawable,然后将它设置为background,简单点这个方法就是用来设置背景的,等价于setBackground方法
     */
    public void complete() {
        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setStroke((int) borderWidth, borderColor, dashWidth, dashGap);
        gradientDrawable.setColor(solidColor);
        if(radius >0){
            gradientDrawable.setCornerRadius(radius);
        }else{
            float[] fourRadius=new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
            gradientDrawable.setCornerRadii(fourRadius);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(gradientDrawable);
        }else{
            this.setBackgroundDrawable(gradientDrawable);
        }
    }
    /***************************************************set方法****************************************************/
    /**
     * 设置清除文本内容的icon
     * @param clearDrawable
     */
    public void setClearDrawable(Drawable clearDrawable) {
//        this.mClearDrawable = clearDrawable;
        setRightDrawble(clearDrawable);
    }
    /**
     * 设置清除文本内容的icon
     * @param clearDrawable
     */
    public void setClearDrawable(int clearDrawable) {
        setClearDrawable(getResources().getDrawable(clearDrawable));
    }
    /**
     * 设置是否显示(隐藏)清除文本内容的icon(默认false-显示)
     * @param isHiddenClear  true隐藏   false显示
     */
    public void setHiddenClearIcon(boolean isHiddenClear) {
        this.isHiddenClear = isHiddenClear;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setSolidColor(int solidColor) {
        this.solidColor = solidColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setDashWidth(float dashWidth) {
        this.dashWidth = dashWidth;
    }

    public void setDashGap(float dashGap) {
        this.dashGap = dashGap;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }
    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }


    /**************************************************************************************************************/

    private void setRightDrawble(Drawable clearIcon) {
        if(isInEditMode()){
            return;
        }
        if(clearIcon!=null){
            mClearDrawable=clearIcon;
        }else{
            mClearDrawable = getCompoundDrawables()[2];
        }
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.textclear);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        this.setCompoundDrawablePadding(dip2px(getContext(), 5));
//        this.setPadding(0,0,15,0);
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(getWatcher());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }
    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible&&!isHiddenClear ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 设置光标位置
     * @param text
     * @param type
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if(!TextUtils.isEmpty(text)){
            try {
                setSelection(text.length());
            }catch (Exception e){
                Log.e("Exception", "输入字符长度超出限制");
            }
        }
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }
    private TextWatcher getWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (hasFoucs) {
                    setClearIconVisible(s.length() > 0);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
    private int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
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
