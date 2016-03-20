package validators;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.*;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

/**
 *
 * @author User
 */
@FacesValidator("dateValidator_ISO8601")
public class DateValidator_ISO8601 implements Validator {

    private static final String DATE_PATTERN = "^([0-9]{4})(-?)(1[0-2]|0[1-9])\\2(3[01]|0[1-9]|[12][0-9])$";
    private final Pattern pattern;
    private Matcher matcher;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public DateValidator_ISO8601() {
        pattern = Pattern.compile(DATE_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        Date date = null;
        String sdate = null;
        
        if(value != null){
            date = (Date) value;
            sdate = formatter.format(date);
        }else
            sdate = "";
        
        matcher = pattern.matcher(sdate);
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage("The date must have the YYYY-MM-DD format");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
