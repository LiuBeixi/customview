package com.github.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by Administrator on 2016/8/29.
 */
public class MyRadioButton extends RadioButton {
    /***未选择状态显示的Drawable*/
    private Drawable my_normal;
    /***选择状态显示的Drawable*/
    private Drawable my_checked;
    /***未选择状态显示的字体颜色*/
    private int my_normal_color;
    /***选择状态显示的字体颜色*/
    private int my_checked_color;
    /***显示Drawable方向*/
    private int my_checked_drawable;
    /***显示Drawable方向--默认*/
    public static final int DEFAULT=0;
    /***显示Drawable方向--左边*/
    public static final int LEFT=1;
    /***显示Drawable方向--顶部*/
    public static final int TOP=2;
    /***显示Drawable方向--右边*/
    public static final int RIGHT=3;
    /***显示Drawable方向--底部*/
    public static final int BOTTOM=4;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    public MyRadioButton(Context context) {
        super(context);
        init(null);
    }
    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private void init(AttributeSet attrs){
        if(attrs==null){
            return;
        }
        TypedArray viewNormal = this.getContext().obtainStyledAttributes(attrs, R.styleable.MyRadioButton);
        my_normal = viewNormal.getDrawable(R.styleable.MyRadioButton_my_radio_normal);
        my_checked = viewNormal.getDrawable(R.styleable.MyRadioButton_my_radio_checked);
        my_normal_color = viewNormal.getColor(R.styleable.MyRadioButton_my_radio_normal_color, this.getTextColors().getDefaultColor());
        my_checked_color = viewNormal.getColor(R.styleable.MyRadioButton_my_radio_checked_color,-1);
        my_checked_drawable = viewNormal.getInteger(R.styleable.MyRadioButton_my_radio_checked_drawable, 0);
        viewNormal.recycle();
        complete();
    }

    /**
     * 设置各个自定义属性之后调用此方法设置ButtonDrawable
     * 这里有必要说明一下,为什么设置属性了还需要调用这个方法才能生效?
     * 这个方法是将代码设置的各个属性收集转成一个Drawable,然后将它设置为ButtonDrawable,简单点这个方法就是用来设置背景的,等价于setButtonDrawable方法
     */
    public void complete() {
        if(my_normal !=null&& my_checked !=null){
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_checked}, my_checked);
            stateListDrawable.addState(new int[]{}, my_normal);
            switch (my_checked_drawable){
                case 0:
                    this.setButtonDrawable(stateListDrawable);
                    break;
                case 1:
                    this.setCompoundDrawablesWithIntrinsicBounds(stateListDrawable,null,null,null);
                    break;
                case 2:
                    this.setCompoundDrawablesWithIntrinsicBounds(null,stateListDrawable,null,null);
                    break;
                case 3:
                    this.setCompoundDrawablesWithIntrinsicBounds(null,null,stateListDrawable,null);
                    break;
                case 4:
                    this.setCompoundDrawablesWithIntrinsicBounds(null,null,null,stateListDrawable);
                    break;
            }

        }

        if(my_checked_color !=-1){
            int [][]colorState=new int[2][];
            int []myColor=new int[]{my_checked_color, my_normal_color};
            colorState[0]=new int[]{android.R.attr.state_checked};
            colorState[1]=new int[]{};
            ColorStateList colorStateList=new ColorStateList(colorState,myColor);
            this.setTextColor(colorStateList);
        }
    }
    /***未选择状态显示的Drawable*/
    public void setMy_normal(Drawable my_normal) {
        this.my_normal = my_normal;
    }
    public void setMy_normal(int my_normal) {
        setMy_normal(getResources().getDrawable(my_normal));
    }
    /***选择状态显示的Drawable*/
    public void setMy_checked(Drawable my_checked) {
        this.my_checked = my_checked;
    }
    public void setMy_checked(int my_checked) {
        setMy_checked(getResources().getDrawable(my_checked));
    }
    /***未选择状态字体颜色*/
    public void setMy_normal_color(int my_normal_color) {
        this.my_normal_color = my_normal_color;
    }
    /***选择状态字体颜色*/
    public void setMy_checked_color(int my_checked_color) {
        this.my_checked_color = my_checked_color;
    }
    /***设置显示Drawable的方向*/
    public void setMy_checked_drawable(int my_checked_drawable) {
        this.my_checked_drawable = my_checked_drawable;
    }
}
