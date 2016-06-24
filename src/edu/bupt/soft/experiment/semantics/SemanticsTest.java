package edu.bupt.soft.experiment.semantics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import edu.bupt.soft.semantic_rules_analysis.SentimentWordPhraseFeature;
import edu.bupt.soft.svm_analysis.innovative_feature.SentencePatternsInnovativeFeature;
import edu.bupt.util.dict.LoadDictionary;
import edu.bupt.util.fileprocess.MyFileReader;
import edu.bupt.util.processor.SentenceProcessor;

/**
 * 基于语义规则+情感词典+表情符号方法的微博情感分析
 * @author DELL
 * @version 创建时间 2016年6月24日上午8:32:56 1.0
 */
public class SemanticsTest {
	
	/**
	 * 计算基于语义规则+情感词典+表情符号方法的微博情感分析
	 * @param testFile          	输入的测试微博+label值 
	 * @param resultFilePath		输出测试结果
	 * @throws Exception 
	 */
	public static void semanticsTest(String testFile, String resultFilePath) throws Exception {
		List<String> blogs = MyFileReader.readFile(testFile);
		File resultFile = new File(resultFilePath);
		BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile));
		for (String blog : blogs) {
			if ("".equals(blog)) {
				bw.write("0.0");
				bw.newLine();
			}
			double label = 0.0, textScore = 0.0, emoticonScore = 0.0;  // 记录最后微博测试得到的label值、微博文本情感值、微博表情符号情感值
			
			// 计算微博中文本部分情感值
			List<String> complexSentences = SentenceProcessor.splitToComplexSentencesWithDelimiter(blog.split("\t")[0]);
			for (String complexSentence : complexSentences) {
				double complexSentenceScore = 0.0;
				List<String> simpleSentences = SentenceProcessor.splitToSimpleSentences(complexSentence);
				for (String simpleSentence : simpleSentences) {
					complexSentenceScore  += SentimentWordPhraseFeature.computeSentimentWordPhraseValue(simpleSentence);  // 计算简单句中的情感极性短语值
				}
				complexSentenceScore = simpleSentences.size() != 0 ? complexSentenceScore / simpleSentences.size() : 0.0; // 对复合句中的简单句取平均
				if (SentencePatternsInnovativeFeature.isExclamatorySentence(complexSentence)) complexSentenceScore *= 2;  // 复合句为感叹句
				if (SentencePatternsInnovativeFeature.isRhetoricalQuestion(complexSentence)) complexSentenceScore *= -1.5; // 复合句为反问句
				
				textScore += complexSentenceScore;
			}
			textScore = complexSentences.size() != 0 ? textScore / complexSentences.size() : 0.0;   // 对微博中的复合句取平均
			// 计算微博中表情部分的情感分值
			emoticonScore = computeEmoticonScore(blog);
			label = 0.4 * emoticonScore + 0.6 * textScore;  // 微博文本和表情加权平均
			if (label > 0) label = 1.0;
			else if (label < 0) label = -1.0;
			else label = 0.0;
			bw.write(String.valueOf(label));
			bw.newLine();
		}
		bw.close();
	}
	
	/**
	 * 计算微博中表情部分的情感分值
	 * @param blog		待分析的微博消息
	 * @return			返回微博中表情部分的情感分值
	 */
	public static double computeEmoticonScore(String blog) {
		double emoticonScore = 0.0;  
		int emoticonCount = 0;  //记录表情出现个数
		if (null == blog || "" == blog) return emoticonScore;
		Map<String,Object> emoticonMap = LoadDictionary.getEmoticons();
		Pattern p = Pattern.compile("\\[(.{1,8}?)\\]");
		Matcher m = p.matcher(blog);
		while (m.find()) {
			String emoticon = m.group(1);
			//System.out.println(emoticon);
			if (emoticonMap.containsKey(emoticon)) {
				if ((int)emoticonMap.get(emoticon) == 1) emoticonScore += 1;          // 记录正向表情个数
				else if ((int)emoticonMap.get(emoticon) == -1) emoticonScore += -1;   // 记录负向表情个数
			}
		}
		return emoticonCount != 0 ? emoticonScore/emoticonCount : 0.0;  // 对表情符号求取平均
	}
	
	@Test
	public void testsemanticsTest() throws Exception {
		String testFile = "weibo\\semantics\\test";
		String resultFile = "weibo\\semantics\\result";
		semanticsTest(testFile,resultFile);
	}
}
