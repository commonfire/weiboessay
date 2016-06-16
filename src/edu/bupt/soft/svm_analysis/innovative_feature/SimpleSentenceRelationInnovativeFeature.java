package edu.bupt.soft.svm_analysis.innovative_feature;

import java.util.Arrays;
import java.util.List;

import com.zjd.nlpir.NlipirTools;

import edu.bupt.soft.semantic_rules_analysis.SentimentRulesAnalysisHelper;

/**
 * 利用连词标识词分析简单句的“句间关系”特征
 * @author DELL
 * @version 创建时间 2016年6月16日上午9:09:55 1.0
 */
public class SimpleSentenceRelationInnovativeFeature {
	private static final List<String> adversatives = Arrays.asList("不过","只不过","而","然而","然则","可是","可","虽然","虽","但","但是","却","反而","反倒","虽说","尽管","只是");   // 转折关系
	private static final List<String> progressives = Arrays.asList("而且","并且","况且","更","又","不但","不仅","不光","何况","尤其","甚至");   // 递进关系
	private static final List<String> hypotheses = Arrays.asList("如果","假如","假设","假若","要是","要不是","倘若","倘使","就是","就算");     // 假设关系
	private static final List<String> concessions = Arrays.asList("纵然","纵使","即使","诚然","诚","固然","固","宁","宁可","宁肯","宁愿");    // 让步关系
	
	/**
	 * 利用连词标识词计算简单句的“句间关系”特征
	 * @param complexSentence    待分析的复合整句
	 * @return					  表示复合句中句间四种关系的整数（由4为二进制数而得）
	 * @throws Exception 
	 */
	public static int computeSimpleSentenceRelationFeature(String complexSentence) throws Exception {
		int result = 0;
		String complexSentence1 = SentimentRulesAnalysisHelper.filterEmoticon(complexSentence);   // 去除简单句含有的表情符号
		List<String> wordBag = NlipirTools.parse(complexSentence1, 0); // 对简单句进行分词
		for (String word : wordBag) {
			if (concessions.contains(word)) {
				result = 1;    //此时复合句分句句间含有“让步关系”，对应0001
				break;
			}
			else if (hypotheses.contains(word)) {
				result += 1 << 1;   //此时复合句分句句间含有“假设关系”，对应0010
				break;
			}
			else if (adversatives.contains(word)) {
				result += 1 << 2;   //此时复合句分句句间含有“转折关系”，对应0100
				break;
			}
			else if (progressives.contains(word)) {
				result += 1 << 3;   //此时复合句分句句间含有“递进关系”，对应1000
				break;
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		int result = computeSimpleSentenceRelationFeature("尽管你不高兴[黑线]，但你要振作起来");
		System.out.println(result);
	}
}
