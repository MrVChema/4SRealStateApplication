package com.cuatroSReal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cuatroSReal.model.CatalogDataModel;
import com.cuatroSReal.model.FiltrosConsultaBigDataModel;
import com.cuatroSReal.model.FiltrosConsultaDatosRangosModel;
import com.cuatroSReal.model.ReTechDataModel;
import com.cuatroSReal.model.ResumenTipoModel;
import com.cuatroSReal.service.ReTechDataService;

@Controller
public class ReTechDataController {
    private final ReTechDataService reTechdataService;

    public ReTechDataController(ReTechDataService reTechdataService) {
        this.reTechdataService = reTechdataService;
    }

    @GetMapping("/data")
    public String getData(Model model) {
        //List<ReTechDataModel> dataList = reTechdataService.getDataFromWebService(null);
        //List<ReTechDataModel> dataListSaliente = reTechdataService.getDataSalienteFromWebService(null);
    	List<CatalogDataModel> estadosList = reTechdataService.getCatalogEstado();
    	List<CatalogDataModel> segmentoList = reTechdataService.getCatalogSegmento();
    	List<CatalogDataModel> periodosList = reTechdataService.getCatalogPeriodo();
        //model.addAttribute("dataList", dataList);
        //model.addAttribute("dataListSaliente", dataListSaliente);
        model.addAttribute("estadosList",estadosList);
        model.addAttribute("segmentoList", segmentoList);
        model.addAttribute("periodosList", periodosList);
    	
        return "ReTechView";
    }
    
    @PostMapping("/api/consulta")
    @ResponseBody
    public List<ReTechDataModel> consultaDatos(@RequestBody FiltrosConsultaBigDataModel filtros) {
        // Lógica para procesar los filtros y consultar datos
        List<ReTechDataModel> resultados = reTechdataService.getDataFromWebService(filtros);
        return resultados;
    }
    
    @PostMapping("/api/consultaSaliente")
    @ResponseBody
    public List<ReTechDataModel> consultaDatosSaliente(@RequestBody FiltrosConsultaBigDataModel filtros) {
        // Lógica para procesar los filtros y consultar datos
        List<ReTechDataModel> resultados = reTechdataService.getDataSalienteFromWebService(filtros);
        return resultados;
    }
    
    @PostMapping("/api/getDormitoriosRangos")
    @ResponseBody
    public ResponseEntity<Object> getDormitoriosRangos(@RequestBody FiltrosConsultaDatosRangosModel filtros) {
        // Lógica para procesar los filtros y consultar datos
    	ResponseEntity<Object> resultados = reTechdataService.getDatosRangos(filtros,1);
    	
    	// Verificar si la respuesta es nula o vacía
        if (resultados == null || resultados.getBody() == null) {
            return ResponseEntity.ok(new HashMap<>()); // Devuelve un JSON vacío con 200 OK
        }
    	
        return resultados;
    }
    
    @PostMapping("/api/getBaniosRangos")
    @ResponseBody
    public ResponseEntity<Object> getBaniosRangos(@RequestBody FiltrosConsultaDatosRangosModel filtros) {
        // Lógica para procesar los filtros y consultar datos
    	ResponseEntity<Object> resultados = reTechdataService.getDatosRangos(filtros,2);
    	
    	// Verificar si la respuesta es nula o vacía
        if (resultados == null || resultados.getBody() == null) {
            return ResponseEntity.ok(new HashMap<>()); // Devuelve un JSON vacío con 200 OK
        }
        
        return resultados;
    }
    
    @PostMapping("/api/consultarDetalleViviendaH")
    @ResponseBody
    public List<ResumenTipoModel> consultaResumenViviendaH(@RequestBody FiltrosConsultaBigDataModel filtros) {
    	List<String> tiposResumen = new ArrayList<String>();
    	
    	if(!filtros.getTipos().isEmpty()) {
    		if(filtros.getTipos().contains("VH")) {
    			tiposResumen.add("VH"); //Se manda V de Vivienda donde agrupa todos los tipos de vivienda
    		}else {
    			tiposResumen.add("X"); //Se manda X de Evitar
    		}
    	}else{
    		tiposResumen.add("VH"); //Se manda V de Vivienda donde agrupa todos los tipos de vivienda
    	}
    	
    	// Lógica para procesar los filtros y consultar datos
    	filtros.setTipos(tiposResumen);
    	return reTechdataService.getResumenTipos(filtros);
    }
    
    @PostMapping("/api/consultarDetalleViviendaV")
    @ResponseBody
    public List<ResumenTipoModel> consultaResumenViviendaV(@RequestBody FiltrosConsultaBigDataModel filtros) {
    	List<String> tiposResumen = new ArrayList<String>();
    	
    	if(!filtros.getTipos().isEmpty()) {
    		if(filtros.getTipos().contains("VV")) {
    			tiposResumen.add("VV"); //Se manda V de Vivienda donde agrupa todos los tipos de vivienda
    		}else {
    			tiposResumen.add("X"); //Se manda X de Evitar
    		}
    	}else{
    		tiposResumen.add("VV"); //Se manda V de Vivienda donde agrupa todos los tipos de vivienda
    	}
    	
    	// Lógica para procesar los filtros y consultar datos
    	filtros.setTipos(tiposResumen);
    	return reTechdataService.getResumenTipos(filtros);
    }
    
    @PostMapping("/api/consultarDetalleLocales")
    @ResponseBody
    public List<ResumenTipoModel> consultaResumenLocales(@RequestBody FiltrosConsultaBigDataModel filtros) {
    	List<String> tiposResumen = new ArrayList<String>();
    	
    	if(!filtros.getTipos().isEmpty()) {
    		if(filtros.getTipos().contains("L")) {
    			tiposResumen.add("L"); //Se manda L de Locales donde agrupa todos los tipos de Locales Comerciales
    		}else {
    			tiposResumen.add("X"); //Se manda X de Evitar
    		}
    	}else{
    		tiposResumen.add("L"); //Se manda L de Locales donde agrupa todos los tipos de Locales Comerciales
    	}
    	
		// Lógica para procesar los filtros y consultar datos
		filtros.setTipos(tiposResumen);
	    return reTechdataService.getResumenTipos(filtros);
    	
    }
    
    @PostMapping("/api/consultarDetalleOficinas")
    @ResponseBody
    public List<ResumenTipoModel> consultarDetalleOficinas(@RequestBody FiltrosConsultaBigDataModel filtros) {
    	List<String> tiposResumen = new ArrayList<String>();
    	
    	if(!filtros.getTipos().isEmpty()) {
    		if(filtros.getTipos().contains("O")) {
    			tiposResumen.add("O"); //Se manda O de oficinas donde agrupa todos los tipos de oficinas
    		}else {
    			tiposResumen.add("X"); //Se manda X de Evitar
    		}
    	}else{
    		tiposResumen.add("O"); //Se manda O de oficinas donde agrupa todos los tipos de oficinas
    	}
    	
		// Lógica para procesar los filtros y consultar datos
		filtros.setTipos(tiposResumen);
	    return reTechdataService.getResumenTipos(filtros);
    }
    
    @PostMapping("/api/consultarDetalleBodegas")
    @ResponseBody
    public List<ResumenTipoModel> consultarDetalleBodegas(@RequestBody FiltrosConsultaBigDataModel filtros) {
    	List<String> tiposResumen = new ArrayList<String>();
    	
    	if(!filtros.getTipos().isEmpty()) {
    		if(filtros.getTipos().contains("B")) {
    			tiposResumen.add("B"); //Se manda B de Bodegas donde agrupa todos los tipos de Bodegas
    		}else {
    			tiposResumen.add("X"); //Se manda X de Evitar
    		}
    	}else{
    		tiposResumen.add("B"); //Se manda B de Bodegas donde agrupa todos los tipos de Bodegas
    	}
    	
    	//Lógica para procesar los filtros y consultar datos
		filtros.setTipos(tiposResumen);
	    return reTechdataService.getResumenTipos(filtros);
    	
    }
    
    @PostMapping("/api/consultarDetalleSinAsignar")
    @ResponseBody
    public List<ResumenTipoModel> consultarDetalleSinAsignar(@RequestBody FiltrosConsultaBigDataModel filtros) {
    	List<String> tiposResumen = new ArrayList<String>();
    	
    	if(!filtros.getTipos().isEmpty()) {
    		if(filtros.getTipos().contains("S")) {
    			tiposResumen.add("S"); //Se manda S de sin asignar donde agrupa todos los tipos de sin asignar
    		}else {
    			tiposResumen.add("X"); //Se manda X de Evitar
    		}
    	}else{
    		tiposResumen.add("S"); //Se manda S de sin asignar donde agrupa todos los tipos de sin asignar
    	}
    	
		// Lógica para procesar los filtros y consultar datos
		filtros.setTipos(tiposResumen);
	    return reTechdataService.getResumenTipos(filtros);
    	
    }
    
    @PostMapping("/api/getZonasPorEstados")
    @ResponseBody
    public List<CatalogDataModel> getZonasPorEstados(@RequestBody List<String> estados) {
        // Lógica para procesar los filtros y consultar datos
        List<CatalogDataModel> resultados = reTechdataService.getCatalogZona(estados);
        return resultados;
    }
}