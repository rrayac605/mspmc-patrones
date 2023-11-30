package mx.gob.imss.cit.pmc.patrones.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.mspmccommons.dto.BitacoraErroresDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.PatronDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.patrones.input.ValidacionLocalInput;
import mx.gob.imss.cit.pmc.patrones.service.PmcPatroneService;
import mx.gob.imss.cit.pmc.patrones.service.PmcValidacionService;

@Component
public class PmcValidacionServiceImpl implements PmcValidacionService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PmcPatroneService pmcPatroneService;

	@Override
	public DetalleRegistroDTO validaRegistro(ValidacionLocalInput input) throws BusinessException {
		BitacoraErroresDTO bitacoraErroresDTO = null;
		List<BitacoraErroresDTO> errores = new ArrayList<>();
		PatronDTO patronDTO = null;
		DetalleRegistroDTO detalle = new DetalleRegistroDTO();
		logger.info("Validando cambios");

		patronDTO = pmcPatroneService.obtenerPatron(input.getRp());

		if (patronDTO == null) {
			logger.info("No existe el rp: [{}]", input.getRp());
			bitacoraErroresDTO = new BitacoraErroresDTO();
			bitacoraErroresDTO.setDesCodigoError("No existe el RP en BDTU");
			errores.add(bitacoraErroresDTO);
		}		
		if (errores.isEmpty()) {
			detalle.setPatronDTO(patronDTO);

		} else {
			detalle.setBitacoraErroresDTO(errores);
		}

		return detalle;
	}
}
