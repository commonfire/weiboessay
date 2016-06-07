package oldbackup.segtoolcomputation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.test.WordSegAnsj;

import edu.bupt.util.dict.LoadDictionaryHelper;
import edu.bupt.util.jdbc.SQLHelper;

/**
 * 结合已有分词工具进行“新词发现”
 * @version 1.0.0 2015-11-30
 * @author zjd
 */
/**
 * @author DELL
 *
 */
public class FindNewWordByTool {
	
	private static List<String> stopwords;              //停用词词库
	static{
		loadStopwordDic();
	}
	
    /**
     * 加载停用词词库
     */
    private static void loadStopwordDic(){
    	Connection conn = SQLHelper.getConnection();
    	String sql = "select * from stopword";
		ResultSet rs;
		try {
			rs = SQLHelper.executeQuery(sql, null, conn);
			stopwords = LoadDictionaryHelper.loadStopword(rs);          //获得情感词库中情感单词
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
	 * 利用分词工具发现新词
	 * @param subSentence  微博子句
	 * @param stopwords    停用词词库
	 * @return			      发现新词的结果列表
	 */
	public static List<String> findNewWordByTool(String subSentence, List<String> stopwords){
		List<String> result = new ArrayList<>();
		List<Term> parsedWords = ToAnalysis.parse(subSentence);
		List<String> parseWordsNoPro = WordSegAnsj.splitOriginal(parsedWords); //不含词性的分词结果
		List<Integer> stopPos = new ArrayList<>();  //记录停用词的位置
		for(int i = 0;i < parseWordsNoPro.size();i++) if(stopwords.contains(parseWordsNoPro.get(i))) stopPos.add(i);
		
		if(0 == stopPos.size()){   //没有停用词，直接组合词语
			StringBuilder sb = new StringBuilder();
			for(int wordIndex = 0;wordIndex < parseWordsNoPro.size();wordIndex++){
				//组合单字词
				//if(1 == String.valueOf(parseWordsNoPro.get(wordIndex)).length()) sb.append(String.valueOf(parseWordsNoPro.get(wordIndex)));
				sb.append(String.valueOf(parseWordsNoPro.get(wordIndex)));
			}
			if(!"".equals(sb.toString())) result.add(sb.toString());
		}
		else{  //存在停用词
			for(int posIndex = 0; posIndex < stopPos.size();posIndex++){
				StringBuilder sb = new StringBuilder();
				if(posIndex == stopPos.size()-1){  //新词可能出现在最后一个停用词和句尾之间
					for(int wordIndex = stopPos.get(posIndex)+1;wordIndex < parseWordsNoPro.size();wordIndex++){
						sb.append(String.valueOf(parseWordsNoPro.get(wordIndex)));
					}
				}
				else{
					if(posIndex == 0){  //新词可能出现在第一个停用词和句首之间
						for(int wordIndex = 0;wordIndex < stopPos.get(posIndex);wordIndex++){
							sb.append(String.valueOf(parseWordsNoPro.get(wordIndex)));
						}
					}
		
					for(int wordIndex = stopPos.get(posIndex)+1;wordIndex < stopPos.get(posIndex+1);wordIndex++){
						//相邻两停用词间只有一个词，则放弃
						if(stopPos.get(posIndex+1)-stopPos.get(posIndex)>2) sb.append(String.valueOf(parseWordsNoPro.get(wordIndex)));
					}
				}
				if(!"".equals(sb.toString())) result.add(sb.toString());
			}
		}
		return result;
	}
	
	
	public static void main(String[] args) {
//		List<String> result = new ArrayList<>();
//		List<String> set = new ArrayList<String>(){{add("难道");add("没有");add("吗");}};
		//"难道社会没有正能量吗" 他是个高帅富他是个屌丝,但是很帅"  "他是个屌丝"
//		System.out.println("他是个屌丝");
//		System.out.println(findNewWordByTool("他是个屌丝", stopwords));
		Integer a = 1;
		Integer b = 1;
	}
}
