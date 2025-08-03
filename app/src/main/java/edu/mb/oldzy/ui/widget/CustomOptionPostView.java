package edu.mb.oldzy.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
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
            boolean isRequired = a.getBoolean(R.styleable.CustomOptionPostView_isRequired, false);
            boolean isDigitOnly = a.getBoolean(R.styleable.CustomOptionPostView_isDigitOnly, false);
            boolean isReadOnly = a.getBoolean(R.styleable.CustomOptionPostView_isReadOnly, false);
            a.recycle();

            setTitle(title);
            setRequired(isRequired);
            setInput(isInput, isDigitOnly, isReadOnly);
            setSubtitle(subTitle);
        }
    }

    public void setInput(boolean isInput, boolean isDigitOnly, boolean isReadOnly) {
        binding.tvSubTitle.setVisibility(isInput ? View.GONE : View.VISIBLE);
        binding.ivDropDown.setVisibility(isInput ? View.GONE : View.VISIBLE);
        binding.edtInput.setVisibility(isInput ? View.VISIBLE : View.GONE);
        binding.edtInput.setHint(subTitle);
        if (isDigitOnly) {
            binding.edtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (isReadOnly) {
            binding.edtInput.setEnabled(false);
        }
    }

    public void setRequired(boolean isRequired) {
        binding.tvRequired.setVisibility(isRequired ? View.VISIBLE : View.GONE);
    }

    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    public void setSubtitle(String subtitle) {
        this.subTitle = subtitle;
        binding.tvSubTitle.setText(subtitle);
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void addListener(View.OnClickListener listener) {
        binding.getRoot().setOnClickListener(listener);
    }

    public String getValue() {
        return binding.edtInput.getText().toString().trim();
    }

    public void focus() {
        binding.edtInput.requestFocus();
    }

    public void setInput(String input) {
        binding.edtInput.setText(input);
    }
}
