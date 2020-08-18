package com.toolkit.comm.util.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:To change this template use File | Settings | File Templates.
 */
public class FileUtil {




    public static List<File> ls(String strPath, boolean recursive) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files == null)
            return null;
        List<File> fileList = new ArrayList<File>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                if (recursive) {
                    ls(files[i].getAbsolutePath(), recursive);
                }
            } else {
                String fullFileName = files[i].getAbsolutePath().toLowerCase();
                logger.debug(fullFileName);
                fileList.add(files[i]);
            }
        }
        return fileList;
    }
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
}
