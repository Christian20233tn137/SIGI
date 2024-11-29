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




