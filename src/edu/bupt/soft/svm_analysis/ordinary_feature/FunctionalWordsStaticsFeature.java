package edu.bupt.soft.svm_analysis.ordinary_feature;

import java.util.List;
import java.util.Map;

import com.zjd.nlpir.NlipirTools;

import edu.bupt.soft.semantic_rules_analysis.SentimentRulesAnalysisHelper;
import edu.bupt.util.dict.LoadDictionary;

/**
 * 分析微博中功能词（否定词，程度副词，“转折”连词）出现个数特征
 * @author DELL
 * @version 创建时间 2016年6月13日下午5:21:55 1.0
 */
public class FunctionalWordsStaticsFeature {
	
	static {
		LoadDictionary.loadAdverbsDic();    			 // 从数据库中加载程度副词
		LoadDictionary.loadNegativeAdverbsDic();         // 从数据库中加载否定副词词典
		LoadDictionary.loadAdversativesDic();            // 从数据库中加载“转折”连词词典
	}
	
	
	/**
	 * 计算程度副词个数特征
	 * @param blog             待分析的微博
	 * @return				      微博中出现的程度副词个数
	 * @throws Exception
	 */
	public static int computeAdverbsNumberFeature(String blog) throws Exception {
		return computeFunctionalWordsStaticsFeature(blog, LoadDictionary.getAdverbs());
	}
	
	
	/**
	 * 计算否定副词个数特征
	 * @param blog             待分析的微博
	 * @return				      微博中出现的否定副词个数
	 * @throws Exception
	 */
	public static int computeNegativeAdverbsNumberFeature(String blog) throws Exception {
		return computeFunctionalWordsStaticsFeature(blog, LoadDictionary.getNegativeAdverbs());
	}
	
	/**
	 * 计算“转折”连词个数特征
	 * @param blog             待分析的微博
	 * @return				      微博中出现的“转折”连词个数
	 * @throws Exception
	 */
	public static int computeAdversativesNumberFeature(String blog) throws Exception {
		return computeFunctionalWordsStaticsFeature(blog, LoadDictionary.getAdversatives());
	}
	
	
	/**
	 * 计算微博中功能词（否定词，程度副词，“转折”连词）出现个数特征
	 * @param blog            待分析的微博
	 * @param wordDicMap	    加载相应的功能词词典Map
	 * @return				    返回相应功能词出现的次数
	 * @throws Exception 
	 */
	private static int computeFunctionalWordsStaticsFeature(String blog, Map<String, Object> wordDicMap) throws Exception {
		int count = 0;
		if (null == blog || "" == blog) return count;
		String blog1 = SentimentRulesAnalysisHelper.filterEmoticon(blog);   // 去除微博中含有的表情符号
		List<String> wordBag = NlipirTools.parse(blog1, 0); // 对微博进行分词
		for (String word : wordBag) {
			if (wordDicMap.containsKey(word)) count++;
		}
		return count;
	}
	
	/**
	 * 计算微博中功能词（否定词，程度副词，“转折”连词）出现个数特征
	 * @param blog                     待分析的微博
	 * @param wordDicList              加载相应的功能词词典List
	 * @return                         返回相应功能词出现的次数
	 * @throws Exception
	 */
	private static int computeFunctionalWordsStaticsFeature(String blog, List<String> wordDicList) throws Exception {
		int count = 0;
		if (null == blog || "" == blog) return count;
		String blog1 = SentimentRulesAnalysisHelper.filterEmoticon(blog);   // 去除微博中含有的表情符号
		List<String> wordBag = NlipirTools.parse(blog1, 0); // 对微博进行分词
		for (String word : wordBag) {
			if (wordDicList.contains(word)) count++;
		}
		return count;
	}
	
	public static void main(String[] args) throws Exception {
		String blog = "今天心情非常不高兴，明天估计也特别不好。";
		System.out.println(computeNegativeAdverbsNumberFeature(blog));
		System.out.println(NlipirTools.parse(blog, 0));
	}
}
