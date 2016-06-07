package oldbackup.newworddetection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 新词发现算法实现Beta
 * @version 1.0.0 2015-11-11
 * @author zjd
 */
public class NewWordDetection {
	private NewWordDetection newWordDetector;
	private Map<String, TermCountNeighbor> wordTermCountNeighbor;  //含有字串总信息的映射
	private int totalWordNumber;  //文本的总字符数
	
	public int getTotalWordNumber() {
		return totalWordNumber;
	}

	private final static String stopwords = "的了呢是嘛个也比还于与就在和对去";
	
	public NewWordDetection(){
		wordTermCountNeighbor = new HashMap<String, TermCountNeighbor>();
	}
	
	/**
	 * 对微博文本内容进行N元分词
	 * @param content  微博文本内容
	 * @param N        N元分词
	 * @return         分词结果列表
	 */
	public static HashSet<String> splitWordByN(String content, int N){
		HashSet<String> result = new HashSet<String>();
		for(int i = 0;i < content.length()-(N-1);i++){
			result.add(content.substring(i, i+N));
		}
		return result;	
	}
 
	//TODO public static void countSingleTerm(String candidateStr,String phrase){}
	
	/**
	 * 计算词语的频数和左右邻近字集
	 * @param subSentence	待搜索的文本子句
	 * @param N             N元分词
	 */
	public void countTermNeighbor(String subSentence,int N){
		String candidateStr = null;
		int n;
		if(null != subSentence){
			totalWordNumber += subSentence.length(); //用于记录总词数
			for(int i = 0;i < subSentence.length()-1;i++){
				n = 2;
				while(n <= N){
					if((i+n) <= subSentence.length()){
						candidateStr = subSentence.substring(i, i+n);
						TermCountNeighbor termCountNeighbor = wordTermCountNeighbor.get(candidateStr);
						if(termCountNeighbor == null)  termCountNeighbor = new TermCountNeighbor();	
						wordTermCountNeighbor.put(candidateStr, termCountNeighbor);
				
						termCountNeighbor.increaseTermCount();
						
						if(i!=0)termCountNeighbor.addToLeftNeighbor(subSentence.charAt(i-1)); //匹配到的串的位置不在文本首,即存在左邻字
						if((i+n)!=subSentence.length())termCountNeighbor.addToRightNeighbor(subSentence.charAt(i+n)); //匹配到的串的位置不在文本末，即存在右邻字
						
						System.out.println(candidateStr+" aWordNumber:"+wordTermCountNeighbor.get(candidateStr).getTermCount());
						System.out.println("left "+wordTermCountNeighbor.get(candidateStr).getLeftNeighbor());
						System.out.println("right "+wordTermCountNeighbor.get(candidateStr).getRightNeighbor());
					}
					else break;
					n++;
				}	
			}
		}
	}
	
	public static void findNewWord(){
		
	}
	
	public static void main(String[] args) {
		
		String test1 = "吃葡萄不吐葡萄皮不吃葡萄倒吐葡萄皮";
		String test2 = "今天买了斤葡萄，葡萄很酸，以后再也不买葡萄了。";
//		String test1 = "中国银行信用卡太坑爹了，一年前电话注销的信用卡产生了188年费。md，什么东西。刷五次可以申请免除，卡都剪了，上哪刷去。";
//		String test2 = "双十一果然坑爹啊！淘宝拍下的款式和今天收到的实物差距也太大了吧！难道是去韩国整过容？！";
		
 		String[] testArray = new String[]{test1,test2};
		
		NewWordDetection newWordDetector = new NewWordDetection();		
		
		for(String test : testArray){
			System.out.println("~~~~~~~~~~~~~~~~");
			//对于每一条test的微博：		
			String[] phrases = test.split("[^\u4E00-\u9FA5]+|["+stopwords+"]");  //非汉字及停用词
			for(String phrase : phrases){
			
				System.out.println("!!!!!!"+phrase);
				newWordDetector.countTermNeighbor(phrase,4);

				System.out.println("----------------");
			}
		}
		System.out.println("总字数:"+newWordDetector.getTotalWordNumber());
	}

}
