package cc.xiaobaicz.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

/**
 * 自动隐藏键盘布局
 * @author BC
 */
public class AutoHideSoftInputLayout extends FrameLayout {

    /**
     * 隐藏监听接口
     */
    public interface OnHideListener {
        /**
         * 隐藏回调函数
         */
        void onHide();
    }

    private OnHideListener mOnHideListener;

    public AutoHideSoftInputLayout(Context context) {
        super(context);
    }

    public AutoHideSoftInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHideSoftInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoHideSoftInputLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置隐藏监听
     */
    public void setOnHideListener(OnHideListener listener) {
        mOnHideListener = listener;
    }

    /**
     * 隐藏键盘
     */
    public void hideSoftInput() {
        final InputMethodManager imf = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imf != null && imf.isAcceptingText()) {
            imf.hideSoftInputFromWindow(getWindowToken(), 0);
            clearFocus();
            onHide();
        }
    }

    private void onHide() {
        if (mOnHideListener != null)
            mOnHideListener.onHide();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean active = super.dispatchTouchEvent(ev);
        //无处理时，可隐藏键盘
        if (!active) {
            hideSoftInput();
        }
        return active;
    }

}
