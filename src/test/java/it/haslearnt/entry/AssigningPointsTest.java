package it.haslearnt.entry;

import static org.junit.Assert.assertEquals;
import it.haslearnt.entry.Entry.TimeType;

import org.junit.*;

public class AssigningPointsTest {

	@Test
	public void shouldAssignZeroPointsForZeroLearningTime() throws Exception {
		Entry entry = new Entry().today().iveLearnt("java").andItWas("hard").itTook(0, TimeType.MINUTES);

		entry.build();

		assertEquals(0, entry.points());
	}

	@Test
	public void calculatePointsForEasy() throws Exception {
		Entry entry = new Entry().today().iveLearnt("java").andItWas("easy").itTook(10, TimeType.MINUTES);

		entry.build();

		assertEquals(10, entry.points());

	}

	@Test
	public void calculatePointsForHard() throws Exception {
		Entry entry = new Entry().today().iveLearnt("java").andItWas("hard").itTook(10, TimeType.MINUTES);

		entry.build();

		assertEquals(14, entry.points());

	}

}
