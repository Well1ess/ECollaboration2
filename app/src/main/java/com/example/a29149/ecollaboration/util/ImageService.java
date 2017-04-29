package com.example.a29149.ecollaboration.util;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageService {

    public static byte[] getImage(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");   //设置请求方法为GET
        conn.setReadTimeout(5*1000);    //设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据
        byte[] data = StreamTool.readInputStream(inputStream);     //获得图片的二进制数据
        return data;
    }

    /**
     * 图像转字节
     * @param bm
     * @return
     */public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}