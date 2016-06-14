package edu.bupt.soft.svm_analysis.innovative_feature;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import edu.bupt.util.https.HttpUtils;

/**
 * 利用哈工大语言云LTP平台进行语义依存分析
 * @author DELL
 * @version 创建时间 2016年6月12日下午5:07:37 1.0
 */
public class SemanticDependencyFeature {
	
	/**
	 * 对复合整句进行基于LTP平台进行语义依存分析，获取“句间关系”特征
	 * @param complexSentence  待分析的复合整句
	 * @return				      表示复合句中句间四种关系的整数（由4为二进制数而得）
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static int computeSemanticDependencyFeature(String complexSentence) throws ClientProtocolException, IOException {
		// 语言云Restful API请求地址
		String url = "http://api.ltp-cloud.com/analysis/?api_key=n5Y5v584kMmPSXMaLyYABlxpatwMRUFEaAGB2cgX&text=" + complexSentence + "&pattern=sdp&format=json";
		String respString = HttpUtils.httpGet(url);
		int result = 0;
		if (respString.contains("eConc")) {
			result = 1; //此时复合句分句句间含有“让步关系”，对应0001
		} 
		else if (respString.contains("eSupp")) {
			result += 1 << 1;   //此时复合句分句句间含有“假设关系”，对应0010
		}
		else if (respString.contains("eAdvt")) {
			result += 1 << 2;   //此时复合句分句句间含有“转折关系”，对应0100
		}
		else if (respString.contains("eProg")) {
			result += 1 << 3;   //此时复合句分句句间含有“递进关系”，对应1000
		}
		//System.out.println(respString);
		return result;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		int value = computeSemanticDependencyFeature("你变丑了，但是我依然我爱你");
		System.out.println(value);
	}
}
