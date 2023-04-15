package com.example.mopsfinalproject.constant;

/**
 * SQL commands
 * Including select/delete/update/insert
 */

public abstract class SQLCommand
{
    //query all students
    public static String QUERY_STUDENT = "select stid, stname from Student";

    public static String QUERY_01 = "SELECT lbcallnum, lbtitle FROM libbook";

    public static String QUERY_02 = "SELECT lbcallnum FROM libbook WHERE lbtitle LIKE '%Database Management%'";

//    Query 03: Show all students who have checked out a book
    public static String QUERY_03 = "SELECT DISTINCT Student.stid, Student.stname " +
                                        "FROM CheckOut, Student " +
                                        "WHERE CheckOut.stid = Student.stid";

//    Query 04: Show books that haven't been checked out
    public static String QUERY_04 = "SELECT lbcallnum, lbtitle " +
                                        "FROM LibBook " +
                                        "WHERE lbcallnum NOT IN " +
                                        "(SELECT DISTINCT lbcallnum FROM CheckOut)";

//    Query 05: Show how much students owe in fines
    public static String QUERY_05 = "SELECT Student.stname, SUM(CheckOut.cofine) " +
                                        "FROM Student, CheckOut " +
                                        "WHERE Student.stid = Checkout.stid " +
                                        "GROUP BY CheckOut.stid";

//    Query 06: Show how many times each book has been checked out
    public static String QUERY_06 = "SELECT LibBook.lbtitle, COUNT(CheckOut.lbcallnum) " +
                                        "FROM LibBook LEFT JOIN CheckOut " +
                                            "ON LibBook.lbcallnum = CheckOut.lbcallnum " +
                                        "GROUP BY LibBook.lbtitle";

//    Query 07: Show how many times each student has checked out a book.
    public static String QUERY_07 = "SELECT Student.stname, COUNT(CheckOut.stid) " +
                                        "FROM Student LEFT JOIN CheckOut " +
                                            "ON Student.stid = CheckOut.stid " +
                                        "GROUP BY Student.stname";

    public static String RETURN_BOOK = "UPDATE CheckOut SET coreturned=? " +
                                            "WHERE stid=? AND lbcallnum=?";

    public static String CHECK_BOOK = "INSERT INTO CheckOut(coreturned, stid, lbcallnum, coduedate) " +
                                        "VALUES(?,?,?,?)";

}



