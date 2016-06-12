package oldbackup.semantic_rules_analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.ansj.test.WordSegAnsj;

import edu.bupt.util.dict.SentimentWordItem;

public class SentenceProcessor {

//	public String[] delimiter=new String[]{"。","；","！","？",".",";","!","?"};
	
	/**
	 * 输入一篇博客，返回一个句子的列表
	 * @param blogContent  输入博文内容
	 * @return  返回分割的句子列表
	 */
	public static List<String> SplitToSentences(String blogContent){
		ArrayList<String> sentenceList = new ArrayList<String>();
		if(blogContent!=null){
			String[] sentenceArray = blogContent.split("。|；|！|？|\\.|;|!|\\?|\\s+");
			sentenceList = new ArrayList<String>(Arrays.asList(sentenceArray));
				for(int i = sentenceList.size()-1;i>=0;i--){
					if(sentenceList.get(i).equals("")){
						sentenceList.remove(sentenceList.get(i));
					}	
				}
		}
		return sentenceList;
	}
	
	/**
	 * @param sentence1          待计算的句子1
	 * @param sentence2          待计算的句子2
	 * @return              返回两个句子距离，即相似度值
	 * @throws Exception
	 */
	public double sentenceDistance(String sentence1,String sentence2) throws Exception{
		List<String> str1 = WordSegAnsj.split(sentence1);     //对句子1分词
		List<String> str2 = WordSegAnsj.split(sentence2);     //对句子2分词
		double distance = SentSimilarity.getSimilarity(str1, str2);
		return distance;
	}
	
	/**
	 * 输入一个句子列表，返回观点句子列表
	 * @param sentenceList    输入句子列表  
	 * @param sentimentWords  输入情感词表
	 * @return                返回观点句子列表  
	 */
	public ArrayList<String> getSentimentSentences(ArrayList<String> sentenceList, ArrayList<SentimentWordItem> sentimentWords){
		ArrayList<String> sentimentList = new ArrayList<String>();
		if(sentenceList.size()!=0){
			for (int i = 0;i < sentenceList.size();i++){
	        	for(int j = 0; j < sentimentWords.size();j++){
	        		if(sentenceList.get(i).contains(sentimentWords.get(j).getPhrase())){
	        			sentimentList.add(sentenceList.get(i));
	        		}
	        	}
	        }
		}
		return sentimentList;
	}
	

	/**
	 * 输入一个句子列表，返回非观点句子列表
	 * @param sentenceList    输入句子列表  
	 * @param sentimentWords  输入情感词表
	 * @return                返回非观点句子列表  
	 */
	public ArrayList<String> getNonSentimentSentences(ArrayList<String> sentenceList, ArrayList<SentimentWordItem> sentimentWords){
		ArrayList<String> nonSentimentList = new ArrayList<String>();      	
        	for(int i = sentenceList.size()-1;i >= 0;i--){
            	for(int j = 0; j < sentimentWords.size();j++){
            		if(sentenceList.get(i).contains(sentimentWords.get(j).getPhrase())){
            			sentenceList.remove(i);
            			break;
            	  }
            	}
            }
        	nonSentimentList = sentenceList;
		return nonSentimentList;
	}
	
	/**
	 * 输入一个句子，返回判断该句是否为情感句结果（用于网页前端的内容）
	 * @param sentence        输入句子 
	 * @param sentimentWords  输入情感词表
	 * @return                返回判断该句是否为情感句结果
	 */
	public String isSentimentSentences(String sentence, ArrayList<SentimentWordItem> sentimentWords){
                String result = "否";
	        	for(int j = 0; j < sentimentWords.size();j++){
	        		if(sentence.contains(sentimentWords.get(j).getPhrase())){
	        			result = "是";   //该句是观点句
	        		}
	        	}
	        	return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String blog = "片头笑尿了";
		System.out.println(SplitToSentences(blog));
	}

}
