package oldbackup.semantic_rules_analysis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.bupt.util.dict.LoadDictionaryHelper;
import edu.bupt.util.dict.SentimentWordItem;
import edu.bupt.util.jdbc.SQLHelper;



/**
 * 文本观点倾向的定量计算算法
 * @version 1.0.0 2015-11-30
 * @author zjd
 */
public class OrientationCompute {
	
	 private static final double BETA = 0.5;                      //算法2的加权系数BETA>=0
    private static final int BASEWORD_COUNT = 40;                //褒/贬基准词表词数
    private static List<SentimentWordItem> sentimentWords;   //情感词库词语
    private static Map<String, Float> emoticons;              //表情基准词库
    
    //加载词库，只需要一次
    static{
    	 loadEmotionDic();       //加载情感词库
    	 loadEmoticons();        //加载表情基准库
    }
    
    /**
     * 加载情感词库
     */
    private static void loadEmotionDic(){
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from emotion_dictionary";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			//sentimentWords = LoadDictionaryHelper.loadSentimentWords(rs);          //获得情感词库中情感单词
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    /**
     * 加载表情基准库
     */
    private static void loadEmoticons(){
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from emoticon_baseword";
    	ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			//emoticons =  LoadDictionaryHelper.loadEmoticons(rs);               //获得贬义基准词库中单词
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}     

    }

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	//	new OrientationCompute().calcDSOofSentence3( "", sentimentWords, positiveWords, negativeWords);
	//	System.out.println(new OrientationCompute().calcDSOofSentence("在杭州玩的超开心www不想回日本了QAQ 我在这里:", sentimentWords));
	}

}
