package com.kaidin.appframe.entity;

import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class BatchSequenceGeneratorTest {

	@Test
	public void test() throws Exception {
		InputStream is = BatchSequenceGenerator.class.getResourceAsStream("/cfg/sequence.properties");
		if (is == null) {
			throw new Exception("Can not find sequence.properties.");
		}
		Properties p = new Properties();
		p.load(is);
		for (Object obj: p.keySet()) {
			String sequence = String.valueOf(obj).toUpperCase();
			System.out.println(sequence + ",");
		}
	}
}
