package com.example.librarymanagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Sistema de Gestión de Biblioteca
 */
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Book book1 = new Book("1", "Effective Java", "Joshua Bloch", "Programming");
        Book book2 = new Book("2", "Clean Code", "Robert C. Martin", "Programming");

        Member member = new Member("101", "John Doe");
        Librarian librarian = new Librarian("201", "Jane Smith");

        Library library = new Library();
        library.addBook(book1);
        library.addBook(book2);
        library.addMember(member);
        library.setLibrarian(librarian);

        library.issueBook(book1.getId(), member.getId());
        library.returnBook(book1.getId(), member.getId());

        System.out.println(library.getLibraryDetails());
    }
}

/**
 * Clase que representa un libro en la biblioteca.
 */
class Book {
    private String id;
    private String title;
    private String author;
    private String category;

    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }
}

/**
 * Clase que representa un miembro de la biblioteca.
 */
class Member {
    private String id;
    private String name;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

/**
 * Clase que representa un bibliotecario.
 */
class Librarian {
    private String id;
    private String name;

    public Librarian(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

/**
 * Clase que representa la biblioteca.
 */
class Library {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private Librarian librarian;

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public void issueBook(String bookId, String memberId) {
        System.out.println("Issuing book ID " + bookId + " to member ID " + memberId);
    }

    public void returnBook(String bookId, String memberId) {
        System.out.println("Returning book ID " + bookId + " from member ID " + memberId);
    }

    public String getLibraryDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Library Details:\n");
        details.append("Books:\n");
        for (Book book : books) {
            details.append("ID: ").append(book.getId()).append(", Title: ").append(book.getTitle()).append("\n");
        }
        details.append("Members:\n");
        for (Member member : members) {
            details.append("ID: ").append(member.getId()).append(", Name: ").append(member.getName()).append("\n");
        }
        details.append("Librarian: ").append(librarian.getName()).append("\n");
        return details.toString();
    }
}
