package edu.bupt.soft.svm_analysis.ordinary_feature;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zjd.nlpir.NlipirTools;

import edu.bupt.util.dict.LoadDictionary;
import edu.bupt.util.processor.PreprocessWeibo;
import edu.bupt.util.processor.SentenceProcessor;

/**
 * 分析微博中基础情感词的（个数）特征
 * @author DELL
 * @version 创建时间 2016年6月13日下午3:36:59 1.0
 */
public class EmotionStaticesOrdinaryFeature {
	static {
		LoadDictionary.loadPositiveSentimentWordsDic();  // 从数据库中加载基础积极情感词典
		LoadDictionary.loadNegativeSentimentWordsDic();  // 从数据库中加载基础消极情感词典
	}
	
	/**
	 * 计算微博中正负面基础情感词的（个数）特征
	 * @param blog  	微博文本内容
	 * @return			返回微博中正、负情感词个数特征值
	 * @throws Exception 
	 */
	public static int[] computeEmotionStaticsFeature(String blog) throws Exception {
		int[] result = new int[2];
		if (null == blog || "" == blog) return result;
		List<String> wordBag = NlipirTools.parse(blog, 0);     // 对微博进行分词
		for (String word : wordBag) {
			if (LoadDictionary.getPositiveSentimentWords().containsKey(word)) result[0]++;   //记录正面情感词个数
			if (LoadDictionary.getNegativeSentimentWords().containsKey(word)) result[1]++;   //记录负面情感词个数
		}
		return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		int[] result = computeEmotionStaticsFeature("我很高兴呀[高兴]");
		System.out.println(Arrays.toString(result));
	}
}
