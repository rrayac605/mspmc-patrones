package mx.gob.imss.cit.pmc.patrones.service;

import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.patrones.input.ValidacionLocalInput;

public interface PmcValidacionService {

	DetalleRegistroDTO validaRegistro(ValidacionLocalInput input) throws BusinessException;

}
