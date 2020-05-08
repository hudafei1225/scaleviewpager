package com.hu.scaleviewpager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyPhotoRoundBannerScroll bannerScroll = findViewById(R.id.banner_scroll);

        final List<Integer> list = new ArrayList<>();
//        list.add("http://b-ssl.duitang.com/uploads/item/201707/16/20170716231054_WH85Q.png");
//        list.add("http://b-ssl.duitang.com/uploads/item/201610/30/20161030173537_hiFSs.jpeg");
//        list.add("http://b-ssl.duitang.com/uploads/item/201707/16/20170716231054_WH85Q.png");
//        list.add("http://b-ssl.duitang.com/uploads/item/201610/30/20161030173537_hiFSs.jpeg");
        list.add(R.drawable.default_bg);
        list.add(R.drawable.default_bg);
        list.add(R.drawable.default_bg);
        list.add(R.drawable.default_bg);
            double ratio = 7.0 / 16;
            bannerScroll.initUI(this, list, ratio);
            bannerScroll.setMyClick(new MyPhotoRoundBannerScroll.OnMyclick() {
                @Override
                public void getView(int position, Object obj) {
                    if(position == list.size()) {
                        position = position - 1;
                    }
                    Toast.makeText(MainActivity.this, "点击位置 : "+position, Toast.LENGTH_SHORT).show();
                }
            });
    }
}
