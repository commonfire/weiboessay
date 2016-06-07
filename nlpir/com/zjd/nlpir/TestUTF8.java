package com.zjd.nlpir;

import java.util.Arrays;

import kevin.zhang.NLPIR;

public class TestUTF8 {

	public static void main(String[] args) {
		try {
			testUTF8();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void testUTF8() throws Exception {
		// 创建接口实例
		NLPIR nlpir = new NLPIR();
		// NLPIR_Init方法第二个参数设置0表示编码为GBK, 1表示UTF8编码
		if (!NLPIR.NLPIR_Init("./file/".getBytes("utf-8"), 1)) {
			System.out.println("NLPIR初始化失败...");
			return;
		}
		String temp = "每天的日报都记得  要发送以配合经理掌握项目的进度情况";
		// 要统一编码, 否则分词结果会产生乱码
		byte[] resBytes = nlpir.NLPIR_ParagraphProcess(temp.getBytes("UTF-8"),0); //1表示分词带有词性标注
		String a = new String(resBytes, "UTF-8");
		String[] l = a.split("\\s+");
		System.out.println(Arrays.toString(l));
		
		System.out.println("分词结果: " + new String(resBytes, "UTF-8"));
	/*	String utf8File = "./test/18.TXT";
		String utf8FileResult = "./test/18_result.TXT";
		nlpir.NLPIR_FileProcess(utf8File.getBytes("utf-8"), utf8FileResult.getBytes("utf-8"), 1);*/
		// 退出, 释放资源
		NLPIR.NLPIR_Exit();
	}
}