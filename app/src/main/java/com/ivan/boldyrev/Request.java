package com.ivan.boldyrev;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Request {
    public void initialRequest(Fragment fragment, String tab){
        String baseURL = "https://developerslife.ru";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        int page = 0;

        Call<Result> listCall = api.getPost(tab, page);

        listCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(fragment.getActivity().getApplicationContext(), "Response code:" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                String uri = "";
                Result posts = response.body();

                switch (tab) {
                    case "hot":
                        try {
                            uri = posts.getPosts().get(0).getGifURL();
                            ImageView imageView = fragment.getView().findViewById(R.id.image_view2);
                            Glide.with(fragment.getActivity()).asGif().load(uri).into(imageView);
                        } catch (IndexOutOfBoundsException e) {
                            ImageView imageView = fragment.getView().findViewById(R.id.image_view2);
                            imageView.setImageResource(fragment.getResources().getIdentifier("ic_notfound", "drawable", fragment.getActivity().getPackageName()));
                        }
                        break;
                    case "latest":
                        try {
                            uri = posts.getPosts().get(0).getGifURL();
                            ImageView imageView = fragment.getView().findViewById(R.id.image_view3);
                            Glide.with(fragment.getActivity()).asGif().load(uri).into(imageView);
                        } catch (IndexOutOfBoundsException e) {
                            ImageView imageView = fragment.getView().findViewById(R.id.image_view3);
                            imageView.setImageResource(fragment.getResources().getIdentifier("ic_notfound", "drawable", fragment.getActivity().getPackageName()));
                        }
                        break;
                    default:
                        try {
                            uri = posts.getPosts().get(0).getGifURL();
                            ImageView imageView = fragment.getView().findViewById(R.id.image_view1);
                            Glide.with(fragment.getActivity()).asGif().load(uri).into(imageView);
                        } catch (IndexOutOfBoundsException e) {
                            ImageView imageView = fragment.getView().findViewById(R.id.image_view1);
                            imageView.setImageResource(fragment.getResources().getIdentifier("ic_notfound", "drawable", fragment.getActivity().getPackageName()));
                        }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(fragment.getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestBack(MainActivity activity, ViewPager2 pager, HashMap<Integer, List<Result>> results, Button buttonBack) {
        ViewAdapter adapter = (ViewAdapter) pager.getAdapter();
        int currentFragment = pager.getCurrentItem();
        Bundle bundle = new Bundle();
        int page;

        String uri;
        Result posts;
        switch (currentFragment) {
            case 1:
                Fragment2 fragment2 = (Fragment2) adapter.hashMap.get(currentFragment);
                try {
                    page = (fragment2.postNum - 1) / 5;
                    posts = results.get(currentFragment).get(page);
                    uri = posts.getPosts().get(fragment2.postNum - page * 5 - 1).getGifURL();
                    bundle.putString("uri", uri);
                    fragment2.setArguments(bundle);
                    fragment2.reloadView();
                }
                catch (IndexOutOfBoundsException e) {
                    ImageView imageView = activity.findViewById(R.id.image_view2);
                    imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                }
                fragment2.postNum -= 1;
                break;
            case 2:
                Fragment3 fragment3 = (Fragment3) adapter.hashMap.get(currentFragment);
                try {
                    page = (fragment3.postNum - 1) / 5;
                    posts = results.get(currentFragment).get(page);
                    uri = posts.getPosts().get(fragment3.postNum - page * 5 - 1).getGifURL();
                    bundle.putString("uri", uri);
                    fragment3.setArguments(bundle);
                    fragment3.reloadView();
                } catch (IndexOutOfBoundsException e) {
                    ImageView imageView = activity.findViewById(R.id.image_view3);
                    imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                }
                fragment3.postNum -= 1;
                break;

            default:
                Fragment1 fragment1 = (Fragment1) adapter.hashMap.get(currentFragment);
                try {
                    page = (fragment1.postNum - 1) / 5;
                    posts = results.get(currentFragment).get(page);
                    uri = posts.getPosts().get(fragment1.postNum - page * 5 - 1).getGifURL();
                    bundle.putString("uri", uri);
                    fragment1.setArguments(bundle);
                    fragment1.reloadView();
                } catch (IndexOutOfBoundsException e) {
                    ImageView imageView = activity.findViewById(R.id.image_view1);
                    imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                }
                fragment1.postNum -= 1;
        }
        ButtonState state = new ButtonState();
        state.changeButtonState(buttonBack, pager, currentFragment);
    }

    public HashMap<Integer, List<Result>> request(MainActivity activity, ViewPager2 pager, HashMap<Integer, List<Result>> results, Button buttonBack, Button buttonNext) {
        String baseURL = "https://developerslife.ru";
        ViewAdapter adapter = (ViewAdapter) pager.getAdapter();
        int currentFragment = pager.getCurrentItem();
        Bundle bundle = new Bundle();
        ButtonState state = new ButtonState();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);

        String tab;
        int page;
        switch (currentFragment) {
            case 1:
                Fragment2 fragment2 = (Fragment2) adapter.hashMap.get(currentFragment);
                tab = activity.getString(R.string.tab_hot);
                page = (fragment2.postNum + 1) / 5;
                break;
            case 2:
                Fragment3 fragment3 = (Fragment3) adapter.hashMap.get(currentFragment);
                tab = activity.getString(R.string.tab_latest);
                page = (fragment3.postNum + 1) / 5;
                break;
            default:
                Fragment1 fragment1 = (Fragment1) adapter.hashMap.get(currentFragment);
                tab = activity.getString(R.string.tab_top);
                page = (fragment1.postNum + 1) / 5;
        }

        if (results.get(currentFragment).size() - 1 >= page) {
            buttonNext.setClickable(false);

            String uri;
            Result posts = results.get(currentFragment).get(page);
                switch (currentFragment) {
                    case 1:
                        Fragment2 fragment2 = (Fragment2) adapter.hashMap.get(currentFragment);
                        try {
                            uri = posts.getPosts().get(fragment2.postNum - page * 5 + 1).getGifURL();
                            bundle.putString("uri", uri);
                            fragment2.setArguments(bundle);
                            fragment2.reloadView();
                            fragment2.postNum += 1;
                        } catch (IndexOutOfBoundsException e) {
                            ImageView imageView = activity.findViewById(R.id.image_view2);
                            imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                        }
                        break;
                    case 2:
                        Fragment3 fragment3 = (Fragment3) adapter.hashMap.get(currentFragment);
                        try {
                            uri = posts.getPosts().get(fragment3.postNum - page * 5 + 1).getGifURL();
                            bundle.putString("uri", uri);
                            fragment3.setArguments(bundle);
                            fragment3.reloadView();
                            fragment3.postNum += 1;
                        } catch (IndexOutOfBoundsException e) {
                            ImageView imageView = activity.findViewById(R.id.image_view3);
                            imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                        }
                        break;
                    default:
                        Fragment1 fragment1 = (Fragment1) adapter.hashMap.get(currentFragment);
                        try {
                            uri = posts.getPosts().get(fragment1.postNum - page * 5 + 1).getGifURL();
                            bundle.putString("uri", uri);
                            fragment1.setArguments(bundle);
                            fragment1.reloadView();
                            fragment1.postNum += 1;
                        } catch (IndexOutOfBoundsException e) {
                            ImageView imageView = activity.findViewById(R.id.image_view1);
                            imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                        }
                }
                state.changeButtonState(buttonBack, pager, currentFragment);
                buttonNext.setClickable(true);
            }
        else {
            buttonNext.setClickable(false);

            Call<Result> listCall = api.getPost(tab, page);

            listCall.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(activity.getApplicationContext(), "Response code:" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String uri = "";
                    Result posts = response.body();
                    results.get(currentFragment).add(posts);

                    switch (currentFragment) {
                        case 1:
                            Fragment2 fragment2 = (Fragment2) adapter.hashMap.get(currentFragment);
                            try {
                                uri = posts.getPosts().get(fragment2.postNum - page * 5 + 1).getGifURL();
                                bundle.putString("uri", uri);
                                fragment2.setArguments(bundle);
                                fragment2.reloadView();
                                fragment2.postNum += 1;
                            } catch (IndexOutOfBoundsException e) {
                                ImageView imageView = activity.findViewById(R.id.image_view2);
                                imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                            }
                            break;
                        case 2:
                            Fragment3 fragment3 = (Fragment3) adapter.hashMap.get(currentFragment);
                            try {
                                uri = posts.getPosts().get(fragment3.postNum - page * 5 + 1).getGifURL();
                                bundle.putString("uri", uri);
                                fragment3.setArguments(bundle);
                                fragment3.reloadView();
                                fragment3.postNum += 1;
                            } catch (IndexOutOfBoundsException e) {
                                ImageView imageView = activity.findViewById(R.id.image_view3);
                                imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                            }
                            break;
                        default:
                            Fragment1 fragment1 = (Fragment1) adapter.hashMap.get(currentFragment);
                            try {
                                uri = posts.getPosts().get(fragment1.postNum - page * 5 + 1).getGifURL();
                                bundle.putString("uri", uri);
                                fragment1.setArguments(bundle);
                                fragment1.reloadView();
                                fragment1.postNum += 1;
                            } catch (IndexOutOfBoundsException e) {
                                ImageView imageView = activity.findViewById(R.id.image_view1);
                                imageView.setImageResource(activity.getResources().getIdentifier("ic_notfound", "drawable", activity.getPackageName()));
                            }
                    }
                    state.changeButtonState(buttonBack, pager, currentFragment);
                    buttonNext.setClickable(true);
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(activity.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    state.changeButtonState(buttonBack, pager, currentFragment);
                    buttonNext.setClickable(true);
                }
            });
        }
        return results;
    }
}
