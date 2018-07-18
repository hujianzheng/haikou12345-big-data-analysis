package cluster;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;





/**
 * ȥ��ͣ�ô�
 * @author WangWei
 *
 */
public class StopWordsHandler {
	private static HashMap<String,Integer> _Chstopwords = new HashMap<String,Integer>();
	private static HashMap<String,Integer> _Enstopwords = new HashMap<String,Integer>();

	public static void ReadChStopWordFile(String chpath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(chpath));
		String line = null;
		while ((line = br.readLine()) != null) {
			_Chstopwords.put(line, 1);
		}

	}

	public static void ReadEnStopWordFile(String enpath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(enpath));
		String line = null;
		while ((line = br.readLine()) != null) {
			_Enstopwords.put(line, 1);
		}

	}
	public static void loadStopWordFile() throws IOException{
		System.out.println("loading stopwordList ......");
		//ReadEnStopWordFile("englishStopWord.txt");
		ReadChStopWordFile(".\\dict\\userfilterword.txt");
	}

	public static boolean IsChStopword(String str) {

		return _Chstopwords.containsKey(str);
	}

	public static boolean IsEnStopword(String str) {

		return _Enstopwords.containsKey(str.toLowerCase());
	}

}

