package com.hu.scaleviewpager;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hu.stackcardview.CustomRoundImageView;
import com.hu.stackcardview.DensityUtils;
import com.hu.stackcardview.DisallowParentTouchViewPager;
import com.hu.stackcardview.PageTransformerConfig;
import com.hu.stackcardview.StackCardPageTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MyPhotoRoundBannerScroll extends RelativeLayout {


    Activity activity;
//    List<ImageView> picList; // 图片组
    List<Button> dotList; // 点组
    public LinearLayout dotLine; // 点容器
    public DisallowParentTouchViewPager index_viewPage;
    Timer timer; // 定时器
    OnMyclick onclick; // 点击事件
    List<Integer> urlList;
    private String mTagText = "";

    private int mCurPager;
    float mDotWidth;
    private RelativeLayout rl_ad;


    public MyPhotoRoundBannerScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyImgScroll);
        //是否显示更多标签 默认显示
        mDotWidth = a.getDimension(R.styleable.MyImgScroll_dot_radis, 6);

        a.recycle();
    }

    private ImageView getImgView(int url, double ratio) {

        ImageView view = new CustomRoundImageView(activity);
        LayoutParams params = new LayoutParams(DensityUtils.dip2px(activity, 300),
                LayoutParams.MATCH_PARENT);
        int vp_width = DensityUtils.getWindowWidth(activity)- DensityUtils.dip2px(activity, 30);
        params.height = (int) (vp_width * ratio);
        params.width = DensityUtils.dip2px(activity, 300);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((CustomRoundImageView) view).setRadius(8);
        view.setLayoutParams(params);
//        loadImage(url, view);
        view.setImageResource(url);
        return view;
    }


    public void initUI(Activity _context, List<Integer> _listViews, final double ratio4) {
        urlList = _listViews;
        activity = _context;
        dotList = new ArrayList<>();

        final View view = LayoutInflater.from(activity).inflate(R.layout.controls_index_ad, this, true);
        dotLine = (LinearLayout) view.findViewById(R.id.index_dot_line);
        rl_ad = view.findViewById(R.id.rl_ad);


        index_viewPage = view.findViewById(R.id.index_viewPage);
        final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        int vp_width = DensityUtils.getWindowWidth(activity) - DensityUtils.dip2px(activity, 20);
        params.height = (int) (vp_width * ratio4);
        index_viewPage.setLayoutParams(params);
//        rl_ad.setLayoutParams(params);

        index_viewPage.setPageTransformer(true, StackCardPageTransformer.getBuild()
                .setViewType(PageTransformerConfig.RIGHT) //层叠方向
                .setTranslationOffset(DensityUtils.dp2px(activity, 30f)) //左右位置偏移量
                .setScaleOffset(DensityUtils.dp2px(activity, 50f)) //缩放偏移量
                .setAlphaOffset(1f) //卡片透明度偏移量
                .setMaxShowPage(3) //最大显示的页数
                .create(index_viewPage));

        for (int i = 0; i < _listViews.size(); i++) {
            ImageView imgView = getImgView(_listViews.get(i), ratio4);
            if(mTagList.size() > 0) {
                String desc = mTagList.get(i);
            }


//            picList.add(imgView);
        }
        if (urlList.size() == 2 || urlList.size() == 3) {
            for (int i = 0; i < _listViews.size(); i++) {
                ImageView imgView = getImgView(_listViews.get(i), ratio4);
                if(mTagList.size() > 0) {
                    String desc = mTagList.get(i);
                }
//                picList.add(imgView);
            }
        }

        dotLine.removeAllViews();
        if (urlList.size() < 2) {
            dotLine.setVisibility(GONE);
        }
        for (int i = 0; i < urlList.size(); i++) {
            Button btn = new Button(activity);
            btn.setBackground(null);
            btn.setBackgroundResource(R.drawable.ad_indicator_order_selector);
            setUnSelect(btn);

            if (i == 0) {
                btn.setSelected(true);
                setSelect(btn);
            }
            dotLine.addView(btn);
            dotList.add(btn);
        }

        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        llParams.setMargins(0, params.height - DensityUtils.dip2px(activity, 10), 0, DensityUtils.dip2px(activity, 10));
        dotLine.setLayoutParams(llParams);
        index_viewPage.setOnTouchListener(new OnTouchListener() {
            float lastx;
            int state;

            public boolean onTouch(View v, MotionEvent event) {

                try {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        lastx = event.getX();
                        stopTimer();
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        if (Math.abs(event.getX() - lastx) > 100) {
                            state = 1;
                        }
                        lastx = event.getX();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (state == 1) {
                            state = 0;
                            startTimer();
                        }
                    }
                } catch (Exception e) {

                }
                return false;
            }
        });
        index_viewPage.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                if (urlList.size() == 1) {
                    return 1;
                }
                return Integer.MAX_VALUE;
            }

            // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
            @Override
            public void destroyItem(ViewGroup view, int position, Object object) {
                if(urlList.size() > 0) {
                    int i = position % urlList.size();
                    try {
//                        clearChildFocus(picList.get(i));
					view.removeView((View) object);
                    } catch (Exception e) {
                    }
                }

            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View view = View.inflate(activity, R.layout.layout_photo_banner, null);
                CustomRoundImageView image = view.findViewById(R.id.iv_thumb);
                ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
                int vp_width = DensityUtils.getWindowWidth(activity) - DensityUtils.dip2px(activity, 20);
                layoutParams.height = (int) (vp_width * ratio4);
                image.setLayoutParams(layoutParams);
                image.setRadius(8);
//                loadImage(urlList.get(position % urlList.size()), image);
                image.setImageResource(urlList.get(position % urlList.size()));
                container.addView(view);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclick != null) {
                            onclick.getView(position  %  urlList.size(), v);
                        }
                    }
                });
                return view;
            }
        });

        index_viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(final int position) {
                // TODO Auto-generated method stub
                for (Button ele : dotList) {
                    setUnSelect(ele);
                    ele.setSelected(false);
                }
                Button button = dotList.get(position % dotList.size());

                setSelect(button);
                button.setSelected(true);
                mCurPager = position;
                if(onPageSelected != null) {
                    onPageSelected.onPageSelected(position);
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        if (_listViews.size() > 1) {
            startTimer();
        }
    }

    // 停止滚动
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // 开始滚动
    public void startTimer() {
        stopTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        index_viewPage.setCurrentItem(index_viewPage
                                .getCurrentItem() + 1);
                    }
                });
            }
        }, 2000, 5000);
    }

    public void setMyClick(OnMyclick click) {
        onclick = click;
    }

    public interface OnMyclick {
        public void getView(int position, Object obj);
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
    }

    public void setSelect(Button button) {
        LinearLayout.LayoutParams selectParams = new LinearLayout.LayoutParams(
                DensityUtils.dip2px(activity,13), DensityUtils.dip2px(activity,6));

        selectParams.setMargins(0, 0, DensityUtils.dip2px(activity,8), 0);
        button.setLayoutParams(selectParams);
    }

    public void setUnSelect(Button button) {
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                DensityUtils.dip2px(activity,6), DensityUtils.dip2px(activity,6));
        itemParams.setMargins(0, 0, DensityUtils.dip2px(activity,8), 0);
        button.setLayoutParams(itemParams);
    }

    public void setTagText(String tagText) {

        this.mTagText = tagText;
    }

    List<String> mTagList = new ArrayList<>();
    public void setTagText(List<String> tagList) {
        this.mTagList = tagList;
    }

    public void setOnPageSelected(OnPageSelected pageSelected) {
        onPageSelected = pageSelected;
    }
    OnPageSelected onPageSelected;
    public interface OnPageSelected {
        public void onPageSelected(int position);
    }


    private void loadImage(String url, ImageView imageView) {
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .placeholder(R.drawable.bg_img_defult).centerCrop()
                .error(R.drawable.bg_img_defult);
        Glide.with(activity)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
