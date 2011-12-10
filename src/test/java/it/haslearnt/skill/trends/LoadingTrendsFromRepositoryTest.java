package it.haslearnt.skill.trends;

import static org.junit.Assert.*;
import it.haslearnt.entry.*;
import it.haslearnt.entry.Entry.TimeType;

import java.util.*;

import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import setup.*;

public class LoadingTrendsFromRepositoryTest extends IntegrationTest {

	@Autowired
	SkillTrendsRepository repository;

	@Autowired
	EntryRepository entryRepository;

	@Test
	public void shouldLoadTopTrends() {
		// given
		String user = "test";
		String skill1 = "java";
		String skill2 = "cooking";
		String skill3 = "oop";
		Entry entry1 = new Entry().today().iveLearnt(skill1).andItWas("easy").itTook(1, TimeType.MINUTES).build();
		Entry entry2 = new Entry().today().iveLearnt(skill2).andItWas("easy").itTook(1, TimeType.MINUTES).build();
		Entry entry3 = new Entry().today().iveLearnt(skill3).andItWas("easy").itTook(1, TimeType.MINUTES).build();
		entryRepository.saveEntry(entry1, user);
		entryRepository.saveEntry(entry2, user);
		entryRepository.saveEntry(entry3, user);

		SkillTrend firstSkillTrend = new SkillTrend().withSkill(skill1).learntBy(10);
		SkillTrend secondSkillTrend = new SkillTrend().withSkill(skill2).learntBy(2);
		SkillTrend thirdSkillTrend = new SkillTrend().withSkill(skill3).learntBy(1);

		repository.save(thirdSkillTrend);
		repository.save(firstSkillTrend);
		repository.save(secondSkillTrend);

		// when
		List<SkillTrend> skillTrends = repository.loadTop(2);

		// then
		assertEquals(2, skillTrends.size());
		SkillTrend firstLoaded = skillTrends.get(0);
		SkillTrend secondLoaded = skillTrends.get(1);

		assertEquals(firstSkillTrend, firstLoaded);
		assertEquals(secondSkillTrend, secondLoaded);
	}

	@Test
	public void shouldLoadSkillTrend() throws Exception {

		SkillTrend firstSkillTrend = new SkillTrend().withSkill("skill1").learntBy(10);

		repository.save(firstSkillTrend);

		SkillTrend skillTrend = repository.load("skill1");

		assertNotNull(skillTrend);
		assertEquals("skill1", skillTrend.skill());
		assertEquals(10, skillTrend.learnedBy());

	}
}
