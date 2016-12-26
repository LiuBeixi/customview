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

    private Drawable mClearDrawable;
    private boolean hasFoucs,isHiddenClear;

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
        float borderWidth = viewNormal.getDimension(R.styleable.MyEditText_my_et_border_width, 0);
        int solidColor = viewNormal.getColor(R.styleable.MyEditText_my_et_solid, Color.parseColor("#00000000"));
        int borderColor = viewNormal.getColor(R.styleable.MyEditText_my_et_border_color, -1);
        Drawable clearIcon = viewNormal.getDrawable(R.styleable.MyEditText_my_et_clearIcon);
        setRightDrawble(clearIcon);
        float dashWidth = viewNormal.getDimension(R.styleable.MyEditText_my_et_border_dashWidth, 0);
        float dashGap = viewNormal.getDimension(R.styleable.MyEditText_my_et_border_dashGap, 0);
        isHiddenClear= viewNormal.getBoolean(R.styleable.MyEditText_my_et_hiddenClear,false);

        float radius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_radius, 0);
        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setStroke((int) borderWidth, borderColor, dashWidth, dashGap);
        gradientDrawable.setColor(solidColor);
        if(radius>0){
            gradientDrawable.setCornerRadius(radius);
        }else{
            float topLeftRadius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_topLeftRadius, 0);
            float topRightRadius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_topRightRadius, 0);
            float bottomLeftRadius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_bottomLeftRadius, 0);
            float bottomRightRadius = viewNormal.getDimension(R.styleable.MyEditText_my_et_corner_bottomRightRadius, 0);
            float[] fourRadius=new float[]{topLeftRadius,topLeftRadius,topRightRadius,topRightRadius,bottomRightRadius,bottomRightRadius,bottomLeftRadius,bottomLeftRadius};
            gradientDrawable.setCornerRadii(fourRadius);
        }
        viewNormal.recycle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(gradientDrawable);
        }else{
            this.setBackgroundDrawable(gradientDrawable);
        }
    }

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
}
