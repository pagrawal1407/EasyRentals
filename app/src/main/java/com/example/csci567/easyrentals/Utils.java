package com.example.csci567.easyrentals;

/**
 * Created by Parag on 10/26/2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        /*try {
            JSONObject jsonObject = new JSONObject(is.toString());
            String image = jsonObject.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}