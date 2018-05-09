package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.util.SharedPrefsUtil;
import com.lzx.listenmovieapp.util.ToastUtil;

import butterknife.BindView;

/**
 * 修改用户名
 *
 * @author road
 */
public class ModifyAccountActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.et_account)
    EditText et_account;

    String account;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBarMarginTop(R.id.ll_title)
                .statusBarColor(R.color.colorPrimary)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_modify_account;
    }

    @Override
    protected void initData() {
        account = getIntent().getStringExtra("account");
    }

    @Override
    protected void initView() {
        tv_title.setText("我的");
        tv_right.setText("保存");
        if (!TextUtils.isEmpty(account)) {
            et_account.setText(account);
        }
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);

        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
                account = et_account.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    ToastUtil.show(this, "请输入用户昵称");
                    return;
                }
                SharedPrefsUtil.saveString(this, "account", account);
                Intent intent = new Intent();
                intent.putExtra("account", account);
                setResult(RESULT_OK, intent);
                ToastUtil.show(this, "保存成功");
                finish();
                break;
        }
    }
}
