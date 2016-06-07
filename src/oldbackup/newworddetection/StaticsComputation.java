package oldbackup.newworddetection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 计算各个统计量指标，用于新词发现
 * @author zjd
 * @version 1.0.0 2015-11-12
 *
 */
public class StaticsComputation {
	
	/**
	 * 计算候选字串的左右邻近字集信息熵
	 * @param leftNeighbor   候选字串的左邻近字集
	 * @param rightNeighbor  候选字串的右邻近字集
	 * @return  候选字串的左右邻近字集信息熵(二者较小)
	 */
	public double getNeighborEntropy(Map<Character, Integer> leftNeighbor,Map<Character, Integer> rightNeighbor){
		if(null == leftNeighbor || null == rightNeighbor) return 0;
		if(0 == leftNeighbor.size()) return getSingleNeighborEntropy(leftNeighbor);
		if(0 == rightNeighbor.size()) return getSingleNeighborEntropy(rightNeighbor);
		return Math.min(getSingleNeighborEntropy(leftNeighbor), getSingleNeighborEntropy(rightNeighbor));	
	}
	
	/**
	 * 计算候选字串的左/右邻近字集信息熵, 用于辅助计算最终邻近集信息熵
	 * @param singleNeighbor  候选字串的左/右邻近字集
	 * @return  候选字串的左/右邻近字集信息熵
	 */
	public double getSingleNeighborEntropy(Map<Character, Integer> singleNeighbor){
		double entropy = 0;
		int sum = 0;
		for(int number : singleNeighbor.values()){
			entropy += number * Math.log(number);
			sum += number;
		}
		if(0 == sum) return 0;
		return Math.log(sum) - entropy/sum;
	}
	
	
	/**
	 * 计算内部凝聚度，即互信息值
	 * @param candidateStr   候选字串
	 * @param wordTermCountNeighbor 含有字串总信息的映射
	 * @param totalWordNumber 文本的总字符数
	 * @return   互信息值
	 */
	public double computeMI(String candidateStr, Map<String, TermCountNeighbor> wordTermCountNeighbor, int totalWordNumber){
		if(null == candidateStr || candidateStr.length() <= 1) return 0;
		List<Double> mi = new ArrayList<>();
		double fullProb = wordTermCountNeighbor.get(candidateStr).getTermCount()/totalWordNumber;
		for(int i = 0;i < candidateStr.length();i++){
			String leftStr = candidateStr.substring(0, i);
			String rightStr = candidateStr.substring(i);
			double leftProb = wordTermCountNeighbor.get(leftStr).getTermCount()/totalWordNumber;
			double rightProb = wordTermCountNeighbor.get(rightStr).getTermCount()/totalWordNumber;
			mi.add(fullProb/(leftProb + rightProb));
		}
		return Collections.min(mi);
	}

}
