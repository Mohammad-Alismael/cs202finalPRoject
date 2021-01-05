package edu.ozu.cs202project.controllers;

import edu.ozu.cs202project.Salter;
import edu.ozu.cs202project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
@SessionAttributes({ "username", "level", "itemData","errorStatus","logged","user_type" })
public class AppController
{
    @Autowired
    LoginService service;

    @Autowired
    JdbcTemplate conn;

    @GetMapping("/")
    public String index(ModelMap model)
    {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(ModelMap model) {
        model.addAttribute("errorStatus", false);
        return "login";
    }

    @PostMapping("/login")
    public String login(ModelMap model, @RequestParam String username, @RequestParam String password) {
        password = Salter.salt(password, "CS202Project");

        if (!service.validate(username, password))
        {
            model.addAttribute("errorStatus", true);

            return "login";
        }
        model.addAttribute("errorStatus", false);
        model.put("username", username);

        return "login";
    }

    @GetMapping("/sign_in")
    public String sign_inPage(ModelMap model)
    {
        model.addAttribute("logged", false);
        return "sign_up";
    }

    @PostMapping("/sign_in")
    public String sign_in(ModelMap model,@RequestParam String username, @RequestParam String password,
                          @RequestParam String email,@RequestParam String phoneNumber) {
        // inserting data here
        return "login";
    }

    @GetMapping("/main_page_user")
    public String main_page_userPage(ModelMap model) {
        model.addAttribute("username","ahmed");
        return "main_page_user";
    }

//    @PostMapping("/main_page_user/view_books")
//    public String main_page_userPage_view_books(ModelMap model,@RequestParam("title") String title,
//    @RequestParam("author_name") String author_name,@RequestParam("topics") String topics,
//                                                @RequestParam("genre") String genre                                       ) {
//        model.addAttribute("username","gerfg");
//        System.out.println(title);
//        return "view_books";
//    }

    @RequestMapping(value = "/main_page_user/view_books", params = {"title","author_name","topics","genre",
            "publisher","myActiveHoldings","publicationYear","sortBy"})
    public String main_page_userPage_view_books_row(ModelMap model,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
       @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("publisher") String publisher,
       @RequestParam("myActiveHoldings") String myActiveHoldings,@RequestParam("publicationYear") String publicationYear,
                                                    @RequestParam("sortBy") String sortBy) {

        System.out.println("----------------");
        System.out.println(title);
        System.out.println(author_name);
        System.out.println(topics);
        System.out.println(genre);
        System.out.println(publisher);
        System.out.println(myActiveHoldings);
        System.out.println(publicationYear);
        System.out.println(sortBy);
        return "redirect:/main_page_user/view_books";
    }

    @RequestMapping(value = "/main_page_user/view_books", params = {"isbn","which_table"})
    public String main_page_userPage_view_books_row(ModelMap model,@RequestParam("isbn") String isbn,@RequestParam("which_table") String which_table) {

        System.out.println("----------------");
        System.out.println(isbn);
        System.out.println(which_table);
        return "redirect:/main_page_user/view_books";
    }

    @GetMapping("/main_page_user/view_books")
    public String main_page_userPage_view_books(ModelMap model) {
        model.addAttribute("username","gerfg");
        System.out.println("first time");
        return "view_books";
    }


    @GetMapping("/main_page_user/history")
    public String main_page_userPage_history(ModelMap model) {
        model.addAttribute("username","gerfg");
        return "history";
    }

    @GetMapping("/logout")
    public String logout(ModelMap model, WebRequest request, SessionStatus session)
    {
        session.setComplete();
        request.removeAttribute("username", WebRequest.SCOPE_SESSION);

        return "redirect:/login";
    }

    @GetMapping("/list")
    public String list(ModelMap model)
    {
            List<String[]> data = conn.query("SELECT * FROM items",
                    (row, index) -> {
                        return new String[]{ row.getString("item_name"), row.getString("item_value") };
                    });

            model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "list";
    }

    @GetMapping("/change-status")
    public String changeStatus(ModelMap model) {

        if ((Boolean) model.get("logged")){
            model.addAttribute("logged", false);
        }else{
            model.addAttribute("logged", true);
        }
        return "sign_up";
    }
}
