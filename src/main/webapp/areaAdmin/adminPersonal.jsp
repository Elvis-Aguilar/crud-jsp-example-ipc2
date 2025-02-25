<%-- 
    Document   : adminPersonal
    Created on : 24 feb. 2025, 10:37:38
    Author     : elvis
--%>

<%@page import="com.example1ipc2.app.model.RoleModel"%>
<%@page import="java.util.List"%>
<%
    String view = request.getParameter("view");

    if ("create".equals(view) && request.getAttribute("roles") == null) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("RoleServlet");
        dispatcher.forward(request, response);
    }

    if ("list".equals(view) && request.getAttribute("users") == null) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserServlet");
        dispatcher.forward(request, response);
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de Usuarios</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body class="flex h-screen bg-gray-100">

        <!-- Sidebar -->
        <aside class="w-64 bg-gray-800 text-white flex flex-col">
            <div class="p-5 text-xl font-bold border-b border-gray-700">
                Panel de Usuarios
            </div>
            <nav class="flex-1 p-4">
                <a href="panelAdministracion.jsp" class="block px-4 py-2 rounded-lg hover:bg-gray-700">Panel administración</a>
                <a href="adminPersonal.jsp?view=create" class="block px-4 py-2 rounded-lg hover:bg-gray-700">Crear Usuario</a>
                <a href="adminPersonal.jsp?view=list" class="block px-4 py-2 rounded-lg hover:bg-gray-700">Ver Usuarios</a>
            </nav>
            <div class="p-4 border-t border-gray-700">
                <a href="#" class="block px-4 py-2 text-red-400 hover:bg-red-600 hover:text-white rounded-lg">
                    Cerrar sesión
                </a>
            </div>
        </aside>

        <!-- Contenido Principal -->
        <div class="flex-1 flex flex-col">

            <!-- Navbar -->
            <header class="bg-white shadow-md p-4 flex justify-between items-center">
                <h1 class="text-xl font-bold">Administración de Usuarios</h1>
                <div class="flex items-center space-x-4">
                    <span class="text-gray-700">Admin</span>
                    <img src="https://via.placeholder.com/40" class="w-10 h-10 rounded-full border">
                </div>
            </header>

            <!-- Contenido -->
            <main class="flex-1 p-6">
                <div class="bg-white p-6 shadow rounded-lg">

                    <!-- Título -->
                    <h2 class="text-2xl font-bold mb-4">Gestión de Usuarios y Roles</h2>
                    <p class="text-gray-600 mb-6">Aquí puedes crear nuevos usuarios, editar roles y dar de baja.</p>

                    <!-- Formulario para crear usuario -->
                    <% if ("create".equals(view)) { %> 
                    <div class="mb-8">
                        <h3 class="text-xl font-bold mb-3">Crear Nuevo Usuario</h3>
                        <form class="bg-gray-100 p-4 rounded-lg"
                              action="UserServlet" method="POST">
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div>
                                    <label class="block text-gray-700">Nombre</label>
                                    <input type="text" name="name" class="w-full p-2 border rounded-lg" required>
                                </div>
                                <div>
                                    <label class="block text-gray-700">Correo</label>
                                    <input type="email" name="email" class="w-full p-2 border rounded-lg" required>
                                </div>
                                <div>
                                    <label class="block text-gray-700">Direccion</label>
                                    <input type="text" name="adress" class="w-full p-2 border rounded-lg" required>
                                </div>
                                <div>
                                    <label class="block text-gray-700">DPI</label>
                                    <input type="text" name="dpi" class="w-full p-2 border rounded-lg" required>
                                </div>
                                <div>
                                    <label class="block text-gray-700">Rol</label>
                                    <select name="rolId" class="w-full p-2 border rounded-lg">
                                        <c:forEach var="role" items="${roles}">
                                            <option value="${role.id}">${role.name}</option>
                                        </c:forEach>
                                    </select>

                                </div>
                                <div>
                                    <label class="block text-gray-700">Contraseña</label>
                                    <input type="password" name="password" class="w-full p-2 border rounded-lg" required
                                           placeholder="mala practica XD">
                                </div>
                            </div>
                            <button type="submit" class="mt-4 bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600">
                                Crear Usuario
                            </button>
                        </form>
                    </div>
                    <% }%>

                    <!-- Tabla de usuarios -->
                    <% if ("list".equals(view)) { %> 
                    <div>
                        <h3 class="text-xl font-bold mb-3">Lista de Usuarios</h3>
                        <table class="w-full bg-white border border-gray-300 rounded-lg">
                            <thead class="bg-gray-200">
                                <tr>
                                    <th class="p-2 border">Nombre</th>
                                    <th class="p-2 border">Correo</th>
                                    <th class="p-2 border">Rol</th>
                                    <th class="p-2 border">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${users}">
                                    <tr class="hover:bg-gray-100">
                                        <td class="p-2 border">${user.name}</td>
                                        <td class="p-2 border">${user.email}</td>
                                        <td class="p-2 border">
                                            <c:forEach var="role" items="${roles}">
                                                <c:if test="${role.id == user.roleId}">
                                                    ${role.name}
                                                </c:if>
                                            </c:forEach>
                                        </td>

                                        <td class="p-2 border flex gap-2">
                                            <form action="UserServlet" method="POST">
                                                <input type="hidden" name="_method" value="PUT">
                                                <input type="hidden" name="action" value="update">
                                                <input type="hidden" name="userId" value="${user.id}">
                                                <button type="submit" class="bg-yellow-500 text-white px-3 py-1 rounded-lg hover:bg-yellow-700">
                                                    Editar
                                                </button>
                                            </form>
                                            <form action="UserServlet" method="POST">
                                                <input type="hidden" name="_method" value="PUT">
                                                <input type="hidden" name="action" value="darDeBaja">
                                                <input type="hidden" name="userId" value="${user.id}">
                                                <button type="submit" class="bg-green-500 text-white px-3 py-1 rounded-lg hover:bg-red-700">
                                                    dar de baja
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <% }%>


                </div>
            </main>

            <!-- Footer -->
            <footer class="bg-white text-center text-gray-600 p-4 border-t">
                &copy; 2025 Admin Panel. Todos los derechos reservados.
            </footer>

        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.has("success")) {
                    Swal.fire({
                        title: "¡Éxito!",
                        text: "Usuario guardado correctamente.",
                        icon: "success",
                        confirmButtonText: "OK"
                    }).then(() => {
                        // Limpiar la URL después de cerrar el SweetAlert
                        window.history.replaceState(null, null, window.location.pathname + "?view=create");
                    });
                }
                if (urlParams.has("enable")) {
                    Swal.fire({
                        title: "¡Éxito!",
                        text: "dado de baja con exito",
                        icon: "success",
                        confirmButtonText: "OK"
                    }).then(() => {
                        // Limpiar la URL después de cerrar el SweetAlert
                        window.history.replaceState(null, null, window.location.pathname + "?view=list");
                    });
                }
            });
        </script>


    </body>
</html>
