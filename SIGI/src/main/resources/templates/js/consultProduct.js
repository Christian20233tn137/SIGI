document.addEventListener('DOMContentLoaded', function () {
    // URL base de la API
    const API_URL = 'http://localhost:8080/producto'; // Cambia esto por la URL real de tu API
    
    // Función para obtener datos de la API y llenar la tabla
    function loadTable() {
        fetch(API_URL)
            .then(response => response.json())
            .then(data => {
                if (data.type === "SUCCESS") {
                    const tableBody = document.querySelector('#productos-table tbody');
                    tableBody.innerHTML = ''; // Limpiar la tabla antes de llenarla
                    data.result.forEach((producto, index) => {
                        const row = document.createElement('tr');


                        // Columna Nombre
                        const nameCell = document.createElement('td');
                        nameCell.textContent = producto.nombre;
                        row.appendChild(nameCell);

                        // Columna Cantidad
                        const cantidadCell = document.createElement('td');
                        cantidadCell.textContent = producto.cantidad;
                        row.appendChild(cantidadCell);

                        // Columna Categoría
                        const categoriaCell = document.createElement('td');
                        categoriaCell.textContent = producto.categoria;
                        row.appendChild(categoriaCell);

                        // Columna Precio
                        const precioCell = document.createElement('td');
                        precioCell.textContent = producto.precioUnitario;
                        row.appendChild(precioCell);

                        // Columna Proveedor
                        const proveedorCell = document.createElement('td');
                        proveedorCell.textContent = producto.proveedor;
                        row.appendChild(proveedorCell);

                        // Columna Estado
                        const estadoCell = document.createElement('td');
                        estadoCell.textContent = producto.estado ? 'Activo' : 'Inactivo';
                        row.appendChild(estadoCell);

                        // Columna Acciones
                        const actionsCell = document.createElement('td');

                        // Botón de Editar
                        const editButton = document.createElement('button');
                        editButton.className = 'btn btn-primary btn-sm mr-2';
                        editButton.textContent = 'Editar';
                        editButton.addEventListener('click', function () {
                            openEditModal(producto.id, producto.nombre, producto.categoria, producto.precioUnitario);
                        });
                        actionsCell.appendChild(editButton);

                        // Botón de Activar/Desactivar
                        const toggleButton = document.createElement('button');
                        toggleButton.className = `btn btn-sm ${producto.estado ? 'btn-danger' : 'btn-success'}`;
                        toggleButton.textContent = producto.estado ? 'Desactivar' : 'Activar';
                        toggleButton.addEventListener('click', function () {
                            toggleProductStatus(producto.id, row, estadoCell, toggleButton);
                        });
                        actionsCell.appendChild(toggleButton);

                        row.appendChild(actionsCell);
                        tableBody.appendChild(row);
                    });
                } else {
                    alert('Error al cargar los productos.');
                }
            })
            .catch(error => {
                console.error('Error al obtener los datos:', error);
            });
    }

    // Función para abrir el modal de edición
    function openEditModal(id, nombre, precio, categoria, proveedor, cantidad) {
        // Asignar los valores actuales a los campos del formulario de edición
        document.getElementById('productId').value = id;
        document.getElementById('productName').value = nombre;
        document.getElementById('precioUnitario').value = precio;
        document.getElementById('categoria').value = categoria;
        document.getElementById('proveedor').value = proveedor;
        document.getElementById('cantidad').value = cantidad;
        // Abrir el modal
        document.getElementById('productModal').style.display = 'block';
    }

    // Función para cerrar el modal
    function closeEditModal() {
        document.getElementById('productModal').style.display = 'none';
    }

    // Función para actualizar el estado del producto (Activar/Desactivar)
    function toggleProductStatus(id, row, estadoCell, toggleButton) {
        const newStatus = toggleButton.textContent === 'Activar' ? true : false;
        const updatedProduct = { estado: newStatus };

        fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedProduct)
        })
        .then(response => response.json())
        .then(data => {
            if (data.type === "SUCCESS") {
                estadoCell.textContent = newStatus ? 'Activo' : 'Inactivo';
                toggleButton.textContent = newStatus ? 'Desactivar' : 'Activar';
                toggleButton.className = `btn btn-sm ${newStatus ? 'btn-danger' : 'btn-success'}`;
            } else {
                alert('Error al actualizar el estado del producto');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al cambiar el estado del producto');
        });
    }

    // Registrar un nuevo producto
    document.getElementById("addProductButton").addEventListener("click", () => {
        document.getElementById("productModal").style.display = "block";
    });

    // Salir
    document.getElementById("closeModalButton").addEventListener("click", () => {
        document.getElementById("productModal").style.display = "none";
    });

    // Cancelar
    document.getElementById("cancelButton").addEventListener("click", () => {
        document.getElementById("productModal").style.display = "none";
    });

    // Registrar un nuevo producto
    document.getElementById("registerProductButton").addEventListener("click", function (event) {
        event.preventDefault();
    
        // Obtener valores del formulario
        const productId = document.getElementById('productId').value.trim();
        const productName = document.getElementById('productName').value.trim();
        const productPrice = document.getElementById('precioUnitario').value.trim();
        const productCategory = document.getElementById('categoria').value.trim();
        const productProveedor = document.getElementById('proveedor').value.trim();
        const productCantidad = document.getElementById('cantidad').value.trim();
    
        // Validación de campos obligatorios
        if (!productName || !productPrice || !productCategory || !productProveedor || !productCantidad) {
            alert('Por favor, complete todos los campos obligatorios.');
            return;
        }
    
        // Crear objeto de datos del producto
        const productData = {
            id: productId || null, // Asume `null` si no está disponible
            nombre: productName,
            precioUnitario: parseFloat(productPrice), // Asegurarse de que el precio sea numérico
            categoria: productCategory,
            proveedor: productProveedor,
            cantidad: parseInt(productCantidad, 10) // Convertir la cantidad a entero
        };
    
        // Llamada al backend para registrar el producto
        fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error en la solicitud: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            alert('Producto registrado exitosamente');
            // Limpiar el formulario
            document.getElementById('productId').value = '';
            document.getElementById('productName').value = '';
            document.getElementById('precioUnitario').value = '';
            document.getElementById('categoria').value = '';
            document.getElementById('proveedor').value = '';
            document.getElementById('cantidad').value = '';
            // Cerrar el modal y actualizar la tabla
            document.getElementById('productModal').style.display = 'none';
            loadTable(); // Función para actualizar la tabla de productos
        })
        .catch(error => {
            alert('Error al registrar el producto');
            console.error('Error:', error);
        });
    });
    

    // Inicializar la tabla al cargar la página
    loadTable();
});
