package ca.sheridancollege.kau12280.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.kau12280.beans.Book;
import ca.sheridancollege.kau12280.beans.ErrorMessage;
import ca.sheridancollege.kau12280.beans.Review;

@Repository
public class DatabaseAccess {
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	public ErrorMessage findUserAccount(String email) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String q1 = "SELECT * FROM users WHERE email=:email;";
		parameters.addValue("email", email);
		try {
			return jdbc.queryForObject(q1, parameters, new BeanPropertyRowMapper<ErrorMessage>(ErrorMessage.class));
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public List<String> getRolesById(Long userId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String q2 = "SELECT roles.rolename FROM user_role,roles WHERE user_role.roleid=roles.roleid AND userid=:userId";
		parameters.addValue("userId", userId);
		return jdbc.queryForList(q2, parameters, String.class);
	}

	public List<Book> getAllBooks() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String q3 = "Select * From books;";
		ArrayList<Book> Booklist = (ArrayList<Book>) jdbc.query(q3, namedParameters,
				new BeanPropertyRowMapper<Book>(Book.class));
		return Booklist;
	}

	public Book getBookById(Long bookId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String sql = "SELECT * FROM books WHERE id = :id";
		namedParameters.addValue("id", bookId);
		BeanPropertyRowMapper<Book> mapper = new BeanPropertyRowMapper<>(Book.class);
		List<Book> Books = jdbc.query(sql, namedParameters, mapper);

		if (Books.isEmpty()) {
			return null; 
		} else {
			return Books.get(0);
		}
	}

	public List<Review> getReviewsByBookId(Long bookId) {
		String sql = "SELECT * FROM reviews WHERE bookId = :bookId";
		MapSqlParameterSource params = new MapSqlParameterSource("bookId", bookId);

		return jdbc.query(sql, params, (q1, rowNum) -> {
			Review review = new Review();
			review.setId(q1.getLong("id"));
			review.setBookId(q1.getLong("bookId"));
			review.setText(q1.getString("text"));
			return review;
		});

	}

	public void addBook(Book book) {
		String sql = "INSERT INTO books (title, author) VALUES (:title, :author)";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue("title", book.getTitle())
				.addValue("author", book.getAuthor());

		int rowsInserted = jdbc.update(sql, namedParameters);

		if (rowsInserted > 0) {
			System.out.println("New book added!");
		}
	}

	public void addReview(Long bookId, String text) {
		String sql = "INSERT INTO reviews (bookId, text) VALUES (:bookId, :text)";
		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bookId", bookId).addValue("text",
				text);

		int rowsInserted = jdbc.update(sql, namedParameters);

		if (rowsInserted > 0) {
			System.out.println("New review added!");
		} else {
			System.out.println("Failed to add review!");
		}
	}

	public void addUser(String email, String encryptedPassword, boolean enabled) {
		String sql = "INSERT INTO users (email, encryptedpassword, enabled) VALUES (:email, :encryptedPassword, :enabled)";

		MapSqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email)
				.addValue("encryptedPassword", encryptedPassword).addValue("enabled", enabled);

		int rowsInserted = jdbc.update(sql, namedParameters);

		if (rowsInserted > 0) {
			System.out.println("New user added Successfully!");
		} else {
			System.out.println("Failed to add user to the list!");
		}
	}
}
