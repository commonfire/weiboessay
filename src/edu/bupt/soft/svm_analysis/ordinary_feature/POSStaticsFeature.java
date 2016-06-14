package edu.bupt.soft.svm_analysis.ordinary_feature;

import java.util.Arrays;
import java.util.List;

import com.zjd.nlpir.NlipirTools;

import edu.bupt.util.dict.LoadDictionary;
import edu.bupt.util.processor.PreprocessWeibo;

/**
 * 分析微博中词性（名/动/形/副）
 * @author DELL
 * @version 创建时间 2016年6月13日下午4:03:06 1.0
 */
public class POSStaticsFeature {
	
	static {
		LoadDictionary.loadStopWordsDic();               // 从数据库中加载停用词词典
	}
	
	/**
	 * 计算微博中词性（名/动/形/副）个数特征
	 * @param blog			待分析的微博
	 * @return				返回分析的词性特征
	 * @throws Exception
	 */
	public static int[] computePOSStaticsFeature(String blog) throws Exception {
		int[] result = new int[4];
		if (null == blog || "" == blog) return result;
		String blog1 = PreprocessWeibo.filterEmoticon(blog);  // 过滤微博中的表情符号
		List<String> wordBag = NlipirTools.parse(blog1, 1);   // 对微博进行分词
		for (String word : wordBag) {
			String word1 = word.substring(word.indexOf("/") + 1, word.length());
			if (word1.equals("n")) {      //保留名词
				result[0]++;
			} else if(word1.equals("a")) {  //保留形容词
				result[1]++;
			} else if(word1.equals("d")) {  //保留副词
				result[2]++;
			} else if(word1.equals("v")) { //保留动词
				result[3]++;
			}
		}
		System.out.println(wordBag);
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		int[] result = computePOSStaticsFeature("今天不是特别的高兴，也不知道为什么，希望明天天气能够好些");
		System.out.println(Arrays.toString(result));
	}
}
