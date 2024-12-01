// Selecciona el botón para abrir el modal y el modal mismo
const openModal = document.querySelector('.plus-button');
const modal = document.querySelector('.modal');

// Selecciona el botón para cerrar el modal
const closeModal = document.querySelector('.close-modal');

// Agregar evento al botón para abrir el modal
openModal.addEventListener('click', (e) => {
    e.preventDefault(); // Prevenir comportamientos predeterminados
    modal.classList.add('modal-show'); // Mostrar modal
});

// Agregar evento al botón para cerrar el modal
closeModal.addEventListener('click', (e) => {
    e.preventDefault();
    modal.classList.remove('modal-show'); // Ocultar modal
});

//Codigo para el buscador 
 // Función para filtrar la tabla
 function filterTable() {
    const searchInput = document.getElementById("searchInput").value.toLowerCase(); // Obtener el valor del input en minúsculas
    const rows = document.querySelectorAll(".provider-table tbody tr"); // Seleccionar todas las filas del cuerpo de la tabla

    rows.forEach(row => {
        const cells = row.querySelectorAll("td"); // Obtener todas las celdas de la fila
        const rowText = Array.from(cells).map(cell => cell.textContent.toLowerCase()).join(" "); // Concatenar el texto de todas las celdas

        if (rowText.includes(searchInput)) {
            row.style.display = ""; // Mostrar la fila si coincide con la búsqueda
        } else {
            row.style.display = "none"; // Ocultar la fila si no coincide
        }
    });
}

// Asignar el evento al input
document.getElementById("searchInput").addEventListener("input", filterTable);










// Registro de categorias 
function registrarCategoria() {
    // Obtén los valores de los inputs
    const nombre = document.getElementById("nombreCategoria").value;
    const descripcion = document.getElementById("descripcionCategoria").value;

    // Crea el objeto a enviar
    const categoria = {
        nombre: nombre,
        descripcion: descripcion,
        estado: true // Por defecto, las categorías se registran activas
    };

    // Realiza la solicitud POST al backend
    fetch("/categorias", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(categoria)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error al registrar la categoría");
            }
            return response.json();
        })
        .then(data => {
            alert("¡Categoría registrada con éxito!");
            cerrarModal(); // Cierra el modal
            cargarCategorias(); // Actualiza la tabla
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Ocurrió un error al registrar la categoría.");
        });
}


//Agregar categoria a tabla 
function agregarCategoriaATabla(categoria) {
    const tbody = document.querySelector(".provider-table tbody");

    // Crea una nueva fila
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${categoria.nombre}</td>
        <td>${categoria.descripcion}</td>
        <td>${categoria.estado ? "Activo" : "Inactivo"}</td>
        <td>
            <button class="action-button" onclick="editarCategoria(${categoria.id})">
                <i class="fas fa-edit"></i>
            </button>
            <button class="habilitar" onclick="cambiarEstadoCategoria(${categoria.id}, true)">
                <i class="fas fa-check-circle"> Habilitar</i>
            </button>
            <button class="deshabilitar" onclick="cambiarEstadoCategoria(${categoria.id}, false)">
                <i class="fas fa-times-circle"> Deshabilitar</i>
            </button>
        </td>
    `;

    // Añade la nueva fila al cuerpo de la tabla
    tbody.appendChild(row);
}
