package com.zjd.nlpir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kevin.zhang.NLPIR;

/**
 * Nlpir分词工具
 * @author DELL
 * @version 创建时间 2016年6月12日下午2:37:30 1.0
 */
public class NlipirTools {

	/**
	 * 对句子进行分词
	 * @param str			输入的待分词句子		
	 * @param needPOS		分词结果是否需要带有词性，0:表示不带，1:表示带
	 * @return				返回分词结果集
	 * @throws Exception
	 */
	public static List<String> parse(String str, int needPOS) throws Exception {
		// 创建接口实例
		NLPIR nlpir = new NLPIR();
		List<String> result = new ArrayList<String>();
		// NLPIR_Init方法第二个参数设置0表示编码为GBK, 1表示UTF8编码
		if (!NLPIR.NLPIR_Init("./file/".getBytes("utf-8"), 1)) {
			System.out.println("NLPIR初始化失败...");
			return result;
		}
		// 要统一编码, 否则分词结果会产生乱码
		byte[] resBytes = nlpir.NLPIR_ParagraphProcess(str.getBytes("UTF-8"), needPOS); //1表示分词带有词性标注
		String tmpStr = new String(resBytes, "UTF-8");
		String[] tmpArray = tmpStr.split("\\s+");
		System.out.println(tmpArray.length);
		result = Arrays.asList(tmpArray).subList(0, tmpArray.length-1);  //去除最后一个空格串
		
		//System.out.println("分词结果: " + new String(resBytes, "UTF-8"));
	/*	String utf8File = "./test/18.TXT";
		String utf8FileResult = "./test/18_result.TXT";
		nlpir.NLPIR_FileProcess(utf8File.getBytes("utf-8"), utf8FileResult.getBytes("utf-8"), 1);*/
		// 退出, 释放资源
		NLPIR.NLPIR_Exit();
		return result;
	}
	

	public static void main(String[] args) {
		try {
			String temp = "我很高兴";
			System.out.println(parse(temp, 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}