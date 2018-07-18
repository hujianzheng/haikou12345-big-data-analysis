package cluster;

import java.util.ArrayList;
import java.util.List;

public class ClusterInfo {
	
	/// 该聚类的数据成员索引
	List<Integer> memberIndex = new ArrayList<Integer>();
	/// 该聚类的中心
	double[] Mean;
	
	public ClusterInfo(int dataindex,double[] data)
    {
		memberIndex.add(dataindex);
         Mean = data;
    }
	/**
	 * 更新聚类中心
	 * 
	 * @param coordinates  所有文档的向量集合
	 */
	 public void UpdateMean(double[][] coordinates)
     {
         // 根据 memberIndex 取得原始资料点对象 coord ，该对象是 coordinates 的一个子集；
         //然后取出该子集的均值；取均值的算法很简单，可以把 coordinates 想象成一个 m*n 的距阵 ,
         //每个均值就是每个纵向列的取和平均值 , //该值保存在 mCenter 中

         for (int i = 0; i < memberIndex.size(); i++)
         {
             double[] coord = coordinates[memberIndex.get(i)];
             for (int j = 0; j < coord.length; j++)
             {
                 Mean[j] += coord[j]; // 得到每个纵向列的和；
             }
             
            
         }
         for (int k = 0; k < Mean.length; k++)
         {
             Mean[k] /= memberIndex.size(); // 对每个纵向列取平均值
         }
     }
}
