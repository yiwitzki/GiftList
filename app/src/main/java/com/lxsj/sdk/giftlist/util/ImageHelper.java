package com.lxsj.sdk.giftlist.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

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
    public static String getKeyFromUrl(String url) {
        String fileName = "";
        if (url.startsWith("http://") || url.startsWith("https://")) {
            int start = 0;
            if (url.startsWith("http://"))
                start = "http://".length();
            else if (url.startsWith("https://"))
                start = "https://".length();
            fileName = url.substring(start, url.length() - 1);
        }
        fileName = fileName.replace("/","");
        return fileName;
    }
    public static String getLocalOrNetBitmap(String url)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        String imageFileName = getKeyFromUrl(url);
        try
        {
            in = new BufferedInputStream(new URL(url).openStream());
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            saveFile(bitmap, imageFileName);
            data = null;
            return imageFileName;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[8192];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
    private static void saveFile(Bitmap bm, String fileName) throws IOException
    {
        String dirPath = Environment.getExternalStorageDirectory().getPath() + "/testPic/tp/";
        File dirFile = new File(dirPath);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(dirPath + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
}
