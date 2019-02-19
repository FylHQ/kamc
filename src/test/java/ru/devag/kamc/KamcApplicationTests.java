package ru.devag.kamc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ru.devag.kamc.model.I3Relation;
import ru.devag.kamc.repo.I3RelationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class KamcApplicationTests {
	@Autowired
	I3RelationRepository rtnRepo;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCadnum() {
		assert (ImportService.getCadnum("41:01:0010130:537").equals("41:01:0010130:00537"));
	}

	@Test
	public void testRtnDate() throws ParseException {
		Date inDate = new SimpleDateFormat("dd.MM.yyyy").parse("12.01.1983");

		I3Relation rtn = new I3Relation();
      rtn.setCatCategoryId(999L);
      rtn.setRtnDate(inDate);
      rtn.setStsStatusId(2L);
      rtn.setRtnNumber(RandomStringUtils.random(10));
		rtnRepo.save(rtn);
		
		Date outDate = rtn.getRtnDate();
		rtnRepo.delete(rtn);

		assert(inDate.equals(outDate));
	}

}
