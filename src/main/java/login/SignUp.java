package login;

import org.apache.commons.validator.EmailValidator;

public class SignUp {

    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Integer managerId;
    private Integer idCreated;

    public SignUp(String fullName, String userName, String email, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public SignUp(String fullName, String userName, String email, String password, Integer managerId) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.managerId = managerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getIdCreated() {
        return idCreated;
    }

    public void setIdCreated(Integer idCreated) {
        this.idCreated = idCreated;
    }

    
    public boolean validPassword(String password) {
        Boolean hasBig = false;
        boolean hasSmall = false;
        boolean hasNum = false;
        for (char c : password.toCharArray()) {
            if (!hasSmall && c >= 'a' && c <= 'z') {
                hasSmall = true;
            } else if (!hasBig && c >= 'A' && c <= 'Z') {
                hasBig = true;
            } else if (!hasNum && c >= '0' && c <= '9') {
                hasNum = true;
            }
        }
        return hasBig && hasSmall && hasNum;
    }

    public boolean validEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
    
    

}
