package com.lxsj.sdk.giftlist.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import com.lxsj.sdk.giftlist.views.AnimationDrawableImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by TP on 16/5/23.
 */
public class ImageHelper
{
    public static String dirPath = Environment.getExternalStorageDirectory().getPath() + "/testPic/tp/";
    private AnimationDrawableImageView.DownLoadHandler handler;
    public ImageHelper()
    {
    }
    public ImageHelper(AnimationDrawableImageView.DownLoadHandler handler)
    {
        this.handler = handler;
    }

    public static String getKeyFromUrl(String url) {
        String fileName = "";
        if (url.startsWith("http://") || url.startsWith("https://")) {
            int start = 0;
            if (url.startsWith("http://"))
                start = "http://".length();
            else if (url.startsWith("https://"))
                start = "https://".length();
            fileName = url.substring(start, url.length());
        }
        fileName = fileName.replace("/","");
        return fileName;
    }
    public String getNetBitmap(final String url)
    {
        final String imageFileName = getKeyFromUrl(url);
        Thread td = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Bitmap bitmap = null;
                    InputStream in = null;
                    BufferedOutputStream out = null;
                    Log.d("ImageHelper", "getNetBitmap: " + url);
                    in = new BufferedInputStream(new URL(url).openStream());
                    final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                    out = new BufferedOutputStream(dataStream);
                    copy(in, out);
                    out.flush();
                    byte[] data = dataStream.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Log.d("111", "getNetBitmap: " + imageFileName);
                    String filePath = saveFile(bitmap, imageFileName);
                    bitmap.recycle();
                    bitmap = null;
                    data = null;
                    Message message = new Message();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("filePath", filePath);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        td.start();
        return imageFileName;
    }
    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[8192];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
    private static String saveFile(Bitmap bm, String fileName) throws IOException
    {
        File dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        Log.d("ImageHelper", "saveFile: " + fileName);
        String filePath = dirPath + fileName;
        File myCaptureFile = new File(filePath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
        return filePath;
    }

}
