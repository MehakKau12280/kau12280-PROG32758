package ca.sheridancollege.kau12280.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.kau12280.beans.Book;
import ca.sheridancollege.kau12280.beans.Review;
import ca.sheridancollege.kau12280.database.DatabaseAccess;


@Controller
public class BookController {
	private final DatabaseAccess da;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BookController(DatabaseAccess database, PasswordEncoder passwordEncoder) {
        this.da = database;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", da.getAllBooks());
        return "index";
    }

    @GetMapping("/view-book/{bookId}")
    public String viewBook(@PathVariable Long bookId, Model model) {
        Book book = da.getBookById(bookId);
        if (book == null) {
            return "error/permissionDenied";
        }

        List<Review> reviews = da.getReviewsByBookId(bookId);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        return "view-book";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/add-review/{id}")
    public String showAddReviewPage(@PathVariable Long id, Model model) {
        model.addAttribute("bookId", id);
        return "user/add-review";
    }

    @PostMapping("/add-review/{id}")
    public String addReview(@PathVariable Long id, @RequestParam String text) {
        da.addReview(id, text);
        return "redirect:/view-book/" + id;
    }

    @GetMapping("/add-book")
    public String showAddBookPage() {
        return "admin/add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@RequestParam String title, @RequestParam String author) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);

        da.addBook(book);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        String encodedPassword = passwordEncoder.encode(password);

        da.addUser(username, encodedPassword, true);

        return "redirect:/login";
    }
}
