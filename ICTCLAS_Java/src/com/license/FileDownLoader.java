package com.license;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownLoader {
 /**
  * 
  * 在jsp页面调用fileDown(request,response) url传参：dirPath(文件的全路径),fileName(文件名)
  * 
  * @return boolean
  */
	public static void saveUrlAs(String Url, String fileName){
        //此方法只能用HTTP协议
        //保存文件到本地
        //Url是文件下载地址,fileName 为一个全名(路径+文件名)文件
        URL url;
        DataOutputStream out = null;
        DataInputStream in = null;
        try {
            url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(new FileOutputStream(fileName));
            byte[] buffer = new byte[4096];
            int count = 0;
                 while ((count = in.read(buffer)) > 0) {
                     out.write(buffer, 0, count);
                 }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}
