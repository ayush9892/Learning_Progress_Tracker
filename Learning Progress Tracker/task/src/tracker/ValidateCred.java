package tracker;

public class ValidateCred {

    public boolean checkEmail(String email) {
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z0-9]+)$");
    }

    public boolean checkFirstName(String email) {
        return email.matches("^\\p{Alpha}([-'](?=\\p{Alpha})|\\p{Alpha})*\\p{Alpha}$");
    }

    public boolean checkLastName(String email) {
        return email.matches("(\\p{Alpha}([-'](?=\\p{Alpha})|\\p{Alpha})*\\p{Alpha}\\s*)+");
    }


    public boolean validationCheck(String fstName, String lstName, String email) {
        if(!checkFirstName(fstName)) {
            System.out.println("Incorrect first name.");
            return false;
        } else if (!checkLastName(lstName)) {
            System.out.println("Incorrect last name.");
            return false;
        } else if (!checkEmail(email)) {
            System.out.println("Incorrect email.");
            return false;
        }
        return true;
    }
}
