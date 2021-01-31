package com.ivan.boldyrev;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;

public class Fragment2 extends Fragment {
    public Fragment2(){

    }
    public int postNum = 0;
    public String tab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tab = getString(R.string.tab_hot);
        Request request = new Request();
        request.initialRequest(this, tab);
    }

    public void reloadView(){
        final CircularProgressDrawable progressDrawable = new CircularProgressDrawable(getActivity());
        progressDrawable.setStrokeWidth(8f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        String uri = getArguments().getString("uri");
        String description = getArguments().getString("description");
        ImageView imageView1 = getView().findViewById(R.id.image_view2);
        TextView textView1 = getView().findViewById(R.id.text_view2);
        Glide.with(this)
                .asGif()
                .placeholder(progressDrawable)
                .load(uri)
                .into(imageView1);
        textView1.setText(description);
    }
}
