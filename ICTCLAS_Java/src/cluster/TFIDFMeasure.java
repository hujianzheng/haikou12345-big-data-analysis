package cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import util.ICTLAS;

/**
 * 计算TF-IDF
 * 
 * @author WangWei
 * 
 */
public class TFIDFMeasure {

	private String[] _docs;
	private String[][] _ngramDoc;
	private int _numDocs = 0;
	private int _numTerms = 0;
	private Map<String, Integer> _terms;
	private int[][] _termFreq;
	private int[] _docFreq;
	private int[] _docLength;
	private double[][] _termWeight;

	/**
	 * 获得词表
	 * 
	 * @param docs
	 * @return
	 */
	public Map<String, Integer> GenerateTerms(String[] docs) {
		
		Map<String, Integer> wordList = new HashMap<String, Integer>();
		int number = 0;

		for (String docTemp : docs) {
			String convertedInput = docTemp.toLowerCase();
			String[] words = convertedInput.split("\\s+");
			for (String word : words) {
				if(StopWordsHandler.IsChStopword(word)||StopWordsHandler.IsEnStopword(word))
					continue;
				else{
					
					if (!wordList.containsKey(word)) {
						wordList.put(word, number);
						number++;
					}
				}
				
			}
		}
		return wordList;
	}

	public TFIDFMeasure(String[] documents) {
		System.out.println("TFIDFMeasure()");
		_docs = documents;
		_numDocs = documents.length;
		System.out.println("start Init()");
		Init();
	}

	private void Init() {
		System.out.println("generate terms……");
		_terms = GenerateTerms(_docs);
		//System.out.println(_terms);
		System.out.println("after generate ,terms.size()" + _terms.size());

		_numTerms = _terms.size();
		_termFreq = new int[_numTerms][];
		_termWeight = new double[_numTerms][];
		_docFreq = new int[_numTerms];
		_docLength = new int[_numDocs];
		for (int i = 0; i < _numTerms; i++) {
			_termWeight[i] = new double[_numDocs];
			_termFreq[i] = new int[_numDocs];

		}
		GenerateTermFrequency();
		GenerateTermWeight();

	}

	/**
	 * 生成短语在每个文档中的词频矩阵
	 * 
	 */
	private void GenerateTermFrequency() {

		for (int i = 0; i < _numDocs; i++) {
			String curDoc = _docs[i];
			Map<String, Integer> curDocfreq = GetDocWordFrequency(curDoc, i);
			Iterator<String> it = curDocfreq.keySet().iterator();
			while (it.hasNext()) {
				String temp = it.next();
				int wordFreq = curDocfreq.get(temp);
				int wordNo = _terms.get(temp);
				_termFreq[wordNo][i] = wordFreq;
				_docFreq[wordNo]++;

			}

		}
	}

	private void GenerateTermWeight() {
		for (int i = 0; i < _numTerms; i++) {
			for (int j = 0; j < _numDocs; j++)
				_termWeight[i][j] = ComputeTermWeight(i, j);
		}
	}

	/**
	 * 计算term短语在doc文档中的权重
	 * 
	 * @param term
	 * @param doc
	 * @return
	 */
	private double ComputeTermWeight(int term, int doc) {
		double tf = GetTermFrequency(term, doc);
		double idf = GetInverseDocumentFrequency(term);
		return tf * idf;
	}

	/**
	 * 获得term的IDF
	 * 
	 * @param term
	 * @return
	 */
	private double GetInverseDocumentFrequency(int term) {
		int df = _docFreq[term];
		return (double) Math.log(_numDocs / (double) df);
	}

	/**
	 * 计算term短语在doc文档中的词频
	 * 
	 * @param term
	 * @param doc
	 * @return
	 */
	private double GetTermFrequency(int term, int doc) {
		int freq = _termFreq[term][doc];
		int docLen = _docLength[doc];

		return ((double) freq / (double) docLen);
	}

	/**
	 * 得到文档向量
	 * 
	 * @param doc
	 * @return
	 */
	public double[] GetDocVector(int doc) {

		double[] w = new double[_numTerms];
		for (int i = 0; i < _numTerms; i++)
			w[i] = _termWeight[i][doc];
		
//		for(double temp : w){
//			System.out.println(temp);
//		}

		return w;
	}

	/**
	 * 就算文档i和文档j的相似度
	 * 
	 * @param doc_i
	 * @param doc_j
	 * @return
	 */
	public double GetSimilarity(int doc_i, int doc_j) {
		double[] vector1 = GetDocVector(doc_i);
		double[] vector2 = GetDocVector(doc_j);

		return utilVector.ComputeCosineSimilarity(vector1, vector2);

	}

	/**
	 * 计算文档中的词频
	 * 
	 * @param input
	 * @return
	 */
	private Map<String, Integer> GetDocWordFrequency(String input, int i) {
		String convertedInput = input.toLowerCase();
		Map<String, Integer> wordfreq = new HashMap<String, Integer>();
		
		String[] wordList = convertedInput.split("\\s+");
		//System.out.println(wordList);
		_docLength[i] = wordList.length;
		for (String word : wordList) {
			if(StopWordsHandler.IsChStopword(word)||StopWordsHandler.IsEnStopword(word))
				continue;
			else{
				if (wordfreq.get(word) == null)
					wordfreq.put(word, 1);
				else {
					int value = wordfreq.get(word) + 1;
					wordfreq.put(word, value);
				}
			}

		}

		return wordfreq;
	}
	/**
	 * 获得术语个数
	 * @return
	 */
	public int get_numTerms() {
		return _numTerms;
	}
}
