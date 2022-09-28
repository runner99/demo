package com.runner;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TestFile {
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackFor = {Exception.class})
    public void addOneIcon(Integer spaceID, int width, int hight, Double size, MultipartFile iconFile) {
        SpringUtil.getBean(SpaceIconService.class).deleteIconBySpaceIDAndSize(spaceID,size);

        String baseDir = ServerConfiguration.getSpaceIconRootPath();

        String fileName = width+"_"+hight+System.currentTimeMillis ()+iconFile.getOriginalFilename ().substring (iconFile.getOriginalFilename ().lastIndexOf ("."));

        File file = new File (baseDir+File.separatorChar+fileName);

        try {
            iconFile.transferTo (file);
        } catch (IOException e) {
            e.printStackTrace ();
            throw new BaseException(StatusCode.FILE_RECEIVE_FAILED);
        }

        String baseURL =  ServerConfiguration.getSpaceIconRootURL();

        String iconurl =  baseURL+File.separatorChar+fileName;

        Spaceicon spaceicon = new Spaceicon();
        spaceicon.setSpaceid(spaceID);
        spaceicon.setIcon(iconurl);
        spaceicon.setWidth(width);
        spaceicon.setHeight(hight);
        spaceicon.setSize(size);
        spaceicon.setTime(new Date());
        SpringUtil.getBean(SpaceIconService.class).addIcon(spaceicon);
    }
}
