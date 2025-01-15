$(document).ready(function(){
    
  $('#myTable').pageMe({pagerSelector:'#myPager',showPrevNext:true,hidePageNumbers:false,perPage:250});
  
  var hoy = new Date();
  var hoyMesesAtras = new Date();
  hoyMesesAtras.setMonth(hoyMesesAtras.getMonth() - 3);
  
  var mesActual = ("0" + (hoy.getMonth() + 1)).slice(-2);
  var anioActual = hoy.getFullYear();
  var mesPasado = ("0" + (hoyMesesAtras.getMonth() + 1)).slice(-2);

  var inputIni = document.getElementById("iniFecBusc");
  var inputFin = document.getElementById("finFecBusc");

  inputIni.max = anioActual+"-"+mesActual;
  inputFin.max = anioActual+"-"+mesActual;
  
  inputIni.value = hoyMesesAtras.getFullYear() + "-"+ mesPasado;
  inputFin.value = anioActual+"-"+mesActual;
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
		
		// Obtén los valores seleccionados de los selects
		var selectedEstados = Array.from(document.querySelector("select[name='estados']").selectedOptions)
		        .map(option => option.value) // Obtén los valores
		        .filter(value => value.trim() !== ""); // Elimina valores vacíos
		var selectedTipos = Array.from(document.querySelector("select[name='tipos']").selectedOptions)
		        .map(option => option.value) // Obtén los valores
		        .filter(value => value.trim() !== ""); // Elimina valores vacíos
		var selectedZonas = Array.from(document.querySelector("select[name='zonas']").selectedOptions)
		        .map(option => option.value) // Obtén los valores
		        .filter(value => value.trim() !== ""); // Elimina valores vacíos
		var selectedSegmentos = Array.from(document.querySelector("select[name='segmentos']").selectedOptions)
		        .map(option => option.value) // Obtén los valores
		        .filter(value => value.trim() !== ""); // Elimina valores vacíos
		
        // Captura los datos del formulario con FormData
        const formData = new FormData(form);

        // Convierte los datos a un objeto JSON
        const jsonData = {
            estados: selectedEstados,
            tipos: selectedTipos,
            zonas: selectedZonas,
            segmentos: selectedSegmentos,
            iniPrecioFBusc: formData.get("iniPrecioFBusc"),
            finPrecioFBusc: formData.get("finPrecioFBusc"),
            iniPrecioM2Busc: formData.get("iniPrecioM2Busc"),
            finPrecioM2Busc: formData.get("finPrecioM2Busc"),
            iniFecBusc: formData.get("iniFecBusc"),
            finFecBusc: formData.get("finFecBusc"),
        };

        // Llamar al webservice usando fetch
        fetch("/api/consulta", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(jsonData), // Convierte los datos a JSON
        })
            .then((response) => response.json()) // Procesar la respuesta como JSON
            .then((data) => {
                console.log("Datos recibidos:", data); // Para depuración
                actualizarTabla(data); // Llamar a la función para actualizar la tabla
            })
            .catch((error) => {
                console.error("Error al llamar al webservice:", error);
            });
	});

    // Función para actualizar la tabla con los datos recibidos
    function actualizarTabla(data) {
        const tableBody = document.querySelector("#myTable tbody");
        tableBody.innerHTML = ""; // Limpia el contenido actual de la tabla

        // Iterar sobre los datos y agregar filas a la tabla
        data.forEach((row) => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
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
            tableBody.appendChild(tr);
        });
		
		// Reiniciar el paginador después de actualizar la tabla
		$('#myPager').empty(); // Limpia el paginador existente
		$('#myTable').pageMe({ pagerSelector: '#myPager', showPrevNext: true, hidePageNumbers: false, perPage: 250 });
    }
	
	// Función para formatear los precios en estilo de moneda
	function formatCurrency(value) {
	    if (value == null) return ""; // Si el valor es nulo o indefinido, retorna vacío
	    return new Intl.NumberFormat("es-MX", {
	        minimumFractionDigits: 2,
	        maximumFractionDigits: 2,
	    }).format(value);
	}
});

document.getElementById("exportExcel").addEventListener("click", function () {
    exportTableToExcel("myTable", "DatosExportados");
});

function exportTableToExcel(tableID, fileName) {
    // Obtén la tabla
    const table = document.getElementById(tableID);
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

    // Crea la hoja de cálculo con los datos
    const worksheet = XLSX.utils.aoa_to_sheet(data);

    // Crea un libro de trabajo y agrega la hoja de cálculo
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Datos");

    // Genera y descarga el archivo Excel
    XLSX.writeFile(workbook, `${fileName}.xlsx`);
}

//Procedimiento para exportar dormitorios
document.getElementById("startExport").addEventListener("click", async function () {
    const precioIniExcel = document.getElementById("precioIniExcel").value;
    const precioFinExcel = document.getElementById("precioFinExcel").value;
	const divisorExcel = document.getElementById("divisorExcel").value;
	const precioRazon = document.querySelector('input[name="precioXExcel"]:checked').value;
	
	
	//Validaciones
    if (!precioIniExcel || !precioFinExcel) {
        alert("Por favor, complete ambos parámetros.");
        return;
    }
	
	//Si no hay ninguno lanzamos alerta
    if(!document.querySelector('input[name="precioXExcel"]:checked')) {
		alert('Por favor, seleccione un tipo de Precio.');
		return;
  	}
	
	// Obtén los valores seleccionados de los selects
	var selectedEstados = Array.from(document.querySelector("select[name='estados']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
	var selectedTipos = Array.from(document.querySelector("select[name='tipos']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
	var selectedZonas = Array.from(document.querySelector("select[name='zonas']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
	var selectedSegmentos = Array.from(document.querySelector("select[name='segmentos']").selectedOptions)
	        .map(option => option.value) // Obtén los valores
	        .filter(value => value.trim() !== ""); // Elimina valores vacíos
			
	// Convierte los datos a un objeto JSON
	const jsonData = {
	    estados: selectedEstados,
	    tipos: selectedTipos,
	    zonas: selectedZonas,
	    segmentos: selectedSegmentos,
	    iniPrecioFBusc: (precioRazon==1 ? precioIniExcel : ''),
	    finPrecioFBusc: (precioRazon==1 ? precioFinExcel : ''),
	    iniPrecioM2Busc: (precioRazon==0 ? precioIniExcel : ''),
	    finPrecioM2Busc: (precioRazon==0 ? precioFinExcel : ''),
	    iniFecBusc: document.getElementById("iniFecBusc").value,
	    finFecBusc: document.getElementById("finFecBusc").value,
		rangosPrecio: divisorExcel,
		indicadorMonto: precioRazon,
	};
	
	var dividosNombre = getNombresColumns(precioIniExcel, precioFinExcel, divisorExcel);
	
	// Llamar al webservice usando fetch
	fetch("/api/getDormitoriosRango", {
	    method: "POST",
	    headers: {
	        "Content-Type": "application/json",
	    },
	    body: JSON.stringify(jsonData), // Convierte los datos a JSON
	})
	    .then((response) => response.json()) // Procesar la respuesta como JSON
	    .then((data) => {
			// Iterar sobre las claves del objeto `data` y procesar los datos
	        let formattedData = [];
	        if (data && typeof data === "object") {
	            formattedData = Object.keys(data).flatMap(key => {
	                return data[key].map(row => {
	                    // Construir dinámicamente las columnas de precios
	                    const dynamicColumns = dividosNombre.map(monto => row[monto.toString()] || "0");
	                    return [
	                        row.loc_estado || key, // Usa la clave principal si no existe `loc_estado`
	                        row.loc_zona || "-",   // Valor por defecto si falta
	                        row.dormitorios_cantidad || "-",
	                        ...dynamicColumns     // Agrega columnas dinámicas
	                    ];
	                });
	            });
	        } else {
	            throw new Error("Formato inesperado en la respuesta del webservice");
	        }

	        // Generar encabezados dinámicos
	        const columnHeaders = [
	            "Loc_Estado",
	            "Loc_Zona",
	            "Dormitorios_Cantidad",
	            ...dividosNombre.map(monto => `${monto.toLocaleString()}`)
	        ];

	        // Combinar encabezados y datos en una matriz final para el Excel
	        const worksheetData = [
	            columnHeaders,
	            ...formattedData
	        ];

	        // Crear la hoja de cálculo
	        const worksheet = XLSX.utils.aoa_to_sheet(worksheetData);

	        // Crear un libro de trabajo y agregar la hoja
	        const workbook = XLSX.utils.book_new();
	        XLSX.utils.book_append_sheet(workbook, worksheet, "Dormitorios por rango");

	        // Generar y descargar el archivo Excel
	        XLSX.writeFile(workbook, "DormitoriosRangoExportados.xlsx");
	    })
	    .catch((error) => {
	        console.error("Error al llamar al webservice:", error);
	    });
		
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