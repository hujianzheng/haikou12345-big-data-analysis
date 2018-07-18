package FileExcludeStopWord;

import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileReader; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.io.PrintWriter;
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.Comparator; 
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 

//import ICTCLAS.I3S.AC.ICTCLAS50; 
/** 
* 分词去停用词后词频统计（设定阈值，去掉低频值） 
* @author Administrator 
* 
*/ 
public class FileExcludeStopWord { 

	static String[] stopWords=new String[1599];//停用词个数 
	public static void loadStop() throws IOException { 
	//ArrayList stopwords=new ArrayList();//存放词语 
	        BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(".\\dict\\userfilterword.txt"),"utf-8")); 
	        String word=null; 
	        int count=0; 
	        word=fr.readLine(); 
	        while(word!=null){ 
	        //stopwords.add(word); 
	        stopWords[count]=word; 
	        count++; 
	        word=fr.readLine(); 
	        }//省去每次加载停用词 
	} 
	public static String removeAll(String str){//去除停用等，同时去除词性标注 
		String RAll=""; 
		String[] allWords = str.split(" "); 
		for(int i=0;i<allWords.length;i++){ 
			int pos = allWords[i].lastIndexOf("/"); 
			String temp=""; 
			if(pos>0) 
			temp=allWords[i].substring(0,pos).trim(); 
			if(temp.equals(" ")||temp.equals("　")||temp.equals("")); 
			else 
			RAll=RAll+temp+" "; 
		} 
		return RAll; 
	} 
	public static String removeW(String str){//去除标点符号 
		String removeW="";	
		String[] allWords = str.split(" "); 
		for(int k=0;k<allWords.length;k++){ 
		        int pos = allWords[k].lastIndexOf("/"); 
		        if(pos>0){ 
		            String temp2=allWords[k].substring(pos+1,pos+2);//词性标注 
			        if(temp2.equals("w")||temp2.equals(""));//w标点  或者为空 
			        else 
			        removeW=removeW+allWords[k]+" "; 
		        } 
		} 
		return removeW;	
	} 
	public static String removeStop(String str){//去除停用等，但是保留词性标注 
		String afterStop=""; 
		boolean flag=true;	
		String[] allWords = str.split(" "); 
		for(int k=0;k<allWords.length;k++){ 
		        int pos = allWords[k].lastIndexOf("/"); 
		        int n; 
		        if(pos>0){ 
		            String temp1=allWords[k].substring(0, pos);//中文不包括词性标注 
		            String temp2=allWords[k].substring(pos+1,pos+2);//词性标注 
		            flag=true; 
			        if(temp2.equals("　")||temp2.equals("t")||temp1.equals("")||temp1.equals(" ")||temp2.equals("s")||temp2.equals("z") 
			        ||temp2.equals("d")||temp2.equals("p")||temp2.equals("c")||temp2.equals("u")||temp2.equals("e")||temp2.equals("y")
			        ||temp2.equals("o")||temp2.equals("h")||temp2.equals("k")||temp2.equals("m")||temp2.equals("x")||temp2.equals("q")
			        ||temp2.equals("r")){ 
			        //t时间词，s处所词，z状态词，d副词，p介词，c连词，u助词，e叹词，y语气词，o拟声词，h前缀，k后缀，m数词,q量词，x英语等字符串 
			        flag=false; 
				    }else 
				    for(n=0;n<stopWords.length;n++){         
					    if(temp1.equals(stopWords[n])){//去除停用词 
						    flag=false; 
						    break; 
					    } 
				    } 
			    if(flag) 
			        afterStop=afterStop+allWords[k]+" "; 
		     } 
		} 
		return afterStop;	
	} 
//public static void testICTCLAS_FileProcess(String inDirectory,String OutDirectory) { 
//	try 
//	{ 
//	ICTCLAS50 testICTCLAS50 = new ICTCLAS50(); 
//	//分词所需库的路径 
//	String argu = "."; 
//	//初始化 
//	if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("gb2312")) == false) 
//	{ 
//	System.out.println("Init Fail!"); 
//	return; 
//	} 
//	testICTCLAS50.ICTCLAS_SetPOSmap(2);	
//	//	String OutDirectory="D:\\trian\\"; 
//	//	        String inDirectory="D:\\articals\\";	
//	//	        File dirOut = new File(OutDirectory); 
//	//	        File fileOut[] = dirOut.listFiles(); 
//	//	        for (int i = 0; i < fileOut.length; i++) {//先删除所有输出目录中的文件 
//	//	        if(fileOut[i].isFile()) 
//	//	        fileOut[i].delete(); 
//	//	        System.out.println("删除了"+fileOut[i].getName()); 
//	//	        } 
//	        String usrdir = "userdict.txt"; //用户字典路径 
//	byte[] usrdirb = usrdir.getBytes();//将string转化为byte类型 
//	//第一个参数为用户字典路径，第二个参数为用户字典的编码类型(0:type unknown;1:ASCII码;2:GB2312,GBK,GB10380;3:UTF-8;4:BIG5) 
//	int nCount = testICTCLAS50.ICTCLAS_ImportUserDictFile(usrdirb, 2);//导入用户字典,返回导入用户词语个数	
//	System.out.println(nCount+"个自定义词…………"); 
//	        File dirIn= new File(inDirectory); 
//	        File fileIn[] = dirIn.listFiles(); 
//	        for (int i = 0; i < fileIn.length; i++) { 
//	        String Inputfilename=fileIn[i].getPath(); 
//	byte[] Inputfilenameb = Inputfilename.getBytes();//将文件名string类型转为byte类型 
//	//分词处理后输出文件名 
//	String Outputfilename =OutDirectory+fileIn[i].getName(); 
//	byte[] Outputfilenameb = Outputfilename.getBytes();//将文件名string类型转为byte类型	
//	//文件分词(第一个参数为输入文件的名,第二个参数为文件编码类型,第三个参数为是否标记词性集1 yes,0 no,第四个参数为输出文件名) 
//	if(testICTCLAS50.ICTCLAS_FileProcess(Inputfilenameb, 0, 1, Outputfilenameb)==false){ 
//	System.out.println(fileIn[i].getPath()+"没有分词…………"); 
//	} 
//	else 
//	System.out.println(fileIn[i].getPath()+"分词成功，这是第"+i+"个文档"); 
//	        } 
//	      //保存用户字典 
//	testICTCLAS50.ICTCLAS_SaveTheUsrDic(); 
//	//释放分词组件资源 
//	testICTCLAS50.ICTCLAS_Exit(); 
//	} 
//	catch (Exception ex) 
//	{ 
//	} 
//} 

	public static HashMap<String, Integer> every(String str){ 
		String out=""; 
		HashMap<String, Integer> wordmap= new HashMap<String, Integer>(); 
		String[] words=str.split(" "); 
		int count=words.length; 
		System.out.println(count); 
		String[] strTongji=new String[count];//词 
		int[] strTimes=new int[count];//词频 
		for(int k=0;k<count;k++){//初始化 
			strTimes[k]=0; 
			strTongji[k]=""; 
		} 
		for(int i=0;i<count;i++){// 
		
			if(words[i].equals("")||words[i].equals(" ")||words[i].equals("　")); 
			else{ 
				for(int j=0;j<count;j++){//存储着最终的统计词	
					if(strTongji[j].equals("")){//如果最终统计词表为空则添加进去 
						strTongji[j]=words[i]; 
						//System.out.println(words[i]); 
						strTimes[j]++; 
						break; 
					}else { 
						if(words[i].equals(strTongji[j])){//终统计词表中存在这个表里就词频数加1 
						strTimes[j]++; 
						break; 
						} 
					} 
				}
			} 
		} 
		for(int n=0;n<count;n++){ 
			if(!strTongji[n].equals("")&&strTimes[n]!=0) wordmap.put(strTongji[n],strTimes[n]); 
			else 
			break; 
		} 
		return wordmap; 
	} 

//	public static void compute(String InDirectory,String OutDirectory) throws IOException{ 
//		loadStop(); 
//		BufferedWriter bw = null; 
//		File dirIn= new File(InDirectory); 
//		        File fileIn[] = dirIn.listFiles(); 
//		        for(int i=0;i<fileIn.length;i++){ 
//			        bw = new BufferedWriter(new FileWriter(new File(OutDirectory+fileIn[i].getName())));//文件名称 
//			        String str=""; 
//			        BufferedReader reader = new BufferedReader(new FileReader(InDirectory+fileIn[i].getName()));//读取页数大于1的文件内容        
//			        String line = null; 
//			        line=reader.readLine(); 
//			        while (line != null) { 
//				        line=removeW(line); //去除标点符号
//				        line=removeStop(line);//去除停用等，但是保留词性标注  
//				        String temp=removeAll(line);//去除停用等，同时去除词性标注  
//				        str=str+" "+temp; 
//				        line=reader.readLine();  			        	
//			        } 
//			        reader.close();
//			        List<Map.Entry<String, Integer>> sortedlist = new ArrayList<Map.Entry<String,Integer>>(every(str.trim()).entrySet()); 
//			    
//			        Collections.sort(sortedlist , new Comparator<Map.Entry<String, Integer>>() {   
//				        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
//					        if((o2.getValue() - o1.getValue()) > 0){ 
//					        return 1; 
//					        } 
//					        else 
//					        return -1; 
//				        } 
//			        }); 
//			
//				    for (int j = 0; j < sortedlist.size(); j++) { 
//					    Map.Entry entry = sortedlist.get(j); 
//					    //根据词频取词，大于2的词 
//					    if((Integer)entry.getValue()>2) 
//					    bw.write(entry.getKey().toString() + "  " + entry.getValue().toString()+"\r\n"); 
//				    } 
//				        bw.close(); 
//		        } 
//	} 
	
	//对某个字符串进行去除停用词  不能分段
	public static void excludeStopWord2(String InDirectory,String OutDirectory) throws IOException{ 
		loadStop(); 
		BufferedWriter bw = null; 
//		File dirIn= new File(InDirectory); 
//		        File fileIn[] = dirIn.listFiles(); 
		         
			       // bw = new BufferedWriter(new FileWriter(new File(OutDirectory+fileIn[i].getName())));//文件名称 
			        String str=""; 
			        //BufferedReader reader = BufferedReader(InDirectory);//读取页数大于1的文件内容     
			        
			       // String line = InDirectory; 
			        //line=reader.readLine(); 
			       // while (line != null) { 
			        InDirectory=removeW(InDirectory); //去除标点符号
			        InDirectory=removeStop(InDirectory);//去除停用等，但是保留词性标注  
				    InDirectory=removeAll(InDirectory);//去除停用等，同时去除词性标注  
//				    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(OutDirectory)));  
//		            writer.write(InDirectory.replace("\n", "\r\n"));//注意windows系统在保存成txt时要将文本中的"\n"修改为"\r\n"才可以实现换行  
//		            writer.close();
				    //str=str+" "+temp; 
				      //  line=reader.readLine();  			        	
			        //} 
				   // InDirectory=InDirectory.replace("\n", "\r\n");
			        PrintWriter pfp= new PrintWriter(new File(OutDirectory));
	      	        pfp.println(InDirectory);
	      	        pfp.close();
				  
			        //reader.close();
			        
				       // bw.close(); 
		        
	}
	

	//对某个文档进行去除停用词
	public static void excludeStopWord1(String InDirectory,String OutDirectory) throws IOException{ 
		loadStop(); 
		BufferedWriter bw = null; 
//		File dirIn= new File(InDirectory); 
//		        File fileIn[] = dirIn.listFiles(); 
					PrintWriter pfp= new PrintWriter(new File(OutDirectory));
			       // bw = new BufferedWriter(new FileWriter(new File(OutDirectory+fileIn[i].getName())));//文件名称 
			       // String str=""; 
			        BufferedReader reader = new BufferedReader(new FileReader(InDirectory));//读取页数大于1的文件内容        
			        String line = null; 
			        line=reader.readLine(); 
			        String temp="";
			        while (line != null) { 
				        line=removeW(line); //去除标点符号
				        line=removeStop(line);//去除停用等，但是保留词性标注  
				        temp=removeAll(line);//去除停用等，同时去除词性标注  
				        //str=str+" "+temp;
				        pfp.println(temp);
				        line=reader.readLine();  			        	
			        } 
			        
	      	        pfp.close();
			        reader.close();
			        
				       // bw.close(); 
		        
	}
	
	
	//对多个文档去除停用词
	public static void excludeStopWord(String InDirectory,String OutDirectory) throws IOException{ 
		loadStop(); 
		BufferedWriter bw = null; 
		File dirIn= new File(InDirectory); 
		        File fileIn[] = dirIn.listFiles(); 
		        for(int i=0;i<fileIn.length;i++){ 
			       // bw = new BufferedWriter(new FileWriter(new File(OutDirectory+fileIn[i].getName())));//文件名称 
			        String str=""; 
			        BufferedReader reader = new BufferedReader(new FileReader(InDirectory+fileIn[i].getName()));//读取页数大于1的文件内容        
			        String line = null; 
			        line=reader.readLine(); 
			        while (line != null) { 
				        line=removeW(line); //去除标点符号
				        line=removeStop(line);//去除停用等，但是保留词性标注  
				        String temp=removeAll(line);//去除停用等，同时去除词性标注  
				        str=str+" "+temp; 
				        line=reader.readLine();  			        	
			        } 
			        PrintWriter pfp= new PrintWriter(new File(OutDirectory+fileIn[i].getName()));
	      	        pfp.println(str);
	      	        pfp.close();
			        reader.close();
			        
				       // bw.close(); 
		        } 
	}
//	public static void main(String[] args) throws IOException { 
//	
//		//testICTCLAS_FileProcess("E:语料库路径\\","D:\\分词后生成路径\\");//将文档分词 
//		test("D:\\测试分词\\"); 
//	
//	} 

} 