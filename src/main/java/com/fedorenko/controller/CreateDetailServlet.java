package com.fedorenko.controller;

import com.fedorenko.model.Detail;
import com.fedorenko.service.DetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CreateDetail", value = "/start")
public class CreateDetailServlet extends HttpServlet {
    private DetailService detailService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateDetailServlet.class);

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        final PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Loading</title>");
        out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"loading\">");
        out.println("        Please wait, loading...");
        out.println("    <form action=\"index.html\">");
        out.println("        <button class=\"loading-button\" type=\"submit\"><span class=\"loading-spinner\"></span>Go to Menu</button>");
        out.println("    </form>");
        out.println("    </div>");
        out.println("    <script>");
        out.println("        window.onload = function() {");
        out.println("            window.location.href = \"" + req.getContextPath() + "/stats\";");
        out.println("        }");
        out.println("    </script>");
        out.println("</body>");
        out.println("</html>");
        out.flush();

        final Detail detail = detailService.createDetailAndSave();
        req.setAttribute("id", detail.getId());
        LOGGER.info("Detail with {} - id was created.", detail.getId());
    }
}
