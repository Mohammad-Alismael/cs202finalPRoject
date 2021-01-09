package edu.ozu.cs202project.controllers;

import edu.ozu.cs202project.Salter;
import edu.ozu.cs202project.services.Functions;
import edu.ozu.cs202project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes({ "user_id","username","access_type" ,"level", "itemData","select_publisher",
        "errorStatus","logged","books_info","feedback"})
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
        if (service.validate(username, password)) {

            service.getSessions(username,password,model);

            model.addAttribute("errorStatus",false);
            if (model.getAttribute("access_type").equals("user")){
                return "redirect:/main_page_user";
            }else if (model.getAttribute("access_type").equals("publisher")){
                return "redirect:/main_page_publisher";
            }else if (model.getAttribute("access_type").equals("manager")){
                return "redirect:/main_page_manager";
            }else{
                return "login";
            }

        }
        model.addAttribute("errorStatus", true);
//        model.put("username", username);
//        System.out.println(model.getAttribute("errorStatus"));
        return "login";
    }

    @GetMapping("/sign_up")
    public String sign_inPage(ModelMap model)
    {

        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String sign_up(ModelMap model,@RequestParam String username, @RequestParam String password,
                          @RequestParam String email,@RequestParam String phoneNumber,@RequestParam String name,@RequestParam String surname) {

        password = Salter.salt(password, "CS202Project");
        service.sign_up(username,password,name,surname,email,phoneNumber,model);
        System.out.println(model.getAttribute("feedback"));
        if (model.getAttribute("feedback") == null ||
                model.getAttribute("feedback").equals("signed up successfully")){
            return "redirect:/login";
        }else {
            return "redirect:/sign_up";
        }

    }

    @GetMapping("/main_page_user")
    public String main_page_userPage(ModelMap model) {

        return "main_page_user";
    }

    @GetMapping("/main_page_manager")
    public String main_page_managerPage(ModelMap model) {

        return "main_page_manager";
    }

    @GetMapping("/main_page_publisher")
    public String main_page_publisherPage(ModelMap model) {

        return "main_page_publisher";
    }

    @GetMapping("/main_page_publisher/view_books_publisher")
    public String main_page_publisherPage_view_books_row(ModelMap model) {

        return "view_books_publisher";
    }

    @RequestMapping(value = "/main_page_publisher/view_books_publisher", params = {"title","author_name","topics","genre",
            "publisher","myActiveHoldings","publicationYear","sortBy","currentlyOverdue","currentlyBorrowed"})
    public String main_page_publisherPage_view_books_row(ModelMap model,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
                                                    @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("publisher") String publisher,
                                                    @RequestParam("myActiveHoldings") String myActiveHoldings,@RequestParam("publicationYear") String publicationYear,
                                                    @RequestParam("sortBy") String sortBy,@RequestParam("currentlyOverdue") String currentlyOverdue,@RequestParam("currentlyBorrowed") String currentlyBorrowed) {

        System.out.println("----------------");
        // filter view book publisher
//        List<String[]> data = conn.query(Functions.books("","","","",title,
//                "","","Books.publication_date","false","false"),
//                (row, index) -> {
//                    return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("author_name"),
//                            row.getString("topics"), row.getString("genre"), row.getString("description") ,
//                            row.getString("publisher"),
//                            row.getString("Borrow_units"), row.getString("Sell_units")};
//                });
//        model.addAttribute("books_info", data.toArray(new String[0][10]));
        return "redirect:/main_page_publisher/view_books_publisher";
    }

    @GetMapping("/main_page_publisher/book_request_publisher")
    public String book_request_publisher(ModelMap model) {

        return "book_request_publisher";
    }

    @RequestMapping(value = "/main_page_publisher/book_request_publisher", params={"ISBN","quantity"})
    public String book_request_publisher2(ModelMap model,@RequestParam("ISBN") String isbn,
                                                    @RequestParam("quantity") String quantity){
        System.out.println(isbn);
        System.out.println(quantity);
        //existing book
        return "redirect:/main_page_publisher/book_request_publisher";
    }
    @RequestMapping(value = "/main_page_publisher/book_request_publisher", params={"ISBN"})
    public String book_request_publisher3(ModelMap model,@RequestParam("ISBN") String isbn
                                          ){
        System.out.println(isbn);

        //remove book
        return "redirect:/main_page_publisher/book_request_publisher";
    }
    @RequestMapping(value = "/main_page_publisher/book_request_publisher", params={"ISBN","title","author_name","topics","genre","description",
            "borrow_units","sell_units","publication_date"})
    public String book_request_publisher4(ModelMap model,@RequestParam("ISBN") String isbn,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
                                          @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("description") String description,@RequestParam("borrow_units") String borrow_units,@RequestParam("sell_units") String sell_units,
                                          @RequestParam("publication_date") String publication_date){
        // none existing book


        return "redirect:/main_page_publisher/book_request_publisher";
    }

    @RequestMapping(value = "/main_page_user/view_books", params = {"title","author_name","topics","genre",
            "publisher","myActiveHoldings","publicationYear","sortBy","currentlyOverdue","currentlyBorrowed"})
    public String main_page_userPage_view_books_row(ModelMap model,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
       @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("publisher") String publisher,
       @RequestParam("myActiveHoldings") String myActiveHoldings,@RequestParam("publicationYear") String publicationYear,
       @RequestParam("sortBy") String sortBy,@RequestParam("currentlyOverdue") String currentlyOverdue,@RequestParam("currentlyBorrowed") String currentlyBorrowed) {

        System.out.println("----------------");
        // filter
        List<String[]> data = conn.query(Functions.books(author_name,genre,publicationYear,publisher,title,
                topics,"","Books.publication_date",currentlyBorrowed,currentlyOverdue,"user"),
                (row, index) -> {
                    return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("author_name"),
                            row.getString("topics"), row.getString("genre"), row.getString("description") ,
                            row.getString("publisher"),
                            row.getString("Borrow_units"), row.getString("Sell_units"),row.getString("publication_date")};
                });
        model.addAttribute("books_info", data.toArray(new String[0][11]));
        return "redirect:/main_page_user/view_books";
    }

    @RequestMapping(value = "/main_page_user/view_books", params = {"isbn","which_table","quantity"})
    public String main_page_userPage_view_books_row(ModelMap model,@RequestParam("isbn") String isbn,
                                                    @RequestParam("which_table") String which_table,
                                                    @RequestParam("quantity") String quantity) {
        Functions functions = new Functions();
        System.out.println("----------------");
        if (which_table.equals("borrow")){

            functions.borrow((String) model.getAttribute("user_id"),isbn);
        }else if (which_table.equals("purchase")){
            functions.purchase((String) model.getAttribute("user_id"),isbn,quantity);
        }else if (which_table.equals("hold request")){
            functions.addHoldRequest((String) model.getAttribute("user_id"),isbn);
        }else if (which_table.equals("preorder book")){
            functions.addNotificationRequest((String) model.getAttribute("user_id"),isbn);
        }else if (which_table.equals("return books")){
            functions.returnBook((String) model.getAttribute("user_id"),isbn);
        }
        return "redirect:/main_page_user/view_books";
    }

    @GetMapping("/main_page_user/view_books")
    public String main_page_userPage_view_books(ModelMap model) {

        List<String[]> data2 = conn.query("SELECT company_name FROM Publishers",
                (row, index) -> {
                    return new String[]{row.getString("company_name")};
                });

//        System.out.println(data2.get(0)[1]);
  model.addAttribute("select_publisher", data2.toArray(new String[0][0]));
        return "view_books";
    }


    @GetMapping("/main_page_user/history")
    public String main_page_userPage_history(ModelMap model) {

        // history user
        return "history";
    }

    @GetMapping("/main_page_publisher/history_publisher")
    public String main_page_publisherPage_history(ModelMap model) {
        // history publisher
        return "history_publisher";
    }

    @GetMapping("/main_page_manager/view_books_manager")
    public String main_page_managerPage_view_books(ModelMap model) {

        // history user
        return "view_books_manager";
    }

    @GetMapping("/main_page_manager/add_publisher")
    public String add_publisher(ModelMap model)
    {

        return "add_publisher";
    }

    @PostMapping("/main_page_manager/add_publisher")
    public String add_publisher_post(ModelMap model,@RequestParam String username, @RequestParam String password,
                                     @RequestParam String email,@RequestParam String phoneNumber,@RequestParam String company_name,@RequestParam String address) {
        // add publisher from the manager
        return "add_publisher";
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
//        List<String[]> data2 = conn.query(Functions.books("","","","","",
//                "","","Books.publication_date","false","false"),
//                (row, index) -> {
//                    return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("author_name"),
//                            row.getString("topics"), row.getString("genre"), row.getString("description") ,
//                            row.getString("publisher"),
//                            row.getString("Borrow_units"), row.getString("Sell_units")};
//                });
//        model.addAttribute("itemData", data2.toArray(new String[0][10]));

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
