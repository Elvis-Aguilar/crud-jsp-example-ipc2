package com.example1ipc2.app.service;

import com.example1ipc2.app.model.RoleModel;
import com.example1ipc2.app.model.UserModel;
import com.example1ipc2.app.model.UserReportDTO;
import com.example1ipc2.app.persistence.RoleDAO;
import com.example1ipc2.app.persistence.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportService {
    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();

    public List<UserReportDTO> getUsersForReport() throws SQLException {
        List<UserModel> users = userDAO.findAll();
        List<UserReportDTO> reportList = new ArrayList<>();

        for (UserModel user : users) {
            RoleModel role = roleDAO.findById(user.getRoleId());

            UserReportDTO reportUser = new UserReportDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress(),
                    role.getName()
            );

            reportList.add(reportUser);
        }
        return reportList;
    }
}
