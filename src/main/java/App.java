import database.DBConnection;
import filters.CalculatorFilter;
import filters.CalculatorFilterDivByZero;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

import javax.servlet.DispatcherType;
import java.sql.Connection;
import java.util.EnumSet;

public class App {

    public static void main(String[] args) throws Exception {
        ServletContextHandler handler = new ServletContextHandler();
        Connection connection = new DBConnection().connection();

        handler.addServlet(new ServletHolder(new ServletRegistration()), "/registration/*");
        handler.addServlet(new ServletHolder(new ServletLogin()), "/login/*");
        handler.addServlet(new ServletHolder(new ServletLogout()), "/logout/*");
        handler.addServlet(new ServletHolder(new ServletCalculator(connection)), "/calculation/*");
        handler.addServlet(new ServletHolder(new ServletHistory()), "/history/*");
        handler.addServlet(new ServletHolder(new ServletCalculationHistory(connection)), "/calculation/history/*");

        handler.addFilter(CalculatorFilter.class, "/calculation/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
        handler.addFilter(CalculatorFilterDivByZero.class, "/calculation/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));

        Server server = new Server(82);
        server.setHandler(handler);
        server.start();
        server.join();
    }

}