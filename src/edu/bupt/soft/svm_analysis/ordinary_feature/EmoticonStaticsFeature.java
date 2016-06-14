package edu.bupt.soft.svm_analysis.ordinary_feature;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.bupt.util.dict.LoadDictionary;

/**
 * 分析微博中的表情个数特征
 * @author DELL
 * @version 创建时间 2016年6月13日下午4:53:29 1.0
 */
public class EmoticonStaticsFeature {
	
	static {
		LoadDictionary.loadEmoticonDic();            // 从数据库中加载表情符号库
	}
	
	/**
	 * 计算微博中的表情个数特征
	 * @param blog			      待分析的微博
	 * @return				      返回微博中正、中、负表情个数特征值
	 */
	public static int[] computeEmoticonStaticsFeature(String blog) {
		int[] result = new int[3];  // result数组分别用于存储正、中、负表情个数
		if (null == blog || "" == blog) return result;
		Map<String,Object> emoticonMap = LoadDictionary.getEmoticons();
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
