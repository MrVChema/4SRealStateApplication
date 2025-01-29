package com.cuatroSReal.model;

import java.util.List;

public class FiltrosConsultaDatosRangosModel {
	private List<String> estados;
    private List<String> tipos;
    private List<String> zonas;
    private List<String> segmentos;
    private String iniBusc;
    private String finBusc;
    private List<String> periodoScrap;
    private String rangosPrecio;
    private String indicadorMonto;
    
	public String getIndicadorMonto() {
		return indicadorMonto;
	}
	public void setIndicadorMonto(String indicadorMonto) {
		this.indicadorMonto = indicadorMonto;
	}
	public String getRangosPrecio() {
		return rangosPrecio;
	}
	public void setRangosPrecio(String rangosPrecio) {
		this.rangosPrecio = rangosPrecio;
	}
	public String getFinBusc() {
		return finBusc;
	}
	public void setFinBusc(String finBusc) {
		this.finBusc = finBusc;
	}
	public String getIniBusc() {
		return iniBusc;
	}
	public void setIniBusc(String iniBusc) {
		this.iniBusc = iniBusc;
	}
	public List<String> getSegmentos() {
		return segmentos;
	}
	public void setSegmentos(List<String> segmentos) {
		this.segmentos = segmentos;
	}
	public List<String> getZonas() {
		return zonas;
	}
	public void setZonas(List<String> zonas) {
		this.zonas = zonas;
	}
	public List<String> getTipos() {
		return tipos;
	}
	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}
	public List<String> getEstados() {
		return estados;
	}
	public void setEstados(List<String> estados) {
		this.estados = estados;
	}
	public List<String> getPeriodoScrap() {
		return periodoScrap;
	}
	public void setPeriodoScrap(List<String> periodoScrap) {
		this.periodoScrap = periodoScrap;
	}
    
}
