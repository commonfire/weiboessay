package edu.bupt.soft.experiment.libsvm;

import java.io.IOException;

/**
 * 引入新特征下的svm测试
 * @author DELL
 * @version 创建时间 2016年6月23日上午9:16:29 1.0
 */
public class LibsvmInnovativeTest {

	/**
	 * 新特征下的svm测试
	 * @param trainFile          训练数据文件
	 * @param testFile		          测试数据文件
	 * @param resultFile         测试结果文件
	 * @throws IOException
	 */
	public static void svmInnovativeTest(String trainFile, String testFile, String resultFile) throws IOException {
 		long start = System.currentTimeMillis();
		System.out.println("----------SVM运行开始---------");
 		//String[] trainArgs = {"UCI-breast-cancer-tra"};//directory of training file
		String[] trainArgs = {"-t","3",trainFile};  // 0 -- linear，1 -- polynomial，2 -- radial basis function，3 -- sigmoid
		String modelFile = svm_train.main(trainArgs);
		String[] testArgs = {testFile, modelFile, resultFile};//directory of test file, model file, result file
		Double accuracy = svm_predict.main(testArgs);  // 自动会输出相应的准确率
		long end  = System.currentTimeMillis();
		System.out.println("消耗的时间为:"+(end-start)/60000+"min");
	}
	
	public static void main(String[] args) throws IOException {
		String trainFile = "weibo\\innovative_svm\\train", testFile = "weibo\\innovative_svm\\test", resultFile = "weibo\\innovative_svm\\result";
		svmInnovativeTest(trainFile, testFile, resultFile);
	}
	
}
