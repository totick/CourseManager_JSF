/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author John
 */
@FacesConverter(value = "dateConverter")
public class DateConverter implements Converter {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }

        Date date = null;

        try {
            date = formatter.parse(value);
        } catch (DateTimeParseException e) {
        } finally {
            return date;
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Date) {
            return formatter.format(value);
        } else {
            throw new IllegalArgumentException("Cannot convert the date-object to a string");
        }
    }

}
