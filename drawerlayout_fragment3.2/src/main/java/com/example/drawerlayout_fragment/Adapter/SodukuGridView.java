package com.example.drawerlayout_fragment.Adapter;

/**
 * Created by Administrator on 2016/11/7.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
//import android.widget.GridView;

/**
 * 自定义的“九宫格”——用在显示帖子详情的图片集合
 * 解决的问题：GridView显示不全，只显示了一行的图片，比较奇怪，尝试重写GridView来解决
 *
 * @author lichao
 * @since 2014-10-16 16:41
 */
public class SodukuGridView extends GridView {
    public SodukuGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public SodukuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public SodukuGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);// 注意这里,这里的意思是直接测量出GridView的高度
    }

}
