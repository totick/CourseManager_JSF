package beans;

import ejb.ILoginService;
import entities.*;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.*;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    @NotNull(message = "Email can not be empty")
    @Pattern(message = "Invalid email", regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotNull(message = "Password can not be empty")
    private String password;
    private Person loggedInPerson;

    @EJB
    private ILoginService loginService;

    public Person getLoggedInPerson() {
        return loggedInPerson;
    }

    public void setLoggedInPerson(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
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

    public String getRole() {
        if (loggedInPerson instanceof Student) {
            return "student";
        } else if (loggedInPerson instanceof Teacher) {
            return "teacher";
        } else {
            return "admin";
        }
    }

    public String login(){
        loggedInPerson = loginService.verifyLogin(this.email, this.password);

        if (loggedInPerson != null) {
            return "student";
        }
        FacesMessage msg = new FacesMessage("You have entered wrong login information");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        return null;
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

}
