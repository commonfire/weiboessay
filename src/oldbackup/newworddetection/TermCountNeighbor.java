package oldbackup.newworddetection;

import java.util.HashMap;
import java.util.Map;

/**
 * 获得候选字串对应的频数和相应的左右邻近字集
 * @version 1.0.0 2015-11-11
 * @author zjd
 */
public class TermCountNeighbor {
	private int termCount;  //候选字串的频数
	private Map<Character, Integer> leftNeighbor;   //候选字串的左邻近字集
	private Map<Character, Integer> rightNeighbor;  //候选字串的右邻近字集
	
	public TermCountNeighbor(){
		leftNeighbor = new HashMap<Character, Integer>();
		rightNeighbor = new HashMap<Character, Integer>();
	}
	
	/**
	 * 增加候选字符串的频数
	 */
	public void increaseTermCount(){
		termCount++;
	}
	
	public int getTermCount() {
		return termCount;
	}
	
	public Map<Character, Integer> getLeftNeighbor() {
		return leftNeighbor;
	}
	
	public Map<Character, Integer> getRightNeighbor() {
		return rightNeighbor;
	}
	
	
	/**
	 * 将候选字串的某个左字添加到左邻近字集中，同时记录相应的频数
	 * @param leftChar  候选字串的某个左字
	 */
	public void addToLeftNeighbor(Character leftChar){
		Integer charNumber = leftNeighbor.get(leftChar);
		leftNeighbor.put(leftChar,charNumber == null ? 1 : 1+charNumber);
	}
	
	/**
	 * 将候选字串的某个右字添加到右邻近字集中，同时记录相应的频数
	 * @param rightChar  候选字串的某个右字
	 */
	public void addToRightNeighbor(Character rightChar){
		Integer charNumber = rightNeighbor.get(rightChar);
		rightNeighbor.put(rightChar,charNumber == null ? 1 : 1+charNumber);
	}
	
	
	public static void main(String[] args) {
	}
}
