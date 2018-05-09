package com.lzx.listenmovieapp.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.util.SharedPrefsUtil;
import com.lzx.listenmovieapp.util.ToastUtil;

import butterknife.BindView;

/**
 * 我的
 *
 * @author cx
 */
public class MyActivity extends BaseActivity {

    private static final int REQ_MODIFY_ACCOUNT = 0;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.rl_account)
    RelativeLayout rl_account;

    @BindView(R.id.tv_account)
    TextView tv_account;

    @BindView(R.id.rl_vipPlate)
    RelativeLayout rl_vipPlate;

    @BindView(R.id.tv_authorization)
    TextView tv_authorization;

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
        return R.layout.activity_my;
    }

    @Override
    protected void initData() {
        account = SharedPrefsUtil.getString(this, "account", "");
    }

    @Override
    protected void initView() {
        tv_title.setText("我的");

        if (!TextUtils.isEmpty(account)) {
            tv_account.setText(account);
        }
    }

    @Override
    protected void setListener() {
        tv_back.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        rl_vipPlate.setOnClickListener(this);
        tv_authorization.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.rl_account:
                Intent intent = new Intent(this, ModifyAccountActivity.class);
                intent.putExtra("account", account);
                startActivityForResult(intent, REQ_MODIFY_ACCOUNT);
                break;

            case R.id.rl_vipPlate:
                ToastUtil.show(this, "开发中，敬请期待");
                break;

            case R.id.tv_authorization:
                ToastUtil.show(this, "开发中，敬请期待");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            default:
                break;
            case REQ_MODIFY_ACCOUNT:
                if (data != null) {
                    account = data.getStringExtra("account");
                    tv_account.setText(account);
                }
                break;
        }
    }
}
