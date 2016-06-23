package edu.bupt.soft.experiment.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * @return				返回key为正/中/负/平均情况下微博的value准确率，召回率，F值的性能指标map结果
	 * @throws IOException 
	 */
	public static Map<String, Index> computeResultStatics(String testFile, String resultFile) throws IOException {
		List<String> testLabels = TestFileReader.readTestFile(testFile);
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
	 * @param testLabels        测试文件中的label数据
	 * @param resultLabels	        结果文件中的label数据
	 * @param label			        需要计算的label（-1,0,1）  
	 * @return				        返回相应label的含有准确率，召回率的数组
	 */
	public static double[] computePrecisionAndRecall(List<String> testLabels, List<String> resultLabels, String label) {
		int correct = 0, resultTotal = 0, testTotal = 0;  // testTotal用于计算召回率，resultTotal用于计算准确率
		double[] result = new double[2];
		for (int i = 0; i < testLabels.size(); i++) {
			if (testLabels.get(i).equals(resultLabels.get(i)) && resultLabels.get(i).equals(label)) correct++;
			if (resultLabels.get(i).equals(label)) resultTotal++;
			if (testLabels.get(i).equals(label)) testTotal++;
		}
		System.out.println("correct: " + correct + "  label: " + label);
		System.out.println("resultTotal: " + resultTotal + "  label: " + label);
		System.out.println("testTotal: " + testTotal + "  label: " + label);
		System.out.println("-------------");
		result[0] = (double)correct/(double)resultTotal;
		result[1] = (double)correct/(double)testTotal;
		return result;
	}
	
	
	public static void main(String[] args) throws IOException {
		String testFile = "weibo\\test1w";
		String resultFile = "weibo\\result";
		System.out.println(computeResultStatics(testFile, resultFile));
	}
}
