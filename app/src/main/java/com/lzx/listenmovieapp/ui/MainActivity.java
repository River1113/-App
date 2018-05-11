package com.lzx.listenmovieapp.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.lzx.listenmovieapp.R;
import com.lzx.listenmovieapp.adapter.HomeAdapter;
import com.lzx.listenmovieapp.adapter.HomePagerAdapter;
import com.lzx.listenmovieapp.base.BaseActivity;
import com.lzx.listenmovieapp.bean.HomeItem;
import com.lzx.listenmovieapp.util.ToastUtil;
import com.tmall.ultraviewpager.UltraViewPager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 主页面
 *
 * @author road
 */
public class MainActivity extends BaseActivity {
    private static final int CODE_PERMISSION = 1;
    @BindView(R.id.viewPager)
    UltraViewPager viewPager;

    @BindView(R.id.ll_voice)
    LinearLayout ll_voice;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private List<View> views;
    public static final int[] VIEWPAGERS = {R.mipmap.viewpaer, R.mipmap.viewpaer,
            R.mipmap.viewpaer, R.mipmap.viewpaer, R.mipmap.viewpaer
    };

    private BaseQuickAdapter mAdapter;
    private List<HomeItem> mData;

    //设置全局说出结果
    private String result;
    SpeechRecognizer hearer;  //听写对象
    RecognizerDialog dialog;  //讯飞提示框
    private HashMap<String, String> hashMapTexts = new LinkedHashMap<String, String>();

    public static final String[] TITLES = {"影库资源",
            "下载电影",
            "最近播放",
            "收藏",
            "远程点播",
            "大众配音",
            "活动专区",
            "设置",
            "我的",
    };

    public static final int[] IMAGES = {R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
    };

    private static final Class<?>[] ACTIVITYS = {SourceActivity.class,
            DownLoadActivity.class,
            RecentlyPlayedActivity.class,
            CollectActivity.class,
            RemotePlayedActivity.class,
            DubbingActivity.class,
            EventsActivity.class,
            SetActivity.class,
            MyActivity.class,
    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLES[i]);
            item.setResId(IMAGES[i]);
            mData.add(item);
        }

        views = new ArrayList<>();
        // 将ImageView添加到view
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < VIEWPAGERS.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            InputStream is = getResources().openRawResource(VIEWPAGERS[i]);
            Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
            BitmapDrawable bd = new BitmapDrawable(getResources(), bm);
            iv.setBackgroundDrawable(bd);
            views.add(iv);
        }
    }

    @Override
    protected void initView() {
        initRecyclerView();
        initViewPager();
        permissionReq();
    }

    private void permissionReq() {
        AndPermission.with(this)
                .requestCode(CODE_PERMISSION)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {

                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("存储权限被拒绝，需要开启")
                                .setPositiveButton("确定", (dialogInterface, i) -> permissionReq())
                                .create()
                                .show();
                    }
                })
                .start();
    }

    @Override
    protected void setListener() {
        ll_voice.setOnClickListener(this);
    }

    private void initRecyclerView() {
        rv_list.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new HomeAdapter(R.layout.item_home, mData);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MainActivity.this, ACTIVITYS[position]);
                startActivity(intent);
            }
        });
        rv_list.setAdapter(mAdapter);
    }

    private void initViewPager() {
        viewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
        });
        viewPager.setAdapter(new HomePagerAdapter(views));
        viewPager.initIndicator();
        viewPager.getIndicator().setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.BLUE)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        viewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        viewPager.getIndicator().build();
        //设定页面循环播放
        viewPager.setInfiniteLoop(true);
        //设定页面自动切换  间隔2秒
        viewPager.setAutoScroll(2500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_voice:
                ToastUtil.show(this, "开始语音");
                // 语音配置对象初始化
                SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID + "=5ac89264");

                // 1.创建SpeechRecognizer对象，第2个参数：本地听写时传InitListener
                hearer = SpeechRecognizer.createRecognizer(MainActivity.this, null);
                // 交互动画
                dialog = new RecognizerDialog(MainActivity.this, null);
                // 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
                hearer.setParameter(SpeechConstant.DOMAIN, "iat"); // domain:域名
                hearer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                hearer.setParameter(SpeechConstant.ACCENT, "mandarin"); // mandarin:普通话

                //3.开始听写
                dialog.setListener(new RecognizerDialogListener() {  //设置对话框

                    @Override
                    public void onResult(RecognizerResult results, boolean isLast) {
                        // TODO 自动生成的方法存根
                        Log.d("Result", results.getResultString());
                        //(1) 解析 json 数据<< 一个一个分析文本 >>
                        StringBuffer strBuffer = new StringBuffer();
                        try {
                            JSONTokener tokener = new JSONTokener(results.getResultString());
                            Log.i("TAG", "Test" + results.getResultString());
                            Log.i("TAG", "Test" + results.toString());
                            JSONObject joResult = new JSONObject(tokener);

                            JSONArray words = joResult.getJSONArray("ws");
                            for (int i = 0; i < words.length(); i++) {
                                // 转写结果词，默认使用第一个结果
                                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                                JSONObject obj = items.getJSONObject(0);
                                strBuffer.append(obj.getString("w"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //                  String text = strBuffer.toString();
                        // (2)读取json结果中的sn字段
                        String sn = null;

                        try {
                            JSONObject resultJson = new JSONObject(results.getResultString());
                            sn = resultJson.optString("sn");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //(3) 解析语音文本<< 将文本叠加成语音分析结果  >>
                        hashMapTexts.put(sn, strBuffer.toString());
                        StringBuffer resultBuffer = new StringBuffer();  //最后结果
                        for (String key : hashMapTexts.keySet()) {
                            resultBuffer.append(hashMapTexts.get(key));
                        }
                        result = resultBuffer.toString();
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(SpeechError error) {
                        // TODO 自动生成的方法存根
                        error.getPlainDescription(true);
                    }
                });
                dialog.show();  //显示对话框
                break;
        }
    }

}
