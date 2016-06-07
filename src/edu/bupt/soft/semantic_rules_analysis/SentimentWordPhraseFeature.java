package edu.bupt.soft.semantic_rules_analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.bupt.util.dict.LoadDictionary;

/**
 * 计算简单句中的情感极性短语，用于后续的svm中特征
 * @author DELL
 * @version 创建时间 2016年6月6日下午9:56:52 1.0
 */
public class SentimentWordPhraseFeature {
	
	// 加载所需的词典
	static {
		LoadDictionary.loadPositiveSentimentWordsDic();  // 从数据库中加载基础积极情感词典
		LoadDictionary.loadNegativeSentimentWordsDic();  // 从数据库中加载基础消极情感词典
		LoadDictionary.loadAdverbsDic();    			 // 从数据库中加载程度副词
		LoadDictionary.loadNegativeAdverbsDic();         // 从数据库中加载否定副词词典
		LoadDictionary.loadEmoticonDic();                // 从数据库中加载表情符号库
		LoadDictionary.loadStopWordsDic();               // 从数据库中加载停用词词典
	}
	
	/**
	 * 计算“情感极性短语极性(特征)值”（利用滑动窗口匹配修饰词）
	 * @param originalSimpleSentence  输入一个简单句（可能含有表情符号，需要去除）
	 * @return 						    返回情感极性短语极性(特征)值
	 */
	public static double computeSentimentWordPhraseValue(String originalSimpleSentence) {
		String simpleSentence = filterEmoticon(originalSimpleSentence); // 去除简单句含有的表情符号
		return 0;
	}
	
	/**
	 * 去除简单句含有的表情符号
	 * @param originalSimpleSentence  可能含有表情符号的简单句
	 * @return 						    已经去除表情符号的简单句
	 */
	public static String filterEmoticon(String originalSimpleSentence) {
		Pattern pattern = Pattern.compile("\\[.*?\\]");
		Matcher m = pattern.matcher(originalSimpleSentence);
		String result = m.replaceAll("");
		return result;
	}
	
	public static void main(String[] args) {
		String testStr = "这是[哈哈]一个简单[good]句";
		System.out.println(filterEmoticon(testStr));
	}
}
