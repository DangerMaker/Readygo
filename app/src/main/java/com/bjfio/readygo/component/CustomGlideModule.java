package com.bjfio.readygo.component;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by Administrator on 2016/12/2.
 */
public class CustomGlideModule implements GlideModule {

    int yourSizeInBytes = 1024 * 1024 * 250; //250M
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        builder.setDiskCache(new DiskCache.Factory() {
//            @Override
//            public DiskCache build() {
//                File cacheLocation = getMyCacheLocationBlockingIO();
//                cacheLocation.mkdirs();
//
//                return DiskLruCacheWrapper.get(cacheLocation, yourSizeInBytes);
//            }
//        });
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,"glide_cache",yourSizeInBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }


    public File getMyCacheLocationBlockingIO() {
        return new File(Environment.getExternalStorageDirectory() + File.separator + "glide_cache");
    }
}
