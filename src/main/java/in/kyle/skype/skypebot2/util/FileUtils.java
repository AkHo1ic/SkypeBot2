package in.kyle.skype.skypebot2.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kyle on 11/28/2015.
 */
public class FileUtils {
    
    public static void copy(String resourceName, File targetFile) throws IOException {
        InputStream inputStream = FileUtils.class.getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new FileNotFoundException("Could not find resource " + resourceName);
        }
        FileOutputStream fos = new FileOutputStream(targetFile);
        IOUtils.copy(inputStream, fos);
        fos.close();
        inputStream.close();
    }
}
