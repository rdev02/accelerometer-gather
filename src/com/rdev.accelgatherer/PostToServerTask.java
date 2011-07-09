package com.rdev.accelgatherer;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: revenge
 * Date: 7/10/11
 * Time: 12:55 AM
 */
public class PostToServerTask extends AsyncTask<Collection<float[]>, Void, Void> {
    private static final String urlToPostToStr = "http://localhost:8080/testReceiver/testGet?";
    private static URL urlToPostTo;

    static{
        try{
            urlToPostTo = new URL(urlToPostToStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Collection<float[]>... collections) {
        for(int i = 0; i< collections.length; i++){
            processListAndPostToUrl(collections[i]);
        }
        return null;
    }

    private void processListAndPostToUrl(Collection<float[]> list) {
        for(float[] data : list){
            //do some calc here
        }
        //post to http here
        String
        System.out.println("Posted " + list.size() + " items to " + urlToPostToStr);

    }
}
