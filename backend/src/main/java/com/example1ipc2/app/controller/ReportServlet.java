package com.example1ipc2.app.controller;

import com.example1ipc2.app.model.UserReportDTO;
import com.example1ipc2.app.service.ReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = "/areaAdmin/reports")
public class ReportServlet extends HttpServlet {
    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        System.out.println("Recibiendo en doGet");
        try {
            System.out.println("Generando reporte");
            List<UserReportDTO> users = reportService.getUsersForReport();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);

            InputStream jasper = getClass().getResourceAsStream("/reports/EjemploEmpleadosjrxml.jasper");
            InputStream logo = getClass().getResourceAsStream("/reports/logo.png");

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("LOGO", logo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, dataSource);

            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=usuarios.pdf");

            JasperExportManager.exportReportToPdfStream(jasperPrint, resp.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo generar el reporte.");
            System.out.println("No se pudo generar el reporte.");
        }
    }
}
