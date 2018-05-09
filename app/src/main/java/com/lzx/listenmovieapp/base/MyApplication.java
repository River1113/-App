package com.lzx.listenmovieapp.base;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.lzx.listenmovieapp.R;

/**
 * Author：Road
 * Date  ：2018/4/26
 */

public class MyApplication extends Application {
    private SpeechSynthesizer speaker;

    @Override
    public void onCreate() {
        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        SpeechUtility.createUtility(MyApplication.this, "appid=" + getString(R.string.app_id));
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);


        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        //SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5ac89264");
        //初始化 SDK
        //创建语音合成对象
        speaker = SpeechSynthesizer.createSynthesizer(MyApplication.this, null);
        speaker.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //初始化语音合成相关设置
        speaker.setParameter(SpeechConstant.SPEED, "50");
        speaker.setParameter(SpeechConstant.PITCH, "50");
        speaker.setParameter(SpeechConstant.VOLUME, "50");
        speaker.setParameter(SpeechConstant.STREAM_TYPE, "3");
        speaker.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        speaker.startSpeaking("您好，欢迎进入听影，我们将给您带来以下服务:影库资源请说1,下载专区请说2,重复请说0", null);
        super.onCreate();
    }
}
