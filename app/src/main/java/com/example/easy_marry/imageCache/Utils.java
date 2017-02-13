package com.example.easy_marry.imageCache;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ${Rajaji} on 29-04-2016.
 */

public class Utils {

    public static void CopyStream(InputStream is, OutputStream os)

    {

        final int buffer_size = 1024;

        try

        {

            byte[] bytes = new byte[buffer_size];

            for (; ; )

            {

                int count = is.read(bytes, 0, buffer_size);

                if (count == -1)

                    break;

                os.write(bytes, 0, count);

            }

        } catch (Exception ex) {
        }

    }


}