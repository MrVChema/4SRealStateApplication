package com.cuatroSReal.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cuatroSReal.model.CatalogDataModel;
import com.cuatroSReal.model.FiltrosConsultaBigDataModel;
import com.cuatroSReal.model.FiltrosConsultaDatosRangosModel;
import com.cuatroSReal.model.ReTechDataModel;
import com.cuatroSReal.model.ResumenTipoModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReTechDataService {

	private final RestTemplate restTemplate = new RestTemplate();

    public List<ReTechDataModel> getDataFromWebService(FiltrosConsultaBigDataModel filtros) {
    	String url = "http://localhost:8080/api/consultar?";
    	String filtroEstado = "";
    	String filtroTipo = "";
    	String filtroZona = "";
    	String filtroSegmento = "";
    	String filtroPrecioFichaIni = "";
    	String filtroPrecioFichaFin = "";
    	String filtroPrecioM2Ini = "";
    	String filtroPrecioM2Fin = "";
    	String filtroPeriodoScrap = "";
    	String filtroLat = "";
    	String filtroLng = "";
    	String filtroKms = "";
    	
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
    		if(filtros.getPeriodoScrap() != null && !filtros.getPeriodoScrap().isEmpty()) {
    			filtroPeriodoScrap = String.join(",", filtros.getPeriodoScrap() );
    		}
    		if(filtros.getLatBusc() != null && !filtros.getLatBusc().isEmpty()) {
    			filtroLat = filtros.getLatBusc();
    		}
    		if(filtros.getLngBusc() != null && !filtros.getLngBusc().isEmpty()) {
    			filtroLng = filtros.getLngBusc();
    		}
    		if(filtros.getKmBusc() != null && !filtros.getKmBusc().isEmpty()) {
    			filtroKms = filtros.getKmBusc();
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
    			+ "&periodoScrap=" + filtroPeriodoScrap
    			+ "&latBusc=" + filtroLat
    			+ "&lngBusc=" + filtroLng
    			+ "&kmBusc=" + filtroKms;
        
        ReTechDataModel[] response = restTemplate.getForObject(url, ReTechDataModel[].class);
        return List.of(response); 
    }
    
    public List<ReTechDataModel> getDataSalienteFromWebService(FiltrosConsultaBigDataModel filtros) {
    	String url = "http://localhost:8080/api/consultarSaliente?";
    	String filtroEstado = "";
    	String filtroTipo = "";
    	String filtroZona = "";
    	String filtroSegmento = "";
    	String filtroPrecioFichaIni = "";
    	String filtroPrecioFichaFin = "";
    	String filtroPrecioM2Ini = "";
    	String filtroPrecioM2Fin = "";
    	String filtroPeriodoScrap = "";
    	String filtroLat = "";
    	String filtroLng = "";
    	String filtroKms = "";
    	
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
    		if(filtros.getPeriodoScrap() != null && !filtros.getPeriodoScrap().isEmpty()) {
    			filtroPeriodoScrap = String.join(",", filtros.getPeriodoScrap() );
    		}
    		if(filtros.getLatBusc() != null && !filtros.getLatBusc().isEmpty()) {
    			filtroLat = filtros.getLatBusc();
    		}
    		if(filtros.getLngBusc() != null && !filtros.getLngBusc().isEmpty()) {
    			filtroLng = filtros.getLngBusc();
    		}
    		if(filtros.getKmBusc() != null && !filtros.getKmBusc().isEmpty()) {
    			filtroKms = filtros.getKmBusc();
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
    			+ "&periodoScrap=" + filtroPeriodoScrap
    			+ "&latBusc=" + filtroLat
    			+ "&lngBusc=" + filtroLng
    			+ "&kmBusc=" + filtroKms;
        
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
    
    public List<CatalogDataModel> getCatalogZona(List<String> estados) {
    	String filtroEstado = "";
    	
    	if(estados != null){
    		filtroEstado = String.join(",", estados );
    	}
        String url = "http://localhost:8080/api/getZona?estado=" + filtroEstado;
        CatalogDataModel[] response = restTemplate.getForObject(url, CatalogDataModel[].class);
        return List.of(response);
    }
    
    public List<CatalogDataModel> getCatalogSegmento() {
        String url = "http://localhost:8080/api/getSegmento";
        CatalogDataModel[] response = restTemplate.getForObject(url, CatalogDataModel[].class);
        return List.of(response);
    }
    
    public List<CatalogDataModel> getCatalogPeriodo() {
        String url = "http://localhost:8080/api/getPeriodo";
        CatalogDataModel[] response = restTemplate.getForObject(url, CatalogDataModel[].class);
        return List.of(response);
    }
    
    public ResponseEntity<Object> getDatosRangos(FiltrosConsultaDatosRangosModel filtros, int datoBusqueda) {
    	String url = "";
    	if(datoBusqueda == 1)
    		url = "http://localhost:8080/api/getDormitoriosRangos?";
    	if(datoBusqueda == 2)
    		url = "http://localhost:8080/api/getBaniosRangos?";
    	
    	String filtroEstado = "";
    	String filtroTipo = "";
    	String filtroZona = "";
    	String filtroSegmento = "";
    	String filtroIniBusc = "";
    	String filtroFinBusc = "";
    	String filtroPeriodoScrap = "";
    	String filtroRangosPrecio = "";
    	String filtroIndicadorMonto = "";
    	String filtroLat = "";
    	String filtroLng = "";
    	String filtroKms = "";
    	
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
    		if(filtros.getIniBusc() != null && !filtros.getIniBusc().isEmpty()) {
    			filtroIniBusc = filtros.getIniBusc();
    		}
    		if(filtros.getFinBusc() != null && !filtros.getFinBusc().isEmpty()) {
    			filtroFinBusc = filtros.getFinBusc();
    		}
    		if(filtros.getPeriodoScrap() != null && !filtros.getPeriodoScrap().isEmpty()) {
    			filtroPeriodoScrap = String.join(",", filtros.getPeriodoScrap() );
    		}
    		if(filtros.getRangosPrecio() != null && !filtros.getRangosPrecio().isEmpty()) {
    			filtroRangosPrecio = filtros.getRangosPrecio();
    		}
    		if(filtros.getIndicadorMonto() != null && !filtros.getIndicadorMonto().isEmpty()) {
    			filtroIndicadorMonto = filtros.getIndicadorMonto();
    		}
    		if(filtros.getLatBusc() != null && !filtros.getLatBusc().isEmpty()) {
    			filtroLat = filtros.getLatBusc();
    		}
    		if(filtros.getLngBusc() != null && !filtros.getLngBusc().isEmpty()) {
    			filtroLng = filtros.getLngBusc();
    		}
    		if(filtros.getKmBusc() != null && !filtros.getKmBusc().isEmpty()) {
    			filtroKms = filtros.getKmBusc();
    		}
    	}
    	url += "estado=" + filtroEstado
    			+ "&tipo=" + filtroTipo
    			+ "&zona=" + filtroZona
    			+ "&segmento=" + filtroSegmento
    			+ "&iniBusc=" + filtroIniBusc
    			+ "&finBusc=" + filtroFinBusc
    			+ "&periodoScrap=" + filtroPeriodoScrap
    			+ "&rangosPrecio=" + filtroRangosPrecio
    			+ "&indicadorMonto=" + filtroIndicadorMonto
    			+ "&latBusc=" + filtroLat
    			+ "&lngBusc=" + filtroLng
    			+ "&kmBusc=" + filtroKms;
        
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
    
    public List<ResumenTipoModel> getResumenTipos(FiltrosConsultaBigDataModel filtros) {
    	String url = "http://localhost:8080/api/consultarDetalleTipos?";
    	
    	String filtroEstado = "";
    	String filtroTipo = "";
    	String filtroZona = "";
    	String filtroSegmento = "";
    	String filtroPrecioFichaIni = "";
    	String filtroPrecioFichaFin = "";
    	String filtroPrecioM2Ini = "";
    	String filtroPrecioM2Fin = "";
    	String filtroPeriodoScrap = "";
    	String filtroLat = "";
    	String filtroLng = "";
    	String filtroKms = "";
    	
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
    		if(filtros.getPeriodoScrap() != null && !filtros.getPeriodoScrap().isEmpty()) {
    			filtroPeriodoScrap = String.join(",", filtros.getPeriodoScrap() );
    		}
    		if(filtros.getLatBusc() != null && !filtros.getLatBusc().isEmpty()) {
    			filtroLat = filtros.getLatBusc();
    		}
    		if(filtros.getLngBusc() != null && !filtros.getLngBusc().isEmpty()) {
    			filtroLng = filtros.getLngBusc();
    		}
    		if(filtros.getKmBusc() != null && !filtros.getKmBusc().isEmpty()) {
    			filtroKms = filtros.getKmBusc();
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
    			+ "&periodoScrap=" + filtroPeriodoScrap
    			+ "&latBusc=" + filtroLat
    			+ "&lngBusc=" + filtroLng
    			+ "&kmBusc=" + filtroKms;
        
    	ResumenTipoModel[] response = restTemplate.getForObject(url, ResumenTipoModel[].class);
        return List.of(response); 
    }
    
    public List<ResumenTipoModel> setCargaChema(FiltrosConsultaBigDataModel filtros) {
    	String url = "http://localhost:8080/api/consultarDetalleTipos?";
    	
    	String filtroEstado = "";
    	String filtroTipo = "";
    	String filtroZona = "";
    	String filtroSegmento = "";
    	String filtroPrecioFichaIni = "";
    	String filtroPrecioFichaFin = "";
    	String filtroPrecioM2Ini = "";
    	String filtroPrecioM2Fin = "";
    	String filtroPeriodoScrap = "";
    	String filtroLat = "";
    	String filtroLng = "";
    	String filtroKms = "";
    	
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
    		if(filtros.getPeriodoScrap() != null && !filtros.getPeriodoScrap().isEmpty()) {
    			filtroPeriodoScrap = String.join(",", filtros.getPeriodoScrap() );
    		}
    		if(filtros.getLatBusc() != null && !filtros.getLatBusc().isEmpty()) {
    			filtroLat = filtros.getLatBusc();
    		}
    		if(filtros.getLngBusc() != null && !filtros.getLngBusc().isEmpty()) {
    			filtroLng = filtros.getLngBusc();
    		}
    		if(filtros.getKmBusc() != null && !filtros.getKmBusc().isEmpty()) {
    			filtroKms = filtros.getKmBusc();
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
    			+ "&periodoScrap=" + filtroPeriodoScrap
    			+ "&latBusc=" + filtroLat
    			+ "&lngBusc=" + filtroLng
    			+ "&kmBusc=" + filtroKms;
        
    	ResumenTipoModel[] response = restTemplate.getForObject(url, ResumenTipoModel[].class);
        return List.of(response); 
    }
    
    public ResponseEntity<String> setCargaMasiva(List<Map<String, String>> jsonInserts) {
        String url = "http://localhost:8080/api/cargaMasiva1";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<List<Map<String, String>>> request = 
            new HttpEntity<>(jsonInserts, headers);
        
        return restTemplate.exchange(
        	url,
            HttpMethod.POST,
            request,
            String.class
        );
    }
    
    public String setUpdateCargaMasiva(String estado) {
        String url = "http://localhost:8080/api/cargaMasiva2";
        
        // Crear HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // Crear MultiValueMap para los parámetros
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("estado", estado);
        
        // Crear HttpEntity con headers y body
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        
        // Realizar solicitud POST
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        
        return response.getBody();
    }
    
}
