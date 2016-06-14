package edu.bupt.soft.svm_analysis.ordinary_feature;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分析微博中的句型标点特征（即感叹号与问号个数）
 * @author DELL
 * @version 创建时间 2016年6月13日下午9:08:27 1.0
 */
public class SentencePatternsFeature {
	
	/**
	 * 计算微博中的句型标点特征（即感叹号与问号个数）
	 * @param blog		待分析的微博
	 * @return			微博中的句型标点特征（即感叹号与问号个数）
	 */
	public static int[] computeSentencePatternsFeature(String blog) {
		int[] result = new int[2];
		if (null == blog || "" == blog) return result;
		String regex = "！|？|!|\\?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(blog);
		while (m.find()) {
			String tmp = m.group(0);
			if (tmp.equals("!") || tmp.equals("！")) result[0]++;  //记录微博中的感叹号个数
			if (tmp.equals("?") || tmp.equals("？")) result[1]++;  //记录微博中的问号个数
		}
		return result;
	}
	
	public static void main(String[] args) {
		String blog = "测试?再测试?测试成功？哈哈！太逗了吧！";
		System.out.println(Arrays.toString(computeSentencePatternsFeature(blog)));
	}
}
