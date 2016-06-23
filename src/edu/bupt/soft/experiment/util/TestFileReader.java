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
	 * 读取测试文件中的label值
	 * @param testFile  	   测试文件名
	 * @return
	 * @throws IOException
	 */
	public static List<String> readTestFile(String testFile) throws IOException {
		List<String> fileContent = new ArrayList<String>();
		File file = new File(testFile);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = br.readLine()) != null) {
			fileContent.add(line.split("  ")[0]);
		}
		br.close();
		return fileContent;
	}
	
	
	@Test
	public void readFileTest() throws IOException {
		String testFile = "weibo\\test1w";
		System.out.println(readTestFile(testFile));
	}
}
