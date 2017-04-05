package com.bjfio.readygo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.bjfio.readygo.R;
import com.bjfio.readygo.model.net.MyException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/1/10.
 */
public class FileUtil {
    public static String TOAST_PERMISSION_AGREE = "傻逼呀，点同意";
    public static String TOAST_GETFILE_ERROR = "获取妹妹图片失败";
    public static Observable<Uri> getPathByUrl(final Activity context, final String url) {
        return new RxPermissions(context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).
                flatMap(new Func1<Boolean, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(Boolean aBoolean) {
                        if (aBoolean) {
                            Bitmap bitmap = null;
//                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.umeng_socialize_qq);
                            FutureTarget<File> future = Glide.with(context)
                                    .load(url)
                                    .downloadOnly(382, 574);
                            try {
                                File cacheFile = future.get();
                                String path = cacheFile.getAbsolutePath();
                                bitmap = BitmapFactory.decodeFile(path);
                            } catch (Exception e) {
                                MyException exception = new MyException(TOAST_GETFILE_ERROR);
                                return Observable.error(exception);
                            }

                            return RxUtil.createData(bitmap);
                        } else {
                            MyException exception = new MyException(TOAST_PERMISSION_AGREE);
                            return Observable.error(exception);
                        }
                    }
                })
                // TODO: 2017/2/22 分离出来
                .flatMap(new Func1<Bitmap, Observable<Uri>>() {
                    @Override
                    public Observable<Uri> call(Bitmap bitmap) {
                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));
                        return RxUtil.createData(uri);
                    }
                });
    }


    public static Observable<Bitmap> getBitmapByUrl(final Activity context, final String url) {
        return new RxPermissions(context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).
                flatMap(new Func1<Boolean, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(Boolean aBoolean) {
                        if (aBoolean) {
                            Bitmap bitmap = null;
//                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.umeng_socialize_qq);
                            FutureTarget<File> future = Glide.with(context)
                                    .load(url)
                                    .downloadOnly(382, 574);
                            try {
                                File cacheFile = future.get();
                                String path = cacheFile.getAbsolutePath();
                                bitmap = BitmapFactory.decodeFile(path);
                            } catch (Exception e) {
                                MyException exception = new MyException(TOAST_GETFILE_ERROR);
                                return Observable.error(exception);
                            }

                            return RxUtil.createData(bitmap);
                        } else {
                            MyException exception = new MyException(TOAST_PERMISSION_AGREE);
                            return Observable.error(exception);
                        }
                    }
                });
    }

    public static Uri getImageContentUri(Context context, String absPath) {

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Images.Media._ID}
                , MediaStore.Images.Media.DATA + "=? "
                , new String[]{absPath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));

        } else if (!absPath.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, absPath);
            return context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return null;
        }
    }
}
