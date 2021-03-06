package com.ynyes.cslm.util;

import java.util.Properties;

/**
 * 后台常用常量
 * @author Sharon
 *
 */
public class SiteMagConstant {

    public static final int pageSize = 20;
    
    public static final String templatePath = "src/main/resources/templates/client/";
    
    public static final String apkPath ;
    public static final String backupPath;
    public static final String imagePath ;
    
    static{
		Properties props = System.getProperties();
		String operation = props.getProperty("os.name");
		if(operation.contains("Linux")){
			backupPath = "/mnt/root/backup/";
			imagePath = "/mnt/root/images/goods";
			apkPath = "/mnt/root/max/";
		}else{
			backupPath = "src/main/resources/backup/";
			imagePath = "src/main/resources/static/images";
			apkPath = "src/main/resources/apk/";
		}
    }

    
//    public static final String backupPath = "src/main/resources/backup/";
//    public static final String imagePath = "src/main/resources/static/images";
    
//    public static final String backupPath = "/root/backup/";
//    public static final String imagePath = "/root/images/goods";
    
}