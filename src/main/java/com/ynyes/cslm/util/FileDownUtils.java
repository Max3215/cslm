package com.ynyes.cslm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FileDownUtils {
	
	 /**
		 * @author lc
		 * @注释：文件写入和下载
		 */
	    public static Boolean download(String name,HSSFWorkbook wb, String exportUrl, HttpServletResponse resp){
	    	 try  
	         {  
		          FileOutputStream fout = new FileOutputStream(exportUrl+name+".xls");  
		          OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");	                       	     
		          wb.write(fout);  
		          fout.close();
	         }catch (Exception e)  
	         {  
	             e.printStackTrace();  
	         } 
	    	 OutputStream os;
			 try {
					os = resp.getOutputStream();
					File file = new File(exportUrl +name+ ".xls");
	                 
	             if (file.exists())
	                 {
	                   try {
	                         resp.reset();
	                         resp.setHeader("Content-Disposition", "attachment; filename="
	                                 +name+ ".xls");
	                         resp.setContentType("application/octet-stream; charset=utf-8");
	                         os.write(FileUtils.readFileToByteArray(file));
	                         os.flush();
	                     } finally {
	                         if (os != null) {
	                             os.close();
	                         }
	                     }
	             }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			 }
			 return true;	
	    }

}
