package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.User;
import utils.DBUtil;

public class UserValidator {
    public static List<String> validate(User u, Boolean user_id_duplicate_check_flag, Boolean password_check_flag) {
        List<String> errors = new ArrayList<String>();

        String user_id_error = _validateUserid(u.getUser_id(), user_id_duplicate_check_flag);
        if(!user_id_error.equals("")) {
            errors.add(user_id_error);
        }

        String password_error = _validatePassword(u.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

    // 社員番号
    private static String _validateUserid(String user_id, Boolean user_id_duplicate_check_flag) {
        // 必須入力チェック
        if(user_id == null || user_id.equals("")) {
            return "IDを入力してください。";
        }

        // 既に登録されているIDとの重複チェック
        if(user_id_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery("checkRegisteredUserid", Long.class)
                                           .setParameter("user_id", user_id)
                                             .getSingleResult();
            em.close();
            if(employees_count > 0) {
                return "入力されたIDは既に使用されています。";
            }
        }

        return "";
    }

    // パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // パスワードを変更する場合のみ実行
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}