
import SaveDB.CallStoredProcedures;
import java.io.IOException;
import login.SignUp;
import org.json.JSONObject;

public class test {

    public static void main(String[] args) {
   /*     try {
            

           JSONObject obj = new JSONObject();

            obj.put("userId", 1);
            obj.put("vehicleNumber", 123456);
            GetDataLive gdl = new GetDataLive();
//            JSONObject jsonToken = new JSONObject(gdl.getToken(obj));
//            String tokenToSend = jsonToken.optString("token");
//            System.out.println(tokenToSend);

            
String o = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInZlaGljbGVOdW1iZXIiOjEyMzQ1NiwidXNlck5hbWUiOiJEYW5pZWwiLCJpYXQiOjE2Njc3MzU5MzcsImV4cCI6MTY2OTQ2MzkzN30.z8-i0RdJ4es8GctePV7mICpnzKk08URe36Em9epT8ns";
            JSONObject data = new JSONObject(gdl.getData(o));
            System.out.println(data);
         */  // System.out.println(isTravelError(data));
            CallStoredProcedures csp = new CallStoredProcedures();
            CallStoredProcedures.begin();
           // System.out.println(csp.saveErrorInDB(data));
           csp.bole();
            CallStoredProcedures.end();
      //  } catch (InterruptedException | IOException ex) {
            //System.out.println(ex.getMessage());
        //}

    }

    public static boolean isTravelError(JSONObject data) {

        return !(data.getJSONObject("ForwardWarning").getString("Directions").equals("Good")
                || !(data.getJSONObject("Speed").getBoolean("SpeedAboveLimit"))
                || !(data.getBoolean("SuddenBraking"))
                || data.getString("LaneDepartureWarning").equals("Good"));
    }

}
