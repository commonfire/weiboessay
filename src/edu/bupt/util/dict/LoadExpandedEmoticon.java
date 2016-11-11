package edu.bupt.util.dict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.bupt.util.fileprocess.MyFileReader;

public class LoadExpandedEmoticon {
	
	/**
	 * 获取表情自扩充词典：表情-极性
	 * @param fileName  自扩充的表情文件名
	 * @return          返回表情自扩充词典
	 * @throws IOException
	 */
	public static Map<String, Object> getExpandedEmoticons(String fileName) throws IOException {
		List<String> fileContent = MyFileReader.readFile("E:\\emoticon\\" + fileName + ".txt");
		Map<String, Object> map = new HashMap<>();
		for (String e : fileContent) {
			String e1 = e.replace("[", "");
			String e2 = e1.replace("]", "");
			String[] es = e2.split("=");
			map.put(es[0], es[1]);
		}
		return map;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(getExpandedEmoticons("50w"));
	}
}
