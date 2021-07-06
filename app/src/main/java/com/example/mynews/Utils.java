package com.example.mynews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class Utils {

    public static String LOG_TAG = Utils.class.getName();
    public static Bitmap img;

    //We want a private constructor since no one should ever
    //want or need a object instance
    private Utils(){

    }

    /**
     * This method takes a String url as a parameter and
     * turns it into an URL object.
     **/
    public static URL createUrl(String stringURL){
        //Create a new URL object
        URL url = null;
        //Try creating a url from a string
        //Possible errors with malformed urls
        try{
        url = new URL(stringURL);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method connects our client to the server.
     * Sends a GET request to recieve the data.
     * Reads the request and returns it as a String.
     **/
    public static String makeHttpRequest(URL url) throws IOException {
        //Create a new string where we will later store the json response from the server
        String jsonResponse = "";
        //Create a new HttpsURLConnection object for connection
        HttpsURLConnection connection = null;
        //Create a inputstream variable
        InputStream stream = null;

        //First check if the url is not null and exit the method early.
        if(url == null){
            return null;
        }

        //Start communicating with the server inside the try - catch block.
        //This enables us to catch any exceptions that can occur when trying to connect
        try{
            //First we need to open the connection to the server
            connection = (HttpsURLConnection) url.openConnection();
            //Second we need to specify our request
            connection.setRequestMethod("GET");
            //Third we need to wait a little bit just so we dont overwhelm the server
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            //Lastly connect to the server
            connection.connect();

            //Check the response from the server
            int responseCode = connection.getResponseCode();
            //If the response is positive continue
            if(responseCode == 200){
                //Get the data in bytes from the response
                stream = connection.getInputStream();
                //Translate the the data from bytes with a custom method readFromStream
                jsonResponse = readFromStream(stream);
            } else {
                Log.e(LOG_TAG, "Error with response code " + responseCode);
            }

        }catch (IOException e){
            e.printStackTrace();
        } finally {
            //After the communication with the server is done we need to close the connection
            if(connection != null){
                connection.disconnect();
            }
            //Also close the stream so you dont use any resources
            if(stream != null){
                stream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * This method reads the data from bytes and turns it into
     * String.
     **/

    public static String readFromStream(InputStream s) throws IOException{
        //The stringbuilder class makes it possible to change a certain String
        //It is mutable and we can do whatever we want with it.
        StringBuilder stringBuilder = new StringBuilder();

        //InputStreamReader class turns the byte data from the InputStream
        //into a character set data.
        InputStreamReader reader = new InputStreamReader(s);
        //BufferedReader turns the caracther set data and reads it as a text
        BufferedReader buffer = new BufferedReader(reader);

        //The readLine method enables us to read each line of text until it hits a line breaker (/n)
        //or EOF
        String line = buffer.readLine();
        while(line != null){
            //We append the new text we just read to the stringbuilder
            stringBuilder.append(line);
            line = buffer.readLine();
        }
        return stringBuilder.toString();
    }

    public static ArrayList<Story> extractJsonData(String jsonResponse){
        //Initiate a new arraylist of stories
        ArrayList<Story> stories = new ArrayList<Story>();

        Story story = null;

        //Within a try - catch block we parse the JSON data
        try{

            //Create a new JSONObject which is the root of our response
            JSONObject root = new JSONObject(jsonResponse);
            Log.i(LOG_TAG, "This is root JSON response " + root);

            JSONObject rootObject = root.getJSONObject("response");

            //Navigate the root to find the object where our data lies
            //optJSONArray - returns the array with the associated name
            Log.i(LOG_TAG ,"TEST");
            JSONArray results = rootObject.getJSONArray("results");
            Log.i(LOG_TAG, "This is rootArray response " + results);

            //Navigate the array and get the results
            for(int i = 0; i < results.length(); i++){

                //Get the current result story in the array
                JSONObject currentResult = results.getJSONObject(i);

                //Now we need to parse through the data and get what we want

                //Get the article title
                String title = currentResult.getString("webTitle");

                //Get the article date
                String date = currentResult.getString("webPublicationDate");
                //Format the date from Zulu time to yyyy-mm-dd format
                String formmatedDate = "Published on " + formatDate(date);
                Log.i(LOG_TAG, "Format date " + formmatedDate);

                //Get the fields JSONObject that contains the thumbnail image
                JSONObject thumbnail = currentResult.getJSONObject("fields");
                //Get the thumbnail url
                String image = thumbnail.getString("thumbnail");

                //The custom method that turns url to image bitmap
                Bitmap imageBitmap = turnIntoBitmap(image);

                //Get the url of the article
                String storyUrl = currentResult.getString("webUrl");
                Log.i(LOG_TAG, "Date " + date);
                story = new Story(storyUrl, imageBitmap, title, formmatedDate);

                stories.add(story);
            }

        } catch (JSONException e){
                Log.e(LOG_TAG, "Issues with parsing the JSON");
        }
        return stories;
    }

    /**
     * This method performs a network request and turns an imageURL into a bitmap resource
     **/
    public static Bitmap turnIntoBitmap(String imgUrl){
        URL url = createUrl(imgUrl);
        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
                HttpsURLConnection connect = null;
                try {
                    //Open the connection to the server
                    connect = (HttpsURLConnection) url.openConnection();
                    //We intend to use the connection for an input (the img resource)
                    connect.setDoInput(true);
                    //Connect to the server
                    connect.connect();
                    //Open inputstream and get the data in bytes
                    InputStream s = connect.getInputStream();
                    //Decode the stream so it returns a bitmap image
                    img = BitmapFactory.decodeStream(s);
                    //After everything close the stream
                    s.close();
                } catch (IOException e){
                    e.printStackTrace();
                } finally {
                    //Clear the resources
                    if(connect != null){
                        connect.disconnect();
                    }
                }
                /* }
        }).start();*/
        return img;
    }

    /**
     * Combining all the methods before with this method we perform every function.
     **/
    public static ArrayList<Story> fetchStoryData(String urlString){
        URL url = createUrl(urlString);
        String jsonresponse = null;
        ArrayList<Story> store = null;
        try {
            jsonresponse = makeHttpRequest(url);

        } catch (IOException e){
            e.printStackTrace();
        }
        store = extractJsonData(jsonresponse);
        return store;
    }

    private static String formatDate(String date){
        String dateString = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try{
            Date dateObject = dateFormat.parse(date);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            dateString = formatter.format(dateObject);

        } catch (ParseException e){
            e.printStackTrace();
        }

        return dateString;
    }
}
