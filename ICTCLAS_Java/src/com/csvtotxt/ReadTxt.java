package com.csvtotxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.lingjoin.demo.NlpirMethod;

public class ReadTxt { 
	public void readUserDict(String argPath){
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  

            /* 读入TXT文件 */  
            String pathname = argPath; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
            File filename = new File(pathname); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename),"UTF-8"); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line = "";  
            line = br.readLine();  
            while (line != null) {  
            	//System.out.println(line);
            	NlpirMethod.NLPIR_AddUserWord(line);
                line = br.readLine(); // 一次读入一行数据  
                
            }  
            System.out.println("导入"+argPath+"成功");

            /* 写入Txt文件 */  
//            File writename = new File(".\\result\\en\\output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
//            writename.createNewFile(); // 创建新文件  
//            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
//            out.write("我会写入文件啦\r\n"); // \r\n即为换行  
//            out.flush(); // 把缓存区内容压入文件  
//            out.close(); // 最后记得关闭文件  

        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
	}
	public String readTXT(String argPath){
		String resultString = "";
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  

            /* 读入TXT文件 */  
            String pathname = argPath; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
            File filename = new File(pathname); // 要读取以上路径的input.txt文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename),"UTF-8"); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言 
            
            String line = "";  
            line = br.readLine();  
            while (line != null) {  
            	//System.out.println(line);
            	resultString=resultString+"\n"+line;
                line = br.readLine(); // 一次读入一行数据  
                
                //System.out.println(line);
            }  
            
            //System.out.println(resultString);
            

            /* 写入Txt文件 */  
//            File writename = new File(".\\result\\en\\output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件  
//            writename.createNewFile(); // 创建新文件  
//            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
//            out.write("我会写入文件啦\r\n"); // \r\n即为换行  
//            out.flush(); // 把缓存区内容压入文件  
//            out.close(); // 最后记得关闭文件  

        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return resultString;  
        
	}
} 
