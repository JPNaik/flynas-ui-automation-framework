package com.flynas.utils;


import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class APIUtils {
    public static final Logger log = LogManager.getLogger(APIUtils.class);

    public static void saveResponseToFile(Response response,String fileName) throws IOException {
        String jsonString = response.then().extract().asPrettyString();

        File targetFolder = new File(System.getProperty("user.dir")+File.separator+"target"+File.separator+"api-responses"+File.separator);
        if(!targetFolder.exists()){
            targetFolder.mkdirs();
        }
        Files.write(Path.of(targetFolder.getPath()+File.separator+fileName),jsonString.getBytes());
        log.info("flight search response is saved "+targetFolder.getPath()+File.separator+fileName);
    }
}
