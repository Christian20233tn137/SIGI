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




