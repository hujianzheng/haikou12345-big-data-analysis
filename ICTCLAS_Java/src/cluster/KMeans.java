package cluster;

import java.util.HashSet;
import java.util.Random;



public class KMeans {
	
	private double[][] _originalData;
	private int _k;
	private ClusterInfo[] _clusters;
	private int _docsNum;
    ///����һ������������ʾ���ϵ㵽���ĵ�ľ���, ���С�_distanceCache[i][j]��ʾ��i�����ϵ㵽��j��Ⱥ�۶������ĵ�ľ��룻
    private double[][] _distanceCache;

    /// ����һ���������ڼ�¼�͸���ÿ�����ϵ������ĸ�Ⱥ����
    private int[] _clusterAssignments;
	private int _maxIter = 20;
	private int[] _nearestCluster;
	private Random random = new Random(1);
	public KMeans(double[][] data, int k){
		this._k = k;
		this._docsNum = data.length;
		this._originalData = data;
		_clusters = new ClusterInfo[k];
		_distanceCache = new double[_docsNum][k];
		_clusterAssignments = new int[_docsNum];
        _nearestCluster = new int[_docsNum];
		InitRandomPoint();
	}
	public void InitRandomPoint(){
		
		HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < _k; i++)
        {
            int temp = random.nextInt(_docsNum);
            
            while(set.contains(temp)){
            	temp = random.nextInt(_docsNum);
            }
            set.add(temp);
            _clusterAssignments[temp] = i; //��¼��temp���������ڵ�i������
            _clusters[i] = new ClusterInfo(temp,_originalData[temp]);
        }
    
		
	}
	public void runKMeans(){
		int iter = 0;
		while(iter < _maxIter){
			
			System.out.println("Iteration " + (iter++) + "...");
            System.out.println(_clusters.length);
            
            //1������ÿ����ݺ�ÿ���������ĵľ���
            for(int i = 0; i <_docsNum; i++){
            	
            	for (int j = 0; j < _k; j++)
                {
                    double dist = getDistance(_originalData[i], _clusters[j].Mean);
                    _distanceCache[i][j] = dist;
                }
            	
            }
            
            //2������ÿ��������ĸ��������
            for (int i = 0; i < _docsNum; i++)
            {
                _nearestCluster[i] = nearestCluster(i);
            }
            
            //3���Ƚ�ÿ��������ľ����Ƿ�����������ľ���
            //���ȫ��ȱ�ʾ���еĵ��Ѿ�����Ѿ����ˣ�ֱ�ӷ��أ�
            int k = 0;
            for (int i = 0; i < _docsNum; i++)
            {
                if (_nearestCluster[i] == _clusterAssignments[i])
                    k++;

            }
            if (k == _docsNum)
                break;
            
            
            //5��������Ҫ���µ������ϵ��Ⱥ����Ĺ�ϵ��������Ϻ������¿�ʼѭ����
            //��Ҫ�޸�ÿ������ĳ�Ա�ͱ�ʾĳ����������ĸ�����ı���
            for (int j = 0; j < _k; j++)
            {
                _clusters[j].memberIndex.clear();
            }
            for (int i = 0; i < _docsNum; i++)
            {
                _clusters[_nearestCluster[i]].memberIndex.add(i);
                _clusterAssignments[i] = _nearestCluster[i];
            }
            
            //6�����¼���ÿ������ľ�ֵ
            for (int i = 0; i < _k; i++)
            {
                _clusters[i].UpdateMean(_originalData);
            }
            double sumMeans = 0.0;
            for(int i = 0; i < _k; i++){
            	
            	double oldMean[] = _clusters[i].Mean;
            	_clusters[i].UpdateMean(_originalData);
            	double newMean[] = _clusters[i].Mean;
            	sumMeans += utilVector.getOsDistance(oldMean, newMean);
            	
            }
            System.out.println("sumMeans :"+sumMeans);
            //if(sumMeans < 0.0001)
            	//break;
            
		}
		
		
	}
	

	/**
	 * ����ĳ��������ĸ��������
	 * 
	 */
    public int nearestCluster(int ndx)
    {
        int nearest = -1;
        double min = Double.MAX_VALUE;
        for (int c = 0; c < _k; c++)
        {
            double d = _distanceCache[ndx][c];
            if (d < min)
            {
                min = d;
                nearest = c;
            }
      
        }

        return nearest;
    }
	public double getDistance(double[] vector1, double[] vector2){
		return 1 - utilVector.ComputeCosineSimilarity(vector1, vector2);
		
	}
	
	public ClusterInfo[] getCluter(){
		return _clusters;
	}
	 

}
