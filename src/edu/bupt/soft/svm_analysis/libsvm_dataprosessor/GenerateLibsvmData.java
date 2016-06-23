package edu.bupt.soft.svm_analysis.libsvm_dataprosessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.bupt.soft.svm_analysis.innovative_feature.EmoticonStaticsInnovativeFeature;
import edu.bupt.soft.svm_analysis.innovative_feature.SemanticDependencyInnovativeFeature;
import edu.bupt.soft.svm_analysis.innovative_feature.SentencePatternsInnovativeFeature;
import edu.bupt.soft.svm_analysis.innovative_feature.SentimentWordPhraseInnovativeFeature;
import edu.bupt.soft.svm_analysis.innovative_feature.SimpleSentenceRelationInnovativeFeature;
import edu.bupt.soft.svm_analysis.ordinary_feature.EmoticonStaticsOrdinaryFeature;
import edu.bupt.soft.svm_analysis.ordinary_feature.EmotionStaticesOrdinaryFeature;
import edu.bupt.soft.svm_analysis.ordinary_feature.FunctionalWordsStaticsOrdinaryFeature;
import edu.bupt.soft.svm_analysis.ordinary_feature.POSStaticsOrdinaryFeature;
import edu.bupt.soft.svm_analysis.ordinary_feature.SentencePatternsOrdinaryFeature;
import edu.bupt.util.fileprocess.MyFileReader;
import edu.bupt.util.fileprocess.MyFileWriter;
import edu.bupt.util.processor.PreprocessWeibo;
import edu.bupt.util.processor.SentenceProcessor;

/**
 * 生成libsvm要求格式的数据集
 * @author DELL
 * @version 创建时间 2016年6月15日上午9:24:45 1.0
 */
public class GenerateLibsvmData {
	
	private static int complexSentenceNumThreshold = 9;
	
	/**
	 * 生成用于微博情感分析的libsvm要求格式的数据集
	 * @param blog      		待特征量化的微博文本
	 * @param label				标注好的微博极性分类标签
	 * @param needNewFeature	是否使用创新的特征：即“复合句式下的句子结构特征”
	 * @return 					返回处理好的一条libsvm格式数据
	 * @throws Exception 
	 */
	public static String generateWeiboLibsvmData(String blog, double label, boolean needNewFeature) throws Exception {
		String blog1 = PreprocessWeibo.filterEmoticon(blog);    //过滤微博中的表情符号
		StringBuilder sb = new StringBuilder(label + "  ");  //记录情感极性分类标签
		int index = 1;  //记录libsvm格式中的“index”标号
		
		// 计算微博中正负面基础情感词的（个数）特征（已有特征），index为1,2
		int[] emotionStaticesOrdinaryFeature = EmotionStaticesOrdinaryFeature.computeEmotionStaticsFeature(blog1);
		addFeatureToDataset(sb, index, emotionStaticesOrdinaryFeature[0]); index++;
		addFeatureToDataset(sb, index, emotionStaticesOrdinaryFeature[1]); index++;
		
		// 计算微博中词性（名/动/形/副）个数特征（已有特征），index为3,4,5,6
		int[] poStaticsOrdinaryFeature = POSStaticsOrdinaryFeature.computePOSStaticsFeature(blog1);
		for (int i = 0; i < poStaticsOrdinaryFeature.length; i++) {
			addFeatureToDataset(sb, index, poStaticsOrdinaryFeature[i]);
			index++;
		}
		
		// 计算微博中的表情（正/中/负）个数特征，index为7,8,9
		int[] emoticonStaticsOrdinaryFeature = EmoticonStaticsOrdinaryFeature.computeEmoticonStaticsFeature(blog);
		for (int i = 0; i < emoticonStaticsOrdinaryFeature.length; i++) {
			addFeatureToDataset(sb, index, emoticonStaticsOrdinaryFeature[i]);
			index++;
		}
		
		// 计算否定副词个数特征，index为10
		int negativeAdverbsNumberFeature = FunctionalWordsStaticsOrdinaryFeature.computeNegativeAdverbsNumberFeature(blog1);
		addFeatureToDataset(sb, index, negativeAdverbsNumberFeature); index++;
		
		// 计算程度副词个数特征，index为11
		int adverbsNumberFeature = FunctionalWordsStaticsOrdinaryFeature.computeAdverbsNumberFeature(blog1);
		addFeatureToDataset(sb, index, adverbsNumberFeature); index++;
		
		// 计算“转折”连词个数特征，index为12
		int adversativesNumberFeature = FunctionalWordsStaticsOrdinaryFeature.computeAdversativesNumberFeature(blog1);
		addFeatureToDataset(sb, index, adversativesNumberFeature); index++;
		
		// 计算微博中的句型标点特征（即感叹号与问号个数），index为13,14
		int[] sentencePatternsOrdinaryFeature = SentencePatternsOrdinaryFeature.computeSentencePatternsFeature(blog);
		addFeatureToDataset(sb, index, sentencePatternsOrdinaryFeature[0]); index++;
		addFeatureToDataset(sb, index, sentencePatternsOrdinaryFeature[1]); index++;
		
		if (needNewFeature) {
			// 计算“复合句式下的句子结构特征”，共有6*maxSentenceNum个特征，index为15~（14+6*maxSentenceNum）
			List<String> sentences = SentenceProcessor.splitToComplicatedSentencesWithDelimiter(blog);
			int maxSentenceNum = Math.min(sentences.size(), complexSentenceNumThreshold);  
			for (int i = 0; i < maxSentenceNum; i++) {
				/*// 计算对复合句i进行基于LTP平台进行语义依存分析，获取“句间关系”特征值，index为15+i*6
				int semanticDependencyInnovativeFeature = SemanticDependencyInnovativeFeature.computeSemanticDependencyFeature(sentences.get(i));
				addFeatureToDataset(sb, index, semanticDependencyInnovativeFeature); index++;*/
				
				//计算复合句i中的“句间关系”特征值，index为15+i*6
				int simpleSentenceRelationInnovativeFeature = SimpleSentenceRelationInnovativeFeature.computeSimpleSentenceRelationFeature(sentences.get(i));
				addFeatureToDataset(sb, index, simpleSentenceRelationInnovativeFeature); index++;
				
				// 计算复合句i的句型特点（感叹号与问号）特征值，index为16+i*6
				int sentencePatternsInnovativeFeature = SentencePatternsInnovativeFeature.computeSentencePatternsFeature(sentences.get(i));
				addFeatureToDataset(sb, index, sentencePatternsInnovativeFeature); index++;
				
				// 计算复合句i中的表情个数特征，index为[17,18,19]+i*6
				int[] emoticonStaticsInnovativeFeature = EmoticonStaticsInnovativeFeature.computeEmoticonStaticsFeature(sentences.get(i));
				for (int j = 0; j < emoticonStaticsInnovativeFeature.length; j++) {
					addFeatureToDataset(sb, index, emoticonStaticsInnovativeFeature[j]); index++;
				}
				
				// 计算“情感极性短语极性(特征)值”（利用滑动窗口匹配修饰词），index为20+i*6
				double sentimentWordPhraseInnovativeFeature = SentimentWordPhraseInnovativeFeature.computeSentimentWordPhraseValue(sentences.get(i));
				addFeatureToDataset(sb, index, sentimentWordPhraseInnovativeFeature); index++;
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 将特征以“index:value”格式添加到符合libsvm格式的数据集中
	 * @param StringBuilder		存储的结果集合
	 * @param index		                特征索引
	 * @param value		                特征值
	 */
	private static void addFeatureToDataset(StringBuilder sb, int index, double value) {
		if (value != 0) sb.append(index + ":" + value + " ");
	}
	
	@Test
	public void generateInnovativeDataTest() throws NumberFormatException, Exception {
		List<String> fileContent = new ArrayList<String>();
		List<String> preprocessedWeibo = new ArrayList<String>();
		String[] datanode = null;
		for (int i = 7; i <= 7; i++) {
			fileContent = MyFileReader.readFile("D:\\weiboprocess\\corpus\\processed\\weibo_corpus" + i + ".txt");
			for (String blog : fileContent) {
				datanode = blog.split("\t");
				preprocessedWeibo.add(generateWeiboLibsvmData(datanode[0], Double.valueOf(datanode[1]),true));  // 采用创新的特征
				//System.out.println(datanode[0]+"---"+generateWeiboLibsvmData(datanode[0], Double.valueOf(datanode[1])));
			}
			MyFileWriter.writeFile("D:\\weiboprocess\\libsvm\\innovative_train\\train_" + i + ".txt", preprocessedWeibo);
			preprocessedWeibo.clear();
		}
		System.out.println("finished!!");
	}
	
	@Test
	public void generateTraditionalDataTest() throws NumberFormatException, Exception {
		List<String> fileContent = new ArrayList<String>();
		List<String> preprocessedWeibo = new ArrayList<String>();
		String[] datanode = null;
		for (int i = 1; i <= 7; i++) {
			fileContent = MyFileReader.readFile("D:\\weiboprocess\\corpus\\processed\\weibo_corpus" + i + ".txt");
			for (String blog : fileContent) {
				datanode = blog.split("\t");
				preprocessedWeibo.add(generateWeiboLibsvmData(datanode[0], Double.valueOf(datanode[1]),false));  // 不采用创新的特征
				//System.out.println(datanode[0]+"---"+generateWeiboLibsvmData(datanode[0], Double.valueOf(datanode[1])));
			}
			MyFileWriter.writeFile("D:\\weiboprocess\\libsvm\\traditional_train\\train_" + i + ".txt", preprocessedWeibo);
			preprocessedWeibo.clear();
		}
		System.out.println("finished!!");
	}
	
}
