package edu.bupt.soft.experiment.util;

import java.util.List;

/**
 * 测试指标类，包括准确率，召回率，F值
 * @author DELL
 * @version 创建时间 2016年6月23日上午9:43:13 1.0
 */
public class Index {
	
	private double precision;
	private double recall;
	private double f;
	
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}

	public double getF() {
		return f;
	}
	public void setF(double f) {
		this.f = f;
	}
	
	@Override
	public String toString() {
		return "Index [precision=" + precision + ", recall=" + recall + ", f="
				+ f + "]";
	}
	
	/**
	 * 为指定的index指标对象设置性能指标参数
	 * @param index				index指标对象
	 * @param precision			准确率
	 * @param recall			召回率
	 * @param f					F值
	 */
	public static void setIndex(Index index, List<String> testLabels, List<String> resultLabels, String label) {
		double[] precisionAndRecall = computePrecisionAndRecall(testLabels, resultLabels, label);
		double precision = precisionAndRecall[0];
		double recall = precisionAndRecall[1];
		double f = 2 * precision * recall / (precision + recall);
		index.setPrecision(precision);
		index.setRecall(recall);
		index.setF(f);
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
}
