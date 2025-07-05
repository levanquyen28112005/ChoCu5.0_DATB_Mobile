package edu.mb.oldzy.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.ViewCustomOptionPostBinding;

public class CustomOptionPostView extends FrameLayout {
    public CustomOptionPostView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CustomOptionPostView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomOptionPostView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CustomOptionPostView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private ViewCustomOptionPostBinding binding;
    private String subTitle;

    private void init(Context context, AttributeSet attrs) {
        binding = ViewCustomOptionPostBinding.inflate(LayoutInflater.from(context), this, true);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomOptionPostView);
            String title = a.getString(R.styleable.CustomOptionPostView_title);
            subTitle = a.getString(R.styleable.CustomOptionPostView_subTitle);
            boolean isInput = a.getBoolean(R.styleable.CustomOptionPostView_isInput, false);
            a.recycle();

            setTitle(title);
            setInput(isInput);
            setSubtitle(subTitle);
        }
    }

    public void setInput(boolean isInput) {
        binding.tvSubTitle.setVisibility(isInput ? View.GONE : View.VISIBLE);
        binding.ivDropDown.setVisibility(isInput ? View.GONE : View.VISIBLE);
        binding.edtInput.setVisibility(isInput ? View.VISIBLE : View.GONE);
        binding.edtInput.setHint(subTitle);
    }

    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    public void setSubtitle(String subtitle) {
        binding.tvSubTitle.setText(subtitle);
    }
}
