package edu.bupt.soft.svm_analysis.innovative_feature;

import java.text.DecimalFormat;
import java.util.List;

import edu.bupt.soft.semantic_rules_analysis.SentimentWordPhraseFeature;
import edu.bupt.util.processor.SentenceProcessor;

/**
 * 计算复合句中的情感极性短语
 * @author DELL
 * @version 创建时间 2016年6月15日上午10:58:11 1.0
 */
public class SentimentWordPhraseInnovativeFeature {
	
	/**
	 * 计算“情感极性短语极性(特征)值”（利用滑动窗口匹配修饰词）
	 * @param complexSentence         输入待分析的复合句
	 * @return 						    返回情感极性短语极性(特征)值
	 * @throws Exception 
	 */
	public static double computeSentimentWordPhraseValue(String complexSentence) throws Exception {
		List<String> simpleSentences = SentenceProcessor.splitToSimpleSentences(complexSentence); // 将复合句拆分为简单句集合
		double result = 0;
		DecimalFormat df = new DecimalFormat("#.0000");
		for (String simpleSentence : simpleSentences) {
			result += SentimentWordPhraseFeature.computeSentimentWordPhraseValue(simpleSentence);
		}
		return Double.valueOf(df.format(result));
	}
	
	public static void main(String[] args) throws Exception {
		String str = "算了豆豆时辰命理很好, 不管信否, 希望他能前途光明, 善良有情义。";
		double r = computeSentimentWordPhraseValue(str);
		System.out.println(r);
	}
}
