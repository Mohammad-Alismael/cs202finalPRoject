package edu.ozu.cs202project.services;

import edu.ozu.cs202project.Salter;
import edu.ozu.cs202project.services.LoginService;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

public class Functions {



    public void test(ModelMap model, JdbcTemplate conn2){
        String password = Salter.salt("ozu123", "CS202Project");
        System.out.println(password);
    }

    public void publisherRequestHistory(ModelMap model, JdbcTemplate conn, String user_id){
        System.out.println("select ISBN, title, type, Publisher_requests.status, quantity from Publisher_requests join " +
                "Books on Publisher_requests.book_id = Books.book_id where created_by = "+user_id+";");
        List<String[]> data = conn.query( "select ISBN, title, type, Publisher_requests.status , quantity, created_on from " +
                        "Publisher_requests join Books on Publisher_requests.book_id = Books.book_id " +
                        "where created_by = "+user_id+";",
                (row, index) -> {
                    return new String[]{row.getString("ISBN"), row.getString("title"),
                            row.getString("type"), row.getString("status"),
                            row.getString("quantity"), row.getString("created_on")
                    };
                });
        model.addAttribute("PublisherRequestHistory", data.toArray(new String[0][5]));
    }
    public void removeRequest(ModelMap modelMap, JdbcTemplate conn, String user_id, String ISBN){
        conn.execute("");
        conn.execute("insert into Publisher_requests (book_id,type,quantity,status,created_by)" +
                "values ((select book_id from Books where ISBN = \""+ISBN+"\" and status = \"active\"), \"remove\", 0, \"pending\", "+user_id+" );");
    }

    public void publisherTransactionHistory(ModelMap model, JdbcTemplate conn, String user_id){
        System.out.println("select ISBN, title, type, created_by, created_on from " +
                "completeHistory where publisher = (select company_name from Publishers where user_id =" +user_id+");");
        List<String[]> data = conn.query( "select ISBN, title, type, username, created_on from " +
                        "completeHistory where publisher = (select company_name from Publishers where user_id =" +user_id+");",
                (row, index) -> {
                    return new String[]{row.getString("ISBN"), row.getString("title"),
                            row.getString("type"), row.getString("username"), row.getString("created_on")
                    };
                });
        model.addAttribute("PublisherBookHistory", data.toArray(new String[0][4]));
    }

    public void books(ModelMap model,JdbcTemplate conn2, String Author, String Genre, String Year, String Publisher,
                               String Title, String Topics, String ISBN, String orderBy,
                               String currentlyBorrowed, String currentlyOverdue, String accessType, String user_id) {

        String query;
        System.out.println("books is called");

        if (Author.equals("none"))
            Author = "";
        if (ISBN.equals("none"))
            ISBN = "";
        if (Genre.equals("none"))
            Genre = "";
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
        System.out.println("currentlyOverdue: "+currentlyOverdue+", currently borrowed:"+currentlyBorrowed);
        if(accessType.equals("manager") ) {
            query = "select * from managerTable where title " +
                    "like \"%" + Title + "%\" and author like \"%" + Author + "%\" and topics like " +
                    "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                    "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and publication_date like " +
                    "\"%" + Year + "%\"order by " + orderBy + ";";
        }else
            if (accessType.equals("publisher")) {
                System.out.println(accessType);
                if (currentlyBorrowed.equals("false") && currentlyOverdue.equals("false")) {

                    query = "select DISTINCT Books.ISBN, Books.title, Books.author, Books.topics, " +
                            "Books.genre, Books.description,Books.publication_date , Books.publisher,Books_borrow.units as Borrow_units, " +
                            "Books_sell.units as Sell_units, Books.status, Books_borrow.penalty_info, Books_sell.price from Books left join Books_sell on " +
                            "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id" +
                            " left join Borrowings on Books.book_id = Borrowings.book_id " +
                            "left join Returns on Borrowings.id = Returns.borrowed_id where title " +
                            "like \"%" + Title + "%\" and author like \"%" + Author + "%\" and topics like " +
                            "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                            "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and Books.publication_date like " +
                            "\"%" + Year + "%\"order by " + orderBy + ";";

                } else if (currentlyOverdue.equals("false") && currentlyBorrowed.equals("true")) {
                    query = "select DISTINCT Books.ISBN, Books.title, Books.author, Books.topics, Books.genre, " +
                            "Books.description ,Books.publication_date, Books.publisher, Books_borrow.units as Borrow_units, Books_sell.units as Sell_units, Books.status" +
                            ", Books_borrow.penalty_info, Books_sell.price from Books join overdue_books on Books.ISBN = overdue_books.ISBN left join Books_sell on " +
                            "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id " +
                            "left join Borrowings on Books.book_id = Borrowings.book_id " +
                            "left join Returns on Borrowings.id = Returns.borrowed_id where Books.title like \"%" + Title + "%\" and " +
                            "Books.author like \"%" + Author + "%\" and Books.topics like \"%" + Topics + "%\" and Books.genre like " +
                            "\"%" + Genre + "%\" and Books.ISBN like \"%" + ISBN + "%\" and Books.publisher like \"%" + Publisher + "%\" and " +
                            "Books.publication_date like \"%" + Year + "%\" order by " + orderBy + ";";
                } else {
                    query = "select DISTINCT Books.ISBN, Books.title, Books.author, Books.topics, " +
                            "Books.genre, Books.description,Books.publication_date, Books.publisher,Books_borrow.units as Borrow_units, " +
                            "Books_sell.units as Sell_units, Books.status , Books_borrow.penalty_info, Books_sell.price from Books left join Books_sell on " +
                            "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id" +
                            " join Borrowings on Books.book_id = Borrowings.book_id " +
                            "left join Returns on Borrowings.id = Returns.borrowed_id where title " +
                            "like \"%" + Title + "%\" and author like \"%" + Author + "%\" and topics like " +
                            "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                            "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and Books.publication_date like " +
                            "\"%" + Year + "%\" order by " + orderBy + ";";
                }
        }else{
            System.out.println(accessType);
            if (currentlyBorrowed.equals("false") && currentlyOverdue.equals("false")) {

                query= "select DISTINCT Books.ISBN, Books.title, Books.author, Books.topics, " +
                        "Books.genre, Books.description,Books.publication_date , Books.publisher,Books_borrow.units as Borrow_units, " +
                        "Books_sell.units as Sell_units , Books_borrow.penalty_info, Books_sell.price from Books left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id" +
                        " left join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where title " +
                        "like \"%" + Title + "%\" and author like \"%" + Author + "%\" and topics like " +
                        "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                        "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and Books.publication_date like " +
                        "\"%" + Year + "%\"" + "and status = \"accepted\"" + "order by " + orderBy + ";";

            } else if (currentlyOverdue.equals("false") && currentlyBorrowed.equals("true") ) {
                System.out.println("Query for currentlyBorrowed books for user" + currentlyBorrowed);

                query= "select DISTINCT Books.ISBN, Books.title, Books.author, Books.topics, Books.genre, " +
                        "Books.description ,Books.publication_date, Books.publisher, Books_borrow.units as Borrow_units, " +
                        "Books_sell.units as Sell_units , Books_borrow.penalty_info, Books_sell.price " +
                        "from Books join overdue_books on Books.ISBN = overdue_books.ISBN left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id " +
                        "left join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where Books.title like \"%" + Title + "%\" and " +
                        "Books.author like \"%" + Author + "%\" and Books.topics like \"%" + Topics + "%\" and Books.genre like " +
                        "\"%" + Genre + "%\" and Books.ISBN like \"%" + ISBN + "%\" and Books.publisher like \"%" + Publisher + "%\" and " +
                        "Books.publication_date like \"%" + Year + "%\"and Books.status " +
                        "= \"accepted\" and created_by = "+user_id+" order by " + orderBy + ";";
            } else {
                System.out.println("currentlyOverdue" + currentlyOverdue);
                query= "select DISTINCT Books.ISBN, Books.title, Books.author, Books.topics, " +
                        "Books.genre, Books.description,Books.publication_date, Books.publisher,Books_borrow.units as Borrow_units, " +
                        "Books_sell.units as Sell_units , Books_borrow.penalty_info, Books_sell.price from Books left join Books_sell on " +
                        "Books.book_id=Books_sell.book_id left join Books_borrow on Books.book_id = Books_borrow.book_id" +
                        " left join Borrowings on Books.book_id = Borrowings.book_id " +
                        "left join Returns on Borrowings.id = Returns.borrowed_id where title " +
                        "like \"%" + Title + "%\" and author like \"%" + Author + "%\" and topics like " +
                        "\"%" + Topics + "%\" and genre like \"%" + Genre + "%\" and " +
                        "ISBN like \"%" + ISBN + "%\" and publisher like \"%" + Publisher + "%\" and Books.publication_date like " +
                        "\"%" + Year + "%\"" + "and status = \"accepted\"and created_by = "+user_id + " order by " + orderBy + ";";
            }
        }
        System.out.println(query);
        if(accessType.equals("user")) {
            List<String[]> data = conn2.query(query,
                    (row, index) -> {
                        return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("author"),
                                row.getString("topics"), row.getString("genre"), row.getString("description"),
                                row.getString("publisher"),
                                row.getString("Borrow_units"), row.getString("Sell_units"), row.getString("publication_date"),
                                row.getString("penalty_info"), row.getString("price")};
                    });

            model.addAttribute("books_info", data.toArray(new String[0][11]));
        }else if(accessType.equals("publisher")){
            List<String[]> data = conn2.query(query,
                    (row, index) -> {
                        return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("author"),
                                row.getString("topics"), row.getString("genre"), row.getString("description"),
                                row.getString("publisher"), row.getString("status"),
                                row.getString("Borrow_units"), row.getString("Sell_units"), row.getString("publication_date"),
                                row.getString("penalty_info"), row.getString("price")};
                    });

            model.addAttribute("books_info", data.toArray(new String[0][12]));
        }else {
            List<String[]> data = conn2.query(query,
                    (row, index) -> {
                        return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("author"),
                                row.getString("topics"), row.getString("genre"), row.getString("description"),
                                row.getString("publisher"), row.getString("status"),
                                row.getString("Borrow_units"), row.getString("Sell_units"), row.getString("publication_date"),
                                row.getString("penalty_info"), row.getString("price"), row.getString("NumberOfBorrowings"),
                        row.getString("NumberOfPurchases")};
                    });

            model.addAttribute("books_info", data.toArray(new String[0][14]));
        }
    }

    public void priceAndPenalty (ModelMap model, JdbcTemplate conn, String ISBN){

        System.out.print("called priceAndPenalty");

        String query = "select title, penalty_info, price from Books_sell right join Books_borrow " +
                "on Books_sell.book_id = Books_borrow.book_id left join Books on Books.book_id = Books_borrow.book_id where ISBN =\""+ISBN+"\";";
        List<String[]> data = conn.query(query,
                (row, index) -> {
                    return new String[]{row.getString("title"),
                            row.getString("penalty_info"), row.getString("price")};
                });
        System.out.println("");
        System.out.println(data.get(0).toString());
        model.addAttribute("penaltyAndPrice", data.toArray(new String[0][2]));
    }

    public void numberOfBorrowingsAndPurchases(ModelMap model,JdbcTemplate conn,String user_id, String accessType) {
        System.out.println(" numberOfBorrowingsAndPurchases called\n");
        String query;
        try {

            if (accessType.equals("manager")) {

                query ="select Books.ISBN, Books.title, Books.publisher, " +
                        "COALESCE(NumberOfBorrowings,0), COALESCE(NumberOfPurchases,0) " +
                        "from NumberOfBorrowingsAndPurchases right join Books on NumberOfBorrowingsAndPurchases.ISBN = Books.ISBN;";

                 /*"select DISTINCTROW ISBN, title, publisher, NumberOfBorrowings, NumberOfPurchases from (\n" +
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
                        ") as c;";*/
                System.out.println("manager query:" + query);
                List<String[]> data = conn.query(query,
                        (row, index) -> {
                            return new String[]{row.getString("ISBN"),
                                    row.getString("title"), row.getString("publisher"), row.getString("NumberOfBorrowings"), row.getString("NumberOfPurchases")};
                        });
                System.out.println("");
                System.out.println(data.get(0).toString());
                model.addAttribute("Publisher_books", data.toArray(new String[0][4]));
            } else {
                query ="select Books.ISBN, Books.title, " +
                        "COALESCE(NumberOfBorrowings,0) as \"NumberOfBorrowings\", COALESCE(NumberOfPurchases,0) as \"NumberOfPurchases\"" +
                        "from NumberOfBorrowingsAndPurchases right join Books on NumberOfBorrowingsAndPurchases.ISBN = Books.ISBN where Books.publisher = "+
                "(select company_name from Publishers where user_id = " + user_id + ") ;";
                System.out.println("publisher query:\n" + query);
                List<String[]> data = conn.query(query,
                        (row, index) -> {
                            return new String[]{row.getString("ISBN"),
                                    row.getString("title"), row.getString("NumberOfBorrowings"), row.getString("NumberOfPurchases")};
                        });
                model.addAttribute("Publisher_books", data.toArray(new String[0][3]));
            }
        }catch (Exception e){
            System.out.println("error:\n" + e);
        }
    }



    public void insertUser(JdbcTemplate conn,String Username, String Password, String Email, String PhoneNumber, String Name, String Surname) {
        try {
            System.out.println("Insert into Access");
            System.out.println("insert into Access(username,password,access_type,email,phone_number,is_active) \n" +
                    "\tvalues(\"" + Username + "\",\"" + Password + "\", \"user\",\"" + Email + "\",\"" + PhoneNumber + "\",true);\n");
            System.out.println("Persons");
            System.out.println("insert into Persons (user_id, name, surname) values \n" +
                    "\t( (select user_id from Access where username = \"" + Username + "\" and Access_type = \"user\"),\""
                    + Name + "\",\"" + Surname + "\");");


            conn.execute("insert into Access(username,password,access_type,email,phone_number,is_active) \n" +
                    "\tvalues(\"" + Username + "\",\"" + Password + "\", \"user\",\"" + Email + "\",\"" + PhoneNumber + "\",true);\n");
            conn.execute( "insert into Persons (user_id, name, surname) values \n" +
                    "\t( (select user_id from Access where username = \"" + Username + "\" and Access_type = \"user\"),\""
                            + Name + "\",\"" + Surname + "\");");

        } catch (Exception e) {
            System.out.print("User not added\n" + e);
        }
    }


    public void UserHistory(ModelMap model,JdbcTemplate conn,String user_id) {
        try {
            List<String[]> data = conn.query("Select ISBN, title, quantity, total, created_on from Purchases natural join Books where created_by = " + user_id,
                    (row, index) -> {
                        return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("quantity"),
                                row.getString("total"), row.getString("created_on")};
                    });
            model.addAttribute("UserPurchaseHistory", data.toArray(new String[0][4]));
        }catch (Exception e){
            System.out.println("Error" + e);
        }

        try {
            List<String[]> data1 = conn.query("Select ISBN, title, start_date, end_date, return_date from Borrowings left join Returns on Borrowings.id = " +
                            "Returns.borrowed_id natural join Books where created_by = " + user_id,
                    (row, index) -> {
                        return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("start_date"),
                                row.getString("end_date"), row.getString("return_date")};
                    });
            model.addAttribute("UserBorrowHistory", data1.toArray(new String[0][4]));

        }catch (Exception e){
            System.out.println("Error" + e);
        } try{
        List<String[]> data2 = conn.query("select ISBN, title, created_on from Notification_requests natural join Books where created_by = " + user_id,
                (row, index) -> {
                    return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("created_on")};
                });
        model.addAttribute("UserNotificationRequestHistory", data2.toArray(new String[0][2]));

        }catch (Exception e){
            System.out.println("Error" + e);
        } try{
        List<String[]> data3 = conn.query("select ISBN, title, Hold_requests.status, created_by from Hold_requests natural join Books where Hold_requests.created_by = " + user_id+";",
                (row, index) -> {
                    return new String[]{row.getString("ISBN"), row.getString("title"),row.getString("status") ,row.getString("created_by")};
                });
        model.addAttribute("UserHoldRequestHistory", data3.toArray(new String[0][3]));

        }catch (Exception e){
            System.out.println("Error" + e);
        }try{
        List<String[]> data4 = conn.query("Select ISBN, title, start_date, end_date, return_date from overdued_books where user_id = " + user_id+";",
                (row, index) -> {
                    return new String[]{row.getString("ISBN"), row.getString("title"), row.getString("start_date"),
                            row.getString("end_date"), row.getString("return_date")};
                });

        model.addAttribute("UserOverdueHistory", data4.toArray(new String[0][4]));

        }catch (Exception e){
            System.out.println("Error" + e);
        }
    }

    public void favourites(ModelMap model, JdbcTemplate conn,String AccessType, String User_id, String Includes, String Type) {
        String query;
        System.out.println("access type:" +AccessType+", User_id: "+User_id+", Includes:"+Includes+" ,Type:"+Type);
        switch (AccessType) {
            case "manager":
                break;
            case "publisher":
                break;
            case "user":
                switch (Includes) {
                    case "borrow":
                        query= "Select " + Type + ", count(*) as \"number\" from Borrowings join Books B on Borrowings.book_id = B.book_id " +
                                "    where created_by = " + User_id + " \n" +
                                "    group by " + Type + "  order by \"number\" desc limit 1;\n";
                    case "sell":
                        query= "Select " + Type + ", count(*) as \"number\" from Purchases  join Books B on Purchases.book_id = B.book_id " +
                                "    where created_by = " + User_id + " \n" +
                                "    group by  " + Type + " order by \"number\" desc limit 1;\n";
                    default:
                        query= "Select " + Type + ", count(*) as \"number\" from combined_table  join Books B on combined_table.book_id = B.book_id " +
                                "    where created_by = " + User_id + " \n" +
                                "    group by " + Type + " order by \"number\" desc limit 1;\n";
                }
                System.out.println(query);
                System.out.println("Type: " +Type);

                List<String[]> favourite = conn.query(query,
                        (row, index) -> {
                            return new String[]{row.getString(Type)};
                        });
                System.out.println("favourite: "+favourite.get(0)[0]);
                model.addAttribute("favourite", favourite.get(0)[0]);
        }
    }

    public void updateBooks_borrow(JdbcTemplate conn,String ISBN, String type, String user_id){
        System.out.println("called updateBooks_borrow");


        List<String[]> count = conn.query("Select count(*) as \"number of hold requests\" from Hold_requests where book_id = " +
                        "(select book_id from Books where ISBN =\""+ISBN+"\") and status = \"active\" and created_by = "+user_id+";",
                (row, index) -> {
                    return new String[]{row.getString("number of hold requests")};
                });


        System.out.println("number of hold requests: " + count.get(0)[0]);
        if(type.equals("add")){
            if(count.get(0)[0].equals("0")){
                System.out.println("add, count = 0");
                System.out.println("update Books_borrow set units = units + 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\") ;");
                conn.execute("update Books_borrow set units = units + 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\");");
            }else{
                System.out.println("add, count > 0 ");
                System.out.println("update Books_borrow set held = held + 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\");");
                System.out.println("update Hold_requests set status = \"active\" where book_id = (select book_id from Books where ISBN =\""+ISBN+"\") and created_by = "+user_id+
                        " order by created_on asc limit 1;");
                conn.execute("update Books_borrow set held = held + 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\");");
                conn.execute("update Hold_requests set status = \"active\" where book_id = (select book_id from Books where ISBN =\""+ISBN+"\") and created_by = "+user_id +
                        " order by created_on asc limit 1;");
            }
        }else{
            if(count.get(0)[0].equals("0")){
                System.out.println("remove, count = 0");
                conn.execute("update Books_borrow set units = units - 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\");");
            }else{
                System.out.println("remove, count>0");
                System.out.println("update Books_borrow set held = held - 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\");");
                System.out.println("update Hold_requests set status = \"removed\" where book_id = (select book_id from Books where ISBN =\""+ISBN+"\")" +
                        "and created_by = "+user_id+" order by created_on asc limit 1;");

                conn.execute("update Books_borrow set held = held - 1 where book_id = (select book_id from Books where ISBN = \""+ISBN+"\");");
                conn.execute("update Hold_requests set status = \"removed\" where book_id = (select book_id from Books where ISBN =\""+ISBN+"\")" +
                        "and created_by = "+user_id+" order by created_on asc limit 1;");
            }
        }
        System.out.println("updateBooksBorrow successful");
    };

    public void borrow(JdbcTemplate conn,String user_id, String ISBN)
    {
        System.out.println("Called borrow");
        updateBooks_borrow(conn ,ISBN, "remove", user_id);
        conn.execute("insert into Borrowings (book_id,start_date,end_date,created_by) values("+
                "(select book_id from Books where ISBN = "+ISBN+" and status=\"accepted\"),"+"(SELECT CURDATE()), " +
                                "(SELECT DATE_ADD(CURDATE(), INTERVAL 14 DAY))," + user_id + ");" );

    }

    public void purchase(JdbcTemplate conn,String user_id, String ISBN, String quantity)
    {
        System.out.println("purchase called");
        System.out.println("update Books_sell set units = units - \""+quantity+"\" where book_id = (select book_id from Books where " +
                "ISBN = \""+ISBN+"\" and status=\"accepted\");");
        conn.execute(
                "update Books_sell set units = units - \""+quantity+"\" where book_id = (select book_id from Books where " +
                        "ISBN = \""+ISBN+"\" and status=\"accepted\");");
        System.out.println("1st query done");
        System.out.println("insert into Purchases (book_id,quantity,total,created_by) values"
                +"( (select book_id from Books where ISBN =\""+ ISBN + "\"and status=\"accepted\"),"+ quantity+","
                +"(select price" + quantity + " from Books_sell where book_id = (select book_id from Books where ISBN = \"" +ISBN+"\" and status=\"accepted\")),"
                + user_id + ");");
        conn.execute("insert into Purchases (book_id,quantity,total,created_by) values"
        +"( (select book_id from Books where ISBN =\""+ ISBN + "\"and status=\"accepted\"),"+ quantity+","
        +"(select price from Books_sell where book_id = (select book_id from Books where ISBN = \"" +ISBN+"\" and status=\"accepted\") )* "+quantity+" ,"
        + user_id + ");");
        System.out.println("purchase complete");
    }

    public void getPenalty(ModelMap model, JdbcTemplate conn,String user_id, String ISBN){


        List<String[]> penalty = conn.query("SELECT GREATEST (DATEDIFF (CURRENT_DATE, (select end_date from Borrowings join Books on Borrowings.book_id = Books.book_id "
                +"join Access on Borrowings.created_by = Access.user_id "
                +"where  ISBN = \""+ISBN+"\" and user_id = "+user_id+" and Access_type = \"user\" order by Borrowings.end_date limit 1)) * (select penalty_info "
                +"from Books_borrow where book_id = (select book_id from Books where ISBN = \"" +ISBN+ "\") ), 0) as penalty ;",
                (row, index) -> {
                    return new String[]{row.getString("penalty")};
                });
        System.out.println("penalty: " + penalty.get(0)[0].toString());
        model.addAttribute("penalty", penalty.get(0)[0].toString());

    }

    public void returnBook(JdbcTemplate conn,String user_id, String ISBN){
        System.out.println("called returnBook");
        updateBooks_borrow(conn ,ISBN, "add", "user_id");

        List<String[]> borrowed_id = conn.query("select id from Borrowings left join Returns on Borrowings.id = Returns.borrowed_id join " +
                "Books on Borrowings.book_id = Books.book_id where Returns.return_date is null and created_by = "+user_id+" and ISBN =\"" +ISBN+ "\"order by end_date limit 1;",
                (row, index) -> {
                    return new String[]{row.getString("id")};
                });

        conn.execute(
        "insert into Returns (borrowed_id) values ( "+borrowed_id.get(0)[0]+");");
    }

    public void addUnits(JdbcTemplate conn, String ISBN, String type, String quantity, String priceOrPenalty) {
        System.out.println("add units is called");
        if (type.equals("borrow")) {
                conn.execute("insert into Books_borrow (book_id, units, penalty_info) values" +
                                "( (select book_id from Books where ISBN = \""+ISBN+"\"), "+quantity+","+ priceOrPenalty+" );");
        }else{
            conn.execute("insert into Books_sell (book_id, units, price) values" +
                    "( (select book_id from Books where ISBN = \""+ISBN+"\"), "+quantity+","+ priceOrPenalty+" );");
        }
    }

        public void updateUnits(JdbcTemplate conn, String ISBN, String type, String quantity){
            System.out.println("add units is called");
            if(type.equals("borrow")){
                    conn.execute("update Books_borrow set units = units + "+quantity+" where book_id = (select book_id from Books where ISBN = \""+ISBN+"\") ;\n");
            }else {
                conn.execute("update Books_sell set units = units + "+quantity+" where book_id = (select book_id from Books where ISBN = \""+ISBN+"\") ;\n");
            }
    }
    public void assignBook(JdbcTemplate conn, String username, String ISBN, String operation, String quantity){
        System.out.println("assign book is called operation:" + operation);

        List<String[]> user_id = conn.query("select user_id from Access where username = \""+username+"\";",
                (row, index) -> {
                    return new String[]{row.getString("user_id")};
                });
        String assignee_id = user_id.get(0)[0];
        switch (operation){
            case "borrow":
                System.out.println("calling borrow");
                borrow(conn, assignee_id, ISBN);
                break;
            case "return":
                returnBook(conn, assignee_id, ISBN);
                break;
            case "purchase":
                purchase(conn, assignee_id, ISBN, quantity);
                break;
            case "hold request":
                addHoldRequest(conn, assignee_id, ISBN);
                break;
            case "preorder book":
                addNotificationRequest(conn, assignee_id, ISBN);
                break;
        }
    }



    public void addBookStock(JdbcTemplate conn,String ISBN, String type, String quantity, String price){
        if (type.equals("sell"))
            conn.execute(String.format("insert Books_sell (book_id, units, price) values ( (select book_id from Books where ISBN = \"%s\"),%s, %s );",ISBN, quantity, price ));
        else
            conn.execute(String.format("insert Books_borrow (book_id, units, penalty_info) values ( (select book_id from Books where ISBN = \"%s\"),%s, %s );",ISBN, quantity, price ));
    }

    public void removeBook(JdbcTemplate conn,String ISBN){
        conn.execute("update Books set status = \"removed\" where ISBN = \""+ISBN+"\";\n");
    }

    public void viewRequestsHistory(JdbcTemplate conn, ModelMap model){
         String query = "select ISBN, Title, quantity, type ,company_name, Publisher_requests.status, Publisher_requests.created_on" +
                " from Publisher_requests natural join Books natural join Publishers;";
    }


    public void viewPendingRequests(JdbcTemplate conn, ModelMap model){
        System.out.println("called viewPendingRequests");
        String query = "select Publisher_requests.id, ISBN, title, quantity, type ,company_name from Publisher_requests natural " +
                "join Books join Publishers on Publisher_requests.created_by = Publishers.user_id where Publisher_requests.status = \"pending\";";
        System.out.println(query);
        List<String[]> data = conn.query(query,
                (row, index) -> {
                    return new String[]{row.getString("id"),row.getString("ISBN"),
                            row.getString("title"), row.getString("quantity"), row.getString("type"),
                            row.getString("company_name")};
                });
        model.addAttribute("pendingPublisherRequests", data.toArray(new String[0][5]));
    }

    public void mostBorrowingUsers(JdbcTemplate conn, ModelMap model){
        String query = "select username, name, surname, email, phone_number, count(*) as \"number of borrowings\"  "+
        "from Borrowings join Access on Borrowings.created_by ="+
                "Access.user_id join Persons on Persons.user_id = Access.user_id group by created_by order by count(*) desc;";

        List<String[]> data = conn.query(query,
                (row, index) -> {
                    return new String[]{row.getString("username"),
                            row.getString("name"), row.getString("surname"), row.getString("email"),
                    row.getString("phone_number"), row.getString("number of borrowings")};
                });
        model.addAttribute("mostBorrowingUsers", data.toArray(new String[0][5]));
    }

    public void mostPurchasingUsers(JdbcTemplate conn, ModelMap model){
        String query = "select username, name, surname, email, phone_number, count(*) as \"number of purchases\"  "+
                "from Purchases join Access on Purchases.created_by ="+
                "Access.user_id join Persons on Persons.user_id = Access.user_id group by created_by order by count(*) desc;";

        List<String[]> data = conn.query(query,
                (row, index) -> {
                    return new String[]{row.getString("username"),
                            row.getString("name"), row.getString("surname"), row.getString("email"),
                            row.getString("phone_number"), row.getString("number of purchases")};
                });
        model.addAttribute("mostPurchasingUsers", data.toArray(new String[0][5]));
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    public void rejectRequest(JdbcTemplate conn, String id){
        conn.execute("update Publisher_requests set status = \"rejected\" where id = "+id+");");
    }
    public void approveRequest(JdbcTemplate conn,String id){

        // get the ID, get the type
        List<Map<String, Object>> temp = conn.queryForList(
                "Select type, ISBN,  from Publisher_requests where id = "+id+";",
                new Object[]{"type"});
        Object o = temp.get(0).get(0);

        if ("borrow".equals(o)) {

            String query = "select username, name, surname, email, phone_number, count(*) as \"number of purchases\"  "+
                    "from Purchases join Access on Purchases.created_by ="+
                    "Access.user_id join Persons on Persons.user_id = Access.user_id group by created_by order by count(*) desc;";

            List<String[]> data = conn.query(query,
                    (row, index) -> {
                        return new String[]{row.getString("username"),
                                row.getString("name"), row.getString("surname"), row.getString("email"),
                                row.getString("phone_number"), row.getString("number of purchases")};
                    });
            //model.addAttribute("mostPurchasingUsers", data.toArray(new String[0][5]));
        //    addBookStock();
            // check if book exists in book stock, if so update



            // else insert
        conn.execute("update Books_borrow set units = ");
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



    public void deleteBook(JdbcTemplate conn, String ISBN){
        conn.execute("SET SQL_SAFE_UPDATES = 0; delete from Books where ISBN = \""+ISBN+"\";");
    }


    public void addNotificationRequest(JdbcTemplate conn, String user_id, String ISBN){
        conn.execute(String.format("insert into Notification_requests (book_id, created_by) " +
                "values ((select book_id from Books where ISBN = \"%s\"), %s)",ISBN, user_id));
    }

    public void addBook(JdbcTemplate conn, String title, String description, String publication_date, String author, String genre, String ISBN ,String Book_status){
        conn.execute(String.format("insert into Books(title,description,publication_date," +
                        "author,topics,genre,ISBN,publisher,status) values  " +
                        "(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\");"
                        , title, description, publication_date, author, genre, ISBN, Book_status));
    }
    public void addHoldRequest(JdbcTemplate conn, String user_d, String ISBN){
        System.out.println("addHoldRequest called");
        System.out.println(String.format("insert into Hold_requests (book_id, status, created_by) values \n" +
                "\t((select book_id from Books where ISBN = \"%s\" and status=\"accepted\"), \"pending\", \n" +
                "\t%s );\n",ISBN, user_d));
        conn.execute(String.format("insert into Hold_requests (book_id, status, created_by) values \n" +
                "\t((select book_id from Books where ISBN = \"%s\" and status=\"accepted\"), \"pending\", \n" +
                "\t%s );\n",ISBN, user_d));
    }

    public void addBookRequest(JdbcTemplate conn, String bookType, String title, String description, String publication_date,
                               String author, String genre, String ISBN, String type, String quantity, String user_id){
        addBook(  conn, title, description, publication_date, author, genre, ISBN, "pending");
        conn.execute(String.format("\tinsert into Publisher_requests (book_id,type,quantity,status,created_by)\n" +
                "values ((select book_id from Books where ISBN = \"%s\"), \"%s\", %s, \"pending\"," +
                "%s);\n", ISBN, type, quantity, user_id ));

    };

    public void removeBookRequest(JdbcTemplate conn, String ISBN, String user_id){
        conn.execute(String.format("\tinsert into Publisher_requests (book_id,type,quantity,status,created_by)\n" +
                "values ((select book_id from Books where ISBN = \"%s\"), \"remove\", 0, \"pending\"," +
                "%s);\n", ISBN, user_id ));
    };

    public void addBookStockRequest(JdbcTemplate conn, String ISBN, String user_id, String type, String quantity){
        conn.execute(String.format("\tinsert into Publisher_requests (book_id,type,quantity,status,created_by)\n" +
                "values ((select book_id from Books where ISBN = \"%s\"), \"%s\", %s, \"pending\"," +
                "%s);\n", ISBN, type, quantity, user_id ));
    };

    public void assignBook(JdbcTemplate conn, String ISBN, String username){
        List<Map<String, Object>> temp = conn.queryForList(
                "Select user_id Access where username = \""+username+"\";",
                "user_id");

        updateBooks_borrow(conn, ISBN, "remove", temp.get(0).get(0).toString());
        borrow(conn, temp.get(0).get(0).toString(), ISBN);
    }


}
