package edu.ozu.cs202project.services;

import edu.ozu.cs202project.services.LoginService;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

public class Functions {

    @Autowired
    LoginService service;

    @Autowired
    JdbcTemplate conn;

    public static String books(String Author, String Genre, String Year, String Publisher,
                               String Title, String Topics, String ISBN, String orderBy,
                               String currentlyBorrowed, String currentlyOverdue, String accessType) {

        if (Author.equals("none"))
            Author = "";
        if (Year.equals("none"))
            Year = "";
        if (Publisher.equals("none"))
            Publisher = "";
        if (Title.equals("none"))
            Title = "";
        if (Topics.equals("none"))
            Topics = "";
        if (ISBN.equals("none"))
            ISBN = "";
        if (orderBy.equals("none"))
            orderBy = "";

        if (accessType.equals("manager") || accessType.equals("publisher")) {
            if (currentlyBorrowed == "false" && currentlyOverdue == "false") {

                return "select DISTINCT Books.ISBN, Books.title, Books.author_name, Books.topics, " +
                        "Books.genre, Books.description,Books.publication_date , Books.publisher,Books_borrow.units as borrow_units, " +
                        "Books_sell.units as Sell_units, Books.status from Books left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id" +
                        " left join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where title " +
                        "like \"%" + Title + "%\" and author_name like \"%" + Author + "%\" and topics like " +
                        "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                        "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and Books.publication_date like " +
                        "\"%" + Year + "%\"order by " + orderBy + ";";

            } else if (currentlyOverdue == "false" && currentlyBorrowed == "true") {
                return "select DISTINCT Books.ISBN, Books.title, Books.author_name, Books.topics, Books.genre, " +
                        "Books.description ,Books.publication_date, Books.publisher, Books_borrow.units as borrow_units, Books_sell.units as Sell_units, Books.status" +
                        "from Books join overdue_books on Books.book_id = overdue_books.book_id left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id " +
                        "left join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where Books.title like \"%" + Title + "%\" and " +
                        "Books.author_name like \"%" + Author + "%\" and Books.topics like \"%" + Topics + "%\" and Books.genre like " +
                        "\"%" + Genre + "%\" and Books.ISBN like \"%" + ISBN + "%\" and Books.publisher like \"%" + Publisher + "%\" and " +
                        "Books.publication_date like \"%" + Year + "%\" order by " + orderBy + ";";
            } else {
                return "select DISTINCT Books.ISBN, Books.title, Books.author_name, Books.topics, " +
                        "Books.genre, Books.description,Books.publication_date, Books.publisher,Books_borrow.units as borrow_units, " +
                        "Books_sell.units as Sell_units, Books.status from Books left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id" +
                        " join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where title " +
                        "like \"%" + Title + "%\" and author_name like \"%" + Author + "%\" and topics like " +
                        "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                        "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and Books.publication_date like " +
                        "\"%" + Year + "%\" order by " + orderBy + ";";
            }
        }else{
            if (currentlyBorrowed == "false" && currentlyOverdue == "false") {

                return "select DISTINCT Books.ISBN, Books.title, Books.author_name, Books.topics, " +
                        "Books.genre, Books.description,Books.publication_date , Books.publisher,Books_borrow.units as borrow_units, " +
                        "Books_sell.units as Sell_units from Books left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id" +
                        " left join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where title " +
                        "like \"%" + Title + "%\" and author_name like \"%" + Author + "%\" and topics like " +
                        "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                        "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and Books.publication_date like " +
                        "\"%" + Year + "%\"" + "and status = \"accepted\"" + "order by " + orderBy + ";";

            } else if (currentlyOverdue == "false" && currentlyBorrowed == "true") {
                return "select DISTINCT Books.ISBN, Books.title, Books.author_name, Books.topics, Books.genre, " +
                        "Books.description ,Books.publication_date, Books.publisher, Books_borrow.units as borrow_units, Books_sell.units as Sell_units" +
                        "from Books join overdue_books on Books.book_id = overdue_books.book_id left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id " +
                        "left join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where Books.title like \"%" + Title + "%\" and " +
                        "Books.author_name like \"%" + Author + "%\" and Books.topics like \"%" + Topics + "%\" and Books.genre like " +
                        "\"%" + Genre + "%\" and Books.ISBN like \"%" + ISBN + "%\" and Books.publisher like \"%" + Publisher + "%\" and " +
                        "Books.publication_date like \"%" + Year + "%\"and Books.status " +
                        "= \"accepted\"order by " + orderBy + ";";
            } else {
                return "select DISTINCT Books.ISBN, Books.title, Books.author_name, Books.topics, " +
                        "Books.genre, Books.description,Books.publication_date, Books.publisher,Books_borrow.units as borrow_units, " +
                        "Books_sell.units as Sell_units from Books left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id" +
                        " join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where title " +
                        "like \"%" + Title + "%\" and author_name like \"%" + Author + "%\" and topics like " +
                        "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                        "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and Books.publication_date like " +
                        "\"%" + Year + "%\"" + "and status = \"accepted\"" + "order by " + orderBy + ";";
            }
        }
    }

    public String numberOfBorrowingsAndPurchases(String user_id){

        List<Map<String, Object>> temp = conn.queryForList(
                "Select access_type from Access where user_id ="+user_id+";",
                new Object[]{"number of hold requests"});

        if(temp.get(0).get(0).equals("manager"))
            return "select DISTINCTROW ISBN, title, publisher, NumberOfBorrowings, NumberOfPurchases from (\n" +
                    "select a.ISBN, a.title, a.publisher, NumberOfBorrowings, NumberOfPurchases from \n" +
                    "(select ISBN, title, publisher, count(*) as NumberOfBorrowings from Borrowings natural join \n" +
                    "Books group by book_id) as a \n" +
                    "LEFT OUTER JOIN \n" +
                    "(select ISBN, title, publisher, count(*) as NumberOfPurchases  \n" +
                    "from Purchases natural join Books group by book_id) as b \n" +
                    "on a.ISBN=b.ISBN\n" +
                    "union all\n" +
                    "select b.ISBN, b.title, b.publisher, NumberOfBorrowings, NumberOfPurchases from \n" +
                    "(select ISBN, title, publisher, count(*) as NumberOfBorrowings from Borrowings natural join \n" +
                    "Books group by book_id) as a \n" +
                    "Right OUTER JOIN \n" +
                    "(select ISBN, title, publisher, count(*) as NumberOfPurchases  \n" +
                    "from Purchases natural join Books group by book_id) as b \n" +
                    "on a.ISBN=b.ISBN\n" +
                    ") as c;";
        else
            return "select DISTINCTROW ISBN, title, publisher, NumberOfBorrowings, NumberOfPurchases from (\n" +
                "select a.ISBN, a.title, a.publisher, NumberOfBorrowings, NumberOfPurchases from \n" +
                "(select ISBN, title, publisher, count(*) as NumberOfBorrowings from Borrowings natural join \n" +
                "Books group by book_id) as a \n" +
                "LEFT OUTER JOIN \n" +
                "(select ISBN, title, publisher, count(*) as NumberOfPurchases  \n" +
                "from Purchases natural join Books group by book_id) as b \n" +
                "on a.ISBN=b.ISBN\n" +
                "union all\n" +
                "select b.ISBN, b.title, b.publisher, NumberOfBorrowings, NumberOfPurchases from \n" +
                "(select ISBN, title, publisher, count(*) as NumberOfBorrowings from Borrowings natural join \n" +
                "Books group by book_id) as a \n" +
                "Right OUTER JOIN \n" +
                "(select ISBN, title, publisher, count(*) as NumberOfPurchases  \n" +
                "from Purchases natural join Books group by book_id) as b \n" +
                "on a.ISBN=b.ISBN\n" +
                ") as c where publisher = (select company_name from Pubishers where user_id = "+user_id+") ;";

    }


    public void insertUser(String Username, String Password, String Email, String PhoneNumber, String Name, String Surname) {
        try {
            conn.execute("insert into Access(username,password,access_type,email,phone_number,is_active) \n" +
                    "\tvalues(\"" + Username + "\",\"" + Password + "\", \"user\",\"" + Email + "\",\"" + PhoneNumber + "\",true);\n");
                    conn.execute( "insert into Persons (user_id, name, surname) values \n" +
                    "\t( (select user_id from Access where username = \"" + Username + "\" and Access_type = \"user\"),\""
                            + Name + "\",\"" + Surname + "\");");

        } catch (Exception e) {
            System.out.print("User not added\n" + e);
        }
    }

    public static String UserPurchaseHistory(int user_id) {
        return "Select ISBN, title, quantity, total, created_on from Purchases join Books on " +
                "Purchases.book_id = Books.book_id where created_by = " + user_id;
    }

    public static String UserBorrowHistory(int user_id) {
        return "Select ISBN, title, start_date, end_date, return_date from Borrowings left join Returns on Borrowings.id = " +
                "Returns.borrowed_id join Books on Borrowings.book_id = Borrowings.book_id where created_by = " + user_id;
    }

    public static String UserNotificationRequestHistory(int user_id) {
        return "select ISBN, title, created_on from Notification_requests join Books on Notification_requests.book_id = " +
                "Books.book_id where created_by = " + user_id;
    }

    public static String UserHoldRequestHistory(int user_id) {
        return "select ISBN, title, status, created_by from Hold_requests join Books on Hold_requests.book_id = " +
                "Books.book_id where created by = " + user_id;
    }

    public static String UserOverdueHistory(int user_id) {
        return "Select ISBN, title, start_date, end_date, return_date from overdued_books where user_id = " + user_id;
    }

    public static String favourites(String AccessType, String User_id, String Includes, String Type) {
        switch (AccessType) {
            case "manager":
                break;
            case "publisher":
                break;
            case "user":
                switch (Includes) {
                    case "borrowings":
                        return "Select \"" + Type + "\", count(*) as \"number of borrowings\" from Borrowings \n" +
                                "    where created_by = \"" + User_id + "\" \n" +
                                "    group by \" " + Type + " \" order by \"number of borrowings\" desc limit 1;\n";
                    case "purchases":
                        return "Select \"" + Type + "\", count(*) as \"number of purchases\" from Purchases \n" +
                                "    where created_by = \"" + User_id + "\" \n" +
                                "    group by \" " + Type + " \" order by \"number of purchases\" desc limit 1;\n";
                    default:
                        return "Select " + Type + ", count(*) as \"number of borrowings/purchases\" from combined_table \n" +
                                "    where created_by = \"" + User_id + "\" \n" +
                                "    group by \"" + Type + "\" order by \"number of borrowings/purchases\" desc limit 1;\n";
                }
        }
        return "";
    }

    public void updateBooks_borrow(String ISBN, String type, String user_id){
        List<Map<String, Object>> count = conn.queryForList(
                "Select count(*) as \"number of hold requests\" from Hold_requests where book_id =\n" +
                        "(select book_id from Books where ISBN =\""+ISBN+"\") and status = \"active\";",
                new Object[]{"number of hold requests"});
        if(type.equals("add")){
            if(count.get(0).get(0).equals("0")){
                conn.execute("update Books_borrow set units = units + 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\"");
            }else{
                conn.execute("update Books_borrow set held = held + 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\"");
                conn.execute("update Hold_requests set status = \"active\" where book_id = (select book_id from Books where ISBN =\""+ISBN+"\") order by created_on asc limit 1");
            }
        }else{
            if(count.get(0).get(0).equals("0")){
                conn.execute("update Books_borrow set units = units - 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\"");
            }else{
                conn.execute("update Books_borrow set held = held - 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\"");
                conn.execute("update Hold_requests set status = \"removed\" where book_id = (select book_id from Books where ISBN =\""+ISBN+"\")" +
                        "and created_by = "+user_id+" order by created_on asc limit 1");
            }
        }
    };

    public void borrow(String user_id, String ISBN) {
        updateBooks_borrow(ISBN, "remove", "user_id");
        conn.execute("insert into Borrowings (book_id,start_date,end_date,created_by) values("+
                "(select book_id from Books where ISBN = "+ISBN+" and status=\"accepted\"),"+"(SELECT CURDATE()), " +
                                "(SELECT DATE_ADD(CURDATE(), INTERVAL 14 DAY))," + user_id + ");" );

    }
    public void addNotificationRequest(String user_id, String ISBN){
        conn.execute(String.format("insert into Notification_requests (book_id, created_by) " +
                "values ((select book_id from Books where ISBN = \"%s\"), %s)",ISBN, user_id));
    }
    public void addHoldRequest(String user_d, String ISBN){
        conn.execute(String.format("insert into hold_requests (book_id, status, created_by) values \n" +
                "\t((select book_id from Books where ISBN = \"%s\" and status=\"accepted\"), \"pending\", \n" +
                "\t%s );\n",ISBN, user_d));
    }
    public void purchase(String user_id, String ISBN, String quantity)
    {
        conn.execute(
                "update Books_sell set units = units - \""+quantity+"\" where book_id = (select book_id from Books where " +
                        "ISBN = \""+ISBN+"\" and status=\"accepted\");");

        conn.execute("insert into Purchases (book_id,quantity,total,created_by) values"
        +"( (select book_id from Books where ISBN =\""+ ISBN + "\"and status=\"accepted\"),"+ quantity+","
        +"(select price" + quantity + " from Books_sell where book_id = (select book_id from Books where ISBN = \"" +ISBN+"\" and status=\"accepted\")),"
        + user_id + ");");
    }

    public String displayPenalty(String user_id, String ISBN){

        return "SELECT GREATEST (DATEDIFF (CURRENT_DATE, (select end_date from Borrowings join Books on Borrowings.book_id = Books.book_id "
                +"join Access on Borrowings.created_by = Access.user_id"
                +"where  ISBN = \""+ISBN+"\" and user_id = "+user_id+" and Access_type = \"user\")) * (select penalty_info"
                 +"from Books_borrow where book_id = (select book_id from Books where ISBN = \"" +ISBN+ "\") ), 0) as penalty ;";
    }

    public void returnBook(String user_id, String ISBN){

        updateBooks_borrow(ISBN, "add", "user_id");
        conn.execute(
        "insert into Returns (borrowed_id) values ("+
                "( select id from Borrowings where book_id = (select book_id from Books where ISBN = \""+ISBN+"\")"+
        "and created_by ="+user_id+" ) );");
    }

    public void addBookStock(String ISBN, String type, String quantity, String price){
        if (type.equals("sell"))
            conn.execute(String.format("insert Books_sell (book_id, units, price) values ( (select book_id from Books where ISBN = \"%s\"),%s, %s );",ISBN, quantity, price ));
        else
            conn.execute(String.format("insert Books_borrow (book_id, units, penalty_info) values ( (select book_id from Books where ISBN = \"%s\"),%s, %s );",ISBN, quantity, price ));
    }

    public void removeBook(String ISBN){
        conn.execute("\tupdate Books set status = \"removed\" where ISBN = \""+ISBN+"\";\n");
    }

    public String viewRequestsHistory(){
        return "select ISBN, Title, quantity, type ,company_name, Publisher_requests.status, Publisher_requests.created_on" +
                " from Publisher_requests natural join Books natural join Publishers;";
    }
    public String viewPendingRequests(){
        return "select ISBN, Title, quantity, type ,company_name from Publisher_requests natural " +
                "join Books natural join Publishers where Publisher_requests.status = \"pending\";";
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void approveRequest(String id){

        // get the ID, get the type
        List<Map<String, Object>> temp = conn.queryForList(
                "Select type, ISBN,  from Publisher_requests where id = "+id+";",
                new Object[]{"type"});
        Object o = temp.get(0).get(0);

        if ("borrow".equals(o)) {
//            addBookStock();

        } else if ("sell".equals(o)) {

        } else if ("remove".equals(o)) {

        }
    }

    // manager force borrow

    // Managers can add publishers

    // Manager view which users borrowed which books within a time interval
    // Users can view books currently not available for borrowing
    // Users can view books currently not available for borrowing
    // Users can view books currently available for borrowing
    // Publishers can view how many times their books have been borrowed or sold
    // Managers can view how many times any book has been borrowed or sold
    // Manager can display overdue books
    // Total number of overdue books
    // Total number of books that are held
    // Number of times they overdued a book

    // for managers:
    // most borrowed genres
    // Which publisher has the most borrowed books
    // Number of times users overdue any book



    public void deleteBook(String ISBN){
        conn.execute("SET SQL_SAFE_UPDATES = 0; delete from Books where ISBN = \""+ISBN+"\";");

    }


    public void addBook(String title, String description, String publication_date, String author, String genre, String ISBN ,String Book_status){
        conn.execute(String.format("insert into Books(title,description,publication_date," +
                        "author_name,topics,genre,ISBN,publisher,status) values  " +
                        "(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\");"
                        , title, description, publication_date, author, genre, ISBN, Book_status));
    }

    public void addBookRequest(String bookType, String title, String description, String publication_date,
                               String author, String genre, String ISBN, String type, String quantity, String user_id){
        addBook( title, description, publication_date, author, genre, ISBN, "pending");
        conn.execute(String.format("\tinsert into Publisher_requests (book_id,type,quantity,status,created_by)\n" +
                "values ((select book_id from Books where ISBN = \"%s\"), \"%s\", %s, \"pending\"," +
                "%s);\n", ISBN, type, quantity, user_id ));

    };

    public void removeBookRequest(String ISBN, String user_id){
        conn.execute(String.format("\tinsert into Publisher_requests (book_id,type,quantity,status,created_by)\n" +
                "values ((select book_id from Books where ISBN = \"%s\"), \"remove\", 0, \"pending\"," +
                "%s);\n", ISBN, user_id ));
    };

    public void addBookStockRequest(String ISBN, String user_id, String type, String quantity){
        conn.execute(String.format("\tinsert into Publisher_requests (book_id,type,quantity,status,created_by)\n" +
                "values ((select book_id from Books where ISBN = \"%s\"), \"%s\", %s, \"pending\"," +
                "%s);\n", ISBN, type, quantity, user_id ));
    };

    public void assignBook(String ISBN, String username){
        List<Map<String, Object>> temp = conn.queryForList(
                "Select user_id Access where username = \""+username+"\";",
                "user_id");

        updateBooks_borrow(ISBN, "remove", temp.get(0).get(0).toString());
        borrow(temp.get(0).get(0).toString(), ISBN);
    }

    public void insertPublisher(String Username, String Password, String Email, String PhoneNumber, String company_name, String address) {
        try {
            conn.execute("insert into Access(username,password,access_type,email,phone_number,is_active) \n" +
                    "\tvalues(\"" + Username + "\",\"" + Password + "\", \"publisher\",\"" + Email + "\",\"" + PhoneNumber + "\",true);\n");
            conn.execute( "insert into Publishers (user_id, company_name, address) values \n" +
                    "\t( (select user_id from Access where username = \"" + Username + "\" and Access_type = \"publisher\"),\""
                    + company_name + "\",\"" + address + "\");");

        } catch (Exception e) {
            System.out.print("Publisher not added\n" + e);
        }
    }

}
