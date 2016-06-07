package edu.bupt.util.dict;


/**
 * 基础情感词词库的每个条目
 * @author DELL
 * @version 创建时间 2016年6月6日下午11:00:08 1.0
 */
public class SentimentWordItem {
	private String phrase;
	private float power;

	public float getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
}
