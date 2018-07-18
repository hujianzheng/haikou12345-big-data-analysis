package com.lingjoin.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import FileExcludeStopWord.FileExcludeStopWord;

import com.algorithm.TFIDF;
import com.csvtotxt.ReadTxt;
import com.lingjoin.demo.NlpirLib;
import com.lingjoin.demo.NlpirMethod;
import com.traverseFolder.TraverseFolder;

public class Main {

	public static void main(String[] args) throws IOException {
		//选择csv文件目录为path
		System.out.println("选择要进行处理的csv文件");
		String path = RunMethod.chooseDocument();
		
      // 读取csv文件到list.ToString 
		System.out.println("选择生成txt文档的目录");
		String txtStringpath = RunMethod.chooseFolder("选择生成txt文档的目录");
        RunMethod.CsvtoTxt(path,txtStringpath); 
       
      
		// TODO Auto-generated method stub
      //分词
      //NLPIR_Init(String sDataPath, int encoding, String sLicenceCode);
      //NlpirMethod.NLPIR_ImportUserDict调用用户词典根本不成功 只有调用user_dict.txt才成功 其他都失败
        // NlpirMethod.NLPIR_ImportUserDict("dict/user_dict.txt", true);//导入用户词典
//         NlpirMethod.NLPIR_ImportUserDict("dict/black_dict", true);//导入用户词典
//         NlpirMethod.NLPIR_ImportUserDict("dict/海口市城市信息精选.txt", true);//导入用户词典
//         NlpirMethod.NLPIR_ImportUserDict("dict/海口市公交站名.txt", true);//导入用户词典
//         NlpirMethod.NLPIR_ImportUserDict("dict/海口市行政区划地名.txt", true);//导入用户词典
//         NlpirMethod.NLPIR_ImportUserDict("dict/海口市信息大全.txt", true);//导入用户词典
//        System.out.println(f1);
//         NlpirMethod.NLPIR_SaveTheUsrDic();
         //设置黑名单无效
     // NlpirMethod.NLPIR_ImportKeyBlackList("dict/black_dict.txt");
      
      /*新建一个类，读取txt并且添加用户词典，然后在这里调用方法*/
        //NlpirMethod.NLPIR_AddUserWord("白坡里");
        //搜狗输入法词库
        System.out.println("导入用户词典：");
	    ReadTxt readtxt =new ReadTxt();
	    readtxt.readUserDict("dict/海口地名街道名.txt");
	    readtxt.readUserDict("dict/海口市城市信息精选.txt");
	    readtxt.readUserDict("dict/海口市公交站名.txt");
	    readtxt.readUserDict("dict/海口市行政区划地名.txt");
	    readtxt.readUserDict("dict/海口市信息大全.txt");
	    //qq输入法词库
	    readtxt.readUserDict("dict/QQ城市信息（海口）.txt");
	    readtxt.readUserDict("dict/QQ海口地名.txt");
	    //百度输入法词库
	    readtxt.readUserDict("dict/百度海口地名街道名.txt");
	    //自定义词典
	    readtxt.readUserDict("dict/user_dict.txt");
	   
	    //调用com。traverseFolder包中的traverseFolder类 得出文件夹中的txt数量
        TraverseFolder traverse =new TraverseFolder();
        int fileNum=traverse.traverseFolder1(txtStringpath);
        System.out.println("选择分词的目录：");
        String participlePath = RunMethod.chooseFolder("选择分词的目录：");
        //分词
        for(int k = 0;k<fileNum;k++){
        	
			double flag = NlpirMethod.NLPIR_FileProcess(txtStringpath+k+".txt", participlePath+k+".txt", 1);
			//System.out.println(flag);
        }
        
        //去除停用词  停用表\\Data\\userfilterword.txt 去除标点符号  /t时间词，s处所词，z状态词，d副词，p介词，c连词，u助词，e叹词，y语气词，o拟声词，h前缀，k后缀，m数词,q量词，x英语等字符串 
        System.out.println("选择去除停用词后的目录：");
        String excludeStopWordPath = RunMethod.chooseFolder("选择去除停用词后的目录：");
        FileExcludeStopWord.excludeStopWord(participlePath,excludeStopWordPath);
        System.out.println("去除停用词成功");
        
        //调用TFIDF算法
        System.out.println("选择特征值选取后的目录：");
        String tfidfPath = RunMethod.chooseFolder("选择特征值选取后的目录：");    
        TFIDF tfidf = new TFIDF();
        HashMap<String,HashMap<String, Float>> all_tf = tfidf.tfAllFiles(excludeStopWordPath);
        HashMap<String, Float> idfs = tfidf.idf(all_tf);
        tfidf.tf_idf(all_tf, idfs,tfidfPath);
        
        
		//各种选择
		NlpirTest t = new NlpirTest();
		Scanner sc = new Scanner(System.in);
		int i = 1;
		while(i!=0){
			System.out.println("输入0为退出；输入1为测试文本分词；2为测试文本路径分词；3为测试细粒度分词；4测试文本关键词；5测试文本路径关键词；"
					+ "6测试文本新词；7测试文本路径新词；8测试添加用户自定义词；9测试删除用户自定义词； 10测试导入用户词典；11测试文本词频；12测试文本路径词频");
			i = sc.nextInt();
		switch(i){
		case 0: 
			NlpirLib.Instance.NLPIR_Exit();
			System.out.println("退出");
		break;
			case 1: 
				System.out.println("测试文本分词:");
				t.testParagraphProcess();
			break; 

			case 2: 
				System.out.println("测试文本路径分词；");
				t.testFileProcess();

			break;  

			case 3: 
				System.out.println("测试细粒度分词");
				t.testFinerSegment();
			break; 
			
			case 4: 
				System.out.println("测试文本关键词");
				t.testKeyWords();
			break; 
			
			case 5: 
				System.out.println("测试文本路径关键词");
				t.testFileKeyWords();
			break; 
			
			case 6: 
				System.out.println("测试文本新词");
				t.testNewWords();
			break; 
			
			case 7: 
				System.out.println("测试文本路径新词");
				t.testFileNewWords();
			break; 
			
			case 8: 
				System.out.println("测试添加用户自定义词");
				t.testAddUserWord();
			break; 
			
			case 9: 
				System.out.println("测试删除用户自定义词");
				t.testDelUsrWord();
			break; 
			
			case 10: 
				System.out.println("测试导入用户词典");
				t.testImportUserDict();
			break; 
			
			case 11: 
				System.out.println("测试文本词频");
				t.testWordFreq();
			break; 
			
			case 12: 
				System.out.println("测试文本路径词频");
				t.testFileWordFreq();
			break; 
			
			default: 

			System.out.println("default"); 

			break;
		}
		}
	}

}
