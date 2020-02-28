package B2BService;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner; // Import the Scanner class

import pt.fredcardoso.icm.dao.ProductDAO;
import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.model.Multimedia;
import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.ProductForm;

public class B2Bservice {

	private String token;
	private String userId;
	private ArrayList<Product> products;

	Scanner myObj = new Scanner(System.in);

	public B2Bservice() {
		this.token = "";
		this.userId = "";
		products = new ArrayList<Product>();
	}

	public void start() {
		while (true) {

			System.out.println("Choose your option:\n");
			if (token.isEmpty()) {
				System.out.println("1-> Login");
			} else {
				System.out.println("1-> Logout");
				System.out.println("2-> Add Products");
				System.out.println("3-> View Products");

			}
			System.out.println("0-> Exit");
			System.out.println();

			String userInput = myObj.nextLine();
			if (token.isEmpty()) {
				if (userInput.equals("1")) {
					token = loginTrial();
				} else if (userInput.equals("0")) {
					System.exit(0);
				}
			} else {
				switch (userInput) {
				case "1":
					token = "";
					System.out.println("Logged out sucessfully!");
					System.out.println();
					break;
				case "2":
					System.out.println("Create Products: \n");
					createProducts();
					break;
				case "3":
					try {
						String produtos = sendGetViewProducts();
						System.out.println("Your products are: \n");
						products.forEach((p) -> printProduct(p));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "0":
					System.exit(0);
				}

			}
		}
	}

	private String loginTrial() {
		System.out.println("What's your username?");
		String username = myObj.nextLine();
		System.out.println("What's your password?");
		String password = myObj.nextLine();
		// form parameters
		Map<Object, Object> data = new HashMap<>();
		data.put("username", username);
		data.put("password", password);
		try {
			token = sendPostLogin("http://localhost:8080/b2bservice/Login", data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!token.isEmpty())
			System.out.println("Welcome, logged in sucessfuly");
		else
			System.out.println("Failed to authenticate.");

		return token;
	}

	private void createProducts() {
		while (true) {
			System.out.println("1-> Add product to list" + "\n2-> View Products in the list"
					+ "\n3-> Insert product list" + "\n0-> Exit");
			String userInput = myObj.nextLine();
			if (userInput.equals("0"))
				break;
			switch (userInput) {
			case "1":
				addProductToMemory();
				break;
			case "2":
				viewProductsInMemory();
				break;
			case "3":
				insertProducts();
				break;
			case "0":
				break;
			}
		}

	}

	private void insertProducts() {
		// TODO Auto-generated method stub

	}

	private void addProductToMemory() {
		// TODO Auto-generated method stub

	}

	private void viewProductsInMemory() {

	}

	private void printProduct(Product p) {
		System.out
				.println(p.getId() + p.getName() + p.getDescription() + p.getMinimumSalePrice() + p.getAuctionPeriod());
	}

	public Product create(ProductForm productForm, User user) {
		Product product = new Product();
		product.setName(productForm.getName());
		product.setDescription(productForm.getDescription());
		product.setMinimumSalePrice(productForm.getMinimumSalePrice());
		product.setAuctionPeriod(productForm.getAuctionPeriod());
		product.setAwardMode(productForm.getAwardMode());
		product.setUser(user);

		return product;
	}

	private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	private String sendGetViewProducts() throws Exception {
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("http://localhost:8080/b2bservice/viewProducts"))
				.setHeader("User-Agent", "Java 11 HttpClient Bot").setHeader("userId", userId).build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.headers().firstValue("response").get());
		return response.headers().firstValue("response").get();

	}

	private String sendPostLogin(String endPoint, Map<Object, Object> data) throws Exception {

		HttpRequest request = HttpRequest.newBuilder().POST(buildFormDataFromMap(data)).uri(URI.create(endPoint))
				.setHeader("User-Agent", "Java 11 HttpClient Bot") // add
																	// request
																	// header
				.header("Content-Type", "application/x-www-form-urlencoded")
				.setHeader("username", data.get("username").toString())
				.setHeader("password", data.get("password").toString()).build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		userId = response.headers().firstValue("userId").get();
		return response.headers().firstValue("token").get();

		// print response body
		// System.out.println(response.body());

	}

	private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<Object, Object> entry : data.entrySet()) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
			builder.append("=");
			builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
		}

		return HttpRequest.BodyPublishers.ofString(builder.toString());
	}

	public static void main(String[] args) throws Exception {
		B2Bservice newB2BService = new B2Bservice();
		newB2BService.start();

	}

}