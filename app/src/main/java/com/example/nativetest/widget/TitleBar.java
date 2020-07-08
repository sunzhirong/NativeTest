package com.example.nativetest.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nativetest.R;


/**
 * Created by niko on 2019/5/17.
 */

public class TitleBar extends RelativeLayout {
    private ImageView mTitleBarIvBack;
    private ImageView mTitleBarIvRight;
    private TextView mTitleBarTvTitle;
    private RelativeLayout mRlContainer;

    public TitleBar(Context context) {
        super(context);
        init(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_title_bar, this, true);

        mTitleBarIvBack = (ImageView) findViewById(R.id.ivBack);
        mTitleBarIvRight = (ImageView) findViewById(R.id.ivRight);
        mTitleBarTvTitle = (TextView) findViewById(R.id.tvTitle);
        mRlContainer = (RelativeLayout) findViewById(R.id.rlContainer);


        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
            if (array.hasValue(R.styleable.TitleBar_title)) {
                mTitleBarTvTitle.setText(array.getString(R.styleable.TitleBar_title));
            }
//            if (array.hasValue(R.styleable.TitleBar_right_icon)) {
//                mTitleBarIvRight.setImageResource(array.getResourceId(R.styleable.TitleBar_right_icon,0));
//            }
//
//            if (array.hasValue(R.styleable.TitleBar_back_icon)) {
//                mTitleBarIvBack.setBackground(getResources().getDrawable(array.getResourceId(R.styleable.TitleBar_back_icon,0)));
//            }

            initBack();
            array.recycle();
        }
    }

    private void initBack() {
        this.mTitleBarIvBack.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                try {
                    Context context = TitleBar.this.getContext();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
                    if (context != null && context instanceof Activity) {
                        ((Activity) context).onBackPressed();
                    }

                } catch (Exception var4) {

                }
            }
        });
    }


    public void setTitle(int resId) {
        mTitleBarTvTitle.setText(resId);

    }

    public void setTitle(String title) {
        mTitleBarTvTitle.setText(title);

    }

    public void setTitleColor(int color){
        mTitleBarTvTitle.setTextColor(color);
    }

    public void setBackImageRes(int res) {
        mTitleBarIvBack.setImageResource(res);
    }

    public ImageView getBack() {
        return mTitleBarIvBack;
    }

    public void setBackOnClickListener(OnClickListener listener) {
        mTitleBarIvBack.setOnClickListener(listener);
    }

    public void setTitleBg(int color){
        mRlContainer.setBackgroundColor(color);
    }


    public ImageView getTitleBarIvRight(){
        return mTitleBarIvRight;
    }


}