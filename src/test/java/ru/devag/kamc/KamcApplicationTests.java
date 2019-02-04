package ru.devag.kamc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KamcApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCadnum() {
		assert(ImportService.getCadnum("41:01:0010130:537").equals("41:01:0010130:00537"));
	}
}
