package edu.bupt.soft.svm_analysis.innovative_feature;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.bupt.util.dict.LoadDictionary;

/**
 * 分析复合句中的表情个数特征
 * @author DELL
 * @version 创建时间 2016年6月13日上午11:39:34 1.0
 */
public class EmoticonStaticsInnovativeFeature {
	static {
		LoadDictionary.loadEmoticonDic();            // 从数据库中加载表情符号库
	}
	
	/**
	 * 计算复合句中的表情个数特征
	 * @param complexSentence  待分析的复合句
	 * @return				      返回复合句中正、中、负表情个数特征值
	 */
	public static int[] computeEmoticonStaticsFeature(String complexSentence) {
		int[] result = new int[3];  // result数组分别用于存储正、中、负表情个数
		if (null == complexSentence || "" == complexSentence) return result;
		Map<String,Object> emoticonMap = LoadDictionary.getEmoticons();
		Pattern p = Pattern.compile("\\[(.{1,8}?)\\]");
		Matcher m = p.matcher(complexSentence);
		while (m.find()) {
			String emoticon = m.group(1);
			//System.out.println(emoticon);
			if (emoticonMap.containsKey(emoticon)) {
				if ((int)emoticonMap.get(emoticon) == 1) result[0]++;        // 记录正向表情个数
				else if ((int)emoticonMap.get(emoticon) == 0) result[1]++;   // 记录中性表情个数
				else result[2]++;    // 记录负性表情个数
			}
		}
		return result;
	}
	
	/**
	 * 利用一定规模语料下扩充后的表情符号库，计算复合句中的表情个数特征
	 * @param complexSentence
	 * @param emoticonMap
	 * @return
	 */
	public static int[] computeEmoticonStaticsFeature(String complexSentence, Map<String, Object> emoticonMap) {
		int[] result = new int[3];  // result数组分别用于存储正、中、负表情个数
		if (null == complexSentence || "" == complexSentence) return result;
		Pattern p = Pattern.compile("\\[(.{1,8}?)\\]");
		Matcher m = p.matcher(complexSentence);
		while (m.find()) {
			String emoticon = m.group(1);
			//System.out.println(emoticon);
			if (emoticonMap.containsKey(emoticon)) {
				if ((int)emoticonMap.get(emoticon) == 1) result[0]++;        // 记录正向表情个数
				else if ((int)emoticonMap.get(emoticon) == 0) result[1]++;   // 记录中性表情个数
				else result[2]++;    // 记录负性表情个数
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		int[] s = computeEmoticonStaticsFeature("考验来了[晕][晕][晕][晕][晕][抓狂][抓狂][抓狂][抓狂][抓狂][抓狂]");
		System.out.println(Arrays.toString(s));
	}
}
