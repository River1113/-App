package com.lzx.listenmovieapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;

import butterknife.BindView;

/**
 * 反馈
 *
 * @author cx
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.tv_count)
    TextView tv_count;

    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.et_feedback)
    EditText et_feedback;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_title.setText("反馈与建议");
        tv_right.setText("提交");
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        et_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String sum = (200 - et_feedback.getText().length()) + "/200";
                tv_count.setText(sum);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    tv_right.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.tv_right:
                new AlertDialog.Builder(this)
                        .setMessage("感谢您的反馈和建议，我们会尽快优化~")
                        .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
                break;
        }
    }
}
