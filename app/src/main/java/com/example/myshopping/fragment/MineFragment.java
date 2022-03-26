package com.example.myshopping.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.R;
import com.example.myshopping.adapter.RecyclerCombineAdapter;
import com.example.myshopping.adapter.RecyclerOtherAdapter;
import com.example.myshopping.bean.NewsInfo;
import com.example.myshopping.util.RoundImageView;

import java.io.InputStream;

public class MineFragment extends Fragment {
    private View view;
    private ImageView iv_store;
    private RoundImageView riv_avatar;
    private Context context;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        riv_avatar = view.findViewById(R.id.riv_avatar);
        @SuppressLint("ResourceType")
        InputStream stream = getResources().openRawResource(R.drawable.phone01);
        BitmapDrawable drawable = new BitmapDrawable(stream);
        drawable.setTileModeXY(Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        Bitmap bitmap = drawable.getBitmap();
        riv_avatar.setImageBitmap(bitmap);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initOther();
    }

    private void initOther() {
        RecyclerView rv_mine_other = view.findViewById(R.id.rv_mine_other);
        GridLayoutManager manager = new GridLayoutManager(context,4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_mine_other.setLayoutManager(manager);
        RecyclerOtherAdapter adapter=new RecyclerOtherAdapter(context,NewsInfo.getDefaultGrid());
        rv_mine_other.setAdapter(adapter);
    }
}
