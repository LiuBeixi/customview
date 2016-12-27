# customview
    由于平时开发项目需要定义各种形状的view,导致项目中增加很多shape之类的xml,而且有时候仅仅因为颜色值不一样也需要新建xml文件,开发效率和维护效率也不高,为了方便日常开发自己就简单的自定义了部分view,通过布局文件里面的自定义属性控制view形状,开发时比较方便(由于代码比较简单,适合android初学者,大神可以直接无视)
>![github](https://github.com/2380253499/customview/blob/master/study/app/src/main/res/drawable/demo.png "github") 
>### MyCheckBox,MyRadioButton用法
<br />my_checkbox_checked——设置选中状态的button
<br/>my_checkbox_normal——设置未选中状态的button
<br/>my_checkbox_checked_color——设置选中状态时的字体颜色
<br/>my_checkbox_normal_color——设置未选中状态时的字体颜色
<br/>my_checkbox_checked_drawable——设置button显示的位置(left,top,right,bottom),默认在左边(注意:在设置这个属性的时候,会显示默认的button样式,此时需要设置button="@null")
>### MyLinearLayout,MyTextView用法
<br />my_ll_solid——设置控件填充颜色(类似于background,但是用自定义属性的时候千万别设置background,不然会把自定属性全部覆盖掉,因为这些属性最终是以background设置的)
<br/>my_ll_border_width——设置view边框宽度
<br/>my_ll_border_color——设置view边框颜色
<br/>my_ll_border_dashWidth——设置边框虚线长度
<br/>my_ll_border_dashGap——设置边框虚线之间的间距长度
<br/>my_ll_corner_radius——设置view圆角半径(如果设置这个属性则下面4个属性设置无效)
<br/>my_ll_corner_topLeftRadius——设置view左上圆角半径
<br/>my_ll_corner_topRightRadius——设置view右上圆角半径
<br/>my_ll_corner_bottomLeftRadius——设置view左下圆角半径
<br/>my_ll_corner_bottomRightRadius——设置view右下圆角半径
<br/>my_ll_all_line——设置边框(如果设置这个属性则下面4个属性设置无效,默认为灰色,宽度一个像素点)
<br/>my_ll_left_line——设置view左边边框
<br/>my_ll_top_line——设置view顶部边框
<br/>my_ll_right_line——设置view右边边框
<br/>my_ll_bottom_line——设置view底下边框
<br/>my_ll_press——设置点击效果的颜色值(如果view没有设置点击事件是没有press效果的)
>### MyEditText用法
<br/>MyEditText除了上面列出的属性还提供了额外两个属性
<br/>my_et_clearIcon——设置清除文本内容的小图标<br/>(默认提供一个小图标,因为项目开发时UI有提清除文本内容的需求,开发中如果每个edittext都要添加一个布局比较麻烦)
<br/>my_et_hiddenClear——是否隐藏清除的小图标(默认false,如果不需要,或者多行文本输入,可以设置true不显示)
>### 用Android Studio开发的朋友只需要在gradle文件加上
[ ![Download](https://api.bintray.com/packages/zhongrui/customview/customview/images/download.svg) ](https://bintray.com/zhongrui/customview/customview/_latestVersion)
 ```groovy
	dependencies{
 		compile 'com.github:customview:版本号看上面的蓝色小图片'
	}
 ```
<br/>用eclipse的朋友就下载我的源码
