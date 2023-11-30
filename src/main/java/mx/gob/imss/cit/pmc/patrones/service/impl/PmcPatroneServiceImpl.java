package mx.gob.imss.cit.pmc.patrones.service.impl;

import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import mx.gob.imss.cit.mspmccommons.dto.FraccionJsonDTO;
import mx.gob.imss.cit.mspmccommons.dto.PatronDTO;
import mx.gob.imss.cit.mspmccommons.dto.PatronJsonDTO;
import mx.gob.imss.cit.mspmccommons.enums.EnumHttpStatus;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.utils.DigitoVerificadorUtils;
import mx.gob.imss.cit.pmc.patrones.service.PmcPatroneService;

@Component
public class PmcPatroneServiceImpl implements PmcPatroneService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("resource")
	Formatter fmt = new Formatter();

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	@Override
	public PatronDTO obtenerPatron(String regPatronal) throws BusinessException {
		ResponseEntity<PatronJsonDTO> response = null;
		try {
			if ((regPatronal != null && !regPatronal.trim().equals("")) && regPatronal.length() == 10) {
				regPatronal = regPatronal.concat(DigitoVerificadorUtils.generaDigitoVerificadorRP(regPatronal));
			}
			String urlPatron = env.getProperty("urlPatronMSValicacionAlmacenes");
			response = urlPatron != null ? restTemplate.getForEntity(urlPatron.concat(regPatronal), PatronJsonDTO.class) : null;
			
			/*Validamos que el Digito verificador sea correcto - Si es incorrecto se manda excepcion */
			if ((regPatronal != null && !regPatronal.trim().equals("")) &&  regPatronal.length() >= 11) {
				Integer dv = Integer.valueOf(regPatronal.substring(10,11));
				Boolean isDVOK = false;
				PatronJsonDTO digVerificador = response != null ? response.getBody() : null;
				if(digVerificador != null) {
					isDVOK = digVerificador.getDigVerificador().equals(dv);
				}
				
				if (Boolean.FALSE.equals(isDVOK)) {
					fmt.close();
					throw new BusinessException(EnumHttpStatus.SERVER_ERROR_INTERNAL, "Digito verificador erroneo -> ", "El digito verificador no corresponde");
				}
			}			
		} catch (RestClientException e) {
			logger.info("No existe información: {}", regPatronal);
		} catch (RuntimeException e1) {
			logger.info("Error en digito verificador: {}", regPatronal);
			response = null;
		}
		PatronJsonDTO patronBody = response != null ? response.getBody() : null;
		
		return llenarDatosPatron(patronBody, regPatronal);
		
	}	
	
	private String  returnCveFraccion(FraccionJsonDTO fracion) {
		String cve="";
		String numFrac="";
	    
		if(fracion != null ) {
			if(fracion.getNumFraccion() < 10) {
				numFrac="0"+fracion.getNumFraccion() ;
			}else {
				numFrac=String.valueOf(fracion.getNumFraccion()) ;
			}
			cve=fracion.getGrupo().getDivision().getNumDivision()+""+fracion.getGrupo().getNumGrupo()+numFrac;
		}
		
		return cve;
		
	}
	
	private PatronDTO llenarDatosPatron(PatronJsonDTO patronBody, String regPatronal) {
		PatronDTO patronDTO = null;
		
		if (patronBody != null) {			
			try {
				patronDTO = new PatronDTO();
				patronDTO.setCveClase(patronBody.getClasificacion().getFraccion().getClase().getClave());			
				patronDTO.setDesClase(patronBody.getClasificacion().getFraccion().getClase().getDescripcion());				
				patronDTO.setCveFraccion(Integer.valueOf(returnCveFraccion(patronBody.getClasificacion().getFraccion())));
				patronDTO.setDesFraccion((patronBody.getClasificacion().getFraccion().getDescripcion()));
				patronDTO.setCveDelRegPatronal(patronBody.getSubdelegacion().getDelegacion().getClave());
				patronDTO.setDesDelRegPatronal(patronBody.getSubdelegacion().getDelegacion().getDescripcion());
				patronDTO.setCveSubDelRegPatronal(patronBody.getSubdelegacion().getClave());
				patronDTO.setDesSubDelRegPatronal(patronBody.getSubdelegacion().getDescripcion());
				patronDTO.setNumPrima(patronBody.getClasificacion().getPrimaSRTActual());
				if (patronBody.getMoral() != null) {
					patronDTO.setDesRazonSocial(patronBody.getMoral().getRazonSocial());
					patronDTO.setDesRfc(patronBody.getMoral().getRfc());
				}
				else if (patronBody.getFisica() != null) {
					StringBuilder razonSocialFisica = new StringBuilder();
					if(patronBody.getFisica().getNombre() != null)
						razonSocialFisica.append(patronBody.getFisica().getNombre());
					if(patronBody.getFisica().getPrimerApellido() != null) {
						razonSocialFisica.append(" ");
						razonSocialFisica.append(patronBody.getFisica().getPrimerApellido());
					}
					if(patronBody.getFisica().getSegundoApellido() != null) {
						razonSocialFisica.append(" ");
						razonSocialFisica.append(patronBody.getFisica().getSegundoApellido());
					}
					patronDTO.setDesRazonSocial(razonSocialFisica.toString());
					patronDTO.setDesRfc(patronBody.getFisica().getRfc());
				}
				patronDTO.setRefRegistroPatronal(regPatronal);
				patronDTO.setCveDelegacionAux(String
						.valueOf(fmt.format("%02d", patronBody.getSubdelegacion().getDelegacion().getId())));
			} catch (Exception e) {
				logger.error("Error obtenerPatron: ", e);
			}
		}
		
		return patronDTO;
		
	}

}
