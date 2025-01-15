package com.cuatroSReal.init;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
	    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"})
public class cuatroSRealApplicationTests {

	@Test
	void contextLoads() {
	}

}
