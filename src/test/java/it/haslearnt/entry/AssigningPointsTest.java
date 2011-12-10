package it.haslearnt.entry;

import static org.junit.Assert.assertEquals;
import it.haslearnt.entry.Entry.TimeType;

import org.junit.*;

public class AssigningPointsTest {

	Entry sampleEntry = new Entry().today().iveLearnt("java");

	@Test
	public void shouldAssignZeroPointsForZeroLearningTime() throws Exception {
		Entry entry = sampleEntry.itTook(0, TimeType.MINUTES);

		entry.build();

		assertEquals(0, entry.points());
	}

	@Test
	public void calculatePointsForEasy() throws Exception {
		Entry entry = sampleEntry.andItWas("easy").itTook(10, TimeType.MINUTES);

		entry.build();

		assertEquals(10, entry.points());

	}

	@Test
	public void calculatePointsForMedium() throws Exception {
		Entry entry = sampleEntry.andItWas("medium").itTook(10, TimeType.MINUTES);

		entry.build();

		assertEquals(12, entry.points());

	}

	@Test
	public void calculatePointsForHard() throws Exception {
		Entry entry = sampleEntry.andItWas("hard").itTook(10, TimeType.MINUTES);

		entry.build();

		assertEquals(14, entry.points());

	}
}
