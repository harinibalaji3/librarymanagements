import java.sql.*;
import java.util.*;
public class BookDAO 
{
  private Connection connection;
  public BookDAO()throws SQLException{
  connection=Databaseconnection.getConnection();
 }
 public void addBook(Book book)throws SQLException{
    String query="INSERT INTO books(title,author,publisher,quantity,availablequantity)VALUES(?,?,?,?,?)"  ; 
    try(PreparedStatement stmt=connection.prepareStatement(query)){
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getPublisher());
        stmt.setInt(4, book.getQuantity());
        stmt.setInt(5,book.getAvailableQuantity());
        stmt.executeUpdate();
    }
 }
 public List<Book>getAllbooks()throws SQLException
 {
    List<Book> books=new ArrayList<>();
    String query="SELECT * FROM books";
    try(Statement stmt=connection.createStatement();
    ResultSet rs=stmt.executeQuery(query)){
        while (rs.next()) {
            Book book = new Book(
                rs.getInt("bookId"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("publisher"),
                rs.getInt("quantity"),
                rs.getInt("availablequantity")
            );
            books.add(book);
        }
    }
    return books; 
  }
 public void borrowBook(int bookId,int memberId)throws SQLException{
    String query="SELECT * from books WHERE bookId=?";
    connection.setAutoCommit(false);
    try(PreparedStatement stmt=connection.prepareStatement(query))
    {
        stmt.setInt(1, bookId);
        ResultSet rs=stmt.executeQuery();
        if(rs.next())
        {
            int availablequantity=rs.getInt("availablequantity");
            if (availablequantity>0){
                String borrowQuery="INSERT INTO borrowedbook(bookId,memberId,borrowdate) VALUES(?,?,CURDATE())";
                try(PreparedStatement borrowstmt=connection.prepareStatement(borrowQuery)){
                    borrowstmt.setInt(1, bookId);
                    borrowstmt.setInt(2, memberId);
                    borrowstmt.executeUpdate();
                    String updateQuery="UPDATE books SET availablequantity=availablequantity-1 WHERE bookId=?";
                    try(PreparedStatement updatestmt=connection.prepareStatement(updateQuery)){
                       updatestmt.setInt(1, bookId); 
                       updatestmt.executeUpdate();
                       System.out.println("Book borrowed successflly");
                    } }
            }
            else {
                System.out.println("No available copies to borrow");
            }
        }
    }
    
}
 
 public void returnBook(int bookId,int memberId)throws SQLException{
        String query="SELECT * FROM borrowedbook WHERE bookId=? AND memberId=?";    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, bookId);
        stmt.setInt(2, memberId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            String returnQuery="UPDATE borrowedbook  SET status='returned' WHERE bookId = ? AND memberId = ? ";
            try (PreparedStatement returnStmt = connection.prepareStatement(returnQuery)) {
            returnStmt.setInt(1, bookId);
            returnStmt.setInt(2, memberId);
            returnStmt.executeUpdate();
            System.out.println("book returned successfully");
            }
             String updateQuery = "UPDATE books SET availablequantity = availablequantity + 1 WHERE bookId = ?";
             try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
             updateStmt.setInt(1, bookId);
             updateStmt.executeUpdate();
            } }
        else {
             System.out.println("This book was not borrowed");
        }
        }
    }
}
