package SaveDB;

import javax.persistence.*;
import login.SignUp;
import org.json.JSONObject;

public class CallStoredProcedures {

    public static EntityManagerFactory factory;
    public static EntityManager entityManager;
    public static StoredProcedureQuery procedureQuery;

    public void bole() {
        Query x = entityManager.createNamedQuery("Users.findIfUserNameOREmailExists");
        x.setParameter("userName", "yaira");
        x.setParameter("email", "yair@gmail.com'");
        System.out.println(x.getResultList());

    }

    public void isEmailExists(SignUp user) {
        Query x = entityManager.createNamedQuery("Users.findByEmail");
        x.setParameter("email", user.getEmail());
        System.out.println(!x.getResultList().isEmpty());

    }
    
    public void isUserNameExists(SignUp user) {
        Query x = entityManager.createNamedQuery("Users.findByUserName");
        x.setParameter("userName", user.getUserName());
        System.out.println(!x.getResultList().isEmpty());

    }

    public Integer saveErrorInDB(JSONObject jsonData) {
        procedureQuery = entityManager.createStoredProcedureQuery("InsertInformationInRealTime");
        procedureQuery.registerStoredProcedureParameter("tripID", Integer.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("timeFromBeginning", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("lat", Float.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("lon", Float.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("forwardWarningDirection", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("forwardWarningDistance", Object.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("laneDepartureWarning", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("pedestrianAndCyclistCollisionWarning", String.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("suddenBraking", Boolean.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("speedAllowed", Integer.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("currentSpeed", Integer.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("distanceTraveledMile", Float.class, ParameterMode.IN);
        procedureQuery.registerStoredProcedureParameter("ret", Integer.class, ParameterMode.OUT);
        //insert values.
        procedureQuery.setParameter("tripID", 1);
        procedureQuery.setParameter("timeFromBeginning", jsonData.getString("TimeFromBeginning"));
        procedureQuery.setParameter("lat", jsonData.getFloat("Latitude"));
        procedureQuery.setParameter("lon", jsonData.getFloat("Longitude"));
        procedureQuery.setParameter("forwardWarningDirection", jsonData.getJSONObject("ForwardWarning").getString("Directions"));
        procedureQuery.setParameter("forwardWarningDistance", jsonData.getJSONObject("ForwardWarning").get("Distance"));
        procedureQuery.setParameter("laneDepartureWarning", jsonData.getString("LaneDepartureWarning"));
        procedureQuery.setParameter("pedestrianAndCyclistCollisionWarning", jsonData.getString("Pedestrian&CyclistCollisionWarning"));
        procedureQuery.setParameter("suddenBraking", jsonData.getBoolean("SuddenBraking"));
        procedureQuery.setParameter("speedAllowed", jsonData.getJSONObject("Speed").getInt("SpeedAllowed"));
        procedureQuery.setParameter("currentSpeed", jsonData.getJSONObject("Speed").getInt("CurrentSpeed"));
        procedureQuery.setParameter("distanceTraveledMile", jsonData.getInt("DistanceTraveledMile"));

        procedureQuery.execute();

        return (Integer) procedureQuery.getOutputParameterValue("ret");

    }

    public static void begin() {
        factory = Persistence.createEntityManagerFactory("main_RealTimeDrivingInformation_jar_1.0-SNAPSHOTPU");
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    public static void end() {
        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();

    }
}
