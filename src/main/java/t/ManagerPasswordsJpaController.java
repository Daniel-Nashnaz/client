package t;

import SaveDB.ManagerPasswords;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import SaveDB.Managers;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import t.exceptions.NonexistentEntityException;

/**
 *
 * @author Daniel
 */
public class ManagerPasswordsJpaController implements Serializable {

    public ManagerPasswordsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ManagerPasswords managerPasswords) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Managers managerID = managerPasswords.getManagerID();
            if (managerID != null) {
                managerID = em.getReference(managerID.getClass(), managerID.getId());
                managerPasswords.setManagerID(managerID);
            }
            em.persist(managerPasswords);
            if (managerID != null) {
                managerID.getManagerPasswordsCollection().add(managerPasswords);
                managerID = em.merge(managerID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ManagerPasswords managerPasswords) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ManagerPasswords persistentManagerPasswords = em.find(ManagerPasswords.class, managerPasswords.getId());
            Managers managerIDOld = persistentManagerPasswords.getManagerID();
            Managers managerIDNew = managerPasswords.getManagerID();
            if (managerIDNew != null) {
                managerIDNew = em.getReference(managerIDNew.getClass(), managerIDNew.getId());
                managerPasswords.setManagerID(managerIDNew);
            }
            managerPasswords = em.merge(managerPasswords);
            if (managerIDOld != null && !managerIDOld.equals(managerIDNew)) {
                managerIDOld.getManagerPasswordsCollection().remove(managerPasswords);
                managerIDOld = em.merge(managerIDOld);
            }
            if (managerIDNew != null && !managerIDNew.equals(managerIDOld)) {
                managerIDNew.getManagerPasswordsCollection().add(managerPasswords);
                managerIDNew = em.merge(managerIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = managerPasswords.getId();
                if (findManagerPasswords(id) == null) {
                    throw new NonexistentEntityException("The managerPasswords with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ManagerPasswords managerPasswords;
            try {
                managerPasswords = em.getReference(ManagerPasswords.class, id);
                managerPasswords.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The managerPasswords with id " + id + " no longer exists.", enfe);
            }
            Managers managerID = managerPasswords.getManagerID();
            if (managerID != null) {
                managerID.getManagerPasswordsCollection().remove(managerPasswords);
                managerID = em.merge(managerID);
            }
            em.remove(managerPasswords);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ManagerPasswords> findManagerPasswordsEntities() {
        return findManagerPasswordsEntities(true, -1, -1);
    }

    public List<ManagerPasswords> findManagerPasswordsEntities(int maxResults, int firstResult) {
        return findManagerPasswordsEntities(false, maxResults, firstResult);
    }

    private List<ManagerPasswords> findManagerPasswordsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ManagerPasswords.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ManagerPasswords findManagerPasswords(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ManagerPasswords.class, id);
        } finally {
            em.close();
        }
    }

    public int getManagerPasswordsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ManagerPasswords> rt = cq.from(ManagerPasswords.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
