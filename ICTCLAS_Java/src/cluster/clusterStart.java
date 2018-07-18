package cluster;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.csvtotxt.WriteTxt;

public  class clusterStart
    {
	
       public static void main(String[] args) throws IOException
        {
            //1、获取文档输入
            String[] docs = getInputDocs("D:\\dd.txt");
            
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
            
            //加载停用词表
            StopWordsHandler.loadStopWordFile();
            //2、初始化TFIDF测量器，用来生产每个文档的TFIDF权重
            TFIDFMeasure tf = new TFIDFMeasure(docs);
            System.out.println(tf.get_numTerms());
            System.out.println("tfidf值");

            int K = 8; //聚成4个聚类

            //3、生成k-means的输入数据，是一个联合数组，第一维表示文档个数，
            //第二维表示词表的个数
            double[][] data = new double[docs.length][];
            int docCount = docs.length; //文档个数
            int dimension = tf.get_numTerms();//所有词的数目
            for (int i = 0; i < docCount; i++)
            {
             
                    data[i] = tf.GetDocVector(i); //获取第i个文档的TFIDF权重向量
            
            }

            //4、初始化k-means算法，第一个参数表示输入数据，第二个参数表示要聚成几个类
            KMeans kmeans = new KMeans(data,K);
            //5、开始迭代
            kmeans.runKMeans();

            //6、获取聚类结果并输出
            ClusterInfo[] clusters = kmeans.getCluter();
            for(ClusterInfo cluster : clusters){

                List<Integer> members = cluster.memberIndex;
                System.out.print(members);
                System.out.println("-----------------");
                WriteTxt.writetxt(members.toString());
                WriteTxt.writetxt();
               for(Integer i : members){
            	   //System.out.println(docs[i]);
            	   //System.out.println("文档数"+i);
            	   WriteTxt.writetxt(docs[i]);
            	   WriteTxt.writetxt("文档数"+i);
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
