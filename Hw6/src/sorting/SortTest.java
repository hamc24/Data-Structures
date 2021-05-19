package sorting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class SortTest {

	@Test
	void test() {
		Integer[] t1 = {23, 33, 44, 90, 1};
		Sort.sort(t1);
		Integer[] sortedt1 = {1, 23, 33, 44, 90};
		assertEquals(Arrays.equals(t1, sortedt1), true);
		
		Integer[] t2 = {6, 4, 1, 3, 2, 5};
		Sort.sort(t2);
		Integer[] sortedt2 = {1, 2, 3, 4, 5, 6};
		assertEquals(Arrays.equals(t2, sortedt2), true);
		
		Integer[] t3 = {-1, 2, 20, 14, -2, 4};
		Sort.sort(t3);
		Integer[] sortedt3 = {-2, -1, 2, 4, 14, 20};
		assertEquals(Arrays.equals(t3, sortedt3), true); 
	}

}
