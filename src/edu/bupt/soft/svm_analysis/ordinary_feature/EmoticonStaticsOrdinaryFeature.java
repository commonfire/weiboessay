package edu.bupt.soft.svm_analysis.ordinary_feature;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.bupt.util.dict.LoadDictionary;
import edu.bupt.util.dict.LoadExpandedEmoticon;

/**
 * 分析微博中的表情个数特征
 * @author DELL
 * @version 创建时间 2016年6月13日下午4:53:29 1.0
 */
public class EmoticonStaticsOrdinaryFeature {
	
//	static {
//		LoadDictionary.loadEmoticonDic();            // 从数据库中加载表情符号库
//	}
	
	/**
	 * 计算微博中的表情（正/中/负）个数特征
	 * @param blog			      待分析的微博
	 * @param fileName 		      若为null表示从默认表情库获取，否则传入自扩充的表情词典名
	 * @return				      返回微博中正、中、负表情个数特征值
	 * @throws IOException 
	 */
	public static int[] computeEmoticonStaticsFeature(String blog, String fileName) throws IOException {
		int[] result = new int[3];  // result数组分别用于存储正、中、负表情个数
		if (null == blog || "" == blog) return result;
		Map<String,Object> emoticonMap;
		if (null == fileName) {  // 加载默认表情库
			LoadDictionary.loadEmoticonDic();
			emoticonMap = LoadDictionary.getEmoticons();
		}
		else {
			emoticonMap = LoadExpandedEmoticon.getExpandedEmoticons(fileName);
		}
		
		Pattern p = Pattern.compile("\\[(.{1,8}?)\\]");
		Matcher m = p.matcher(blog);
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
}
