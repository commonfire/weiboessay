package edu.bupt.soft.experiment.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 测试文件读取
 * @author DELL
 * @version 创建时间 2016年6月23日上午9:47:17 1.0
 */
public class TestFileReader {
	
	/**
	 * 读取用于测试文件中的label值
	 * @param testFile  	   测试文件名
	 * @param methodType 	  采用“svm”方法or基于语义的“semantic”方法
	 * @return
	 * @throws IOException
	 */
	public static List<String> readTestFile(String testFile, String methodType) throws IOException {
		List<String> fileContent = new ArrayList<String>();
		File file = new File(testFile);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		// 读取svm测试文件中的label值
		if ("svm".equals(methodType)) {
			while ((line = br.readLine()) != null) {
				fileContent.add(line.split("  ")[0]);
			}
		}
		// 读取用于基于语义规则方法的测试文件中的label值
		else {
			while ((line = br.readLine()) != null) {
				fileContent.add(line.split("\t")[1] + ".0");
			}
		}
		br.close();
		return fileContent;
	}
	
	
	@Test
	public void readTestFileForSVM() throws IOException {
		String testFile = "weibo\\innovative_svm\\test";
		System.out.println(readTestFile(testFile,"svm"));
	}
	
	@Test
	public void readTestFileForSemantics() throws IOException {
		String testFile = "weibo\\semantics\\test";
		System.out.println(readTestFile(testFile,"semantic"));
	}
}
