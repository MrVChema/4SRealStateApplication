package com.cuatroSReal.model;

import java.util.List;

public class FiltrosConsultaBigDataModel {
	private List<String> estados;
    private List<String> tipos;
    private List<String> zonas;
    private List<String> segmentos;
    private String iniPrecioFBusc;
    private String finPrecioFBusc;
    private String iniPrecioM2Busc;
    private String finPrecioM2Busc;
    private List<String> periodoScrap;
    private String latBusc;
    private String lngBusc;
    private String kmBusc;
    
	public String getFinPrecioM2Busc() {
		return finPrecioM2Busc;
	}
	public void setFinPrecioM2Busc(String finPrecioM2Busc) {
		this.finPrecioM2Busc = finPrecioM2Busc;
	}
	public String getIniPrecioM2Busc() {
		return iniPrecioM2Busc;
	}
	public void setIniPrecioM2Busc(String iniPrecioM2Busc) {
		this.iniPrecioM2Busc = iniPrecioM2Busc;
	}
	public String getFinPrecioFBusc() {
		return finPrecioFBusc;
	}
	public void setFinPrecioFBusc(String finPrecioFBusc) {
		this.finPrecioFBusc = finPrecioFBusc;
	}
	public String getIniPrecioFBusc() {
		return iniPrecioFBusc;
	}
	public void setIniPrecioFBusc(String iniPrecioFBusc) {
		this.iniPrecioFBusc = iniPrecioFBusc;
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
	public String getKmBusc() {
		return kmBusc;
	}
	public void setKmBusc(String kmBusc) {
		this.kmBusc = kmBusc;
	}
	public String getLngBusc() {
		return lngBusc;
	}
	public void setLngBusc(String lngBusc) {
		this.lngBusc = lngBusc;
	}
	public String getLatBusc() {
		return latBusc;
	}
	public void setLatBusc(String latBusc) {
		this.latBusc = latBusc;
	}

}
