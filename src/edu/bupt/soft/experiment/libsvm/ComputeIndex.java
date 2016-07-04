package edu.bupt.soft.experiment.libsvm;

public class ComputeIndex {
	public static double computeFValue(double precision, double recall) {
		return (2 * precision * recall) / (precision + recall);
	}
	
	public static void main(String[] args) {
		System.out.println(computeFValue(0.693f, 0.558f));
	}
	
}
