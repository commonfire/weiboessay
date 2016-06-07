package edu.bupt.util.dict;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载数据库中的词典的辅助类
 * @author DELL
 * @version 创建时间 2016年6月6日下午10:50:35 1.0
 */
public class LoadDictionaryHelper {
	
	/**
	 * 返回数据库中所需词表中的词语列表
	 * @param rs           数据库查询结果集
	 * @param powerType    表示数据库中“power字段的类型”
	 * @return             从数据库提取的情感词列表
	 */
	public static Map<String,Object> loadDictWords(ResultSet rs, String powerType){
		Map<String, Object> dictMap = new HashMap<String, Object>();
		try {
				if ("Integer".equals(powerType)) {
					while (rs.next()) dictMap.put(rs.getString("phrase"),rs.getInt("power"));
				}
				else if ("Float".equals(powerType)) {
					while (rs.next()) dictMap.put(rs.getString("phrase"),rs.getFloat("power"));
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dictMap;
	}
	

	/**
	 * 返回数据库中停用词词库
	 * @param rs   数据库查询结果集
	 * @return     从数据库提取的停用词词库
	 */
	public static List<String> loadStopword(ResultSet rs){
		List<String> stopwordList = new ArrayList<>();
		try {
			   while (rs.next()) {
				   stopwordList.add(rs.getString("phrase"));
			   }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return stopwordList;
	}
	
	public static void main(String[] args) {
		
	}
}
