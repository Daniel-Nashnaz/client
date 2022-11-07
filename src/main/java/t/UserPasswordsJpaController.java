/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t;

import SaveDB.UserPasswords;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import SaveDB.Users;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import t.exceptions.NonexistentEntityException;

/**
 *
 * @author Daniel
 */
public class UserPasswordsJpaController implements Serializable {

    public UserPasswordsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserPasswords userPasswords) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userID = userPasswords.getUserID();
            if (userID != null) {
                userID = em.getReference(userID.getClass(), userID.getId());
                userPasswords.setUserID(userID);
            }
            em.persist(userPasswords);
            if (userID != null) {
                userID.getUserPasswordsCollection().add(userPasswords);
                userID = em.merge(userID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserPasswords userPasswords) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserPasswords persistentUserPasswords = em.find(UserPasswords.class, userPasswords.getId());
            Users userIDOld = persistentUserPasswords.getUserID();
            Users userIDNew = userPasswords.getUserID();
            if (userIDNew != null) {
                userIDNew = em.getReference(userIDNew.getClass(), userIDNew.getId());
                userPasswords.setUserID(userIDNew);
            }
            userPasswords = em.merge(userPasswords);
            if (userIDOld != null && !userIDOld.equals(userIDNew)) {
                userIDOld.getUserPasswordsCollection().remove(userPasswords);
                userIDOld = em.merge(userIDOld);
            }
            if (userIDNew != null && !userIDNew.equals(userIDOld)) {
                userIDNew.getUserPasswordsCollection().add(userPasswords);
                userIDNew = em.merge(userIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userPasswords.getId();
                if (findUserPasswords(id) == null) {
                    throw new NonexistentEntityException("The userPasswords with id " + id + " no longer exists.");
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
            UserPasswords userPasswords;
            try {
                userPasswords = em.getReference(UserPasswords.class, id);
                userPasswords.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userPasswords with id " + id + " no longer exists.", enfe);
            }
            Users userID = userPasswords.getUserID();
            if (userID != null) {
                userID.getUserPasswordsCollection().remove(userPasswords);
                userID = em.merge(userID);
            }
            em.remove(userPasswords);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserPasswords> findUserPasswordsEntities() {
        return findUserPasswordsEntities(true, -1, -1);
    }

    public List<UserPasswords> findUserPasswordsEntities(int maxResults, int firstResult) {
        return findUserPasswordsEntities(false, maxResults, firstResult);
    }

    private List<UserPasswords> findUserPasswordsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserPasswords.class));
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

    public UserPasswords findUserPasswords(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserPasswords.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserPasswordsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserPasswords> rt = cq.from(UserPasswords.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
