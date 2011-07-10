package com.rdev.accelgatherer;

import android.os.AsyncTask;
import com.rdev.accelgatherer.data.SensorEvent;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: revenge
 * Date: 7/10/11
 * Time: 12:55 AM
 */
public class PostToServerTask extends AsyncTask<Collection<SensorEvent>, Void, Void> {
    private static final String urlToPostToStr = "http://172.24.222.223:8080/testReceiver/testGet?";
    private static URL urlToPostTo;

    static {
        try {
            urlToPostTo = new URL(urlToPostToStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Collection<SensorEvent>... collections) {
        for (int i = 0; i < collections.length; i++) {
            processListAndPostToUrl(collections[i]);
        }
        return null;
    }

    private void processListAndPostToUrl(Collection<SensorEvent> list) {
        for (SensorEvent data : list) {
            //do some calc here
        }
        //post to http here
        String resultPost = urlToPostTo + "datasize=" + String.valueOf(list.size());

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(resultPost);

        try {
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("response.toString() = " + response.getStatusLine());
            System.out.println("Posted " + list.size() + " items to " + urlToPostToStr);

        } catch (ClientProtocolException e) {
            System.err.println("Exception:" + e.getMessage());
        } catch (IOException e) {
            System.err.println("Exception:" + e.getMessage());
        }




    }
}
