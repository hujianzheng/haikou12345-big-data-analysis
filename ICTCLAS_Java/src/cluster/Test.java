package cluster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FileExcludeStopWord.FileExcludeStopWord;

import com.csvtotxt.CsvToTxt;
import com.csvtotxt.ReadTxt;
import com.csvtotxt.WriteTxt;
import com.lingjoin.demo.NlpirMethod;
import com.lingjoin.test.RunMethod;


//import com.csvtotxt;

//import util.ICTLAS;

public class Test {



	public static void main(String[] args) throws IOException {

		System.out.println("选择要进行处理的csv文件");
		String CSVpath = RunMethod.chooseDocument();
        //String CSVpath="D:\\挖掘热点测试数据 - 副本.txt";//选择处理的文件
		String worklistString="D:\\工单信息.txt";
        String participleTXT="D:\\分词.txt";//分词结果存放在这个文件
        String excludeStopWordTXT="D:\\去除停用词.txt";//去除停用词后存放在这个文件
        String kmenasResultTXT="D:\\聚类结果.txt";//Kmenns蕨类结果
        
        //分词
        
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
            
	    //文档去重
	 // 需要处理数据的文件位置
	    FileReader fileReader = new FileReader(CSVpath);   
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(CSVpath),"GBK"));
       // FileReader fileReader = new FileReader(new File("D:\\tt.txt"));
        //BufferedReader bufferedReader = new BufferedReader(fileReader);
        Map<String, String> map = new HashMap<String, String>();
        String readLine = null;
        int iline = 0;

        while ((readLine = bufferedReader.readLine()) != null) {
            // 每次读取一行数据，与 map 进行比较，如果该行数据 map 中没有，就保存到 map 集合中
            if (!map.containsValue(readLine)) {
                map.put("key" + iline, readLine);
                iline++;
            }
        }
        
        File wl=new File(worklistString);
        wl.delete();//删掉worklistString文件之前存放的数据
        String reString="";
        for (int j = 0; j < map.size(); j++) {
            //System.out.println(map.get("key" + j));
        	reString=reString+map.get("key" + j)+"\n";
            WriteTxt.writetxt(map.get("key" + j),worklistString);         
        }
	    //CsvToTxt rt =new CsvToTxt();
	    //String reString =rt.readTXT(CSVpath);
	    //System.out.println(reString);
	    //分词
	    String participle = NlpirMethod.NLPIR_ParagraphProcess(reString, 1);
	    System.out.println(participle);
	    
        /* 把分词结果写入Txt文件 */  
      File writename = new File(participleTXT); // 相对路径，如果没有则要建立一个新的文件  
      writename.createNewFile(); // 创建新文件  
      BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
      out.write(participle.replace("\n", "\r\n")); // \r\n即为换行  
      out.flush(); // 把缓存区内容压入文件  
      out.close(); // 最后记得关闭文件 
      
       //去除停用词
        FileExcludeStopWord.excludeStopWord1(participleTXT,excludeStopWordTXT);
        System.out.println("去除停用词成功");
        
        //把工单信息导入worklistString文档
        File writename1 = new File(worklistString); // 相对路径，如果没有则要建立一个新的文件  
        writename1.createNewFile(); // 创建新文件  
        BufferedWriter out1 = new BufferedWriter(new FileWriter(writename1));  
        out1.write(reString.replace("\n", "\r\n")); // \r\n即为换行  
        out1.flush(); // 把缓存区内容压入文件  
        out1.close(); // 最后记得关闭文件 
        
        String[] docsTXT = getInputDocs(worklistString);
        if (docsTXT.length < 1)
        {
            System.out.println("没有文档输入");
            System.in.read();
          //  System.exit(0);
            return;
        }
        
        
		/*聚类步骤*/
	     //1、获取文档输入
        String[] docs = getInputDocs(excludeStopWordTXT);
        
        if (docs.length < 1)
        {
            System.out.println("没有文档输入");
            System.in.read();
          //  System.exit(0);
            return;
        }
        /*else{
        	for(String s:docs){
        		System.out.println(s);
        	}
        }*/
		
        //2、初始化TFIDF测量器，用来生产每个文档的TFIDF权重
        TFIDFMeasure tf = new TFIDFMeasure(docs);
       // System.out.println(tf.get_numTerms());
        //System.out.println("tfidf值");

        int K = 15; //聚成8个聚类

        //3、生成k-means的输入数据，是一个联合数组，第一维表示文档个数，
        //第二维表示词表的个数
        double[][] data = new double[docs.length][];
        int docCount = docs.length; //文档个数
        System.out.println("文档数"+docCount);
        //int dimension = tf.get_numTerms();//所有词的数目
        for (int i = 0; i < docCount; i++)
        {
         
                data[i] = tf.GetDocVector(i); //获取第i个文档的TFIDF权重向量
                //System.out.println(data[i]);
        }
        
        
//      //首先计算文档TF-IDF向量，保存为Map<String,Map<String,Double>> 即为Map<文件名，Map<特征词，TF-IDF值>>
//      		ComputeWordsVector computeV = new ComputeWordsVector();
//      		Map<String,Map<String,Double>> allTestSampleMap = computeV.computeTFMultiIDF(testSampleDir);

        //4、初始化k-means算法，第一个参数表示输入数据，第二个参数表示要聚成几个类
        KMeans kmeans = new KMeans(data,K);
        //5、开始迭代
        kmeans.runKMeans();

        //6、获取聚类结果并输出
        ClusterInfo[] clusters = kmeans.getCluter();
        int kmeansmun=0;
        
        File f=new File(kmenasResultTXT);
        f.delete();//删掉kmenasResultTXT文件之前存放的数据
        WriteTxt.writetxt("------------------------------"+"总共"+docCount+"工单数"+"------------------------------",kmenasResultTXT);
        WriteTxt.writetxt("******************************"+"总共"+K+"个类"+"*************************************",kmenasResultTXT);
        for(ClusterInfo cluster : clusters){
        	kmeansmun++;
            List<Integer> members = cluster.memberIndex;
            System.out.println("---------"+"第"+kmeansmun+"类"+"---------");
            System.out.println(members);
            WriteTxt.writetxt("------------------------------"+"第"+kmeansmun+"类"+"------------------------------",kmenasResultTXT);
            WriteTxt.writetxt(members.toString(),kmenasResultTXT); 
            WriteTxt.writetxt("----------------------------------------------------------------",kmenasResultTXT);
            //WriteTxt.writetxt();
           for(Integer i : members){
        	   //System.out.println(docs[i]);
        	   //System.out.println("文档数"+i);
        	   //WriteTxt.writetxt(docs[i],kmenasResultTXT);
        	   WriteTxt.writetxt(docsTXT[i], kmenasResultTXT);//i+1是因为生产的工单信息  第一行是空的
        	   //WriteTxt.writetxt("----------------------------------------------------------------");
        	   WriteTxt.writetxt("文档数"+i,kmenasResultTXT);
           }
        }

    
		
	}
    /**
     * 文档输入
     *    
     */
    private static String[] getInputDocs(String file)
    {
        List<String> ret = new ArrayList<String>();
        
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            {
                String temp;
                while ((temp = br.readLine()) != null)
                {
                    ret.add(temp);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        String[] fileString=new String[ret.size()];
        return (String[]) ret.toArray(fileString);
    }
}



