package Additional;

public class exceptio {

    public static void main(String[] args) {
        Library lib = new Library();
        Book b = new Book(1,"cec","c");

        Book b1 = null;
        lib.addBook(b);
        lib.addBook(b);
        lib.addBook(b);
        lib.addBook(b1);
        lib.printBooks();
    }
}
class Book{
    int id;
    String name;
    String author;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }
}
class Library{
    String name;
    String city;
    Book books[] = new Book[3];
    int index = 0;

    void addBook(Book book) {
        try{
            if(index >= books.length){
                System.out.println("The lib is full");
            }
            books[index] = book;
            index++;
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }
    public void printBooks() {
        for (int i = 0; i < index; i++) {
            try {
                if (books[i] != null) {
                    System.out.println(books[i].toString());
                } else {
                    System.out.println("The book is empty");
                }
            } catch (NullPointerException e) {
                System.out.println("The book is empty");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

