import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.libresource.so6.core.client.ClientI;

import fr.loria.ecoo.dso6.core.BasicClientImpl;

public class BasicHTTPTest {

	private ClientI client;

	
	// TODO: write the endpoint uri in the property file during checkout
	// TODO: write sendPatch method
	
	@Before
	public void setUp() {
		//client = new BasicClientImpl();
	}
	@Test
	public void testGetTicket() throws Exception {
		assertTrue(client.getLastTicket() == 1);
	}

	@Test
	public void listPatches() throws Exception {
		long[][] expected = new long[][] { { 1, 2, 10 }, { 3, 4, 11 } };
		long[][] res = client.listPatch();
		assertTrue("expected: "+toString(expected)+" but got "+toString(res), equals(expected, res));
	}

	
	@Test
	public void getPatchFrom() throws Exception {
		String filePath = client.getPatch(1);
		assertTrue((new File(filePath)).length() == 10);
		filePath = client.getPatch(3);
		assertTrue((new File(filePath)).length() == 11);
	}
	
	@Test
	public void getPatchFromTo() throws Exception {
		String filePath = client.getPatch(1,2);
		assertTrue((new File(filePath)).length() == 10);
		filePath = client.getPatch(3,4);
		assertTrue((new File(filePath)).length() == 11);
	}
	
	
	private static boolean equals(long[][] tab1, long[][] tab2) {
		if (tab1.length != tab2.length)
				return false;
		for (int i=0; i<tab1.length; i++) {
			if (! Arrays.equals(tab1[i], tab2[i])) {
				return false;
			}
		}
		return true;
	}
	
	private static String toString(long[][] tab) {
		StringBuffer b = new StringBuffer();
		b.append("[");
		for (int i=0; i<tab.length; i++) {
			b.append(" "+Arrays.toString(tab[i]));
		}
		b.append(" ]");
		return b.toString();	
	}		
}
