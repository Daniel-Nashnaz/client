package t;

import SaveDB.RealTimeInformation;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import SaveDB.Vehicles;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import t.exceptions.NonexistentEntityException;

public class RealTimeInformationJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public RealTimeInformationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RealTimeInformation realTimeInformation) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehicles tripID = realTimeInformation.getTripID();
            if (tripID != null) {
                tripID = em.getReference(tripID.getClass(), tripID.getTripID());
                realTimeInformation.setTripID(tripID);
            }
            em.persist(realTimeInformation);
            if (tripID != null) {
                tripID.getRealTimeInformationCollection().add(realTimeInformation);
                tripID = em.merge(tripID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RealTimeInformation realTimeInformation) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RealTimeInformation persistentRealTimeInformation = em.find(RealTimeInformation.class, realTimeInformation.getId());
            Vehicles tripIDOld = persistentRealTimeInformation.getTripID();
            Vehicles tripIDNew = realTimeInformation.getTripID();
            if (tripIDNew != null) {
                tripIDNew = em.getReference(tripIDNew.getClass(), tripIDNew.getTripID());
                realTimeInformation.setTripID(tripIDNew);
            }
            realTimeInformation = em.merge(realTimeInformation);
            if (tripIDOld != null && !tripIDOld.equals(tripIDNew)) {
                tripIDOld.getRealTimeInformationCollection().remove(realTimeInformation);
                tripIDOld = em.merge(tripIDOld);
            }
            if (tripIDNew != null && !tripIDNew.equals(tripIDOld)) {
                tripIDNew.getRealTimeInformationCollection().add(realTimeInformation);
                tripIDNew = em.merge(tripIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = realTimeInformation.getId();
                if (findRealTimeInformation(id) == null) {
                    throw new NonexistentEntityException("The realTimeInformation with id " + id + " no longer exists.");
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
            RealTimeInformation realTimeInformation;
            try {
                realTimeInformation = em.getReference(RealTimeInformation.class, id);
                realTimeInformation.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The realTimeInformation with id " + id + " no longer exists.", enfe);
            }
            Vehicles tripID = realTimeInformation.getTripID();
            if (tripID != null) {
                tripID.getRealTimeInformationCollection().remove(realTimeInformation);
                tripID = em.merge(tripID);
            }
            em.remove(realTimeInformation);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RealTimeInformation> findRealTimeInformationEntities() {
        return findRealTimeInformationEntities(true, -1, -1);
    }

    public List<RealTimeInformation> findRealTimeInformationEntities(int maxResults, int firstResult) {
        return findRealTimeInformationEntities(false, maxResults, firstResult);
    }

    private List<RealTimeInformation> findRealTimeInformationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RealTimeInformation.class));
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

    public RealTimeInformation findRealTimeInformation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RealTimeInformation.class, id);
        } finally {
            em.close();
        }
    }

    public int getRealTimeInformationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RealTimeInformation> rt = cq.from(RealTimeInformation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
