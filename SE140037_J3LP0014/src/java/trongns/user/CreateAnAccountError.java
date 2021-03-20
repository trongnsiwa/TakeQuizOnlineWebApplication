/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.user;

/**
 *
 * @author TrongNS
 */
public class CreateAnAccountError {
    private String duplicateEmail;
    private String invalidEmail;
    private String confirmNotMatchPassword;

    public CreateAnAccountError() {
    }

    public CreateAnAccountError(String duplicateEmail, String invalidEmail, String confirmNotMatchPassword) {
        this.duplicateEmail = duplicateEmail;
        this.invalidEmail = invalidEmail;
        this.confirmNotMatchPassword = confirmNotMatchPassword;
    }

    /**
     * @return the invalidEmail
     */
    public String getInvalidEmail() {
        return invalidEmail;
    }

    /**
     * @param invalidEmail the invalidEmail to set
     */
    public void setInvalidEmail(String invalidEmail) {
        this.invalidEmail = invalidEmail;
    }

    /**
     * @return the confirmNotMatchPassword
     */
    public String getConfirmNotMatchPassword() {
        return confirmNotMatchPassword;
    }

    /**
     * @param confirmNotMatchPassword the confirmNotMatchPassword to set
     */
    public void setConfirmNotMatchPassword(String confirmNotMatchPassword) {
        this.confirmNotMatchPassword = confirmNotMatchPassword;
    }

    /**
     * @return the duplicateEmail
     */
    public String getDuplicateEmail() {
        return duplicateEmail;
    }

    /**
     * @param duplicateEmail the duplicateEmail to set
     */
    public void setDuplicateEmail(String duplicateEmail) {
        this.duplicateEmail = duplicateEmail;
    }
    
    
}
