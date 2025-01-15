package com.cuatroSReal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cuatroSReal.model.CatalogDataModel;
import com.cuatroSReal.model.FiltrosConsultaBigDataModel;
import com.cuatroSReal.model.FiltrosConsultaDormitoriosModel;
import com.cuatroSReal.model.ReTechDataModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReTechDataService {

	private final RestTemplate restTemplate = new RestTemplate();

    public List<ReTechDataModel> getDataFromWebService(FiltrosConsultaBigDataModel filtros) {
    	LocalDate fechaActual = LocalDate.now();
		LocalDate fechaMesesMenos = fechaActual.minusMonths(3);
		
    	String url = "http://localhost:8080/api/consultar?";
    	String filtroEstado = "";
    	String filtroTipo = "";
    	String filtroZona = "";
    	String filtroSegmento = "";
    	String filtroPrecioFichaIni = "";
    	String filtroPrecioFichaFin = "";
    	String filtroPrecioM2Ini = "";
    	String filtroPrecioM2Fin = "";
    	String filtroFechaScrabIni = fechaMesesMenos.getYear() + "-" + String.format("%02d", fechaMesesMenos.getMonthValue());
    	String filtroFechaScrabFin = fechaActual.getYear() + "-" + String.format("%02d", fechaActual.getMonthValue());
    	
    	if(filtros != null){
    		if(filtros.getEstados() != null && !filtros.getEstados().isEmpty()) {
    			filtroEstado = String.join(",", filtros.getEstados() );
    		}
    		if(filtros.getTipos() != null && !filtros.getTipos().isEmpty()) {
    			filtroTipo = String.join(",", filtros.getTipos() );
    		}
    		if(filtros.getZonas() != null && !filtros.getZonas().isEmpty()) {
    			filtroZona = String.join(",", filtros.getZonas() );
    		}
    		if(filtros.getSegmentos() != null && !filtros.getSegmentos().isEmpty()) {
    			filtroSegmento = String.join(",", filtros.getSegmentos() );
    		}
    		if(filtros.getIniPrecioFBusc() != null && !filtros.getIniPrecioFBusc().isEmpty()) {
    			filtroPrecioFichaIni = filtros.getIniPrecioFBusc();
    		}
    		if(filtros.getFinPrecioFBusc() != null && !filtros.getFinPrecioFBusc().isEmpty()) {
    			filtroPrecioFichaFin = filtros.getFinPrecioFBusc();
    		}
    		if(filtros.getIniPrecioM2Busc() != null && !filtros.getIniPrecioM2Busc().isEmpty()) {
    			filtroPrecioM2Ini = filtros.getIniPrecioM2Busc();
    		}
    		if(filtros.getFinPrecioM2Busc() != null && !filtros.getFinPrecioM2Busc().isEmpty()) {
    			filtroPrecioM2Fin = filtros.getFinPrecioM2Busc();
    		}
    		if(filtros.getIniFecBusc() != null && !filtros.getIniFecBusc().isEmpty()) {
    			filtroFechaScrabIni = filtros.getIniFecBusc();
    		}
    		if(filtros.getFinFecBusc() != null && !filtros.getFinFecBusc().isEmpty()) {
    			filtroFechaScrabFin = filtros.getFinFecBusc();
    		}
    		
    	}
    	url += "estado=" + filtroEstado
    			+ "&tipo=" + filtroTipo
    			+ "&zona=" + filtroZona
    			+ "&segmento=" + filtroSegmento
    			+ "&precioFichaIni=" + filtroPrecioFichaIni
    			+ "&precioFichaFin=" + filtroPrecioFichaFin
    			+ "&preciom2Ini=" + filtroPrecioM2Ini
    			+ "&preciom2Fin=" + filtroPrecioM2Fin
    			+ "&fechaIni=" + filtroFechaScrabIni
    			+ "&fechaFin=" + filtroFechaScrabFin;
        
        ReTechDataModel[] response = restTemplate.getForObject(url, ReTechDataModel[].class);
        return List.of(response); 
    }
    
    public List<CatalogDataModel> getCatalogEstado() {
        String url = "http://localhost:8080/api/getEstados";
        CatalogDataModel[] response = restTemplate.getForObject(url, CatalogDataModel[].class);
        return List.of(response); 
    }
    
    public List<CatalogDataModel> getCatalogTipo() {
        String url = "http://localhost:8080/api/getTipo";
        CatalogDataModel[] response = restTemplate.getForObject(url, CatalogDataModel[].class);
        return List.of(response); // Convierte el array en una lista
    }
    
    public List<CatalogDataModel> getCatalogZona() {
        String url = "http://localhost:8080/api/getZona";
        CatalogDataModel[] response = restTemplate.getForObject(url, CatalogDataModel[].class);
        return List.of(response);
    }
    
    public List<CatalogDataModel> getCatalogSegmento() {
        String url = "http://localhost:8080/api/getSegmento";
        CatalogDataModel[] response = restTemplate.getForObject(url, CatalogDataModel[].class);
        return List.of(response);
    }
    
    public ResponseEntity<Object> getDormitoriosPrecio(FiltrosConsultaDormitoriosModel filtros) {
    	String url = "http://localhost:8080/api/getDormitoriosRango?";
    	String filtroEstado = "";
    	String filtroTipo = "";
    	String filtroZona = "";
    	String filtroSegmento = "";
    	String filtroPrecioFichaIni = "";
    	String filtroPrecioFichaFin = "";
    	String filtroPrecioM2Ini = "";
    	String filtroPrecioM2Fin = "";
    	String filtroFechaScrabIni = "";
    	String filtroFechaScrabFin = "";
    	String filtroRangosPrecio = "";
    	String filtroIndicadorMonto = "";
    	
    	if(filtros != null){
    		if(filtros.getEstados() != null && !filtros.getEstados().isEmpty()) {
    			filtroEstado = String.join(",", filtros.getEstados() );
    		}
    		if(filtros.getTipos() != null && !filtros.getTipos().isEmpty()) {
    			filtroTipo = String.join(",", filtros.getTipos() );
    		}
    		if(filtros.getZonas() != null && !filtros.getZonas().isEmpty()) {
    			filtroZona = String.join(",", filtros.getZonas() );
    		}
    		if(filtros.getSegmentos() != null && !filtros.getSegmentos().isEmpty()) {
    			filtroSegmento = String.join(",", filtros.getSegmentos() );
    		}
    		if(filtros.getIniPrecioFBusc() != null && !filtros.getIniPrecioFBusc().isEmpty()) {
    			filtroPrecioFichaIni = filtros.getIniPrecioFBusc();
    		}
    		if(filtros.getFinPrecioFBusc() != null && !filtros.getFinPrecioFBusc().isEmpty()) {
    			filtroPrecioFichaFin = filtros.getFinPrecioFBusc();
    		}
    		if(filtros.getIniPrecioM2Busc() != null && !filtros.getIniPrecioM2Busc().isEmpty()) {
    			filtroPrecioM2Ini = filtros.getIniPrecioM2Busc();
    		}
    		if(filtros.getFinPrecioM2Busc() != null && !filtros.getFinPrecioM2Busc().isEmpty()) {
    			filtroPrecioM2Fin = filtros.getFinPrecioM2Busc();
    		}
    		if(filtros.getIniFecBusc() != null && !filtros.getIniFecBusc().isEmpty()) {
    			filtroFechaScrabIni = filtros.getIniFecBusc();
    		}
    		if(filtros.getFinFecBusc() != null && !filtros.getFinFecBusc().isEmpty()) {
    			filtroFechaScrabFin = filtros.getFinFecBusc();
    		}
    		if(filtros.getRangosPrecio() != null && !filtros.getRangosPrecio().isEmpty()) {
    			filtroRangosPrecio = filtros.getRangosPrecio();
    		}
    		if(filtros.getIndicadorMonto() != null && !filtros.getIndicadorMonto().isEmpty()) {
    			filtroIndicadorMonto = filtros.getIndicadorMonto();
    		}
    	}
    	url += "estado=" + filtroEstado
    			+ "&tipo=" + filtroTipo
    			+ "&zona=" + filtroZona
    			+ "&segmento=" + filtroSegmento
    			+ "&precioFichaIni=" + filtroPrecioFichaIni
    			+ "&precioFichaFin=" + filtroPrecioFichaFin
    			+ "&preciom2Ini=" + filtroPrecioM2Ini
    			+ "&preciom2Fin=" + filtroPrecioM2Fin
    			+ "&fechaIni=" + filtroFechaScrabIni
    			+ "&fechaFin=" + filtroFechaScrabFin
    			+ "&rangosPrecio=" + filtroRangosPrecio
    			+ "&indicadorMonto=" + filtroIndicadorMonto;
        
    	try {
            // Llama al web service y obtiene la respuesta como un JSONObject
            String responseString = restTemplate.getForObject(url, String.class);

            // Convierte el String a un JSONObject (u otra estructura como Map)
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(responseString, new TypeReference<Map<String, Object>>() {});

            // Devuelve la respuesta envuelta en ResponseEntity
            return ResponseEntity.ok(responseMap);

        } catch (Exception e) {
            // Maneja errores en la solicitud
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
    
    
}
