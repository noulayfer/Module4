package com.fedorenko.controller;

import com.fedorenko.model.Detail;
import com.fedorenko.service.DetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Stats", value = "/stats")
public class StatisticServlet extends HttpServlet {
    private DetailService detailService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticServlet.class);

    @Override
    public void init() {
        ServletContext context = getServletContext();
        detailService = (DetailService) context.getAttribute("detailService");
        if (detailService == null) {
            detailService = DetailService.getInstance();
            context.setAttribute("detailService", detailService);
            LOGGER.info("{} is initialized.", CreateDetailServlet.class.getSimpleName());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String id = req.getParameter("id");
        if (id != null) {
            final Detail detail = detailService.findById(id);
            req.setAttribute("detail", detail);
            req.getRequestDispatcher("detail.jsp").forward(req, resp);
            LOGGER.info("Detail with {} - id.", detail.getId());
        } else {
            final List<Detail> allDetails = detailService.getAll();
            final int amountOfBrokenMicroSchemas = detailService.getAmountOfBrokenMicroSchemas();
            final int amountOfGas = detailService.getTotalAmountOfGas();
            req.setAttribute("amount_of_details", allDetails.size());
            req.setAttribute("amount_of_broken_schemas", amountOfBrokenMicroSchemas);
            req.setAttribute("total_amount_of_gas", amountOfGas);
            final List<String> ids = detailService.getAllId();
            req.setAttribute("ids", ids);
            req.getRequestDispatcher("stats.jsp").forward(req, resp);
            LOGGER.info("All details is shown.");
        }
    }
}
