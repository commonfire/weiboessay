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
		double[] precisionAndRecall = ResultStatics.computePrecisionAndRecall(testLabels, resultLabels, label);
		double precision = precisionAndRecall[0];
		double recall = precisionAndRecall[1];
		double f = 2 * precision * recall / (precision + recall);
		index.setPrecision(precision);
		index.setRecall(recall);
		index.setF(f);
	}
}
