package ua.nure.kn.logvinova.usermanagement.web;
import ua.nure.kn.logvinova.usermanagement.User;
import ua.nure.kn.logvinova.usermanagement.db.DaoFactory;
import ua.nure.kn.logvinova.usermanagement.db.DatabaseException;
import ua.nure.kn.logvinova.usermanagement.db.UserDAO;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class BrowseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("addButton") != null) {
            add(req, resp);
        } else if (req.getParameter("editButton") != null) {
            edit(req, resp);
        } else if (req.getParameter("deleteButton") != null) {
            delete(req, resp);
        } else if (req.getParameter("detailsButton") != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }

    }
    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStrUser = req.getParameter("id");

        if (idStrUser == null || idStrUser.trim().isEmpty()) {
            req.setAttribute("error", "You must select a user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }

        try {
            User foundUser = DaoFactory.getInstance().getUserDAO().find(Long.parseLong(idStrUser));
            req.getSession(true).setAttribute("user", foundUser);
        } catch (Exception e) {
            req.setAttribute("error", "ERROR" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/details").forward(req, resp);
    }


    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStrUser = req.getParameter("id");

        if (idStrUser == null || idStrUser.trim().isEmpty()) {
            req.setAttribute("error", "You must select a user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }

        try {
            UserDAO userDao = DaoFactory.getInstance().getUserDAO();
            User deleteUser = userDao.find(Long.parseLong(idStrUser));
            userDao.delete(deleteUser);
        } catch (Exception e) {
            req.setAttribute("error", "ERROR" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/browse");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().length() == 0) {
            req.setAttribute("error", "You must select a user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            User user = (User) DaoFactory.getInstance().getUserDAO().find(new Long(idStr));
            req.getSession(true).setAttribute("user", user);
        } catch (Exception e) {
            req.setAttribute("error", "ERROR:" + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
    }



    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> users;
        try {
            users = DaoFactory.getInstance().getUserDAO().findAll();
            req.getSession().setAttribute("users", users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            throw new ServletException(e);
        }

    }

}