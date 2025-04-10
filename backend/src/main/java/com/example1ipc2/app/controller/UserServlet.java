package com.example1ipc2.app.controller;

import com.example1ipc2.app.aplication.Encript;
import com.example1ipc2.app.model.RoleModel;
import com.example1ipc2.app.model.UserModel;
import com.example1ipc2.app.persistence.RoleDAO;
import com.example1ipc2.app.persistence.UserDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author elvis
 */
@WebServlet(urlPatterns = "/areaAdmin/users/*")
public class UserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();
    private final Encript encript = new Encript();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<UserModel> users = userDAO.findAll();

            String json = new com.google.gson.Gson().toJson(users);
            response.getWriter().write(json);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener usuarios");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            BufferedReader reader = request.getReader();
            UserModel userForm = new com.google.gson.Gson().fromJson(reader, UserModel.class);

            // Encriptar password 
            userForm.setPassword(this.encript.ecnode(userForm.getPassword()));

            this.userDAO.insert(userForm);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\":\"Usuario creado correctamente\"}");

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al crear usuario");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtener el ID de la URL: /users/{id}
            String pathInfo = request.getPathInfo(); 
            if (pathInfo == null || pathInfo.equals("/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado en la URL");
                return;
            }

            int userId = Integer.parseInt(pathInfo.substring(1)); // Eliminar el "/"

            // Obtener el estado desde el body
            BufferedReader reader = request.getReader();
            UserModel userUpdate = new com.google.gson.Gson().fromJson(reader, UserModel.class);
            
            UserModel user = userDAO.findById(userId);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            }

            userUpdate.setId(userId);
                        
            userDAO.update(userUpdate);

            response.getWriter().write("{\"message\":\"Usuario actualizado\"}");

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar usuario");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
