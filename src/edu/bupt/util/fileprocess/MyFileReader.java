package edu.bupt.util.fileprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.DATE;

import com.mysql.fabric.xmlrpc.base.Array;

/**
 * 从文件获取数据
 * @author DELL
 * @version 创建时间 2016年6月6日下午2:27:01 1.0
 */
public class MyFileReader {
	/**
	 * 读取指定路径的文件数据
	 * @param filePath 文件路径
	 * @return 读取到的文件中内容
	 * @throws IOException 
	 */
	public static List<String> readFile(String filePath) throws IOException {
		List<String> fileContent = new ArrayList<String>();
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = br.readLine()) != null) {
			fileContent.add(line);
		}
		br.close();
		return fileContent;
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		long start = System.currentTimeMillis();
		List<String> fileContent = new ArrayList<String>();
		fileContent = MyFileReader.readFile("D:\\test1");
		int count = 0;
		for (String str : fileContent) {
			Thread.sleep(6);
			count++;
			System.out.println(str);
			if (count >= 200) break;
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
