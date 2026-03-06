package pt.agap2.ordermanager.shared;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class Jpa {
  private static final EntityManagerFactory EMF =
      Persistence.createEntityManagerFactory("orderManagerPU");

  public static EntityManager em() {
    return EMF.createEntityManager();
  }

  private Jpa() {}
  
  public static void shutdown() {
	  EMF.close();
	}
}