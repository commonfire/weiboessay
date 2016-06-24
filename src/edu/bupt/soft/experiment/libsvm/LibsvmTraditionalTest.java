package edu.bupt.soft.experiment.libsvm;

import java.io.IOException;

/**
 * 传统特征下的svm测试
 * @author DELL
 * @version 创建时间 2016年6月23日下午3:00:25 1.0
 */
public class LibsvmTraditionalTest {
	
	/**
	 * 传统特征下的svm测试
	 * @param trainFile          训练数据文件
	 * @param testFile		          测试数据文件
	 * @param resultFile         测试结果文件
	 * @throws IOException
	 */
	public static void svmTraditionalTest(String trainFile, String testFile, String resultFile) throws IOException {
 		long start = System.currentTimeMillis();
		System.out.println("----------SVM运行开始---------");
 		//String[] trainArgs = {"UCI-breast-cancer-tra"};//directory of training file
		String[] trainArgs = {"-t","0",trainFile};
		String modelFile = svm_train.main(trainArgs);
		String[] testArgs = {testFile, modelFile, resultFile};//directory of test file, model file, result file
		Double accuracy = svm_predict.main(testArgs);  // 自动会输出相应的准确率
		long end  = System.currentTimeMillis();
		System.out.println("消耗的时间为:"+(end-start)/60000+"min");
	}
	
	public static void main(String[] args) throws IOException {
		String trainFile = "weibo\\traditional_svm\\train", testFile = "weibo\\traditional_svm\\test", resultFile = "weibo\\traditional_svm\\result";
		svmTraditionalTest(trainFile, testFile, resultFile);
	}
}
