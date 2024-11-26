package ar.edu.unq.dapp_api.webservice.dto.user;

public class RequestLoginUserDTO {
    private String email;
    private String password;

    public RequestLoginUserDTO() {
    }

    public RequestLoginUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
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
}