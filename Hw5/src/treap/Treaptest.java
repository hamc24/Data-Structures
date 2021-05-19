package treap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Treaptest {
	
	Treap<String> T1 = new Treap<String>();
	Treap<Integer> T2 = new Treap<Integer>();
	
	public void StringTest1() {
		assertEquals(T1.add("b", 19), true);
		assertEquals(T1.add("c", 31), true);
		assertEquals(T1.add("a", 11), true);
		assertEquals(T1.add("b", 20), false);
		assertEquals(T1.delete("a"), true);
		assertEquals(T1.find("b"), true);
		assertEquals(T1.find("a"), false);
	}
	
	public void StringTest2() {
		assertEquals(T2.add(4, 19), true);
		assertEquals(T2.add(2, 31), true);
		assertEquals(T2.add(6, 70), true);
		assertEquals(T2.add(1, 84), true);
		assertEquals(T2.add(3, 12), true);
		assertEquals(T2.add(5, 83), true);
		assertEquals(T2.add(7, 26), true);
		assertEquals(T2.find(1), true);
		assertEquals(T2.find(8), false);
	}
}
