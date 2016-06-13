package edu.bupt.soft.svm_analysis.innovative;

import java.util.List;

import edu.bupt.util.processor.SentenceProcessor;

/**
 * 分析复合句的句型标点特征（即感叹号与反问句问号）
 * @author DELL
 * @version 创建时间 2016年6月13日上午8:30:25 1.0
 */
public class SentencePatternsFeature {
	
	/**
	 * 计算复合句的句型特点（感叹号与问号）特征值
	 * @param complexSentence        待分析的复合句
	 * @return			  			  句型特点特征值
	 */
	public static int computeSentencePatternsFeature(String complexSentence) {
		List<String> sentenceList = SentenceProcessor.splitToComplicatedSentencesWithDelimiter(complexSentence);
		int result = 0;
		if (null == complexSentence || 1 == complexSentence.length()) return 0; // 此时句子为空，只有一个句子分隔符（由于“?!!!”此类不规范情况）
		for (String sentence : sentenceList) {
			if (isExclamatorySentence(sentence)) result = 1;  // 该复合句为感叹句，对应01
			if (isRhetoricalQuestion(complexSentence)) result += 1 << 1;  // 该复合句为反问句，对应10
		}
		return result;
	}

	/**
	 * 判断复合句是否为感叹句
	 * @param complexSentence       待分析的复合句
	 * @return                      判断结果
	 */
	private static boolean isExclamatorySentence(String complexSentence) {
		if (complexSentence.contains("!") || complexSentence.contains("！")) return true;
		return false;
	}
	
	private static boolean isRhetoricalQuestion(String complexSentence) {
		String[] rhetoricalMarker = {"难道","怎么能","怎么不","这么","怎能","哪能","不正是","还不是","谁能不","谁还能"}; //反问句标识词
		if (!complexSentence.contains("?") && !complexSentence.contains("？")) return false;
		else {
			for (String marker : rhetoricalMarker) {
				if (complexSentence.contains(marker)) return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		List<String> sentences = SentenceProcessor.splitToComplicatedSentencesWithDelimiter("难道你不高兴吗？!!!!咱们要不去哪里玩吧！还是改天也可以");
		System.out.println(sentences);
		for (String sentence : sentences) {
			System.out.println(computeSentencePatternsFeature(sentence));
		}
	}
}
