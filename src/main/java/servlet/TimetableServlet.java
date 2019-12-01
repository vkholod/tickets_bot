package servlet;

import com.vkholod.wizzair.tickets_bot.TimetableGenerator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "Timetable",
        urlPatterns = {"/timetable"}
)
public class TimetableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("flight_pairs", TimetableGenerator.generate());
        req.getRequestDispatcher("/timetable.jsp").forward(req, resp);

    }

}
