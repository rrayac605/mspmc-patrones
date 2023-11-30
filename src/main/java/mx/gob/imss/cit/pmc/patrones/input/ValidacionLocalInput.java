package mx.gob.imss.cit.pmc.patrones.input;

import lombok.Getter;
import lombok.Setter;

public class ValidacionLocalInput {
	
	@Override
	public String toString() {
		return "ValidacionLocalInput [nss=" + nss + ", rp=" + rp + ", fechaInicio=" + fechaInicio + ", tipoRiesgo="
				+ tipoRiesgo + ", consecuencia=" + consecuencia + ", diasSubsidiados=" + diasSubsidiados
				+ ", procentaje=" + procentaje + ", fechaFin=" + fechaFin + "]";
	}

	@Getter
	@Setter
	private String nss;
	
	@Getter
	@Setter
	private String rp;
	
	@Getter
	@Setter
	private String fechaInicio;
	
	@Getter
	@Setter
	private String tipoRiesgo;
	
	@Getter
	@Setter
	private String consecuencia;
	
	@Getter
	@Setter
	private String diasSubsidiados;
	
	@Getter
	@Setter
	private String procentaje;
	
	@Getter
	@Setter
	private String fechaFin;
	
	@Getter
	@Setter
	private boolean cambioNss;
	
	@Getter
	@Setter
	private boolean cambioRp;

}
