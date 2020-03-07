package com.view;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CTent {
	public static void printQ(Queue queue){
		while(queue.peek()!=null){
			System.out.print(queue.remove()+" ");
		}
	}
	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<Integer>();
		Random rand = new Random(47);
		for(int i=0;i<10;i++){
			queue.offer(rand.nextInt(i+10));
		}
		printQ(queue);
		Queue<Character>  qc = new LinkedList<Character>();
		for(char c:"Brontosaurus".toCharArray()){
			qc.offer(c);
			printQ(qc);
			
		}
/*		Integer[] more = {1,2,3,4,5,6};
		List<Integer> ctlist = new ArrayList<Integer>();
		ctlist.addAll(Arrays.asList(more));
		Iterator<Integer>  it = ctlist.iterator();
		while(it.hasNext()){
			Integer p = it.next();
			System.out.print(":"+p);
		}
 		
		
		for(Integer tc:ctlist){
			
			System.out.println(tc);
		}*/
	}
}
