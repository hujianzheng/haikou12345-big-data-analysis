package com.lingjoin.test;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.csvtotxt.CsvToTxt;

public class RunMethod {
	
	 
	 public static String chooseFolder(String aString){
		 
		 JFileChooser fc = new JFileChooser();
		 FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
		 //System.out.println(fsv.getHomeDirectory()); 
		 int flag = 0 ;
		 
		  fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
		  fc.setCurrentDirectory(fsv.getHomeDirectory());
		  fc.setDialogTitle(aString);
		  String path=null;
		  File f=null;
		  try{     
		            flag = fc.showOpenDialog(null);     
		        }    
		        catch(HeadlessException head){     
		             System.out.println("Open File Dialog ERROR!");    
		        }        
		        if(flag==JFileChooser.APPROVE_OPTION){
		             //获得该文件    
		            f=fc.getSelectedFile();    
		            path=f.getPath();
		         }    
		 
		  //以上获得选择的文件夹
		  //若要判断其中是否还有其他目录，可以这样做
		  boolean hasSubDir=false;
		  File dir = new File(path);
		  //获得改目录下的文件的文件名，如果没有的话，filesName.length()=0
		  
		  String[] filesName= dir.list();
		  for(int i=0;i<filesName.length;i++){
		      File temp=new File(path+"/"+filesName[i]);
		      if(temp.isDirectory()){
		          hasSubDir=true;
		          break;
		      }
		 
		  }
		  System.out.println("输出的文件夹："+path);
		  return path+"/";
	 }
	 
	public static String chooseDocument(){	
		//选择csv文件目录为path
		int result = 0;
		File file = null;
		String path = null;
		JFileChooser fileChooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
		System.out.println(fsv.getHomeDirectory());                //得到桌面路径
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
		fileChooser.setDialogTitle("请选择要处理的文件...");
		fileChooser.setApproveButtonText("确定");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		result = fileChooser.showOpenDialog(fileChooser);
		if (JFileChooser.APPROVE_OPTION == result) {
		    	   path=fileChooser.getSelectedFile().getPath();
		    	   System.out.println("选择文件path: "+path);
		}
		return path;
	}
	
	public static void CsvtoTxt(String path,String outpath){		
		try { 
			
	  	    CsvToTxt csvtotxt = new CsvToTxt();
	  	    //调用com。csvtotxt包中的CSVToTxt类中的readCsvFile（）方法  把txt文件存到指定目录（绝对路径）
	        List<List<String>> list = csvtotxt.readCsvFile(path,outpath);   
	        // System.out.println(list.toString());
	        System.out.println(path+"  生成txt文档成功");
	        
	    } catch (FileNotFoundException ex) {   
	        Logger.getLogger(CsvToTxt.class.getName()).log(Level.SEVERE, null, ex);   
	    } catch (IOException ex) {   
	        Logger.getLogger(CsvToTxt.class.getName()).log(Level.SEVERE, null, ex);   
	    }
			
	}
	
	

}
