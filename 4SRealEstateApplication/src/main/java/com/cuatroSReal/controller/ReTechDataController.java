package com.cuatroSReal.controller;

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
import com.cuatroSReal.model.FiltrosConsultaDormitoriosModel;
import com.cuatroSReal.model.ReTechDataModel;
import com.cuatroSReal.service.ReTechDataService;

@Controller
public class ReTechDataController {
    private final ReTechDataService reTechdataService;

    public ReTechDataController(ReTechDataService reTechdataService) {
        this.reTechdataService = reTechdataService;
    }

    @GetMapping("/data")
    public String getData(Model model) {
        List<ReTechDataModel> dataList = reTechdataService.getDataFromWebService(null);
        //List<ReTechDataModel> dataList = loadDataFromFile();
    	List<CatalogDataModel> estadosList = reTechdataService.getCatalogEstado();
    	List<CatalogDataModel> tipoList = reTechdataService.getCatalogTipo();
        List<CatalogDataModel> zonaList = reTechdataService.getCatalogZona();
    	List<CatalogDataModel> segmentoList = reTechdataService.getCatalogSegmento();
        model.addAttribute("dataList", dataList);
        model.addAttribute("estadosList",estadosList);
        model.addAttribute("tipoList",tipoList);
        model.addAttribute("zonaList",zonaList);
        model.addAttribute("segmentoList", segmentoList);
    	
        return "ReTechView";
    }
    
    @PostMapping("/api/consulta")
    @ResponseBody
    public List<ReTechDataModel> consultaDatos(@RequestBody FiltrosConsultaBigDataModel filtros) {
        // Lógica para procesar los filtros y consultar datos
        List<ReTechDataModel> resultados = reTechdataService.getDataFromWebService(filtros);
        return resultados;
    }
    
    @PostMapping("/api/getDormitoriosRango")
    @ResponseBody
    public ResponseEntity<Object> dormitoriosXPrecio(@RequestBody FiltrosConsultaDormitoriosModel filtros) {
        // Lógica para procesar los filtros y consultar datos
    	ResponseEntity<Object> resultados = reTechdataService.getDormitoriosPrecio(filtros);
        return resultados;
    }
    
    /* Metodo usado solo si es necesario un archivo json de todos los datos
    private List<ReTechDataModel> loadDataFromFile() {
    	ObjectMapper objectMapper = new ObjectMapper();
        try {
            File archivo = new File("src/main/resources/static/ejemploTabla/ejemplo_tabla_json.txt");
            return objectMapper.readValue(archivo, new TypeReference<List<ReTechDataModel>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}