package edu.ozu.cs202project.controllers;

import edu.ozu.cs202project.Salter;
import edu.ozu.cs202project.services.Functions;
import edu.ozu.cs202project.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;

@Controller
@SessionAttributes({ "user_id","username","access_type" ,"level", "itemData","select_publisher",
        "errorStatus","logged","books_info","feedback", "penaltyAndPrice", "penalty", "UserPurchaseHistory",
        "UserBorrowHistory","UserNotificationRequestHistory","UserHoldRequestHistory", "UserOverdueHistory",
        "includes","type", "Publisher_books", "PublisherBookHistory","PublisherRequestHistory", "mostBorrowingUsers", "mostPurchasingUsers","favourite",
        "pendingPublisherRequests"
})
public class AppController
{
    @Autowired
    LoginService service;

    private static Connection conn2 = null;
    private static Statement stmt = null;
    private static PreparedStatement pstmt = null;

    @Autowired
    JdbcTemplate conn;

    JdbcTemplate statement;

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

        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("user"))
            return "main_page_user";
        else
            return "wont_open";
    }

    @GetMapping("/main_page_manager")
    public String main_page_managerPage(ModelMap model) {
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("manager"))
            return "main_page_manager";
        else
            return "wont_open";

    }

    @GetMapping("/main_page_publisher")
    public String main_page_publisherPage(ModelMap model) {
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("publisher"))
            return "main_page_publisher";
        else
            return "wont_open";


    }

    @GetMapping("/main_page_publisher/view_books_publisher")
    public String main_page_publisherPage_view_books_row(ModelMap model) {
        Functions f = new Functions();


        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("publisher")) {
        List<String[]> data2 = conn.query("SELECT company_name FROM Publishers",
                (row, index) -> {
                    return new String[]{row.getString("company_name")};
                });

//        System.out.println(data2.get(0)[1]);
        model.addAttribute("select_publisher", data2.toArray(new String[0][0]));

            String user_id = model.getAttribute("user_id").toString();
            f.numberOfBorrowingsAndPurchases(model, conn, user_id, "publisher");
            return "view_books_publisher";
        }else{
            return "wont_open";
       }


    }

    /*@RequestMapping(value = "/main_page_publisher/view_books_publisher", params = {"title","author_name","topics","genre",
            "publisher","myActiveHoldings","publicationYear","sortBy","currentlyOverdue","currentlyBorrowed"})
    public String main_page_publisherPage_view_books_row(ModelMap model,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
                                                         @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("publisher") String publisher,
                                                         @RequestParam("myActiveHoldings") String myActiveHoldings,@RequestParam("publicationYear") String publicationYear,
                                                         @RequestParam("sortBy") String sortBy,@RequestParam("currentlyOverdue") String currentlyOverdue,@RequestParam("currentlyBorrowed") String currentlyBorrowed) {

        System.out.println("----------------");
        return "redirect:/main_page_publisher/view_books_publisher";
    }*/

    @GetMapping("/main_page_publisher/book_request_publisher")
    public String book_request_publisher(ModelMap model) {
//        if (model.getAttribute("user_id") != null &&
//                model.getAttribute("access_type").equals("publisher")) {

            return "book_request_publisher";
//        }else{
//            return "wont_open";
//        }
    }


    @RequestMapping(value = "/main_page_publisher/view_books_publisher", params = {"title","author_name","topics","genre",
            "publisher","publicationYear","sortBy","filteredIsbn"})
    public String main_page_publisherPage_view_books_row(ModelMap model,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
                                                         @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("publisher") String publisher,
                                                        @RequestParam("publicationYear") String publicationYear,
                                                         @RequestParam("sortBy") String sortBy,
                                                         @RequestParam("filteredIsbn") String filteredIsbn) {

        System.out.println("----------------");
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("publisher")) {
            Functions f = new Functions();
            String user_id = model.getAttribute("user_id").toString();


//            f.books(model, conn, author_name, genre, publicationYear, publisher, title, topics, filteredIsbn, sortBy,currentlyBorrowed, currentlyOverdue, "publisher", user_id);

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
        }else{
            return "wont_open";
        }

    }




    @RequestMapping(value = "/main_page_publisher/book_request_publisher", params={"ISBN","quantity","borrow","sell"})
    public String book_request_publisher2(ModelMap model,@RequestParam("ISBN") String isbn,
                                          @RequestParam("quantity") String quantity,
                                          @RequestParam("borrow") String borrow,
                                          @RequestParam("sell") String sell){
        System.out.println(isbn);
        System.out.println(quantity);
        System.out.println(borrow);
        System.out.println(sell);
        //existing book
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("publisher")) {

            return "redirect:/main_page_publisher/book_request_publisher";
        }else{
            return "wont_open";
        }

    }
    @RequestMapping(value = "/main_page_publisher/book_request_publisher", params={"ISBN"})
    public String book_request_publisher3(ModelMap model,@RequestParam("ISBN") String isbn
    ){
        System.out.println(isbn);
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("publisher")) {

            Functions f = new Functions();
            String user_id = model.getAttribute("user_id").toString();
            f.removeBookRequest(conn,isbn, user_id);
            //remove book
            return "redirect:/main_page_publisher/book_request_publisher";
        }else{
            return "wont_open";
        }

    }
    @RequestMapping(value = "/main_page_publisher/book_request_publisher", params={"ISBN","title","author_name","topics","genre","description",
            "units","publication_date","borrow","sell"})
    public String book_request_publisher4(ModelMap model,@RequestParam("ISBN") String isbn,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
                                          @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("description") String description,@RequestParam("units") String units,
                                          @RequestParam("publication_date") String publication_date, @RequestParam("borrow") String borrow,
                                          @RequestParam("sell") String sell){
        // none existing book
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("publisher")) {
            System.out.println(sell);
            return "redirect:/main_page_publisher/book_request_publisher";
        }else{
            return "wont_open";
        }



    }

    @GetMapping("/main_page_publisher/history_publisher")
    public String main_page_publisherPage_history(ModelMap model) {


        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("publisher")) {


            String user_id = model.getAttribute("user_id").toString();
            System.out.println("calling transaction history user id: " + user_id);
            Functions f = new Functions();
            f.publisherTransactionHistory(model, conn, user_id);
            f.publisherRequestHistory(model, conn, user_id);
            //f.test(model,conn);
            // history publisher
            return "history_publisher";
        }else{
            return "wont_open";
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/main_page_user/view_books", params = {"title","author_name","topics","genre",
            "publisher","myActiveHoldings","publicationYear","sortBy","currentlyOverdue","currentlyBorrowed"})
    public String main_page_userPage_view_books_row(ModelMap model,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
                                                    @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("publisher") String publisher,
                                                    @RequestParam("myActiveHoldings") String myActiveHoldings,@RequestParam("publicationYear") String publicationYear,
                                                    @RequestParam("sortBy") String sortBy,@RequestParam("currentlyOverdue") String currentlyOverdue,
                                                    @RequestParam("currentlyBorrowed") String currentlyBorrowed) {



        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("user")) {
            System.out.println("----------------");
            String user_id = model.getAttribute("user_id").toString();

            // filter
            Functions functions = new Functions();
            functions.books(model,conn, author_name,genre,publicationYear,publisher,title,
                    topics,"","Books.publication_date",currentlyBorrowed,currentlyOverdue,"user", user_id);
            return "redirect:/main_page_user/view_books";
        }else{
            return "wont_open";
        }
    }




    @RequestMapping(value = "/main_page_user/view_books", params = {"isbn"})
    public String main_page_userPage_view_books_row(ModelMap model,@RequestParam("isbn") String isbn){

        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("user")) {

            System.out.println(isbn);
            System.out.println("only isbn and user id");
            String user_id = model.getAttribute("user_id").toString();
            Functions f = new Functions();
            f.getPenalty(model, conn, user_id,isbn);
            return "redirect:/main_page_user/view_books";
        }else{
            return "wont_open";
        }
    }


    @GetMapping("/main_page_user/view_books")
    public String main_page_userPage_view_books(ModelMap model) {


        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("user")) {
            List<String[]> data2 = conn.query("SELECT company_name FROM Publishers",
                    (row, index) -> {
                        return new String[]{row.getString("company_name")};
                    });

//        System.out.println(data2.get(0)[1]);
            model.addAttribute("select_publisher", data2.toArray(new String[0][0]));
            return "view_books";
        }else{
            return "wont_open";
        }
    }

    @RequestMapping(value = "/main_page_user/view_books", params = {"isbn","which_table","quantity"})
    public String main_page_userPage_view_books_row(ModelMap model,@RequestParam("isbn") String isbn,
                                                    @RequestParam("which_table") String which_table,
                                                    @RequestParam("quantity") String quantity) {


        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("user")) {
            Functions functions = new Functions();

            System.out.println("----------------\n calling main_page_userPage_view_books_row");
            String user_id = model.getAttribute("user_id").toString();
            System.out.println("user_id"+user_id + "ISBN" +isbn);
            //functions.getPenalty(model, conn, user_id, isbn);

            if (which_table.equals("borrow")){
                functions.borrow(conn, user_id,isbn);
            }else if (which_table.equals("purchase")){
                functions.purchase(conn, user_id,isbn,quantity);
            }else if (which_table.equals("hold request")){
                functions.addHoldRequest(conn, user_id,isbn);
            }else if (which_table.equals("preorder book")){
                functions.addNotificationRequest(conn, user_id,isbn);
            }else if (which_table.equals("return books")){
                functions.returnBook(conn, user_id,isbn);
            }
            return "redirect:/main_page_user/view_books";
        }else{
            return "wont_open";
        }
    }


    @GetMapping("/main_page_user/history")
    public String user_history(ModelMap model)
    {
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("user")) {
            Functions f = new Functions();
            String user_id = model.getAttribute("user_id").toString();
            f.UserHistory(model,conn,user_id);
            return "history";

        }else{
            return "wont_open";
        }
    }

/// routing issue here, not being called
    @RequestMapping(value = "/main_page_user/history", params={"include","whichType"})
    public String main_page_userPage_history(ModelMap model, @RequestParam("include") String includes, @RequestParam("whichType") String type) {


        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("user")) {

            String user_id = model.getAttribute("user_id").toString();
            //String includes = model.getAttribute("includes").toString();
            //String type = model.getAttribute("type").toString();
            System.out.println("History for" + user_id);
            Functions functions = new Functions();
            try {
                functions.favourites(model, conn, "user", user_id, includes, type);
            } catch (Exception e) {
                System.out.println("Error in favourites" + e);
            }
            try {
                functions.UserHistory(model, conn, user_id);

            } catch (Exception e) {
                System.out.println("Error in history" + e);
            }
            //functions.favourites(model,conn, "user",user_id,includes, type);

            return "history";
        }else{
            return "wont_open";
        }
    }

    /*
    @RequestMapping(value = "/main_page_user/history", params={"user_id","includes","type"})
    public String main_page_userPage_history(ModelMap model) {

        String user_id = model.getAttribute("user_id").toString();
        String includes = model.getAttribute("includes").toString();
        String type = model.getAttribute("type").toString();
        System.out.println("faves for"+user_id);
        Functions functions = new Functions();
        functions.favourites(model,conn, "user",user_id,includes, type);
        return "history";
    }*/

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @GetMapping("/main_page_manager/view_books_manager")
    public String main_page_managerPage_view_books(ModelMap model) {
//        if (model.getAttribute("user_id") != null &&
//                model.getAttribute("access_type").equals("manager")) {
        List<String[]> data2 = conn.query("SELECT company_name FROM Publishers",
                (row, index) -> {
                    return new String[]{row.getString("company_name")};
                });

//        System.out.println(data2.get(0)[1]);
        model.addAttribute("select_publisher", data2.toArray(new String[0][0]));
            return "view_books_manager";
//        }else{
//            return "wont_open";
//        }
    }


    @RequestMapping(value = "/main_page_manager/view_books_manager", params = {"title","author_name","topics","genre",
            "publisher","publicationYear","sortBy","filteredIsbn"})
    public String main_page_managerPage_view_books_row(ModelMap model,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
                                                       @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("publisher") String publisher,
                                                      @RequestParam("publicationYear") String publicationYear,
                                                       @RequestParam("sortBy") String sortBy,
                                                       @RequestParam("filteredIsbn") String filteredIsbn) {
        // view book for manager filter
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("manager")) {
            Functions f = new Functions();
//            String user_id = model.getAttribute("user_id").toString();
//            f.books(model, conn, author_name, genre, publicationYear, publisher, title,
//                    topics, "", "publication_date", currentlyBorrowed, currentlyOverdue, "manager", user_id);
//
//            System.out.println(publicationYear);
            return "redirect:/main_page_manager/view_books_manager";
        }else {
            return "wont_open";
        }
    }

    @RequestMapping(value = "/main_page_manager/view_books_manager", params = {"isbn","which_table","username","quantity"})
    public String main_page_managerPage_view_books_row(ModelMap model,@RequestParam("isbn") String isbn,
                                                       @RequestParam("which_table") String which_table,@RequestParam("username") String username,
                                                       @RequestParam("quantity") String quantity) {
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("manager")) {
            System.out.println(username);
            Functions f = new Functions();
            f.assignBook(conn, username, isbn, which_table, quantity);
            return "redirect:/main_page_manager/view_books_manager";
        }else {
            return "wont_open";
        }
    }

    @RequestMapping(value = "/main_page_manager/view_books_manager", params = {"isbn"})
    public String main_page_managerPage_view_books_seePenalty(ModelMap model,@RequestParam("isbn") String isbn){
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("manager")) {
            System.out.println(isbn);
            return "redirect:/main_page_manager/view_books_manager";
        }else{
            return "wont_open";
        }
    }

    @PostMapping("/main_page_manager/view_books_manager")
    public String update_book_manager(ModelMap model,@RequestParam("isbn_number_update") String isbn_number_update,
                                      @RequestParam("updated_quantity") String updated_quantity,@RequestParam("whichTableUpdate") String whichTableUpdate){
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("manager")) {

            Functions f = new Functions();
            //f.addUnits(conn,isbn_number_update,whichTableUpdate,updated_quantity,priceOrPenalty);
            System.out.println(whichTableUpdate);
            return "redirect:/main_page_manager/view_books_manager";
        }else {
            return "wont_open";
        }
    }

    @RequestMapping(value = "/main_page_manager/view_books_manager", params = {"isbn_number_remove"})
    public String remove_book_manager(ModelMap model,@RequestParam("isbn_number_remove") String isbn){

        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("manager")) {
            System.out.println(isbn);
            Functions f = new Functions();
            f.removeBook(conn, isbn);
            return "redirect:/main_page_manager/view_books_manager";
        }else {
            return "wont_open";
        }
    }


    @GetMapping("/main_page_manager/manage_books")
    public String manage_books(ModelMap model)
    {
//        if (model.getAttribute("user_id") != null &&
//                model.getAttribute("access_type").equals("manager")) {
            Functions f = new Functions();
            System.out.println("calling view pending requests");
            f.viewPendingRequests(conn, model);
            return "manage_books";
//        }else {
//            return "wont_open";
//        }
    }

    @RequestMapping(value = "/main_page_manager/manage_books", params={"ISBN","title","author_name","topics","genre","description",
            "units","publication_date","borrow","sell"})
    public String manage_books2(ModelMap model,@RequestParam("ISBN") String isbn,@RequestParam("title") String title,@RequestParam("author_name") String author_name,
                                          @RequestParam("topics") String topics,@RequestParam("genre") String genre,@RequestParam("description") String description,@RequestParam("units") String units,
                                          @RequestParam("publication_date") String publication_date, @RequestParam("borrow") String borrow,
                                          @RequestParam("sell") String sell){
        // none existing book
//        if (model.getAttribute("user_id") != null &&
//                model.getAttribute("access_type").equals("publisher")) {
            System.out.println(sell);
            return "redirect:/main_page_manager/manage_books";
//        }else{
//            return "wont_open";
//        }



    }


    @RequestMapping(value = "/main_page_manager/manage_books", params={"ISBN","quantity","borrow","sell"})
    public String manage_book3(ModelMap model,@RequestParam("ISBN") String isbn,
                                          @RequestParam("quantity") String quantity,
                                          @RequestParam("borrow") String borrow,
                                          @RequestParam("sell") String sell){
        System.out.println(isbn);
        System.out.println(quantity);
        System.out.println(borrow);
        System.out.println(sell);
        //existing book
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("publisher")) {

            return "redirect:/main_page_manager/manage_books";
        }else{
            return "wont_open";
        }

    }

    @GetMapping("/main_page_manager/history_manager")
    public String history_manager(ModelMap model) {
        // add publisher from the manager
//        if (model.getAttribute("user_id") != null &&
//                model.getAttribute("access_type").equals("manager")) {
//            Functions f = new Functions();
//            f.mostPurchasingUsers(conn, model);
//            f.mostBorrowingUsers(conn, model);
            return "history_manager";
//        }else {
//            return "wont_open";
//        }
    }

    @GetMapping("/main_page_manager/add_publisher")
    public String add_publisher(ModelMap model)
    {
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("manager")) {
            return "add_publisher";
        }else {
            return "wont_open";
        }
    }

    @PostMapping("/main_page_manager/add_publisher")
    public String add_publisher_post(ModelMap model,@RequestParam String username, @RequestParam String password,
                                     @RequestParam String email,@RequestParam String phoneNumber,@RequestParam String company_name,@RequestParam String address) {
        if (model.getAttribute("user_id") != null &&
                model.getAttribute("access_type").equals("manager")) {
            password = Salter.salt(password, "CS202Project");
            service.add_publisher(username, password, company_name, address, email, phoneNumber, model);
            // add publisher from the manager
            return "add_publisher";
        }else {
            return "wont_open";
        }
    }

    @GetMapping("/logout")
    public String logout(ModelMap model, WebRequest request, SessionStatus session)
    {
        session.setComplete();
        request.removeAttribute("username", WebRequest.SCOPE_SESSION);

        return "redirect:/login";
    }

    /*@GetMapping("/list")
    public String list(ModelMap model)
    {
        try {
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            pstmt = conn2.prepareStatement("SELECT * FROM Access WHERE user_id=? AND username=?");
//            pstmt.setString(1, "1");
//            pstmt.setString(2, "ahmad");
//            pstmt.toString();
//            System.out.println(pstmt.toString());
            String ISBN = "6040645710";
             List <String[]> temp = conn.query(
                    "select * from Books where ISBN = ?",
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws
                                SQLException {
                            preparedStatement.setString(1, ISBN);
                        }
                    },
                    new ResultSetExtractor<String>() {
                        public ArrayList<String[]> extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            ArrayList<String[]> Temp = new ArrayList<>();
                            while (resultSet.next()) {
                                String[] data4;
                                data4 = new String[];
                                 data4[0]=(resultSet.getString(1));
                                 data4[1]=(resultSet.getString(2));
                                 Temp.add(data4);
                            }
                            //return null;
                            return Temp;

                        }
                    }
            );
            System.out.println(temp);
            /*ResultSetExtractor<String[]> data = conn.query(
                    "select * from Books where ISBN = ?",
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws
                                SQLException {
                            preparedStatement.setString(1, ISBN);
                        }
                    },
                    new ResultSetExtractor <String[]>() {
                        public  String[] extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            if (resultSet.next()) {
                                (row, index) -> {
                                    return new String[]{resultSet.getString("ISBN"),
                                            resultSet.getString("title"), resultSet.getString("NumberOfBorrowings"),
                                            resultSet.getString("NumberOfPurchases")};
                                }
//                                return new String[]{resultSet.getString("ISBN"),
//                                        resultSet.getString("title"), resultSet.getString("author"),
//                                        resultSet.getString("genre")};

                            }else
                                return null;
                        }
                    }

//                        List<String[]> data = conn.query(query,
//                                (row, index) -> {
//                                    return new String[]{row.getString("ISBN"),
//                                            row.getString("title"), row.getString("NumberOfBorrowings"), row.getString("NumberOfPurchases")};
//                                }
            );
        }catch (Exception e){
            System.out.println(e);
        }
        Functions functions = new Functions();
        //functions.books();
        //functions.priceAndPenalty(model, conn, "9150645735");
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
    }*/

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