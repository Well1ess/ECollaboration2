package com.example.a29149.ecollaboration.httprequest.asynctask.login;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.a29149.ecollaboration.util.ImageService;
import com.example.a29149.ecollaboration.util.GlobalUtil;


/**
 * Created by Administrator on 2016/12/18 0018.
 */

public class TransForPicture extends AsyncTask<String, Integer, byte[]> {


    public TransForPicture() {
        super();
    }

    @Override
    protected byte[] doInBackground(String... params) {
        try {
            byte[] data = ImageService.getImage(params[0]);
            return data;

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(byte[] result) {
        super.onPostExecute(result);
        if (result != null) {
            GlobalUtil.getInstance().setmHead(BitmapFactory.decodeByteArray(result, 0, result.length));
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
