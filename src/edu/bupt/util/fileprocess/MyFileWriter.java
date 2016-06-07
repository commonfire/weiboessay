package edu.bupt.util.fileprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 将数据写入文件中
 * @author DELL
 * @version 创建时间 2016年6月6日下午2:34:20 1.0
 */
public class MyFileWriter {
	/**
	 * 将数据内容写入到文件中
	 * @param filePath      需要写入的文件路径
	 * @param fileContent   待写入文件中的数据内容
	 * @throws IOException 
	 */
	public static void writeFile(String filePath, List<String> fileContent) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) file.createNewFile(); 
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (String line : fileContent) {
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}
