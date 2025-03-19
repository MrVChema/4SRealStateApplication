package com.cuatroSReal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cuatroSReal.model.CatalogDataModel;
import com.cuatroSReal.model.FiltrosConsultaBigDataModel;
import com.cuatroSReal.model.FiltrosConsultaDatosRangosModel;
import com.cuatroSReal.model.ReTechDataModel;
import com.cuatroSReal.model.ResumenTipoModel;
import com.cuatroSReal.service.ReTechDataService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReTechDataController {
    private final ReTechDataService reTechdataService;

    public ReTechDataController(ReTechDataService reTechdataService) {
        this.reTechdataService = reTechdataService;
    }
    
    private static final Map<String, String> CHARACTER_REPLACEMENTS = new HashMap<>();
    private static final Map<String, String> ACENTOS = new HashMap<>();

    
    static {
        CHARACTER_REPLACEMENTS.put("√±", "ñ");
        CHARACTER_REPLACEMENTS.put("√≥", "ó");
        CHARACTER_REPLACEMENTS.put("√©", "é");
        CHARACTER_REPLACEMENTS.put("√≠", "í");
        CHARACTER_REPLACEMENTS.put("√∫", "ú");
        CHARACTER_REPLACEMENTS.put("√°", "á");
        CHARACTER_REPLACEMENTS.put("√º", "ü");
        CHARACTER_REPLACEMENTS.put("√£", "ã");
        CHARACTER_REPLACEMENTS.put("√∂", "ö");
        CHARACTER_REPLACEMENTS.put("Ã³", "ó");
        CHARACTER_REPLACEMENTS.put("ﾃｳ", "ó");
        CHARACTER_REPLACEMENTS.put("Ã©", "é");
        CHARACTER_REPLACEMENTS.put("Ã±", "ñ");
        CHARACTER_REPLACEMENTS.put("ﾃ］", "án");
        CHARACTER_REPLACEMENTS.put("Ã¡", "á");
        CHARACTER_REPLACEMENTS.put("Ã¡", "á");
        // Agrega más caracteres según sea necesario
        
        ACENTOS.put("á", "a");
        ACENTOS.put("é", "e");
        ACENTOS.put("í", "i");
        ACENTOS.put("ó", "o");
        ACENTOS.put("ú", "u");
        ACENTOS.put("Á", "A");
        ACENTOS.put("É", "E");
        ACENTOS.put("Í", "I");
        ACENTOS.put("Ó", "O");
        ACENTOS.put("Ú", "U");
    }

    @GetMapping("/data")
    public String getData(Model model) {
        List<CatalogDataModel> estadosList = reTechdataService.getCatalogEstado();
    	List<CatalogDataModel> segmentoList = reTechdataService.getCatalogSegmento();
    	List<CatalogDataModel> periodosList = reTechdataService.getCatalogPeriodo();
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
    
    
    @GetMapping("/uploadExcel")
    public String showUploadForm() {
        return "uploadExcelView"; // Nombre de la vista Thymeleaf
    }

    @PostMapping("/uploadExcel")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model, HttpServletResponse response) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Por favor, selecciona un archivo.");
            return "uploadExcelView";
        }

        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0); // Selecciona la primera hoja

            // Procesar cada fila
            for (Row row : sheet) {
                // Normalizar columna N (índice 13)
                Cell cellN = row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (cellN.getCellType() == CellType.STRING) {
                    String cellValue = cellN.getStringCellValue();
                    cellValue = separarPalabrasUnidas(cellValue); // Separar palabras unidas
                    cellValue = normalizarAcentos(cellValue);     // Normalizar acentos
                    cellN.setCellValue(cellValue);
                }

                // Normalizar columna P (índice 15)
                Cell cellP = row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (cellP.getCellType() == CellType.STRING) {
                    String cellValue = cellP.getStringCellValue();
                    cellValue = separarPalabrasUnidas(cellValue); // Separar palabras unidas
                    cellValue = normalizarAcentos(cellValue);     // Normalizar acentos
                    cellP.setCellValue(cellValue);
                }

                // Normalizar columna R (índice 17)
                Cell cellR = row.getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (cellR.getCellType() == CellType.STRING) {
                    String cellValue = cellR.getStringCellValue();
                    cellValue = separarPalabrasUnidas(cellValue); // Separar palabras unidas
                    cellValue = normalizarAcentos(cellValue);     // Normalizar acentos
                    cellR.setCellValue(cellValue);
                }

                // Corregir caracteres especiales en todas las celdas
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        for (Map.Entry<String, String> entry : CHARACTER_REPLACEMENTS.entrySet()) {
                            cellValue = cellValue.replace(entry.getKey(), entry.getValue());
                        }
                        cell.setCellValue(cellValue);
                    }
                }
            }

            // Obtener el nombre del archivo original
            String nombreOriginal = file.getOriginalFilename();
            String nombreCorregido = nombreOriginal.replace(".xlsx", "_MrV_approve.xlsx");

            // Configurar la respuesta para descargar el archivo corregido
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + nombreCorregido);
            workbook.write(response.getOutputStream());
            workbook.close();

            return null; // No retornar una vista, ya que se está enviando el archivo directamente
        } catch (IOException e) {
            model.addAttribute("message", "Error al procesar el archivo: " + e.getMessage());
            return "uploadExcelView";
        }
    }
    
    @PostMapping("/generateInserts")
    public String generateInserts(@RequestParam("file") MultipartFile file, Model model, HttpServletResponse response) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Por favor, selecciona un archivo.");
            return "uploadExcelView";
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            StringBuilder inserts = new StringBuilder();

            String[] tiposColumnas = {
                "STRING", "STRING", "STRING", "FLOAT", "STRING", "FLOAT", 
                "STRING", "FLOAT", "STRING", "FLOAT", "FLOAT", "FLOAT", 
                "STRING", "STRING", "STRING", "STRING", "STRING", "STRING", 
                "STRING", "FLOAT", "STRING", "FLOAT", "FLOAT", "FLOAT", 
                "STRING", "STRING", "STRING"
            };

            for (Row row : sheet) {
                if (row != null && row.getRowNum() > 0) {
                    StringBuilder valores = new StringBuilder();

                    for (int colIndex = 1; colIndex <= 27; colIndex++) {
                        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String tipo = tiposColumnas[colIndex - 1];
                        
                        String valor = formatearValor(cell, tipo);
                        valores.append(valor);

                        if (colIndex < 27) valores.append(",");
                    }

                    inserts.append("INSERT INTO `data-agregador-main.fuentes_secundarias.temp_carga_archivo` VALUES (")
                           .append(valores)
                           .append(");\n");
                }
            }

            String nombreArchivo = file.getOriginalFilename().replace(".xlsx", "_inserts.txt");
            
            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

            try (OutputStream os = response.getOutputStream()) {
                os.write(inserts.toString().getBytes(StandardCharsets.UTF_8));
            }

            return null;
        } catch (IOException e) {
            model.addAttribute("message", "Error al procesar el archivo: " + e.getMessage());
            return "uploadExcelView";
        }
    }
    
    @PostMapping("/inserts1")
    public String insertsScrap(@RequestParam("file") MultipartFile file, Model model) {
    	try {
            // Leer archivo TXT
            List<String> inserts = leerInserts(file);
            
            // Crear JSON
            List<Map<String, String>> jsonData = new ArrayList<>();
            for (String insert : inserts) {
                Map<String, String> registro = new HashMap<>();
                registro.put("sql", insert);
                jsonData.add(registro);
            }
            
            // Enviar a WebService
            ResponseEntity<String> response = reTechdataService.setCargaMasiva(jsonData);
            
            model.addAttribute("message", "Proceso completado. Respuesta: " + response.getBody());
            return "redirect:/uploadExcelView";
            
        } catch (IOException e) {
            model.addAttribute("message", "Error: " + e.getMessage());
            return "redirect:/uploadExcelView";
        }
    }
    
    @PostMapping("/inserts2")
    public String updatesScrap(@RequestParam("estadoInsert") String estado, Model model) {
	    // Enviar a WebService
	    String response = reTechdataService.setUpdateCargaMasiva(estado);
	    
	    model.addAttribute("message", "Proceso completado. Respuesta: " + response);
	    return "redirect:/uploadExcelView";
    }

    private String formatearValor(Cell cell, String tipo) {
        DataFormatter dataFormatter = new DataFormatter();
        
        if (cell.getCellType() == CellType.BLANK) {
            return tipo.equals("STRING") ? "'N/A'" : "NULL";
        }

        try {
            switch (tipo) {
                case "STRING":
                    String valor = dataFormatter.formatCellValue(cell)
                                      .replace("'", "")  // Eliminar comillas simples
                                      .replace("\u00A0", " ")
                                      .replaceAll("[\\p{C}\\p{Z}]", " ")
                                      .trim()
                                      .replaceAll(" +", " ");
                    return "'" + valor + "'";
                    
                case "FLOAT":
                    if (cell.getCellType() == CellType.NUMERIC) {
                        return new BigDecimal(cell.getNumericCellValue())
                                .stripTrailingZeros()
                                .toPlainString();
                    } else {
                        try {
                            String rawValue = dataFormatter.formatCellValue(cell)
                                                .replace("\u00A0", "")  // Eliminar non-breaking space
                                                .replaceAll("[^\\d.,]", "");  // Eliminar caracteres no numéricos
                            return new BigDecimal(rawValue.replace(",", ""))
                                    .stripTrailingZeros()
                                    .toPlainString();
                        } catch (NumberFormatException | ArithmeticException e) {
                            return "NULL";
                        }
                    }
                    
                default:
                    return "'N/A'";
            }
        } catch (Exception e) {
            return tipo.equals("STRING") ? "'N/A'" : "NULL";
        }
    }

    private String separarPalabrasUnidas(String texto) {
        // Expresión regular para detectar mayúsculas dentro de una cadena
        return texto.replaceAll("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])", " ");
    }

    private String normalizarAcentos(String texto) {
        for (Map.Entry<String, String> entry : ACENTOS.entrySet()) {
            texto = texto.replace(entry.getKey(), entry.getValue());
        }
        return texto;
    }
    
    private List<String> leerInserts(MultipartFile file) throws IOException {
        List<String> inserts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    inserts.add(linea.trim());
                }
            }
        }
        return inserts;
    }
}