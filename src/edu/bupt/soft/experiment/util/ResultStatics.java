package edu.bupt.soft.experiment.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.bupt.util.fileprocess.MyFileReader;

/**
 * 对测试结果进行统计（准确率，召回率，F值）
 * @author DELL
 * @version 创建时间 2016年6月23日上午9:32:52 1.0
 */
public class ResultStatics {
	
	/**
	 * 根据输入测试文件与输出结果文件计算正/中/负/平均情况下微博的准确率，召回率，F值
	 * @param testFile		输入测试文件名
	 * @param resultFile	输出结果文件名
	 * @param methodType    采用“svm”方法or基于语义的“semantic”方法
	 * @return				返回key为正/中/负/平均情况下微博的value准确率，召回率，F值的性能指标map结果
	 * @throws IOException 
	 */
	public static Map<String, Index> computeResultStatics(String testFile, String resultFile, String methodType) throws IOException {
		List<String> testLabels = TestFileReader.readTestFile(testFile,methodType);
		List<String> resultLabels = MyFileReader.readFile(resultFile);
		Map<String, Index> result = new HashMap<String, Index>();
		Index positiveIndex = new Index();
		Index.setIndex(positiveIndex, testLabels, resultLabels, "1.0");
		result.put("positive", positiveIndex);
		
		Index negativeIndex = new Index();
		Index.setIndex(negativeIndex, testLabels, resultLabels, "-1.0");
		result.put("negative", negativeIndex);
		
		Index neutralIndex = new Index();
		Index.setIndex(neutralIndex, testLabels, resultLabels, "0.0");
		result.put("neutral", neutralIndex);
		return result;
	}  
	

	
	/**
	 * 计算使用创新特征的svm，及不同核函数下的结果指标
	 * @throws IOException
	 */
	@Test
	public void libsvmInnovativeResultStaticsTest() throws IOException {
		String testFile = "weibo\\innovative_svm\\test";
		String resultFile = "weibo\\innovative_svm\\result";
		System.out.println(computeResultStatics(testFile, resultFile, "svm"));
	}
	
	/**
	 * 计算使用传统特征的svm结果指标
	 * @throws IOException
	 */
	@Test
	public void libsvmTraditionalResultStaticsTest() throws IOException {
		String testFile = "weibo\\traditional_svm\\test";
		String resultFile = "weibo\\traditional_svm\\result";
		System.out.println(computeResultStatics(testFile, resultFile, "svm"));
	}
	
	
	/**
	 * 计算计算基于语义规则+情感词典+表情符号方法的微博情感分析结果指标
	 * @throws IOException 
	 */
	@Test
	public void semanticsResultStaticsTest() throws IOException {
		String testFile = "weibo\\semantics\\test";
		String resultFile = "weibo\\semantics\\result";
		System.out.println(computeResultStatics(testFile, resultFile, "semantic"));
	}
}
