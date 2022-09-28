package com.runner.testworks.controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TestFile {
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = {Exception.class})
    public void addOneIcon(Integer spaceID, int width, int hight, Double size, MultipartFile iconFile) {

        String fileName = width+"_"+hight+System.currentTimeMillis ()+iconFile.getOriginalFilename ().substring (iconFile.getOriginalFilename ().lastIndexOf ("."));

        File file = new File (""+File.separatorChar+fileName);

        try {
            iconFile.transferTo (file);
        } catch (IOException e) {
            e.printStackTrace ();
        }

        String iconurl =  ""+File.separatorChar+fileName;

    }
}
