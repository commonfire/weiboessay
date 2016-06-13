package edu.bupt.util.dict;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.bupt.util.jdbc.SQLHelper;

/**
 * 加载数据库中的词典
 * @author DELL
 * @version 创建时间 2016年6月6日下午11:24:13 1.0
 */
public class LoadDictionary {
    private static Map<String, Object> positiveSentimentWords;   // 积极情感词库词语
    private static Map<String, Object> negativeSentimentWords;   // 消极情感词库词语
    private static Map<String, Object> negativeAdverbs;          // 否定副词词语
    private static Map<String, Object> adverbs;					 // 程度副词
    private static List<String> adversatives;			     // “转折”连词词典
    private static Map<String, Object> emoticons;                // 表情符号库
    private static List<String> stopWords;  					 // 停用词
 

	/**
	 * 从数据库中加载基础积极情感词典
	 */
	public static void loadPositiveSentimentWordsDic() {
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from emotion_positive_dictionary";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			positiveSentimentWords = LoadDictionaryHelper.loadDictWords(rs,"Integer");     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 从数据库中加载基础消极情感词典
	 */
	public static void loadNegativeSentimentWordsDic() {
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from emotion_negative_dictionary";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			negativeSentimentWords = LoadDictionaryHelper.loadDictWords(rs,"Integer");          
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从数据库中加载程度副词
	 */
	public static void loadAdverbsDic() {
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from adverbs_dictionary";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			adverbs = LoadDictionaryHelper.loadDictWords(rs,"Float");          
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从数据库中加载否定副词词典
	 */
	public static void loadNegativeAdverbsDic() {
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from negative_adverbs_dictionary";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			negativeAdverbs = LoadDictionaryHelper.loadDictWords(rs,"Integer");          
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从数据库中加载表情符号库
	 */
	public static void loadEmoticonDic() {
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from emoticon_baseword_dictionary";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			emoticons = LoadDictionaryHelper.loadDictWords(rs,"Integer");          
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 从数据库中加载“转折”连词词典
	 */
	public static void loadAdversativesDic() {
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from adversative_dictionary";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			adversatives = LoadDictionaryHelper.loadStopword(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	/**
	 * 从数据库中加载停用词词典
	 */
	public static void loadStopWordsDic() {
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from stopword_dictionary";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			stopWords = LoadDictionaryHelper.loadStopword(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static Map<String, Object> getPositiveSentimentWords() {
		return positiveSentimentWords;
	}


	public static Map<String, Object> getNegativeSentimentWords() {
		return negativeSentimentWords;
	}


	public static Map<String, Object> getNegativeAdverbs() {
		return negativeAdverbs;
	}


	public static Map<String, Object> getEmoticons() {
		return emoticons;
	}


	public static Map<String, Object> getAdverbs() {
		return adverbs;
	}


	public static List<String> getStopWords() {
		return stopWords;
	}


	public static List<String> getAdversatives() {
		return adversatives;
	}


	public static void main(String[] args) {
/*		loadPositiveSentimentWordsDic();
		System.out.println(positiveSentimentWords);*/
		loadAdversativesDic();
		System.out.println(adversatives);
	}
}
