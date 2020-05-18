package com.example.laboratory.manager;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laboratory.R;
import com.example.laboratory.application.AppContext;
import com.example.laboratory.common.Const;



public class ImageLoaderManager {
    private static RequestOptions nomal_image_options = RequestOptions
            .placeholderOf(R.drawable.ic_img_default)
            .error(R.drawable.ic_img_default)
            .centerCrop();
    private static RequestOptions head_options = RequestOptions
            .placeholderOf(R.mipmap.ic_launcher_round)
            .centerCrop();

    public static void displayImage(Object resource,ImageView imageView,int type) {

        switch (type) {
            case Const.IMAGE_LOADER.HEAD_IMG:
                loadImg(resource,head_options,imageView);
                break;
            case Const.IMAGE_LOADER.NOMAL_IMG:
               loadImg(resource,nomal_image_options,imageView);
                break;
            default:
                break;
        }

    }


    private static void loadImg(Object resource, RequestOptions options, ImageView imageView){
        Glide.with(AppContext.getContext()).load(resource).apply(options).into(imageView);
    }

}
