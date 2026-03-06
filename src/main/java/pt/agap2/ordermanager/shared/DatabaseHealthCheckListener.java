package pt.agap2.ordermanager.shared;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseHealthCheckListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // Fail fast: se DB não está acessível, não sobe
    EntityManager em = null;
    try {
      em = Jpa.em();
      Query q = em.createNativeQuery("SELECT 1");
      q.getSingleResult();
      // opcional: log
      System.out.println("[DB] Connection OK");
    } catch (Exception e) {
      System.err.println("[DB] Connection FAILED. Aborting deployment: " + e.getMessage());
      throw new RuntimeException("Database connection check failed", e);
    } finally {
      if (em != null) em.close();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // fecha o EMF quando o Tomcat derrubar a app
    try {
      Jpa.shutdown();
    } catch (Exception ignored) {}
  }
}