import java.sql.SQLException;
import java.util.*;
public class librarymanagement {
    public static void main(String[] args) throws Exception {
      Scanner sc=new Scanner(System.in);
      BookDAO bookDAO;
      try{
      bookDAO=new BookDAO();
      bookDAO.addBook(new Book(1,"java programming","jamesgosling","oracle",2,2)); 
      bookDAO.addBook(new Book(2,"data structure","mark weis","pearson",4,4));
      bookDAO.addBook(new Book(3,"databasemanagement","RP Mahapatra","Khanna",2,2));
      while (true) {
        System.out.println("Library Management System");
        System.out.println("1. View all books");
        System.out.println("2. Borrow a book");
        System.out.println("3. Return a book");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
        int option = sc.nextInt();
      
      switch (option) {
        case 1:
            List<Book>books=bookDAO.getAllbooks();
            if (books.isEmpty()) {
              System.out.println("No books available in the library.");
            }else {
            for(Book book:books){
                System.out.println(book.getBookId()+"\t"+book.getTitle()+"\tby"+book.getAuthor()+"\t(available:"+book.getAvailableQuantity()+")");
             }}
            break;
        case 2:
             System.out.println("enter bookId");
             int bookId=sc.nextInt();
             System.out.println("enter memberId");
             int memberId=sc.nextInt();
             try {
                  bookDAO.borrowBook(bookId, memberId);
              } catch (SQLException e) {
                  System.out.println("Error borrowing the book: " + e.getMessage());
              }
               break;
        case 3:
             System.out.print("Enter book ID to return: ");
             int returnBookId = sc.nextInt();
             System.out.print("Enter member ID: ");
             memberId = sc.nextInt();
             try {
              bookDAO.returnBook(returnBookId, memberId);
             } catch (SQLException e) {
              System.out.println("Error returning the book: " + e.getMessage());
             }
             break;
        case 4:
              System.out.println("exit program");
              break;
        }
        } 
      } catch (SQLException e) {
          System.out.println("Error initializing the BookDAO: " + e.getMessage());
         }

    }
}
