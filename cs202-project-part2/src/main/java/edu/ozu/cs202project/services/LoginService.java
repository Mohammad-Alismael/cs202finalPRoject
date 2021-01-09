package edu.ozu.cs202project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class LoginService
{
    @Autowired
    JdbcTemplate conn;

    public boolean validate(String username, String password)
    {
        List<Map<String, Object>> response = conn.queryForList(
                "SELECT * FROM Access WHERE username = ? AND password = ?", new Object[]{ username, password }
                );

        return response.size() == 1;
    }
    public void getSessions(String username, String password, ModelMap model){
        List<Map<String, Object>> response = conn.queryForList(
                "SELECT * FROM Access WHERE username = ? AND password = ?", new Object[]{ username, password }
        );
        System.out.println(response);
        model.addAttribute("user_id",response.get(0).get("user_id"));
        model.addAttribute("access_type",response.get(0).get("access_type"));
        model.addAttribute("username",response.get(0).get("username"));
    }

    public void sign_up(String username, String password, String name, String surname, String email, String phoneNumber,ModelMap model) {
        List<Map<String, Object>> response = conn.queryForList(
                "SELECT * FROM Access WHERE username = ?", new Object[]{ username}
        );

        if (response.size() == 1){

            model.addAttribute("feedback","the username already taken");
        }else{
            try {
                conn.execute("insert into Access(username,password,access_type,email,phone_number,is_active) \n" +
                        "\tvalues(\"" + username + "\",\"" + password + "\", \"user\",\"" + email + "\",\"" + phoneNumber + "\",true);\n");
                conn.execute( "insert into Persons (user_id, name, surname) values \n" +
                        "\t( (select user_id from Access where username = \"" + username + "\" and Access_type = \"user\"),\"" + name + "\",\"" + surname + "\");");

                model.addAttribute("feedback","signed up successfully");
            } catch (Exception e) {
                model.addAttribute("feedback","User not added" + e);
            }
        }

    }
}
