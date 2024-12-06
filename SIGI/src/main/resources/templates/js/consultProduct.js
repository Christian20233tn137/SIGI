document.addEventListener('DOMContentLoaded', function () {
    const API_URL = 'http://localhost:8080/producto';

    // Función para cargar la tabla de productos
    function loadTable() {
        fetch(`${API_URL}/all`)
            .then(response => response.json())
            .then(data => {
                const tableBody = document.querySelector('#productos-table tbody');
                tableBody.innerHTML = ''; // Limpiar la tabla

                if (data.type === "SUCCESS") {
                    data.result.forEach((product, index) => {
                        const row = document.createElement('tr');

                        // Número
                        const numberCell = document.createElement('th');
                        numberCell.scope = 'row';
                        numberCell.textContent = index + 1;
                        row.appendChild(numberCell);

                        // Nombre
                        const nameCell = document.createElement('td');
                        nameCell.textContent = product.nombre;
                        row.appendChild(nameCell);

                        // Cantidad
                        const quantityCell = document.createElement('td');
                        quantityCell.textContent = product.cantidad;
                        row.appendChild(quantityCell);

                        // Categoría
                        const categoryCell = document.createElement('td');
                        categoryCell.textContent = product.categoria;
                        row.appendChild(categoryCell);

                        // Precio Unitario
                        const priceCell = document.createElement('td');
                        priceCell.textContent = product.precio_unitario;
                        row.appendChild(priceCell);

                        // Proveedor
                        const providerCell = document.createElement('td');
                        providerCell.textContent = product.proveedor;
                        row.appendChild(providerCell);

                        // Estado
                        const statusCell = document.createElement('td');
                        statusCell.textContent = product.status ? 'Activo' : 'Inactivo';
                        row.appendChild(statusCell);

                        // Gestión
                        const actionsCell = document.createElement('td');

                        // Botón de Editar
                        const editButton = document.createElement('button');
                        editButton.className = 'btn btn-primary btn-sm mr-2';
                        editButton.textContent = 'Editar';
                        editButton.addEventListener('click', () => openEditModal(product));
                        actionsCell.appendChild(editButton);

                        // Botón Activar/Desactivar
                        const toggleButton = document.createElement('button');
                        toggleButton.className = `btn btn-sm ${product.status ? 'btn-danger' : 'btn-success'}`;
                        toggleButton.textContent = product.status ? 'Desactivar' : 'Activar';
                        toggleButton.addEventListener('click', () => toggleProductStatus(product.id, product, statusCell, toggleButton));
                        actionsCell.appendChild(toggleButton);

                        row.appendChild(actionsCell);
                        tableBody.appendChild(row);
                    });
                } else {
                    alert('Error al cargar los productos.');
                }
            })
            .catch(error => console.error('Error al cargar los productos:', error));
    }

    // Abrir el modal para editar o registrar
    function openEditModal(product = null) {
        const modal = document.querySelector('.modal');
        const form = document.getElementById('productForm');

        // Limpiar formulario
        form.reset();

        if (product) {
            document.getElementById('productId').value = product.id;
            document.getElementById('productName').value = product.nombre;
            document.getElementById('precioUnitario').value = product.precio_unitario;
            document.getElementById('cantidad').value = product.cantidad;
            document.getElementById('categoria').value = product.categoria_id; // Suponiendo un ID de categoría
            document.getElementById('proveedor').value = product.proveedor_id; // Suponiendo un ID de proveedor
        }

        modal.style.display = 'block';
    }

    // Cerrar el modal
    function closeModal() {
        const modal = document.querySelector('.modal');
        modal.style.display = 'none';
    }

    // Cambiar estado de producto
    function toggleProductStatus(id, product, statusCell, toggleButton) {
        const newStatus = !product.status;

        fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ status: newStatus })
        })
            .then(response => response.json())
            .then(data => {
                if (data.type === "SUCCESS") {
                    product.status = newStatus;
                    statusCell.textContent = newStatus ? 'Activo' : 'Inactivo';
                    toggleButton.textContent = newStatus ? 'Desactivar' : 'Activar';
                    toggleButton.className = `btn btn-sm ${newStatus ? 'btn-danger' : 'btn-success'}`;
                } else {
                    alert('Error al cambiar el estado del producto.');
                }
            })
            .catch(error => console.error('Error al cambiar el estado:', error));
    }

    // Registrar o actualizar producto
    document.getElementById('registerProductButton').addEventListener('click', (event) => {
        event.preventDefault();

        const productId = document.getElementById('productId').value;
        const productName = document.getElementById('productName').value;
        const precioUnitario = document.getElementById('precioUnitario').value;
        const cantidad = document.getElementById('cantidad').value;
        const categoria = document.getElementById('categoria').value;
        const proveedor = document.getElementById('proveedor').value;

        if (!productName || !precioUnitario || !categoria || !proveedor) {
            alert('Por favor, complete todos los campos requeridos.');
            return;
        }

        const productData = {
            nombre: productName,
            precio_unitario: parseFloat(precioUnitario),
            cantidad: parseInt(cantidad, 10),
            categoria_id: parseInt(categoria, 10),
            proveedor_id: parseInt(proveedor, 10)
        };

        const method = productId ? 'PUT' : 'POST';
        const url = productId ? `${API_URL}/${productId}` : API_URL;

        fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(productData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.type === "SUCCESS") {
                    alert('Producto guardado exitosamente.');
                    closeModal();
                    loadTable();
                } else {
                    alert('Error al guardar el producto.');
                }
            })
            .catch(error => console.error('Error al guardar el producto:', error));
    });

    // Botones para cerrar el modal
    document.querySelectorAll('.close-modal').forEach(button => {
        button.addEventListener('click', closeModal);
    });

    // Inicializar la tabla al cargar la página
    loadTable();
});
