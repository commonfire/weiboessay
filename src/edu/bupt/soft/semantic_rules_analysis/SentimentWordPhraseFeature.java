package edu.bupt.soft.semantic_rules_analysis;

import java.util.List;

import com.zjd.nlpir.NlipirTools;

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
	 * @throws Exception 
	 */
	public static double computeSentimentWordPhraseValue(String originalSimpleSentence) throws Exception {
		String simpleSentence = SentimentRulesAnalysisHelper.filterEmoticon(originalSimpleSentence);   // 去除简单句含有的表情符号
		List<String> wordBag = NlipirTools.parse(simpleSentence, 0); // 对简单句进行分词
		List<String> wordBagNoStopWords = SentimentRulesAnalysisHelper.filterStopWords(wordBag);       // 过滤停用词
//		System.out.println(wordBagNoStopWords);
		double value = 0;  // 情感极性短语极性(特征)值
		int sentiPower; 
		for (String word : wordBagNoStopWords) {
			if ((sentiPower = SentimentRulesAnalysisHelper.isSentimentWord(word)) != 0) {
				value += computeValueBySlideWindow(word, sentiPower, wordBagNoStopWords, 3); //利用滑动窗口计算每个中心情感词构成的情感极性短语的极性值
			}
		}
		return value;
	}
	
	/**
	 * 利用滑动窗口计算某个极性短语的“情感极性短语极性值”
	 * @param sentimentWord    中心情感词
	 * @param sentiPower 	      基础情感词极性值
	 * @param wordBag          分词后的词袋
	 * @return
	 */
	public static double computeValueBySlideWindow(String sentimentWord, int sentiPower, List<String> wordBag, int windowThreshold) {
		int sentimentWordIndex = wordBag.indexOf(sentimentWord);
		int len = wordBag.size(), tmpIndex = 0;
		int leftIndex = (tmpIndex = sentimentWordIndex - windowThreshold) < 0 ? 0 : tmpIndex, 
		rightIndex = (tmpIndex = sentimentWordIndex + windowThreshold) >= len ? (len - 1) : tmpIndex;
//		System.out.println("sentimentIndex is: " + sentimentWordIndex);System.out.println("leftIndex is: " + leftIndex);System.out.println("rightIndex is: " + rightIndex);
		double leftValue = 0, rightValue = 0;
		leftValue = SentimentRulesAnalysisHelper.computeWindowValue(leftIndex, (sentimentWordIndex-1 < 0) ? 0 :(sentimentWordIndex-1), sentiPower, 
				wordBag, LoadDictionary.getNegativeAdverbs(), LoadDictionary.getAdverbs());

		rightValue = SentimentRulesAnalysisHelper.computeWindowValue((sentimentWordIndex+1 >= len) ? len-1 :(sentimentWordIndex+1), rightIndex, sentiPower, 
				wordBag, LoadDictionary.getNegativeAdverbs(), LoadDictionary.getAdverbs());
//		System.out.println("leftValue is: " + leftValue);System.out.println("rightValue is: " + rightValue);
		return leftValue * rightValue;
	}
	
	
	public static void main(String[] args) throws Exception {
		String testStr = "我太高兴了";
		System.out.println(computeSentimentWordPhraseValue(testStr));

	}
}
