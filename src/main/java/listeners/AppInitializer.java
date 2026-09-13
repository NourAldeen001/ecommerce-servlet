package listeners;

import dao.ConnectionFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
/**
 * Application Lifecycle Listener implementation class AppLifecycleListener
 *
 */
public class AppInitializer implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public AppInitializer() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	ServletContext context = sce.getServletContext();
    	String url = context.getInitParameter("DB-URL");
    	String user = context.getInitParameter("DB-USER");
    	String pass = context.getInitParameter("DB-PASS");
    	
    	if(url == null || user == null || pass == null) {
    		throw new IllegalStateException("DB-URL | DB-USER | DB-PASS missing from web.xml");
    	}	
    	context.setAttribute("connectionFactory", new ConnectionFactory(url, user, pass));
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	System.out.println("App Shutting Down: ");
    }
	
}
