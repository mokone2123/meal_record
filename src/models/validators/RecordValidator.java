package models.validators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Record;

public class RecordValidator {
    public static List<String> validate(Record r) {
        List<String> errors = new ArrayList<String>();

        String title_error = _validateDate(r.getDate());
        if(!title_error.equals("")) {
            errors.add(title_error);
        }

        return errors;
    }

    private static String _validateDate(Date date) {
        if(date == null || date.equals("")) {
            return "日付を入力してください。";
            }

        return "";
    }

}