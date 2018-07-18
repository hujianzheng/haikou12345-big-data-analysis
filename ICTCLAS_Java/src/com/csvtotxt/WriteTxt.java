package com.csvtotxt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.lingjoin.test.RunMethod;

public class WriteTxt {
	public static void writetxt(String a) {
		 /*写入文件中*/
        FileWriter fw = null;
        try {
        //如果文件存在，则追加内容；如果文件不存在，则创建文件
        File f=new File("D:\\聚类结果.txt");
        fw = new FileWriter(f, true);
        } catch (IOException e) {
        e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(a);
        pw.flush();
        try {
        fw.flush();
        pw.close();
        fw.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
	}
	
	public static void writetxt(String a,String path) {
		 /*写入文件中*/
       FileWriter fw = null;
       try {
       //如果文件存在，则追加内容；如果文件不存在，则创建文件
       File f=new File(path);
       fw = new FileWriter(f, true);
       } catch (IOException e) {
       e.printStackTrace();
       }
       PrintWriter pw = new PrintWriter(fw);
       pw.println(a);
       pw.flush();
       try {
       fw.flush();
       pw.close();
       fw.close();
       } catch (IOException e) {
       e.printStackTrace();
       }
	}
	
	public static void writetxt() {
		 /*写入文件中*/
       FileWriter fw = null;
       try {
       //如果文件存在，则追加内容；如果文件不存在，则创建文件
    	   //System.out.println("11111");
       String path = RunMethod.chooseDocument();
       File f=new File(path);
       fw = new FileWriter(f, true);
       } catch (IOException e) {
       e.printStackTrace();
       }
       PrintWriter pw = new PrintWriter(fw);
       pw.println("------------------------------------------------------");
       pw.flush();
       try {
       fw.flush();
       pw.close();
       fw.close();
       } catch (IOException e) {
       e.printStackTrace();
       }
	}
	public static void writetxtpath() {
		 /*写入文件中*/
	       FileWriter fw = null;
	       try {
	       //如果文件存在，则追加内容；如果文件不存在，则创建文件
	       File f=new File("D:\\ee.txt");
	       fw = new FileWriter(f, true);
	       } catch (IOException e) {
	       e.printStackTrace();
	       }
	       PrintWriter pw = new PrintWriter(fw);
	       //pw.println("------------------------------------------------------");
	       pw.flush();
	       try {
	       fw.flush();
	       pw.close();
	       fw.close();
	       } catch (IOException e) {
	       e.printStackTrace();
	       }
	}

}
