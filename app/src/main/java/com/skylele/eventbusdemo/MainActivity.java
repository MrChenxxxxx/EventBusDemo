package com.skylele.eventbusdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.skylele.eventbusdemo.manager.EventMsg;
import com.skylele.eventbusdemo.manager.EventMsgWithParams;
import com.skylele.eventbusdemo.manager.StudentBean;
import com.skylele.eventbusdemo.ui.activity.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    private static final String TAG = "ThreadName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_sendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventMsg());
            }
        });
        findViewById(R.id.btn_sendMessageWithParams).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentBean studentBean = new StudentBean();
                studentBean.setAge(24);
                studentBean.setName("小李");
                EventBus.getDefault().post(new EventMsgWithParams(studentBean));
            }
        });
        findViewById(R.id.btn_sendMessage1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventMsg());
                    }
                }.start();

            }
        });
    }


    /**
     * EventBug 回调和 方法名onMessageEventOnMain 没有任何关系，可以随便改，只和方法参数里面的Object类型有关
     * 如果Object类型一样，则会全部调用
     *
     * @param eventMsg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventOnMain(EventMsg eventMsg) {
        Log.d(TAG + "MAIN", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventOnPost(EventMsg eventMsg) {
        Log.d(TAG + "POSTING", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventAsync(EventMsg eventMsg) {
        Log.d(TAG + "Async", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackgroud(EventMsg eventMsg) {
        Log.d(TAG + "BACKGROUND", Thread.currentThread().getName());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainWithParams(EventMsgWithParams eventMsgWithParams) {
        StudentBean student = eventMsgWithParams.studentBean;
        Log.d(TAG, student.getName() + " :: " + student.getAge());
    }


}
