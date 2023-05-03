package com.MyFood;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MsEurekaApplicationTests {

	@Autowired
	private EurekaClient eurekaClient;

	@Test
	public void contextLoads() {
		assertNotNull(eurekaClient);
	}

	@Test
	public void eurekaServerRunning() {
		List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
		assertTrue(applications.size() > 0);
	}

}
