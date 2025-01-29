$(document).ready(function(){
    
  $('#myTable').pageMe({pagerSelector:'#myPager',showPrevNext:true,hidePageNumbers:false,perPage:250});
  
});

$.fn.pageMe = function(opts) {
    var $this = this,
        defaults = {
            perPage: 7,
            showPrevNext: false,
            hidePageNumbers: false
        },
        settings = $.extend(defaults, opts);
    
    var listElement = $this.find('tbody');
    var perPage = settings.perPage;
    var children = listElement.children();
    var pager = $('.pager');
    
    if (typeof settings.childSelector!="undefined") {
        children = listElement.find(settings.childSelector);
    }
    
    if (typeof settings.pagerSelector!="undefined") {
        pager = $(settings.pagerSelector);
    }
    
    var numItems = children.size();
    var numPages = Math.ceil(numItems/perPage);

    pager.data("curr",0);
    
    if (settings.showPrevNext){
        $('<li><a href="#" class="prev_link">«</a></li>').appendTo(pager);
    }
    
    var curr = 0;
    while(numPages > curr && (settings.hidePageNumbers==false)){
        $('<li><a href="#" class="page_link">'+(curr+1)+'</a></li>').appendTo(pager);
        curr++;
    }
	
    
    if (settings.showPrevNext){
		$('<li><a href="#" class="prev_link">«</a></li>').prependTo(pager); // Botón "Anterior"
		$('<li><a href="#" class="next_link">»</a></li>').appendTo(pager); // Botón "Siguiente"
    }
    
    pager.find('.page_link:first').addClass('active');
    pager.find('.prev_link').hide();
    if (numPages<=1) {
        pager.find('.next_link').hide();
    }
  	pager.children().eq(1).addClass("active");
    
    children.hide();
    children.slice(0, perPage).show();
    
    pager.find('li .page_link').click(function(){
        var clickedPage = $(this).html().valueOf()-1;
        goTo(clickedPage,perPage);
        return false;
    });
    pager.find('li .prev_link').click(function(){
        previous();
        return false;
    });
    pager.find('li .next_link').click(function(){
        next();
        return false;
    });
    
    function previous(){
        var goToPage = parseInt(pager.data("curr")) - 1;
        goTo(goToPage);
    }
     
    function next(){
        goToPage = parseInt(pager.data("curr")) + 1;
        goTo(goToPage);
    }
    
    function goTo(page){
        var startAt = page * perPage,
            endOn = startAt + perPage;
        
        children.css('display','none').slice(startAt, endOn).show();
        
        if (page>=1) {
            pager.find('.prev_link').show();
        }
        else {
            pager.find('.prev_link').hide();
        }
        
        if (page<(numPages-1)) {
            pager.find('.next_link').show();
        }
        else {
            pager.find('.next_link').hide();
        }
        
        pager.data("curr",page);
		pager.children().find('a').removeClass("active");
		pager.children().eq(page + 2).find('a').addClass("active"); // Agrega clase "active" al número seleccionado
    
    }
};

function showDescription(descripcion) {
  const popupElement = document.getElementById('popupDescription');
  popupElement.innerText = descripcion; // Muestra el texto original
  const modal = new bootstrap.Modal(document.getElementById('popupModal'));
  modal.show();
}

function closeDescription() {
	//document.getElementById('popupModal').style.display = 'none';
	const modal = bootstrap.Modal.getInstance(document.getElementById('popupModal'));
	  if (modal) {
	    modal.hide();
	  }
}

document.getElementById("openPopup").addEventListener("click", function () {
    const modal = new bootstrap.Modal(document.getElementById("dormitoriosModal"));
    modal.show();
});

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector(".needs-validation");

    // Capturar el evento submit del formulario
    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Evita que se recargue la página

        // Obtener valores seleccionados de los selects
        const selectedEstados = getSelectedValues("estados");
        const selectedTipos = getSelectedValues("tipos");
        const selectedZonas = getSelectedValues("zonas");
        const selectedSegmentos = getSelectedValues("segmentos");
		const selectedPeriodo = getSelectedValues("periodo");

        // Crear JSON con los datos del formulario
        const jsonData = {
            estados: selectedEstados,
            tipos: selectedTipos,
            zonas: selectedZonas,
            segmentos: selectedSegmentos,
            iniPrecioFBusc: form["iniPrecioFBusc"].value,
            finPrecioFBusc: form["finPrecioFBusc"].value,
            iniPrecioM2Busc: form["iniPrecioM2Busc"].value,
            finPrecioM2Busc: form["finPrecioM2Busc"].value,
			periodoScrap: selectedPeriodo,
        };

        // Llamadas a diferentes webservices
        fetchAndUpdateTable("/api/consulta", jsonData, "#myTable tbody", "registrosTotales", processGeneralTableData);
		fetchAndUpdateTable("/api/consultaSaliente", jsonData, "#myTableSaliente tbody", "registrosSalienteTotales", processGeneralTableData);
        fetchAndUpdateTable("/api/consultarDetalleViviendaV", jsonData, "#myTableResumenViviendaV tbody", "registrosTotalesViviendaV", processResumenTableDataVivienda);
        fetchAndUpdateTable("/api/consultarDetalleViviendaH", jsonData, "#myTableResumenViviendaH tbody", "registrosTotalesViviendaH", processResumenTableDataVivienda);
        fetchAndUpdateTable("/api/consultarDetalleLocales", jsonData, "#myTableResumenLocales tbody", "registrosTotalesLocales", processResumenTableData);
        fetchAndUpdateTable("/api/consultarDetalleOficinas", jsonData, "#myTableResumenOficinas tbody", "registrosTotalesOficinas", processResumenTableData);
        fetchAndUpdateTable("/api/consultarDetalleBodegas", jsonData, "#myTableResumenBodegas tbody", "registrosTotalesBodegas", processResumenTableData);
        fetchAndUpdateTable("/api/consultarDetalleSinAsignar", jsonData, "#myTableResumenSinAsignar tbody", "registrosTotalesSinAsignar", processResumenTableData);
    });

    // Función para obtener valores seleccionados de un select
    function getSelectedValues(selectName) {
        return Array.from(document.querySelector(`select[name='${selectName}']`).selectedOptions)
            .map(option => option.value)
            .filter(value => value.trim() !== "");
    }

    // Función para hacer fetch y actualizar tablas
    function fetchAndUpdateTable(url, jsonData, tableBodySelector, registrosLabelId, processRowFn) {
        fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(jsonData),
        })
            .then((response) => response.json())
            .then((data) => {
                if (!Array.isArray(data)) {
                    console.error(`Respuesta inválida del servidor para ${url}:`, data);
                    return;
                }
                updateTable(tableBodySelector, registrosLabelId, data, processRowFn);
            })
            .catch((error) => {
                console.error(`Error al llamar al webservice ${url}:`, error);
            });
		if(url == '/api/consulta'){
			// Reiniciar el paginador después de actualizar la tabla
			$('#myPager').empty(); // Limpia el paginador existente
			$('#myTable').pageMe({ pagerSelector: '#myPager', showPrevNext: true, hidePageNumbers: false, perPage: 250 });
		}
		
		if(url == '/api/consultaSaliente'){
			// Reiniciar el paginador después de actualizar la tabla
			$('#myPagerSaliente').empty(); // Limpia el paginador existente
			$('#myTableSaliente').pageMe({ pagerSelector: '#myPagerSaliente', showPrevNext: true, hidePageNumbers: false, perPage: 250 });
		}
    }

    // Función genérica para actualizar una tabla
    function updateTable(tableBodySelector, registrosLabelId, data, processRowFn) {
        const tableBody = document.querySelector(tableBodySelector);
        tableBody.innerHTML = ""; // Limpia el contenido actual de la tabla

        let totalRegistros = 0;

        data.forEach((row) => {
            const tr = document.createElement("tr");
            tr.innerHTML = processRowFn(row); // Procesar la fila con la función proporcionada
            tableBody.appendChild(tr);
            totalRegistros += parseInt(row.countRegistros || 0, 10); // Sumar registros
        });

        // Actualizar el contador de registros
		if(registrosLabelId == 'registrosTotales'){
			document.getElementById(registrosLabelId).innerHTML = `Registros totales consultados: ${data.length}`;
		}else
        	document.getElementById(registrosLabelId).innerHTML = `Registros totales: ${totalRegistros}`;

        // Mostrar/ocultar tabla según los registros
        const tableSection = tableBody.closest("ul");
        tableSection.style.display = totalRegistros > 0 ? "block" : "none";
    }

    // Función para procesar datos de la tabla general
    function processGeneralTableData(row) {
        return `
            <td>${row.locEstado || ""}</td>
            <td>${row.locTipo || ""}</td>
            <td>${row.zona || ""}</td>
            <td>${row.segmento || ""}</td>
            <td style="text-align: center;">
                <button onclick="showDescription('${row.descripcion || ""}');">Detalle</button>
            </td>
            <td>${row.dormitorios || ""}</td>
            <td>${row.banos || ""}</td>
            <td>${row.garages || ""}</td>
            <td>$${formatCurrency(row.fichaPrecio)}</td>
            <td>${row.superficieCubierta || ""}</td>
            <td>$${formatCurrency(row.precioM2)}</td>
            <td style="text-align: center;">${row.fechaScrap || ""}</td>
            <td>${row.lat || ""}</td>
            <td>${row.lng || ""}</td>
        `;
    }

    // Función para procesar datos de las tablas resumen
    function processResumenTableDataVivienda(row) {
        return `
            <td>${row.locEstado || ""}</td>
            <td>${row.zona || ""}</td>
            <td>${row.segmento || ""}</td>
            <td>${row.countRegistros || ""}</td>
            <td>$${formatCurrency(row.avgFichaPrecio) || ""}</td>
            <td>$${formatCurrency(row.minFichaPrecio) || ""}</td>
            <td>$${formatCurrency(row.maxFichaPrecio) || ""}</td>
            <td>$${formatCurrency(row.avgPrecioM2) || ""}</td>
            <td>$${formatCurrency(row.minPrecioM2) || ""}</td>
            <td>$${formatCurrency(row.maxPrecioM2) || ""}</td>
            <td>${row.avgSuperficieCubierta || ""}</td>
            <td>${row.avgDormitorios || ""}</td>
            <td>${row.avgBanos || ""}</td>
            <td>${row.avgGarages || ""}</td>
        `;
    }
	
	// Función para procesar datos de las tablas resumen
	    function processResumenTableData(row) {
	        return `
	            <td>${row.locEstado || ""}</td>
	            <td>${row.zona || ""}</td>
	            <td>${row.segmento || ""}</td>
	            <td>${row.countRegistros || ""}</td>
	            <td>$${formatCurrency(row.avgFichaPrecio) || ""}</td>
	            <td>$${formatCurrency(row.minFichaPrecio) || ""}</td>
	            <td>$${formatCurrency(row.maxFichaPrecio) || ""}</td>
	            <td>$${formatCurrency(row.avgPrecioM2) || ""}</td>
	            <td>$${formatCurrency(row.minPrecioM2) || ""}</td>
	            <td>$${formatCurrency(row.maxPrecioM2) || ""}</td>
	            <td>${row.avgSuperficieCubierta || ""}</td>
	            <td>${row.avgBanos || ""}</td>
	        `;
	    }

    // Función para formatear los precios en estilo de moneda
    function formatCurrency(value) {
        if (value == null) return "";
        return new Intl.NumberFormat("es-MX", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
        }).format(value);
    }
});

document.getElementById("exportExcel").addEventListener("click", function () {
    //exportTableToExcel("myTable", "DatosExportados");
	const tables = [
	        { tableID: "myTable", sheetName: "Datos Generales" },
	        { tableID: "myTableResumenViviendaV", sheetName: "Resumen Vivienda Vertical" },
			{ tableID: "myTableResumenViviendaH", sheetName: "Resumen Vivienda Horizontal" },
	        { tableID: "myTableResumenLocales", sheetName: "Resumen Locales" },
	        { tableID: "myTableResumenOficinas", sheetName: "Resumen Oficinas" },
	        { tableID: "myTableResumenBodegas", sheetName: "Resumen Bodegas" },
			{ tableID: "myTableResumenSinAsignar", sheetName: "Resumen Sin Asignar" },
	    ];

	exportTableToExcel(tables, "DatosExportados");
	exportTableToKMZ("myTable", "CoordenadasExportadas");
});

function exportTableToExcel(tables, fileName) {
	// Crea un nuevo libro de trabajo
    const workbook = XLSX.utils.book_new();

    // Itera sobre las tablas y agrega cada una como una hoja en el workbook
    tables.forEach(({ tableID, sheetName }) => {
        const table = document.getElementById(tableID); // Obtén la tabla por ID
        const rows = Array.from(table.rows); // Convierte las filas de la tabla en un array

        // Convierte las filas a datos para Excel
        const data = rows.map(row => {
            return Array.from(row.cells).map(cell => {
                // Verifica si la celda contiene un botón con datos adicionales
                const button = cell.querySelector("button");
                if (button) {
                    // Si hay un botón, toma el atributo o texto asociado al modal
                    return button.getAttribute("onclick").match(/'([^']+)'/)[1] || "";
                }
                return cell.innerText.trim(); // En otros casos, toma el texto de la celda
            });
        });

        // Crea una hoja de cálculo con los datos
        const worksheet = XLSX.utils.aoa_to_sheet(data);

        // Agrega la hoja al libro de trabajo
        XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
    });

    // Genera y descarga el archivo Excel
    XLSX.writeFile(workbook, `${fileName}.xlsx`);
}

//Proceso para exportar a KMZ
function exportTableToKMZ(tableID, fileName) {
    // Obtén la tabla
    const table = document.getElementById(tableID);
    const rows = Array.from(table.rows); // Convierte las filas de la tabla en un array

    // Filtra las filas que contienen datos
    const data = rows.slice(1).map(row => {
        const cells = row.cells;
        return {
            estado: cells[0]?.innerText.trim() || "",
            tipo: cells[1]?.innerText.trim() || "",
            zona: cells[2]?.innerText.trim() || "",
			precio: cells[8]?.innerText.trim() || "",
            lat: cells[12]?.innerText.trim() || "",
            lng: cells[13]?.innerText.trim() || "",
        };
    });

    // Genera el contenido KML
    const kmlContent = `<?xml version="1.0" encoding="UTF-8"?>
		<kml xmlns="http://www.opengis.net/kml/2.2">
		  <Document>
		    <name>${fileName}</name>
		    ${data
		        .map(row => {
		            if (row.lat && row.lng) {
		                return `
		      <Placemark>
		        <name>${row.estado} - ${row.zona}</name>
		        <description>${row.precio} | ${row.tipo}</description>
		        <Point>
		          <coordinates>${row.lng},${row.lat},0</coordinates>
		        </Point>
		      </Placemark>`;
		            }
		            return ""; // Ignora filas sin coordenadas
		        })
		        .join("\n")}
		  </Document>
		</kml>`;

    // Crear archivo KMZ usando JSZip
    const zip = new JSZip();
    zip.file(`${fileName}.kml`, kmlContent); // Agrega el archivo KML al zip

    // Generar el archivo KMZ (archivo zip con extensión KMZ)
    zip.generateAsync({ type: "blob" }).then(function (content) {
        const kmzBlob = new Blob([content], { type: "application/vnd.google-earth.kmz" });
        const url = URL.createObjectURL(kmzBlob);

        // Descarga el archivo KMZ
        const link = document.createElement("a");
        link.href = url;
        link.download = `${fileName}.kmz`;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    });
}

//Procedimiento para exportar dormitorios
document.getElementById("startExport").addEventListener("click", async function () {
    const precioIniExcel = document.getElementById("precioIniExcel").value;
    const precioFinExcel = document.getElementById("precioFinExcel").value;
	const divisorPrecioExcel = document.getElementById("divisorPrecioExcel").value;
	var precioInd = 0;
	
	const precioM2IniExcel = document.getElementById("precioM2IniExcel").value;
    const precioM2FinExcel = document.getElementById("precioM2FinExcel").value;
	const divisorM2Excel = document.getElementById("divisorM2Excel").value;
	var precioM2Ind = 0;
	
	const superficieIniExcel = document.getElementById("superficieIniExcel").value;
    const superficieFinExcel = document.getElementById("superficieFinExcel").value;
	const divisorSuperficieExcel = document.getElementById("divisorSuperficieExcel").value;
	var superficieInd = 0;
	
	const date = new Date();
	var dateExcel = formatDate(date);
	
	//Validaciones
	if (!precioIniExcel && !precioFinExcel && !precioM2IniExcel && !precioM2FinExcel && !superficieIniExcel && !superficieFinExcel){
		alert("Por favor, complete algun formulario de los 3.");
		return;
	}
	
	if (precioIniExcel || precioFinExcel || divisorPrecioExcel) {
	    if (!precioIniExcel || !precioFinExcel || !divisorPrecioExcel) {
	        alert("Por favor, complete los parámetros de Dormitorios/Baños x Precio.");
	        return;
	    }else
			precioInd = 1;
	}
	
	if (precioM2IniExcel || precioM2FinExcel || divisorM2Excel) {
		if (!precioM2IniExcel || !precioM2FinExcel || !divisorM2Excel) {
	        alert("Por favor, complete los parámetros de Dormitorios/Baños x Precio m\u00B2.");
	        return;
	    }else
			precioM2Ind = 2;
	}
	
	if (superficieIniExcel || superficieFinExcel || divisorSuperficieExcel) {
		if (!superficieIniExcel || !superficieFinExcel || !divisorSuperficieExcel) {
	        alert("Por favor, complete los parámetros de Dormitorios/Baños x Superficie.");
	        return;
	    }else
			superficieInd = 3;
	}
		
	// Obtén los valores seleccionados de los selects
	var selectedEstados = Array.from(document.querySelector("select[name='estados']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
	var selectedZonas = Array.from(document.querySelector("select[name='zonas']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
	var selectedSegmentos = Array.from(document.querySelector("select[name='segmentos']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
	var selectedTipos = Array.from(document.querySelector("select[name='tipos']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
	var selectedPeriodo = Array.from(document.querySelector("select[name='periodo']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
					
	var selectTipoVV = ['VV'];
	var selectTipoVH = ['VH'];
	
	var checkVV = false;
	var checkVH = false;
	
	if(selectedTipos.includes("VV")){
		checkVV = true;
	}
	
	if(selectedTipos.includes("VH")){
		checkVH = true;
	}
	
	if(!selectedTipos.includes("VV") && !selectedTipos.includes("VH")){
		checkVV = true;
		checkVH = true;
	}
			
	// Convierte los datos a un objeto JSON
	//Este procesamiento solo se aplica a VV y VH
	const jsonDataPrecioVV = {
	    estados: selectedEstados,
	    tipos: selectTipoVV,
	    zonas: selectedZonas,
	    segmentos: selectedSegmentos,
	    iniBusc: (precioIniExcel),
	    finBusc: (precioFinExcel),
		periodoScrap: selectedPeriodo,
		rangosPrecio: divisorPrecioExcel,
		indicadorMonto: precioInd,
	};
	
	const jsonDataM2VV = {
	    estados: selectedEstados,
	    tipos: selectTipoVV,
	    zonas: selectedZonas,
	    segmentos: selectedSegmentos,
	    iniBusc: (precioM2IniExcel),
	    finBusc: (precioM2FinExcel),
	    periodoScrap: selectedPeriodo,
		rangosPrecio: divisorM2Excel,
		indicadorMonto: precioM2Ind,
	};
	
	const jsonDataSuperficieVV = {
	    estados: selectedEstados,
	    tipos: selectTipoVV,
	    zonas: selectedZonas,
	    segmentos: selectedSegmentos,
	    iniBusc: (superficieIniExcel),
	    finBusc: (superficieFinExcel),
	    periodoScrap: selectedPeriodo,
		rangosPrecio: divisorSuperficieExcel,
		indicadorMonto: superficieInd,
	};
	
	const jsonDataPrecioVH = {
	    estados: selectedEstados,
	    tipos: selectTipoVH,
	    zonas: selectedZonas,
	    segmentos: selectedSegmentos,
	    iniBusc: (precioIniExcel),
	    finBusc: (precioFinExcel),
	    periodoScrap: selectedPeriodo,
		rangosPrecio: divisorPrecioExcel,
		indicadorMonto: precioInd,
	};
	
	const jsonDataM2VH = {
	    estados: selectedEstados,
	    tipos: selectTipoVH,
	    zonas: selectedZonas,
	    segmentos: selectedSegmentos,
	    iniBusc: (precioM2IniExcel),
	    finBusc: (precioM2FinExcel),
	    periodoScrap: selectedPeriodo,
		rangosPrecio: divisorM2Excel,
		indicadorMonto: precioM2Ind,
	};
	
	const jsonDataSuperficieVH = {
	    estados: selectedEstados,
	    tipos: selectTipoVH,
	    zonas: selectedZonas,
	    segmentos: selectedSegmentos,
	    iniBusc: (superficieIniExcel),
	    finBusc: (superficieFinExcel),
	    periodoScrap: selectedPeriodo,
		rangosPrecio: divisorSuperficieExcel,
		indicadorMonto: superficieInd,
	};
	
	var dividosNombrePrecio = getNombresColumns(precioIniExcel, precioFinExcel, divisorPrecioExcel);
	var dividosNombreM2 = getNombresColumns(precioM2IniExcel, precioM2FinExcel, divisorM2Excel);
	var dividosNombreSuperficie = getNombresColumns(superficieIniExcel, superficieFinExcel, divisorSuperficieExcel);
	
	// Crear el libro de Excel
    const workbook = XLSX.utils.book_new();

    // Función para procesar cada web service
    async function fetchAndFormatDataDormitorio(url, dividosNombre, jsonData, sheetName) {
        const response = await fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(jsonData),
        });

        if (!response.ok) throw new Error(`Error en el web service: ${url}`);
        const data = await response.json();

        // Formatear los datos
        let formattedData = [];
        if (data && typeof data === "object") {
            formattedData = Object.keys(data).flatMap((key) => {
                return data[key].map((row) => {
                    const dynamicColumns = dividosNombre.map((monto) => row[monto.toString()] || "0");
                    return [
                        row.loc_estado || key,
                        row.zona || "-",
                        row.dormitorios_cantidad || "-",
                        ...dynamicColumns,
                    ];
                });
            });
        }

        // Encabezados dinámicos
        const columnHeaders = [
            "Loc_Estado",
            "Zona",
            "Dormitorios_Cantidad",
            ...dividosNombre.map((monto) => `${monto.toLocaleString()}`),
        ];

        // Combinar encabezados y datos
        const worksheetData = [columnHeaders, ...formattedData];

        // Crear la hoja y agregarla al libro
        const worksheet = XLSX.utils.aoa_to_sheet(worksheetData);
        XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
    }
	
	async function fetchAndFormatDataBanios(url, dividosNombre, jsonData, sheetName) {
        const response = await fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(jsonData),
        });

        if (!response.ok) throw new Error(`Error en el web service: ${url}`);
        const data = await response.json();

        // Formatear los datos
        let formattedData = [];
        if (data && typeof data === "object") {
            formattedData = Object.keys(data).flatMap((key) => {
                return data[key].map((row) => {
                    const dynamicColumns = dividosNombre.map((monto) => row[monto.toString()] || "0");
                    return [
                        row.loc_estado || key,
                        row.zona || "-",
                        row.banos_cantidad || "-",
                        ...dynamicColumns,
                    ];
                });
            });
        }

        // Encabezados dinámicos
        const columnHeaders = [
            "Loc_Estado",
            "Zona",
            "Banos_Cantidad",
            ...dividosNombre.map((monto) => `${monto.toLocaleString()}`),
        ];

        // Combinar encabezados y datos
        const worksheetData = [columnHeaders, ...formattedData];

        // Crear la hoja y agregarla al libro
        const worksheet = XLSX.utils.aoa_to_sheet(worksheetData);
        XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
    }

	try {
	    // Llamadas condicionales basadas en `checkVV` y `checkVH`
	    const promises = [];
	
	    if (checkVV) {
	        promises.push(fetchAndFormatDataDormitorio("/api/getDormitoriosRangos", dividosNombrePrecio, jsonDataPrecioVV, "VV Dormitorios x Precio"));
	        promises.push(fetchAndFormatDataDormitorio("/api/getDormitoriosRangos", dividosNombreM2, jsonDataM2VV, "VV Dormitorios x Precio m2"));
	        promises.push(fetchAndFormatDataDormitorio("/api/getDormitoriosRangos", dividosNombreSuperficie, jsonDataSuperficieVV, "VV Dormitorios x Superficie"));
			promises.push(fetchAndFormatDataBanios("/api/getBaniosRangos", dividosNombrePrecio, jsonDataPrecioVV, "VV Baños x Precio"));
	        promises.push(fetchAndFormatDataBanios("/api/getBaniosRangos", dividosNombreM2, jsonDataM2VV, "VV Baños x Precio m2"));
	        promises.push(fetchAndFormatDataBanios("/api/getBaniosRangos", dividosNombreSuperficie, jsonDataSuperficieVV, "VV Baños x Superficie"));
	    }
	
	    if (checkVH) {
	        promises.push(fetchAndFormatDataDormitorio("/api/getDormitoriosRangos", dividosNombrePrecio, jsonDataPrecioVH, "VH Dormitorios x Precio"));
	        promises.push(fetchAndFormatDataDormitorio("/api/getDormitoriosRangos", dividosNombreM2, jsonDataM2VH, "VH Dormitorios x Precio m2"));
	        promises.push(fetchAndFormatDataDormitorio("/api/getDormitoriosRangos", dividosNombreSuperficie, jsonDataSuperficieVH, "VH Dormitorios x Superficie"));
			promises.push(fetchAndFormatDataBanios("/api/getBaniosRangos", dividosNombrePrecio, jsonDataPrecioVH, "VH Baños x Precio"));
	        promises.push(fetchAndFormatDataBanios("/api/getBaniosRangos", dividosNombreM2, jsonDataM2VH, "VH Baños x Precio m2"));
	        promises.push(fetchAndFormatDataBanios("/api/getBaniosRangos", dividosNombreSuperficie, jsonDataSuperficieVH, "VH Baños x Superficie"));
	    }
	
	    // Esperar a que todas las llamadas se completen
	    await Promise.all(promises);
		
		// Exportar el archivo Excel
	    XLSX.writeFile(workbook, "ReporteFuncional_" + dateExcel + ".xlsx");
	
	} catch (error) {
	    console.error("Error durante la exportación:", error);
	    alert("Ocurrió un error durante la exportación.");
	}
	
	// Ocultar el modal
	const modal = bootstrap.Modal.getInstance(document.getElementById("dormitoriosModal"));
	modal.hide();
});
//FIN Procedimiento para exportar dormitorios

function getNombresColumns(monto1, monto2, divisor) {
    const resultados = [];;
    for (let i = parseInt(monto1); i <= parseInt(monto2); i += parseInt(divisor)) {
		resultados.push(i);
    }
	
	const resultadosOrganizados = []
	
	for (let i = 0; i < resultados.length; i++) {
		if(i==0){
			resultadosOrganizados.push('r'+(resultados[i]-1));
		}else if (i+1 == resultados.length) {
			resultadosOrganizados.push('r'+(resultados[i-1])+'_'+(resultados[i]-1));
			resultadosOrganizados.push('r'+resultados[i]);
		}else{
			resultadosOrganizados.push('r'+(resultados[i-1])+'_'+(resultados[i]-1));
		}
	}
	
    return resultadosOrganizados;
}

document.addEventListener("DOMContentLoaded", function () {
    const estadoSelect = document.querySelector("select[name='estados']");
    const zonaSelect = document.querySelector("select[name='zonas']");

    // Escuchar el cambio en el select de estados
    estadoSelect.addEventListener("change", function () {
        var selectedEstados = Array.from(document.querySelector("select[name='estados']").selectedOptions)
				        .map(option => option.value) // Obtén los valores
				        .filter(value => value.trim() !== ""); // Elimina valores vacíos

        if (selectedEstados.length === 0) {
            // Si no hay estados seleccionados, limpiar las opciones de zonas
            zonaSelect.innerHTML = '<option value="" disabled>Seleccione...</option>';
            $('.selectpicker').selectpicker("refresh"); // Refrescar selectpicker
            return;
        }
		
		// Llamar al webservice para obtener las zonas correspondientes
		fetch("/api/getZonasPorEstados", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(selectedEstados), // Enviar el array como JSON plano
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener zonas");
                }
                return response.json();
            })
            .then(zonas => {
                // Validar que la respuesta sea un arreglo
                if (!Array.isArray(zonas)) {
                    throw new Error("Respuesta inválida del servidor: no es un arreglo");
                }

                // Construir las opciones del select de zonas
                const opciones = zonas.map(zona =>
                    `<option value="${zona.catalogName}">${zona.catalogName}</option>`
                ).join("");

                // Actualizar el contenido del select de zonas
                zonaSelect.innerHTML = '<option value="" disabled>Seleccione...</option>' + opciones;

                // Refrescar selectpicker
                $('.selectpicker').selectpicker("refresh");
            })
            .catch(error => {
                console.error("Error al obtener zonas:", error);
                alert("Ocurrió un error al cargar las zonas.");
            });
    });
});

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('_');
}