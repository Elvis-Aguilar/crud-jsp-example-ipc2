package com.example1ipc2.app.controller;

import com.example1ipc2.app.model.RoleModel;
import com.example1ipc2.app.model.UserModel;
import com.example1ipc2.app.persistence.RoleDAO;
import com.example1ipc2.app.persistence.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author elvis
 */
@WebServlet(urlPatterns = "/areaAdmin/UserServlet")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private RoleDAO roleDAO = new RoleDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<UserModel> users = userDAO.findAll();
            request.setAttribute("users", users);
            List<RoleModel> roles = this.roleDAO.findAll();
            request.setAttribute("roles", roles);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/areaAdmin/adminPersonal.jsp?view=list");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String method = request.getParameter("_method");
        if (method != null && method.equalsIgnoreCase("PUT")) {
            doPut(request, response);
            return;
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String adress = request.getParameter("adress");
        String dpi = request.getParameter("dpi");
        String roleId = request.getParameter("rolId");
        String password = request.getParameter("password");

        UserModel userForm = new UserModel(name, email, adress, dpi, password, Integer.valueOf(roleId));

        try {
            this.userDAO.insert(userForm);
            response.sendRedirect("adminPersonal.jsp?view=create&success=true");
            // mostrar un swetAlert de usuario guardado 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String action = request.getParameter("action");

        if ("darDeBaja".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            try {
                UserModel user = userDAO.findById(userId);
                if (user != null) {
                    user.setState("DISABLED");
                    userDAO.update(user);
                    response.sendRedirect("adminPersonal.jsp?view=list&enable=true");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            processRequest(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
