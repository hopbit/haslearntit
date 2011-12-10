package it.haslearnt.entry;

import static org.junit.Assert.assertEquals;


import org.junit.*;

public class AssigningPointsTest {

	Entry sampleEntry = new Entry().today().iveLearnt("java");

	@Test
	public void shouldAssignZeroPointsForZeroLearningTime() throws Exception {
		Entry entry = sampleEntry.itTookInMinutes(0);

		entry.build();

		assertEquals(0, entry.points());
	}

	@Test
	public void calculatePointsForEasy() throws Exception {
		Entry entry = sampleEntry.andItWas("easy").itTookInMinutes(10);

		entry.build();

		assertEquals(10, entry.points());

	}

	@Test
	public void calculatePointsForMedium() throws Exception {
		Entry entry = sampleEntry.andItWas("medium").itTookInMinutes(10);

		entry.build();

		assertEquals(12, entry.points());

	}

	@Test
	public void calculatePointsForHard() throws Exception {
		Entry entry = sampleEntry.andItWas("hard").itTookInMinutes(10);

		entry.build();

		assertEquals(14, entry.points());

	}
}
