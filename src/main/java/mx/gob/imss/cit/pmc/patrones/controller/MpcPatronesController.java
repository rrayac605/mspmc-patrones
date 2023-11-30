package mx.gob.imss.cit.pmc.patrones.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.ErrorResponse;
import mx.gob.imss.cit.mspmccommons.enums.EnumHttpStatus;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.patrones.input.ValidacionLocalInput;
import mx.gob.imss.cit.pmc.patrones.service.PmcValidacionService;

@RestController
@Api(value = "Validadion Patrones PMC", tags = { "Validadion Patrones PMC Rest" })
@RequestMapping("/mspatrones/v1")
public class MpcPatronesController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PmcValidacionService pmcValidacionService;

	@RequestMapping("/health/ready")
	@ResponseStatus(HttpStatus.OK)
	public void ready() {
		// Indica que el ms esta listo para recibir peticiones
	}

	@RequestMapping("/health/live")
	@ResponseStatus(HttpStatus.OK)
	public void live() {
		// Indica que el ms esta vivo
	}

	@ApiOperation(value = "Patrones", nickname = "patrones", notes = "Patrones", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/obtener", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object obtener(@RequestBody ValidacionLocalInput input) throws BusinessException {
		Object resultado = null;
		try {
			logger.info("MpcPatronesController:obtener:try {}", input);
			DetalleRegistroDTO listado = pmcValidacionService.validaRegistro(input);
			resultado = new ResponseEntity<Object>(listado, HttpStatus.OK);
			logger.info("MpcPatronesController:obtener:returnOk");
		} catch (BusinessException be) {
			logger.info("MpcPatronesController:obtener:catch");
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(),
					"Error de aplicaci\u00F3n");

			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());

			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info("MpcPatronesController:obtener:numberHTTPDesired");

		}

		logger.info("MpcPatronesController:obtener:FinalReturn");
		return resultado;
	}

}
