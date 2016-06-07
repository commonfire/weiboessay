package edu.bupt.util.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.bupt.util.fileprocess.MyFileReader;
import edu.bupt.util.fileprocess.MyFileWriter;

/**
 * 进行微博内容噪音数据剔除的预处理
 * @author DELL
 * @version 创建时间 2016年6月6日下午2:46:04 1.0
 */
public class PreprocessWeibo {
	
	/**
	 * 微博噪音数据去除预处理
	 * @param fileContent   待处理的微博内容
	 * @return				已预处理的微博微博
	 */
	public static List<String> preprocessWeibo(List<String> fileContent) {
		List<String> result = new ArrayList<String>();
		for (String s : fileContent) {
			String pattern0 = "//@.*";        //去除转发信息
			String pattern = "回复@.*?\\:";   //去除“回复”标记
			String pattern1 = "@.*? ";		  //去除@用户标记
			String pattern2 = "#.*?#";        //去除#话题标记
			//String pattern3 = "@.*";
			//String pattern4 = "\\.";
			String pattern5 = "http.*";       //去除http链接
			//String pattern6 = "\\/";
			String s1 = s.replaceAll(pattern0, "");
			String s2 = s1.replaceAll(pattern, "");
			String s3 = s2.replaceAll(pattern1, "");
			String s4 = s3.replaceAll(pattern2,"");
			//String s5 = s4.replaceAll(pattern3,"");
			//String s6 = s5.replaceAll(pattern4,"");
			String s7 = s4.replaceAll(pattern5,"");
			//String s8 = s7.replaceAll(pattern6,"");
			result.add(s7);
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		List<String> fileContent = new ArrayList<String>();
		fileContent = MyFileReader.readFile("D:\\test1");
		List<String> preprocessedWeibo = preprocessWeibo(fileContent);
		MyFileWriter.writeFile("D:\\preprocessedWeibo1", preprocessedWeibo);
	}
}
