package com.waracle.cakemgr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"", "/cakes"})
public class CakeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();

        System.out.println("init started");

        // TODO - add dependency injection framework, this is seriously janky in 2017!
        new DatastoreInitialiser()
            .init();

        System.out.println("init finished");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getHeader("Accept").contains("json")) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<CakeEntity> list = session.createCriteria(CakeEntity.class).list();
            new ObjectMapper().writeValue(resp.getOutputStream(), list);
        } else {
            RequestDispatcher view = req.getRequestDispatcher("/cakes.html");
            view.forward(req, resp);
        }
    }

}
