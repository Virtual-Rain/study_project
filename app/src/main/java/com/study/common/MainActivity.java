package com.study.common;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.study.common.adapter.FragmentAdapter;
import com.study.common.adapter.PlayAdapter;
import com.study.common.adapter.PlayAdapterBaseQuick;
import com.study.common.entity.Announcement;
import com.study.common.entity.Generic;
import com.study.common.entity.LongDragoConfig;
import com.study.common.entity.NoticeConfigInfo;
import com.study.common.entity.PlayData;
import com.study.common.entity.TicketData;
import com.study.common.fragment.BettingRecordRecentFragment;
import com.study.common.fragment.LotterTypeFragment;
import com.study.common.helper.SimpleItemTouchHelperCallback;
import com.study.common.listener.MyAnimatorUpdateListener;
import com.study.common.widget.KenoBetView;
import com.study.common.widget.MissingLotterySeries;
import com.study.common.widget.MissingLotteryType;
import com.study.common.widget.MyDrawView;
import com.study.commonlibrary.ZDialog;
import com.study.commonlibrary.base.adapter.ZBaseAdapter;
import com.study.commonlibrary.base.viewHolder.BindViewHolder;
import com.study.commonlibrary.list.ZListDialog;
import com.study.commonlibrary.list.ZViewpageDialog;
import com.study.commonlibrary.uitls.FileUtils;
import com.study.commonlibrary.uitls.ScreenUtils;
import com.study.commonlibrary.uitls.ToastUtil;
import com.study.commonlibrary.uitls.toast.WindowToast;
import com.study.commonlibrary.widget.SlideSwitch;
import com.study.commonlibrary.widget.SpaceItemDecoration;
import com.stx.xhb.xbanner.XBanner;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static android.text.Html.FROM_HTML_MODE_LEGACY;
import static com.study.commonlibrary.uitls.AppUtil.getContext;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static final int WHAT_LOADED = 0;
    public static final int WHAT_PROGRESS = 1;

    private ZDialog zDialog;
    private ZDialog tDialog;
    private ZDialog xDialog;
    private RelativeLayout mLayout;
    private int currentProgress = 5;
    private ProgressBar mProgressBar;
    private TextView mProgressText;
    private SlideSwitch mSlideSwitch;
    private EasyPopup mPopup;
    private RelativeLayout mLongDragonLayout;
    private RelativeLayout mPeriodsLayout;

    private Button btn_basic;
    private LinearLayout mTransferIconLayout;
    private ImageView mArrowUP;
    private ImageView mArrowDown;
    private TextView mRotate;
    private FrameLayout mFrameLayout;
    private RecyclerView mPlayRecyclerView;

    private String[] sharePlatform = {"微信", "朋友圈", "短信", "微博", "QQ空间", "Google", "FaceBook", "微信", "朋友圈", "短信", "微博", "QQ空间"};
    private String[] periods = {"2期", "3期", "4期", "5期", "6期", "7期", "8期", "9期", "10期"};
    private String[] nums = {"5", ""};

    List<Fragment> fragments = new ArrayList<>();
    List<Fragment> fragmentsLongRemind = new ArrayList<>();
    FragmentAdapter adapter;
    ViewPager viewPager;
    TabLayout tablayout;
    private int transferType = 0;
    private boolean isChange = false;
    private ValueAnimator valueAnimator;

    private List<NoticeConfigInfo> lotteryData;

    private Random random = new Random();
    private boolean btnSwitch = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.rl_main);
        btn_basic = findViewById(R.id.btn_basic);
        mSlideSwitch = findViewById(R.id.switch_remind);
        mLongDragonLayout = findViewById(R.id.rl_long_dragon);
        mPeriodsLayout = findViewById(R.id.rl_periods);
        mTransferIconLayout = findViewById(R.id.ll_transfer_icon);
        mArrowUP = findViewById(R.id.iv_arrow_up);
        mArrowDown = findViewById(R.id.iv_arrow_down);
        mRotate = findViewById(R.id.tv_rotate);
        mFrameLayout = findViewById(R.id.root);
        mPlayRecyclerView = findViewById(R.id.rv_play);

        BettingRecordRecentFragment fragment1 = BettingRecordRecentFragment.newInstance(6, 8);
        BettingRecordRecentFragment fragment2 = BettingRecordRecentFragment.newInstance(7, 12);
        fragments.add(fragment1);
        fragments.add(fragment2);
        LotterTypeFragment fragment3 = new LotterTypeFragment();
        fragmentsLongRemind.add(fragment3);

        ObjectAnimator anim = ObjectAnimator.ofFloat(mTransferIconLayout, "rotation", 0f, 360f);
        // 动画的持续时间，执行多久？
        anim.setDuration(2000);
        MyAnimatorUpdateListener updateListener = new MyAnimatorUpdateListener(anim);
        // 回调监听
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (Float) animation.getAnimatedValue();
//                if (value >= 180 && transferType == 1) {
//                    anim.pause();
//                    isPause = true;
//                }
//                mRotate.setText(value + "");
//            }
//        });
        mTransferIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTransferIconLayout.setClickable(false);
                transferType = transferType == 0 ? 1 : 0;
                /*属性动画*/
//                ObjectAnimator animator;
//                if (transferType == 0) {
//                    animator = ObjectAnimator.ofFloat(mTransferIconLayout, "rotation", 180f, 360f);
//                } else {
//                    animator = ObjectAnimator.ofFloat(mTransferIconLayout, "rotation", 0f, 180f);
//                }
//                animator.setDuration(1000);
//                animator.start();
                /*补间动画*/
                Animation anim;
                if (transferType == 0) {
                    anim = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                } else {
                    anim = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                }
                anim.setFillAfter(true);// 设置保持动画最后的状态  
                anim.setDuration(1000);// 设置动画时间  
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mTransferIconLayout.setClickable(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mTransferIconLayout.startAnimation(anim);
//                mTransferIconLayout.startAnimation(anim);

//                if (isPause) {
//                    anim.resume();
//                    isPause = false;
//                } else {
//                    anim.start();
//                }

            }
        });

        lotteryData = getLotteryData();
        initListener();

        startService(new Intent(this, MQTTService.class));

        startRxJava();
    }

    public void initListener() {
        btn_basic.setOnClickListener(view -> {
            switchIcon(2);
            /*测试子线程更新UI*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String str = btnSwitch ? "基本使用" : "改变app";
                    btn_basic.setText(str);
                    btnSwitch = !btnSwitch;
                    view.invalidate();

                }
            }).start();
        });
    }

    /*切换app应用图标*/
    private void switchIcon(int useCode) {
        try {
            //要跟manifest的activity-alias 的name保持一致
            String icon_tag = "com.study.common.icon_tag";
            String icon_tag_1 = "com.study.common.icon_tag_1";

            if (useCode != 3) {
                PackageManager pm = getPackageManager();
                ComponentName normalComponentName = new ComponentName(getBaseContext(), icon_tag);
                //正常图标新状态
                int normalNewState = useCode == 2 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(normalComponentName) != normalNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(
                            normalComponentName,
                            normalNewState,
                            PackageManager.DONT_KILL_APP);
                }

                ComponentName actComponentName = new ComponentName(getBaseContext(), icon_tag_1);
                //正常图标新状态
                int actNewState = useCode == 1 ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                if (pm.getComponentEnabledSetting(actComponentName) != actNewState) {//新状态跟当前状态不一样才执行
                    pm.setComponentEnabledSetting(
                            actComponentName,
                            actNewState,
                            PackageManager.DONT_KILL_APP);
                }
//                restartLuncher();
            }
        } catch (Exception e) {
//            XLogUtils.e("ComponentName=" + e.toString());
        }
    }

    /*重启桌面*/
    private void restartLuncher() {
        PackageManager mPm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = mPm.queryIntentActivities(intent, 0);
        for (ResolveInfo res : resolves) {
            if (res.activityInfo != null) {
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != valueAnimator) {
            valueAnimator = null;
        }
        if (disposable != null) {
            disposable = null;
        }
    }

    public void layoutIcon(int type) {
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                ScreenUtils.dip2px(30), ConstraintLayout.LayoutParams.WRAP_CONTENT);
        mTransferIconLayout.setGravity(type == 0 ? Gravity.NO_GRAVITY : Gravity.CENTER);
        params0.gravity = Gravity.CENTER;
        params1.gravity = Gravity.CENTER;
        mArrowUP.setImageResource(type == 0 ? R.mipmap.icon_arrow_right : R.mipmap.icon_arrow_right1);
        mArrowUP.setLayoutParams(type == 0 ? params0 : params1);
        mArrowDown.setImageResource(type == 0 ? R.mipmap.icon_arrow_left : R.mipmap.icon_arrow_left1);
        mArrowDown.setLayoutParams(type == 0 ? params1 : params0);

    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_LOADED:
                    zDialog.dismiss();
                    return;
                case WHAT_PROGRESS:
                    currentProgress += 5;
                    mProgressBar.setProgress(currentProgress);
                    mProgressText.setText("已加载" + currentProgress + "/100");
                    if (null != zDialog && currentProgress >= 100) {
                        zDialog.dismiss();
                        currentProgress = 0;
                        zDialog = null;
                    } else {
                        mHandler.sendEmptyMessageDelayed(WHAT_PROGRESS, 500);
                    }
                    return;
            }
        }
    };


    public void userDialog(View view1) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_click, null);
        tDialog = new ZDialog.Builder(getSupportFragmentManager())
                .setDialogView(view)
                .setIsInstance(false)
                //弹窗布局
//                .setLayoutRes(R.layout.dialog_click)
                //设置宽度
                .setWidth(100)
                //设置宽度比例
                .setScreenWidthAspect(this, 0.8f)
                //外部是否可以点击隐藏弹窗
                .setCancelableOutside(false)
                //弹窗显示位置
                .setGravity(Gravity.CENTER)
                //弹窗偏移距离
                .setDialogY(500)
                //设置弹窗背景透明度越小越透明
                .setDimAmount(0.8f)
                //弹窗隐藏回调
                .setOnDismissListener(d -> Toast.makeText(this, "弹窗已隐藏", Toast.LENGTH_SHORT).show())
                //通过ViewHolder拿到控件进行修改
                .setOnBindViewListener(viewHolder -> {
                    viewHolder.setText(R.id.tv_title, "标题党");
                    viewHolder.setText(R.id.tv_content, "我是程序员");
                })
                //添加进行点击控件的id
                .addOnClickListener(R.id.btn_left, R.id.btn_right)
                //设置各个点击控件的事件
                .setOnViewClickListener((viewHolder, view2, dialog) -> {
                    switch (view2.getId()) {
                        case R.id.btn_left:
                            Toast.makeText(this, "左边被点击", Toast.LENGTH_SHORT).show();
//                            loadingDialog(view);
                            break;
                        case R.id.btn_right:
                            Toast.makeText(this, "右边被点击", Toast.LENGTH_SHORT).show();
                            tDialog.dismiss();
                            break;
                    }
                })
                .setOnKeyListener((dialog, keyCode, event) -> {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Toast.makeText(MainActivity.this, "返回键无效必须先升级", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                })
                .create()
                .show();
    }

    public void loadingDialog(View view) {
        zDialog = new ZDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_loading)
                .setIsInstance(false)
                .setWidth(300)
                .setHeight(300)
                .setCancelableOutside(false)
                .create()
                .show();
        mHandler.sendEmptyMessageDelayed(WHAT_LOADED, 5000);
    }

    /**
     * 下载进度条
     *
     * @param view
     */
    public void loadProgressDialog(View view) {
//        zDialog = new ZDialog.Builder(getSupportFragmentManager())
//                .setLayoutRes(R.layout.dialog_loading_progress)
//                .setScreenWidthAspect(this, 0.85f)
//                .setOnBindViewListener(new OnBindViewListener() {
//                    @Override
//                    public void bindView(BindViewHolder viewHolder) {
//                        mProgressBar = viewHolder.getView(R.id.pb_loading_progress);
//                        mProgressText = viewHolder.getView(R.id.tv_progress);
//                    }
//                })
//                .setOnDismissListener(dialogInterface -> {
//                    mHandler.removeMessages(WHAT_PROGRESS);
//                    currentProgress = 5;
//                })
//                .create()
//                .show();
//        mHandler.sendEmptyMessageDelayed(WHAT_PROGRESS, 500);
        WindowToast windowToast = new WindowToast();
        windowToast.makeToast(MainActivity.this, "WindowToast", 1000).show();
    }

    /**
     * 首页广告
     *
     * @param view
     */
    public void homeAd(View view) {
//        new ZDialog.Builder(getSupportFragmentManager())
//                .setLayoutRes(R.layout.dialog_home_ad)
//                .setScreenHeightAspect(this, 0.7f)
//                .setScreenWidthAspect(this, 0.8f)
//                .setOnBindViewListener(viewHolder -> {
//
//                })
//                .addOnClickListener(R.id.iv_close)
//                .setOnViewClickListener((viewHolder, view1, tDialog) -> tDialog.dismiss())
//                .create()
//                .show();
        showAnnouncement(Arrays.asList(periods));
    }

    /**
     * 底部分享
     *
     * @param view
     */
    public void shareDialog(View view) {
        new ZListDialog.Builder(getSupportFragmentManager())
                .setListLayoutRes(R.layout.dialog_share, LinearLayoutManager.HORIZONTAL)
                .setScreenWidthAspect(this, 1f)
                .setGravity(Gravity.BOTTOM)
                .setAdatper(new ZBaseAdapter<String>(R.layout.item_share, Arrays.asList(sharePlatform)) {
                    @Override
                    protected void onBind(BindViewHolder holder, int position, String s) {
                        holder.setText(R.id.tv_share_name, s);
                    }
                })
                .setOnAdapterItemClickListener((ZBaseAdapter.OnAdapterItemClickListener<String>) (holder, positon, item, zDialog) -> {
                    Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                    zDialog.dismiss();
                })
                .create()
                .show();
    }

    public void changePortrait(View view) {
        zDialog = new ZDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_change_portrait)
                .setDialogAnimationRes(R.style.animate_dialog)
                .setGravity(Gravity.BOTTOM)
                .setScreenWidthAspect(this, 1f)
                .addOnClickListener(R.id.tv_camera, R.id.tv_album, R.id.tv_cancel)
                .setOnViewClickListener((viewHolder, view1, dialog) -> {
                    switch (view1.getId()) {
                        case R.id.tv_camera:
                            Toast.makeText(MainActivity.this, "打开相机", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.tv_album:
                            Toast.makeText(MainActivity.this, "打开相册", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.tv_cancel:
                            zDialog.dismiss();
                            break;
                    }
                })
                .create()
                .show();
    }

    public void viewpager(View view) {
        zDialog = new ZViewpageDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_viewpage)
                .setDialogAnimationRes(R.style.animate_dialog)
                .setCancelOutside(false)
                .setGravity(Gravity.BOTTOM)
                .setScreenWidthAspect(this, 1f)
                .setScreenHeightAspect(this, 0.8f)
                .setOnBindViewListener(viewHolder -> {
                    TabLayout tabLayout = viewHolder.getView(R.id.dialog_table_layout);
                    //给TabLayout 添加样式
                    for (int i = 0; i < fragments.size(); i++) {
                        TabLayout.Tab tab = tabLayout.getTabAt(i);
                        if (null != tab) {
                            tab.setCustomView(R.layout.item_tablayout);
                            TextView title = (TextView) tab.getCustomView().findViewById(R.id.tv_title);
                            TextView num = (TextView) tab.getCustomView().findViewById(R.id.tv_title_aide);
                            title.setText(sharePlatform[i]);
                            num.setText(nums[i]);
                            if (!TextUtils.isEmpty(nums[i]) && Integer.valueOf(nums[i]) > 0) {
                                num.setVisibility(View.VISIBLE);
                            } else {
                                num.setVisibility(View.GONE);
                            }
                        }
                    }
                })
                .addOnClickListener(R.id.iv_go_back, R.id.tv_betting_record)
                .setOnViewClickListener((viewHolder, view1, dialog) -> {
                    switch (view1.getId()) {
                        case R.id.iv_go_back:
                            zDialog.dismiss();
                            break;
                        case R.id.tv_betting_record:
                            Toast.makeText(MainActivity.this, "投注记录", Toast.LENGTH_SHORT).show();
                            break;
                    }
                })
                .setFragments(fragments)
                .create()
                .show();
    }

    public void longRemind(View view) {
        zDialog = new ZViewpageDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_viewpage)
                .setDialogAnimationRes(R.style.animate_dialog)
                .setCancelOutside(false)
                .setGravity(Gravity.BOTTOM)
                .setScreenWidthAspect(this, 1f)
                .setScreenHeightAspect(this, 0.9f)
                .setOnBindViewListener(viewHolder -> {
                    TabLayout tabLayout = viewHolder.getView(R.id.dialog_table_layout);
                    singleTab(tabLayout, "长龙提醒彩种设置");
                    viewHolder.getView(R.id.tv_betting_record).setVisibility(View.GONE);
                })
                .addOnClickListener(R.id.iv_go_back, R.id.btn_save)
                .setOnViewClickListener((viewHolder, view1, dialog) -> {
                    switch (view1.getId()) {
                        case R.id.iv_go_back:
                            ((LotterTypeFragment) fragmentsLongRemind.get(0)).hideFragment(zDialog);
                            break;
                        case R.id.btn_save:
                            zDialog.dismiss();
                            break;
                    }
                })
                .setFragments(fragmentsLongRemind)
                .create()
                .show();
    }

    public void missingRemind(View view) {
        ZBaseAdapter seriesAdapter = new ZBaseAdapter<NoticeConfigInfo>(R.layout.item_lottery_series, lotteryData) {
            @Override
            protected void onBind(BindViewHolder holder, int position, NoticeConfigInfo noticeConfigInfo) {
                holder.setText(R.id.tv_lottery_name, noticeConfigInfo.seriesName);
                if (getLotteryNum(lotteryData.get(position).ticketList) < 1) {
                    holder.setText(R.id.tv_lottery_num, "未选择彩种");
                } else {
                    holder.setText(R.id.tv_lottery_num, "已选择" + getLotteryNum(lotteryData.get(position).ticketList) + "个彩种");
                }

            }
        };
        GridLayoutManager layoutManage = new GridLayoutManager(this, 2);
        zDialog = new ZListDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_lottery_ticket)
                .setScreenWidthAspect(this, 1f)
                .setScreenHeightAspect(this, 0.7f)
                .setGravity(Gravity.BOTTOM)
                .addOnClickListener(R.id.iv_missing_remind_close, R.id.btn_use_default_setting, R.id.btn_cancel, R.id.btn_save)
                .setOnViewClickListener(((viewHolder, view1, dialog) -> {
                    switch (view1.getId()) {
                        case R.id.iv_missing_remind_close:
                        case R.id.btn_cancel:
                            zDialog.dismiss();
                            break;
                        case R.id.btn_use_default_setting:
                            lotteryDefaultSetting();
                            seriesAdapter.notifyDataSetChanged();
                            break;
                        case R.id.btn_save:
                            if (getLotteryNum(lotteryData.get(0).ticketList) == 0) {

                            } else {
                                zDialog.dismiss();
                            }
                            break;
                    }
                }))
                .setAdatper(seriesAdapter)
                .setLayoutManage(layoutManage)
                .setOnAdapterItemClickListener((ZBaseAdapter.OnAdapterItemClickListener<NoticeConfigInfo>) (holder, positon, item, zDialog) -> {
                    MissingLotteryType lotteryType = new MissingLotteryType(this);
                    lotteryType.initData(lotteryData.get(positon));
                    xDialog = new ZDialog.Builder(getSupportFragmentManager())
                            .setDialogView(lotteryType)
                            .addOnClickListener(R.id.iv_close)
                            .setScreenWidthAspect(this, 1f)
                            .setScreenHeightAspect(this, 0.85f)
                            .setGravity(Gravity.BOTTOM)
                            .setOnViewClickListener(((viewHolder, view1, dialog) -> {
                                switch (view1.getId()) {
                                    case R.id.iv_close:
                                        lotteryData.set(positon, lotteryType.getNoticeConfigInfo());
                                        seriesAdapter.notifyDataSetChanged();
                                        xDialog.dismiss();
                                        break;
                                }
                            }))
                            .create()
                            .show();
                })
                .create()
                .show();
    }

    public void missingRemindAuto(View view) {
        MissingLotterySeries missingLotterySeries = new MissingLotterySeries(this, getSupportFragmentManager());
        missingLotterySeries.initData(lotteryData);
        zDialog = new ZDialog.Builder(getSupportFragmentManager())
                .setDialogView(missingLotterySeries)
                .setIsInstance(false)
                .setScreenWidthAspect(this, 1f)
                .setScreenHeightAspect(this, 0.7f)
                .setGravity(Gravity.BOTTOM)
                .addOnClickListener(R.id.btn_save, R.id.btn_cancel, R.id.iv_missing_remind_close)
                .setOnViewClickListener(((viewHolder, view1, dialog) -> {
                    switch (view1.getId()) {
                        case R.id.btn_save:
                            if (getLotteryNum(lotteryData.get(0).ticketList) == 0) {

                            } else {
                                zDialog.dismiss();
                            }
                            break;
                        case R.id.btn_cancel:
                        case R.id.iv_missing_remind_close:
                            zDialog.dismiss();
                            break;
                    }
                }))
                .create()
                .show();
    }

    public int getLotteryNum(List<TicketData> ticketData) {
        int num = 0;
        for (TicketData data : ticketData
        ) {
            if (data.isChecked()) {
                num++;
            }
        }
        return num;
    }

    public void lotteryDefaultSetting() {
        if (null != lotteryData) {
            for (NoticeConfigInfo info : lotteryData
            ) {
                for (TicketData ticketData : info.ticketList) {
                    ticketData.setChecked(true);
                }
                for (PlayData playData : info.playList) {
                    playData.setChecked(true);
                }
            }
        }
    }

    public List<NoticeConfigInfo> getLotteryData() {
        String content = FileUtils.readJsonFile(this, "lottery");
        Gson gson = new Gson();
        LongDragoConfig entity = gson.fromJson(content, LongDragoConfig.class);
        return entity.data;
    }

    public void singleTab(TabLayout tabLayout, String title) {
        //隐藏下滑线
        tabLayout.setSelectedTabIndicator(0);
        //禁止点击
        LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);
        View tabView = tabStrip.getChildAt(0);
        tabView.setClickable(false);
        //设置标题
        tabLayout.getTabAt(0).setCustomView(R.layout.item_tablayout);
        TextView textTitle = tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title);
        TextView textTitleAide = tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title_aide);
        textTitle.setTextSize(20);
        textTitle.setText(title);
        textTitleAide.setVisibility(View.GONE);
    }

    public void periods(View view) {
        if (null != mPopup && mPopup.isShowing()) {
            mPopup.dismiss();
            mPopup = null;
        } else {
            mPopup = EasyPopup.create()
                    .setContentView(this, R.layout.popup_recyclerview, 400, 800)
                    .setFocusAndOutsideEnable(true)
                    .apply();
            RecyclerView recyclerView = mPopup.findViewById(R.id.recycler_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            recyclerView.setAdapter(new ZBaseAdapter<String>(R.layout.item_periods, Arrays.asList(periods)) {
                @Override
                protected void onBind(BindViewHolder holder, int position, String s) {
                    holder.setText(R.id.tv_periods, s);
                }
            });
            recyclerView.setLayoutManager(gridLayoutManager);
            mPopup.showAtAnchorView(mPeriodsLayout, YGravity.ABOVE, XGravity.CENTER);
        }

    }

    /*动画*/
    public void widthChange(View view) {
        if (!isChange) {
            isChange = true;
            valueAnimator = ValueAnimator.ofInt(view.getLayoutParams().width, 500);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(animation -> {
                int value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().width = value;
                view.requestLayout();
                if (value >= 500) {
                    longRemind(view);
                }
            });
            valueAnimator.start();
        } else {
            longRemind(view);
        }

    }

    public void toTestActivity(View view) {
        Intent intent = new Intent(MainActivity.this, TestPaintActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.out_to_right, R.anim.out_to_left);
    }

    public void drawing(View view) {
        if (null != mFrameLayout) {
            mFrameLayout.addView(new MyDrawView(this));
        }
    }

    public void play(View view) {
        Button play = (Button) view;
        boolean isPlay = "Play".equals(play.getText().toString());
        play.setText(isPlay ? "Clear" : "Play");
        if (isPlay) {
            if (null != mPlayRecyclerView) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 30);
                PlayAdapter adapter = new PlayAdapter(R.layout.item_keno_play, MockData.getPlayData(), 1);
                adapter.setSpanSizeLookup((gridLayoutManager1, position) -> {
                    if (position == 0 || position == 2) {
                        return 12;
                    } else if (position == 1) {
                        return 6;
                    } else if (position == 3 || position == 4) {
                        return 15;
                    } else {
                        return 6;
                    }
                });

                if (mPlayRecyclerView.getItemDecorationCount() == 0) {
                    mPlayRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
                }
                mPlayRecyclerView.setLayoutManager(gridLayoutManager);
                mPlayRecyclerView.setAdapter(adapter);

                adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
                    switch (view1.getId()) {
                        case R.id.ll_container:
                            KenoBetView kenoBetView = new KenoBetView(this);
                            new ZDialog.Builder(getSupportFragmentManager())
                                    .setDialogView(kenoBetView)
                                    .setIsInstance(false)
                                    .setScreenHeightAspect(this, 0.5f)
                                    .setScreenWidthAspect(this, 0.9f)
                                    .addOnClickListener(R.id.btn_left)
                                    .setOnViewClickListener((viewHolder, view2, dialog) -> {
                                        dialog.dismiss();
                                    })
                                    .create()
                                    .show();
                            break;
                    }
                });
                SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(adapter);
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
                itemTouchHelper.attachToRecyclerView(mPlayRecyclerView);
            }
        } else {
            if (null != mPlayRecyclerView) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                mPlayRecyclerView.setLayoutManager(gridLayoutManager);
                PlayAdapterBaseQuick adapter = new PlayAdapterBaseQuick(R.layout.item_keno_play, MockData.getPlayData(), 2);
                mPlayRecyclerView.setAdapter(adapter);
                if (mPlayRecyclerView.getItemDecorationCount() == 0) {
                    mPlayRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
                }
                ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
                itemTouchHelper.attachToRecyclerView(mPlayRecyclerView);
                adapter.enableDragItem(itemTouchHelper, R.id.kbv_item, true);
                adapter.setOnItemDragListener(new OnItemDragListener() {
                    @Override
                    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {
                        viewHolder.itemView.setScaleX(1.01f);
                        viewHolder.itemView.setTranslationZ(20);
                    }

                    @Override
                    public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int fromPosition, RecyclerView.ViewHolder viewHolder1, int toPosition) {

                    }

                    @Override
                    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {
                        viewHolder.itemView.setScaleX(1f);
                        viewHolder.itemView.setTranslationZ(0);
                    }
                });
            }
        }

    }

    public void flingAnima(View view) {
        /*创建一个飞行的筹码*/
        TextView textView = new TextView(MainActivity.this);

        textView.setText(1 + "");
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.circle_black);
        textView.setTextColor(getResources().getColor(R.color.white));

        /*添加筹码 */
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayout.addView(textView, layoutParams);

        /*根据radiobutton 设置筹码的位置*/
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(textView.getLayoutParams());
        int textViewTopX = 0;
        int textViewTopY = 0;
        margin.setMargins(textViewTopX, textViewTopY, 0, 0);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(margin);
        textView.setLayoutParams(layoutParams2);

        /*获取radiobutton的坐标*/
        int[] ints = new int[2];
        mLongDragonLayout.getLocationInWindow(ints);
        int radioButtonTopX = ints[0];
        int radioButtonTopY = ints[1];

        int mRadioButtonWidth = mLongDragonLayout.getWidth();
        int mRadioButtonHeight = mLongDragonLayout.getHeight();

        int statusHeight = ScreenUtils.getStatusHeight(MainActivity.this);
        int actionHeight = ScreenUtils.getActionBarHeight(MainActivity.this);

        int tragetX = radioButtonTopX + mRadioButtonWidth / 2;
        int tragetY = radioButtonTopY + mRadioButtonHeight / 2 - statusHeight - actionHeight;

        int distanceX = tragetX - textViewTopX;
        int distanceY = tragetY - textViewTopY;

        FlingAnimation flingAnimX = new FlingAnimation(textView, DynamicAnimation.TRANSLATION_X)
                .setStartVelocity(distanceX * 5)
                .setFriction(0.02f)
                .setMinValue(0)
                .setMaxValue(distanceX);
        flingAnimX.start();
        FlingAnimation flingAnimY = new FlingAnimation(textView, DynamicAnimation.TRANSLATION_Y)
                .setStartVelocity(distanceY * 5)
                .setFriction(0.02f)
                .setMinValue(0)
                .setMaxValue(distanceY);
        flingAnimY.start();
        startRotationAni(textView);
    }

    public void startRotationAni(View view) {
        int rotatVeloc = random.nextInt(1100) % (1100 - 900 + 1) + 900;
        FlingAnimation rotation = new FlingAnimation(view, DynamicAnimation.ROTATION);
        rotation.setStartVelocity(rotatVeloc);
        rotation.setFriction(0.9f);
        rotation.start();
        rotation.addEndListener((animation, canceled, value, velocity) -> {
            mLayout.removeView(view);
            ToastUtil.showInfo(((TextView) view).getText().toString());
        });
    }

    /**
     * 公告弹出框
     */
    public void showAnnouncement(List<String> announcements) {
        new ZDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_base_close)
                .setScreenHeightAspect(this, 0.5f)
                .setScreenWidthAspect(this, 0.9f)
                .setCancelableOutside(false)
                .setDimAmount(0.5f)
                .setOnBindViewListener(viewHolder -> {
                    FrameLayout frameLayout = viewHolder.itemView.findViewById(R.id.fl_container);
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View v = inflater.inflate(R.layout.dialog_announcement, null);
                    frameLayout.addView(v);
                    XBanner banner = v.findViewById(R.id.xb_announcement);
                    if (null != announcements) {
                        List<Announcement> data = new ArrayList<>();
                        data.add(new Announcement("<p><font size=\"3\" color=\"red\">设置了字号和颜色</font></p>" +
                                "<b><font size=\"5\" color=\"blue\">设置字体加粗 蓝色 5号</font></font></b></br>" +
                                "<h1>这个是H1标签</h1></br>" +
                                "<p>这里显示图片：</p><b><font size=\\\"5\\\" color=\\\"blue\\\">设置字体加粗 蓝色 5号</font></font></b></br>\" +\n" +
                                "                                \"<h1>这个是H1标签</h1></br>\" +\n" +
                                "                                \"<p>这里显示图片：</p><b><font size=\\\"5\\\" color=\\\"blue\\\">设置字体加粗 蓝色 5号</font></font></b></br>\" +\n" +
                                "                                \"<h1>这个是H1标签</h1></br>\" +\n" +
                                "                                \"<p>这里显示图片：</p><b><font size=\\\"5\\\" color=\\\"blue\\\">设置字体加粗 蓝色 5号</font></font></b></br>\" +\n" +
                                "                                \"<h1>这个是H1标签</h1></br>\" +\n" +
                                "                                \"<p>这里显示图片：</p><b><font size=\\\"5\\\" color=\\\"blue\\\">设置字体加粗 蓝色 5号</font></font></b></br>\" +\n" +
                                "                                \"<h1>这个是H1标签</h1></br>\" +\n" +
                                "                                \"<p>这里显示图片：</p><b><font size=\\\"5\\\" color=\\\"blue\\\">设置字体加粗 蓝色 5号</font></font></b></br>\" +\n" +
                                "                                \"<h1>这个是H1标签</h1></br>\" +\n" +
                                "                                \"<p>这里显示图片：</p><b><font size=\\\"5\\\" color=\\\"blue\\\">设置字体加粗 蓝色 5号</font></font></b></br>\" +\n" +
                                "                                \"<h1>这个是H1标签</h1></br>\" +\n" +
                                "                                \"<p>这里显示图片：</p>"));
                        data.add(new Announcement("<p><font size=\"3\" color=\"red\">设置了字号和颜色</font></p>" +
                                "<b><font size=\"5\" color=\"blue\">设置字体加粗 蓝色 5号</font></font></b></br>" +
                                "<h1>这个是H1标签</h1></br>" +
                                "<p>这里显示图片：</p><img src=\"https://img0.pconline.com.cn/pconline/1808/06/11566885_13b_thumb.jpg\""));
                        data.add(new Announcement("<p><font size=\"3\" color=\"red\">设置了字号和颜色</font></p>" +
                                "<b><font size=\"5\" color=\"blue\">设置字体加粗 蓝色 5号</font></font></b></br>" +
                                "<h1>这个是H1标签</h1></br>" +
                                "<p>这里显示图片：</p><img src=\"https://img0.pconline.com.cn/pconline/1808/06/11566885_13b_thumb.jpg\""));
                        data.add(new Announcement("<p><font size=\"3\" color=\"red\">设置了字号和颜色</font></p>" +
                                "<b><font size=\"5\" color=\"blue\">设置字体加粗 蓝色 5号</font></font></b></br>" +
                                "<h1>这个是H1标签</h1></br>" +
                                "<p>这里显示图片：</p><img src=\"https://img0.pconline.com.cn/pconline/1808/06/11566885_13b_thumb.jpg\""));
                        banner.setBannerData(R.layout.item_periods, data);
                        banner.setAutoPlayAble(false);
                        banner.setBannerCurrentItem(0);
                        banner.loadImage(new XBanner.XBannerAdapter() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void loadBanner(XBanner banner, Object model, View view, int position) {
                                TextView tvContent = view.findViewById(R.id.tv_periods);
                                tvContent.setText(Html.fromHtml(data.get(position).getXBannerUrl(), FROM_HTML_MODE_LEGACY));

                            }
                        });
                    }
                })
                .addOnClickListener(R.id.iv_close)
                .setOnViewClickListener((viewHolder, view1, zDialog) -> zDialog.dismiss())
                .create()
                .show();
    }

    /*Rxjava*/
    private Disposable disposable;

    public static void main(String[] args) {
       startRxJava();
    }

    @SuppressLint("CheckResult")
    public static void startRxJava() {
        String[] names = {"a", "b", "c"};

        //Hook
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                //静态全局监听
                System.out.println(TAG+ " 全局 监听 scheduler" + scheduler);
                return scheduler;
            }
        });
        RxJavaPlugins.setInitIoSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                //静态全局监听
                System.out.println(TAG+ " 全局 监听 初始化 scheduler" + schedulerCallable);
                return schedulerCallable.call();
            }
        });

        Observable.create(
                //自定义Source
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        //线程池执行 异步
                        emitter.onNext("a");
                        System.out.println(TAG+" subscribe: "+Thread.currentThread().getName());
//                        emitter.onNext("b");
//                        emitter.onNext("c");
                    }
                })
                //todo 第二部 ObservableCreate。subscribeOn
                //给上面代码代码用
                //subscribeOn(1) 上层代码 1线程执行
                //subscribeOn(2)
                //subscribeOn(3)
               .subscribeOn(
                        //todo 第一部（new IOScheduler ---> 线程池）
                        Schedulers.io()
                )
                //给下面代码用
                //observeOn（A）
                //observeOn（B）
                //observeOn（C）xian
//                .observeOn(AndroidSchedulers.mainThread())
                //A线程 .subscribe
                //ObservableSubscribeOn.subscribe
                .subscribe(
                        //终点
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                //A线程
                                System.out.println(TAG+" onSubscribe: "+Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(String s) {
                                //终点 相当于 emitter.onNext("xxx"); 线程池
                                System.out.println(TAG+" onNext: "+Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });


    }

}
