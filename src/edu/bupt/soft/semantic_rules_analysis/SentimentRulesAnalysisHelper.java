package edu.bupt.soft.semantic_rules_analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.bupt.util.dict.LoadDictionary;

/**
 * 计算简单句中的情感极性短语时需要的辅助类
 * @author DELL
 * @version 创建时间 2016年6月12日上午9:38:54 1.0
 */
public class SentimentRulesAnalysisHelper {
	public static double m = 0.4;  //规则修正参数
	
	/**
	 * 计算左/右window区间中否定词/程度副词对中心情感词的附加权值
	 * @param startIndex   			区间起始点
	 * @param endIndex				区间结束点
	 * @param sentiPower 			基础情感词极性值
	 * @param negativeAdverbsDic	否定词词典
	 * @param adverbsDic			程度副词词典
	 * @return						对中心情感词的附加权值
	 */
	public static double computeWindowValue(int startIndex, int endIndex, int sentiPower, List<String> wordBag,
			Map<String, Object> negativeAdverbsDic, Map<String, Object> adverbsDic) {
		double result = 1;
		String modifer = null;
		boolean hasPreNegative = false;
		for (int i = startIndex; i <= endIndex; i++) {
			modifer = wordBag.get(i);
			if (negativeAdverbsDic.containsKey(modifer)) {
				result *= -1;
				hasPreNegative = true;
			}
			if (adverbsDic.containsKey(modifer)) {
				result *= (Float)adverbsDic.get(modifer);
				if (hasPreNegative) result *= m;  
			}
		}
		return result;
	}
	
	
	
	/**
	 * 判断词语是否为基础情感词
	 * @param word	  待判断的词语
	 * @return		  返回基础情感词权值，若不是则返回0
	 */
	public static int isSentimentWord(String word) {
		if (LoadDictionary.getPositiveSentimentWords().containsKey(word)) return 1;
		else if (LoadDictionary.getNegativeSentimentWords().containsKey(word)) return -1;
		return 0;
	}
	
	/**
	 * 去除词袋中的停用词
	 * @param wordBag  未过滤的词袋
	 * @return		      去除停用词的词袋
	 */
	public static List<String> filterStopWords(List<String> wordBag) {
		List<String> result = new ArrayList<String>();
		List<String> stopWords = LoadDictionary.getStopWords();
		for (String word : wordBag) {
			if (!stopWords.contains(word)) {
				result.add(word);
			}
		}
		return result;
	}
	
	/**
	 * 去除简单句含有的表情符号
	 * @param originalSimpleSentence  可能含有表情符号的简单句
	 * @return 						    已经去除表情符号的简单句
	 */
	public static String filterEmoticon(String originalSimpleSentence) {
		Pattern pattern = Pattern.compile("\\[(.{1,8}?)\\]");
		Matcher m = pattern.matcher(originalSimpleSentence);
		String result = m.replaceAll("");
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(filterEmoticon("[心][心][心]不知道发生什么了[weixiaoh]"));
	}
}
