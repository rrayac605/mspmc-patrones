package mx.gob.imss.cit.pmc.validacionalmacenes;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mx.gob.imss.cit.pmc.patrones.service.PmcValidacionService;

@SpringBootTest
class MspmcValidacionalmacenesApplicationTests {
	
	@Autowired
	PmcValidacionService validacion;

	@Test
	void contextLoads() {
		assertNotNull(validacion);
	}

}
