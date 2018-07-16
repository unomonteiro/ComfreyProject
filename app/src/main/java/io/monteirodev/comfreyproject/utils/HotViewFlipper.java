package io.monteirodev.comfreyproject.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

import io.monteirodev.comfreyproject.R;

public class HotViewFlipper extends ViewFlipper {
    private int initialView = 0;

    public HotViewFlipper(Context context) {
        super(context);
        initialView = 0;
    }

    public HotViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray styled = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.HotViewFlipperAttrs, 0, 0);

        try {
            initialView = styled.getInteger(R.styleable.HotViewFlipperAttrs_initialView, 0);
        } finally {
            styled.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setDisplayedChild(initialView % getChildCount());
    }
}

/*
*
class HotViewFlipper : ViewFlipper {
    private val initialView: Int

    constructor(context: Context) : super(context) {
        initialView = 0
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val styled = context.theme.obtainStyledAttributes(
                attrs, R.styleable.HotViewFlipperAttrs, 0, 0)

        initialView = try {
            styled.getInteger(
                    R.styleable.HotViewFlipperAttrs_initialView, 0)
        } finally {
            styled.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        displayedChild = initialView % childCount
    }
}
* */
