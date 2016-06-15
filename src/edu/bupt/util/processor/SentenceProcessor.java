package edu.bupt.util.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微博句子（复合句，简单句）处理
 * @author DELL
 * @version 创建时间 2016年6月6日下午5:26:59 1.0
 */
public class SentenceProcessor {
	
	/**
	 * 输入一篇微博，返回一个复合句的列表（不含句子分隔符）
	 * @param blog  输入微博内容
	 * @return      返回分割的复合句列表
	 */
	public static List<String> splitToComplicatedSentences(String blog){
		List<String> sentenceList = new ArrayList<String>();
		if (null == blog) return sentenceList;
		
		String[] sentenceArray = blog.split("。|；|！|？|\\.|;|!|\\?"); //复合句分隔符
		sentenceList = new ArrayList<String>(Arrays.asList(sentenceArray));
			for (int i = sentenceList.size() - 1; i >= 0; i--) {
				if (sentenceList.get(i).equals("")) {
					// 删除空句（如由于“！！”造成空句）
					sentenceList.remove(sentenceList.get(i));
				}	
			}
		return sentenceList;
	}
	
	/**
	 * 输入一篇微博，返回一个复合句的列表（含有句子分隔符）
	 * @param blog    输入微博内容
	 * @return        返回含有分隔符的复合句列表
	 */ 
	public static List<String> splitToComplicatedSentencesWithDelimiter(String blog){
		List<String> sentenceList = new ArrayList<String>();
		if (null == blog) return sentenceList;
		
		String regex = "。|；|！|？|\\.|;|!|\\?";
		Pattern p = Pattern.compile(regex);    
		Matcher m = p.matcher(blog);
		String[] sentenceArray = blog.split(regex);
		if (sentenceArray.length > 0) {
			int index = 0;
			while (index < sentenceArray.length) {
				if (m.find()) sentenceArray[index] += m.group();
				index++;
			}
		} 
		sentenceList = new ArrayList<String>(Arrays.asList(sentenceArray));
		for (int i = sentenceList.size() - 1; i >= 0; i--) {
			if (1 == sentenceList.get(i).length()) {
				// 删除只有一个标点“空句”（如由于“！！”造成空句）
				sentenceList.remove(sentenceList.get(i));
			}	
		}
		return sentenceList;
	}
	
	/**
	 * 输入一个复合句，返回其中的简单句
	 * @param complexSentence  输入复合句
	 * @return	                              返回简单句列表
	 */
	public static List<String> splitToSimpleSentences(String complexSentence) {
		List<String> sentenceList = new ArrayList<String>();
		if (null == complexSentence) return sentenceList;
		String[] sentenceArray = complexSentence.split("，|,|\\s+");  //简单句分隔符
		sentenceList = new ArrayList<String>(Arrays.asList(sentenceArray));
			for (int i = sentenceList.size() - 1; i>=0; i--) {
				if (sentenceList.get(i).equals("")) {
					// 删除空句（如由于“！！”造成空句）
					sentenceList.remove(sentenceList.get(i));
				}	
			}
		return sentenceList;
	}
	

	public static void main(String[] args) {
		String blog = "算了豆豆时辰命理很好 不管信否 希望他能前途光明 善良有情义。";
		List<String> list = splitToSimpleSentences(blog);
		System.out.println(list);
	}
}
