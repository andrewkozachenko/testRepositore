package com.academysmart.bookagolf.encryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Club;
import com.academysmart.bookagolf.model.Equipment;
import com.academysmart.bookagolf.model.EquipmentSetItem;
import com.academysmart.bookagolf.model.Field;
import com.academysmart.bookagolf.model.Game;
import com.academysmart.bookagolf.model.Image;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.model.OrderToSend;
import com.academysmart.bookagolf.model.OrderedGame;
import com.academysmart.bookagolf.model.SpecialOffer;
import com.academysmart.bookagolf.model.User;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Provides interaction with API
 * 
 */
public class GolfApi {
	/**
	 * API host
	 */
	private final String HOST;
	/**
	 * email is used for authorization in API
	 */
	private final String EMAIL;
	/**
	 * password is used for authorization in API
	 */
	private final String PASSWORD;
	/**
	 * secret key is used in encryption algorithm
	 */
	private final String SECRET_KEY;
	// private static AESEncrypter encrypter;

	/**
	 * {@link BCAESEncrypter} instance to encrypt data
	 */
	private static BCAESEncrypter encrypter;

	/**
	 * {@link Gson} instance to JSON encode/decode data
	 */
	private static Gson gson;

	public GolfApi() throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		ApplicationConfigValues.loadConfigProperties();
		HOST = ApplicationConfigValues.HOST;
		EMAIL = ApplicationConfigValues.EMAIL;
		PASSWORD = ApplicationConfigValues.PASSWORD;
		SECRET_KEY = ApplicationConfigValues.SECRET_KEY;

		encrypter = new BCAESEncrypter(SECRET_KEY);
		gson = new GsonBuilder()
				.excludeFieldsWithModifiers(Modifier.STATIC)
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

	}

	public static void main(String[] args) throws Exception {

		// ApplicationConfigValues.loadConfigProperties();
		GolfApi golfApi = new GolfApi(); // this class instance

		// User user = golfApi.getUser(73);
		// System.out.println(user.getFullName());
		// List<Club> clubs = golfApi.getAllClubs();
		// System.out.println("Club objects:");
		// for (Club club : clubs) {
		// System.out.println(club);
		// }
		//
		// Club clubGot = golfApi.getClub(ApplicationConfigValues.CLUB_ID);
		// System.out.println("Single club: " + clubGot);

		// List<Field> fields = golfApi
		// .getClubFields(ApplicationConfigValues.CLUB_ID);
		// System.out.println("Club fields:");
		// for (Field field : fields) {
		// System.out.println(field);
		// }
		//
		// List<Equipment> equipments = golfApi
		// .getClubEquipment(ApplicationConfigValues.CLUB_ID);
		// System.out.println("Equipment: ");
		// for (Equipment equipment : equipments) {
		// System.out.println(equipment);
		// }
		//
		// List<Order> orders = golfApi
		// .getClubOrders(ApplicationConfigValues.CLUB_ID);
		// System.out.println("Orders:");
		// for (Order order : orders) {
		// System.out.println(order);
		// }
		//
		// List<Game> games = golfApi.getGames(ApplicationConfigValues.CLUB_ID,
		// 1056);
		// System.out.println("Games:");
		// for (Game game : games) {
		// System.out.println(game);
		// }
		//
		//
		// List<SpecialOffer> specialOffers = golfApi
		// .getSpecialOffers(ApplicationConfigValues.CLUB_ID);
		// System.out.println("Special offers: ");
		// for (SpecialOffer specialOffer : specialOffers) {
		// System.out.println(specialOffer);
		// }
		//
		// List<Image> images = golfApi.getImages(6);
		// System.out.println("Images: ");
		// for (Image image : images) {
		// System.out.println(image);
		// }

		// Order order = new Order();
		// order.setClubId(ApplicationConfigValues.CLUB_ID);
		// order.setCreatedAt(ApplicationConfigValues.API_DATE_TIME_FORMAT
		// .format(new Date()));
		// order.setDate("2013-10-02");
		// order.setEquipmentPrice(25.25);
		// List<EquipmentSetItem> equipmentSet = new
		// ArrayList<EquipmentSetItem>();
		// List<EquipmentSetItem> equipmentSet = null;
		// EquipmentSetItem equipmentSetItem = new EquipmentSetItem();
		// equipmentSetItem.setId(14L);
		// equipmentSetItem.setAmount(1);
		// equipmentSet.add(equipmentSetItem);
		// order.setEquipmentSet(equipmentSet);
		// order.setIdentifier("ident4");
		// order.setPaid(11);
		// order.setPrice(101);
		// order.setSite(false);
		// order.setStatus(Order.STATUS_PENDING);
		// order.setUserId(ApplicationConfigValues.USER_ID);
		// List<OrderedGame> orderedGames = new ArrayList<OrderedGame>();
		// OrderedGame orderedGame = new OrderedGame();
		// orderedGame.setStart(11);
		// orderedGame.setEnd(14);
		// orderedGame.setGameId(65L);
		// orderedGame.setGolfFieldId(1056L);
		// orderedGame.setPlayers(1);
		// orderedGame.setPrice(22.0);
		// orderedGame.setHoles(18);
		// orderedGames.add(orderedGame);
		// order.setOrderedGames(orderedGames);
		// order.setOrderedGames(new ArrayList<Game>());
		// order.getOrderedGames().add(GameProvider.INSTANCE.getGameById(65));
		// OrderToSend orderToSend = new OrderToSend(order);
		// orderToSend.setId(0L);
		// golfApi.addOrderToSend(ApplicationConfigValues.CLUB_ID, orderToSend,
		// order.getOrderedGames());
		// golfApi.makeAddOrderJson(ApplicationConfigValues.CLUB_ID,
		// orderToSend,
		// order.getOrderedGames());
		// golfApi.deleteOrder(ApplicationConfigValues.CLUB_ID, 582);
		// order.setId(223);
		// golfApi.makeClubIdAndObjectIdAndDataObjectJson(
		// ApplicationConfigValues.CLUB_ID, "order", orderToSend.getId()
		// .intValue(), orderToSend);

		// golfApi.updateOrder(ApplicationConfigValues.CLUB_ID, order);
		// golfApi.updateOrder(ApplicationConfigValues.CLUB_ID, order);
		// golfApi.deleteOrder(ApplicationConfigValues.CLUB_ID, 221);

		// Game game = new Game();
		// game.setCreatedAt(ApplicationConfigValues.API_DATE_TIME_FORMAT
		// .format(new Date()));
		// game.setFri(false);
		// game.setGameEnd(12);
		// game.setGameStart(9);
		// game.setGolfFieldId(34);
		// game.setMon(true);
		// game.setPlayers(1);
		// game.setPrice(123);
		// game.setSat(true);
		// game.setSun(false);
		// game.setThu(true);
		// game.setTue(false);
		// game.setWed(true);
		//
		// golfApi.addGame(ApplicationConfigValues.CLUB_ID, 34, game);

		// game.setId(33);
		// game.setWed(false);
		// golfApi.updateGame(ApplicationConfigValues.CLUB_ID, 34, game);

		// golfApi.deleteGame(ApplicationConfigValues.CLUB_ID, 34, 33);

		// Field field = new Field();
		// field.setClubId(ApplicationConfigValues.CLUB_ID);
		// field.setDescription("Awesome field");
		// // field.setId(37);
		// field.setName("new field");

		// golfApi.addField(ApplicationConfigValues.CLUB_ID, field);
		// golfApi.updateField(ApplicationConfigValues.CLUB_ID, field);
		// golfApi.deleteField(ApplicationConfigValues.CLUB_ID, 37);

		Equipment equipment = new Equipment();
		equipment.setName("new equipment");
		// equipment.setId(12);
		equipment.setPrice(122);
		golfApi.addClubEquipment(ApplicationConfigValues.CLUB_ID, equipment);
		// golfApi.updateClubEquipment(ApplicationConfigValues.CLUB_ID,
		// equipment);
		// golfApi.deleteClubEquipment(ApplicationConfigValues.CLUB_ID, 11);

		// SpecialOffer specialOffer = new SpecialOffer();
		// specialOffer.setClubId(ApplicationConfigValues.CLUB_ID);
		// specialOffer
		// .setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
		// specialOffer.setImportant(false);
		// specialOffer.setTitle("New offer");
		// specialOffer.setImageContentType("image/jpeg");
		// specialOffer
		// .setImageFileName("http://wallcapture.com/wp-content/uploads/2013/04/Golf-Wallpapers-HD-Download.jpg");
		// specialOffer.setImageFileSize(123456);
		// specialOffer
		// .setLinkToImage("http://wallcapture.com/wp-content/uploads/2013/04/Golf-Wallpapers-HD-Download.jpg");

		// golfApi.addSpecialOffer(ApplicationConfigValues.CLUB_ID,
		// specialOffer);

		// Club club = new Club();
		// club.set_private(false);
		// club.setAddress("Richardhof 248/2");
		// club.setCar(true);
		// club.setCity("Gumpoldskirchen");
		// club.setCountry("Austria");
		// club.setCurrency("USD");
		// club.setDescription("Description");
		// club.seteMail("bookagolf.test@gmail.com");
		// club.setHitsToWin(70);
		// club.setHolesList(18);
		// club.setLat(48.0558);
		// club.setLng(16.2753);
		// club.setMetaDescription("meta description");
		// club.setMetaKeywords("key, words");
		// club.setMetaTitle("title");
		// club.setName("new club2");
		// club.setPhone("43 (0) 7664-20712");
		// club.setRegion("Austria");
		// club.setShortDescription("short description");
		// club.setUrl("www.cc-golf.at");
		// club.setVisible(true);
		// club.setWorldRegion("Europe");
		// club.setId(38);
		// golfApi.addClub(club);
		// golfApi.updateClub(38, club);
		// golfApi.deleteClub(37);

		// System.out.println("Image base64: "
		// + golfApi.getImageBase64("D:/Vitalik/golf.jpg"));

		// golfApi.addImage(
		// ApplicationConfigValues.CLUB_ID,
		// "Golf-Wallpapers-HD-Download.jpg",
		// "http://wallcapture.com/wp-content/uploads/2013/04/Golf-Wallpapers-HD-Download.jpg");

		// System.out.println(golfApi.isAuthorized());
	}

	/**
	 * Builds URI for GET and DELETE requests that use email, password and data
	 * parameters
	 * 
	 * @param path
	 *            specifies relative path to API service
	 * @param data
	 *            specifies not encrypted data that will be encrypted and sent
	 *            as data parameter in GET or DELETE reqest
	 * @return URI to make GET or DELETE request
	 * @throws DataLengthException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private URI buildGetUri(String path, String data)
			throws DataLengthException, UnsupportedEncodingException,
			IllegalStateException, InvalidCipherTextException, IOException,
			URISyntaxException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(HOST).setPath(path)
				.setParameter("email", EMAIL)
				.setParameter("password", encrypter.encrypt(PASSWORD))
				.setParameter("data", encrypter.encrypt(data));
		URI uri = builder.build();
		return uri;
	}

	/**
	 * Builds URI for GET and DELETE requests that use email and password
	 * parameters
	 * 
	 * @param path
	 *            specifies relative path to API service
	 * @return URI to make GET or DELETE request
	 * @throws DataLengthException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private URI buildGetUri(String path) throws DataLengthException,
			UnsupportedEncodingException, IllegalStateException,
			InvalidCipherTextException, IOException, URISyntaxException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(HOST).setPath(path)
				.setParameter("email", EMAIL)
				.setParameter("password", encrypter.encrypt(PASSWORD));
		URI uri = builder.build();
		return uri;
	}

	/**
	 * Builds URI for POST and PUT requests that use email and password
	 * parameters
	 * 
	 * @param path
	 *            specifies relative path to API service
	 * @return URI to make POST or PUT request
	 * @throws URISyntaxException
	 */
	private URI buildUri(String path) throws URISyntaxException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(HOST).setPath(path);
		URI uri = builder.build();
		return uri;
	}

	/**
	 * Makes GET request to API service specified in uri
	 * 
	 * @param uri
	 *            specifies URI to make request
	 * @return response from API service
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String makeGetRequest(URI uri) throws ClientProtocolException,
			IOException {
		HttpGet getRequest = new HttpGet(uri);
		// System.out.println(getRequest.getURI());
		Loggers.FILE_LOGGER.debug(getRequest.getURI().toString());

		getRequest.addHeader("accept", "application/json");

		return processRequest(getRequest);
	}

	/**
	 * Makes POST request to API service specified in uri. Sends data specified
	 * in data parameter in request body
	 * 
	 * @param uri
	 *            specifies URI to make request
	 * @param data
	 *            specifies data to send in request body
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String makePostRequest(URI uri, String data)
			throws ClientProtocolException, IOException {
		HttpPost postRequest = new HttpPost(uri);
		StringEntity input = new StringEntity(data);
		input.setContentType("application/json");
		postRequest.setEntity(input);
		return processRequest(postRequest);
	}

	/**
	 * Makes PUT request to API service specified in uri. Sends data specified
	 * in data parameter in request body
	 * 
	 * @param uri
	 *            specifies URI to make request
	 * @param data
	 *            specifies data to send in request body
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String makePutRequest(URI uri, String data)
			throws ClientProtocolException, IOException {
		HttpPut putRequest = new HttpPut(uri);
		StringEntity input = new StringEntity(data);
		input.setContentType("application/json");
		putRequest.setEntity(input);
		return processRequest(putRequest);
	}

	/**
	 * Makes DELETE request to API service specified in uri
	 * 
	 * @param uri
	 *            specifies URI to make request
	 * @return response from API service
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String makeDeleteRequest(URI uri) throws ClientProtocolException,
			IOException {
		HttpDelete deleteRequest = new HttpDelete(uri);
		// System.out.println(deleteRequest.getURI());
		Loggers.FILE_LOGGER.debug(deleteRequest.getURI().toString());

		deleteRequest.addHeader("accept", "application/json");

		return processRequest(deleteRequest);
	}

	/**
	 * Sends request to API service
	 * 
	 * @param request
	 *            specifies {@link HttpUriRequest} instance to send
	 * @return response from service
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String processRequest(HttpUriRequest request)
			throws ClientProtocolException, IOException {

		// HostnameVerifier hostnameVerifier =
		// org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
		//
		// DefaultHttpClient client = new DefaultHttpClient();
		//
		// SchemeRegistry registry = new SchemeRegistry();
		// SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
		// socketFactory.setHostnameVerifier((X509HostnameVerifier)
		// hostnameVerifier);
		// registry.register(new Scheme("https", socketFactory, 443));
		// SingleClientConnManager mgr = new
		// SingleClientConnManager(client.getParams(), registry);
		// DefaultHttpClient httpClient = new DefaultHttpClient(mgr,
		// client.getParams());
		//
		// // Set verifier
		// HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpResponse response = httpClient.execute(request);
		// System.out.println("Response code: "
		// + response.getStatusLine().getStatusCode() + " "
		// + response.getStatusLine().getReasonPhrase());
		Loggers.FILE_LOGGER.debug("Response code: "
				+ response.getStatusLine().getStatusCode() + " "
				+ response.getStatusLine().getReasonPhrase());

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));

		String output;
		String fullOutput = "";
		// System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			// System.out.println(output);
			fullOutput += output;
		}
		Loggers.FILE_LOGGER.debug("Output from Server: " + fullOutput);

		httpClient.getConnectionManager().shutdown();
		return fullOutput;
	}

	/**
	 * Decodes encoded data, specified in data parameter in response from API
	 * service
	 * 
	 * @param responseFromServer
	 *            specifies string with response from API service
	 * @return decoded data parameter (from API service response) value
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 */
	private String getDecodedData(String responseFromServer)
			throws IOException, DataLengthException, IllegalStateException,
			InvalidCipherTextException {
		String encodedResponse = null;

		JsonReader jsonReader = new JsonReader(new StringReader(
				responseFromServer));
		// jsonReader.setLenient(true);
		jsonReader.beginObject();
		while (jsonReader.hasNext()) {
			String name = jsonReader.nextName();
			if (name.equals("data")) {
				encodedResponse = jsonReader.nextString();
			} else {
				jsonReader.skipValue();
			}
		}
		jsonReader.endObject();
		jsonReader.close();
		// System.out.println("Encoded response: " + encodedResponse);
		Loggers.FILE_LOGGER.debug("Encoded response: " + encodedResponse);

		String decodedResponse = encrypter.decrypt(encodedResponse);
		// System.out.println("Decoded response: " + decodedResponse);
		Loggers.FILE_LOGGER.debug("Decoded response: " + decodedResponse);

		return decodedResponse.trim();
	}

	/**
	 * Checks if API service returned error
	 * 
	 * @param responseFromServer
	 * @return true if API service returned error
	 */
	private boolean isApiError(String responseFromServer) {
		boolean error = true;
		JsonReader jsonReader = new JsonReader(new StringReader(
				responseFromServer));
		try {
			jsonReader.beginObject();
			while (jsonReader.hasNext()) {
				String name = jsonReader.nextName();
				if (name.equals("error")) {
					error = jsonReader.nextBoolean();
				} else {
					jsonReader.skipValue();
				}
			}
			jsonReader.endObject();
			jsonReader.close();
		} catch (IOException e) {
			// System.out.println("Error in response");
			Loggers.FILE_LOGGER.error("Error in response", e);
		}
		return error;
	}

	/**
	 * Retrieves list of all clubs via API
	 * 
	 * @return list of all clubs
	 * @throws DataLengthException
	 * @throws ClientProtocolException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public List<Club> getAllClubs() throws DataLengthException,
			ClientProtocolException, UnsupportedEncodingException,
			IllegalStateException, InvalidCipherTextException, IOException,
			URISyntaxException {
		String fullOutput = makeGetRequest(buildGetUri("/api/club/index"));
		String decodedResponse = getDecodedData(fullOutput);
		JsonParser parser = new JsonParser();
		JsonArray clubsArray = parser.parse(decodedResponse).getAsJsonArray();
		List<Club> clubs = new ArrayList<Club>();
		for (int i = 0; i < clubsArray.size(); i++) {
			Club club = gson.fromJson(clubsArray.get(i), Club.class);
			if (club != null) {
				clubs.add(club);
			}
		}
		return clubs;
	}

	/**
	 * Retrieves information about club with specified id
	 * 
	 * @param id
	 *            specifies club id to get information about
	 * @return {@link Club} instance with specified id
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws URISyntaxException
	 */
	public Club getClub(int id) throws IOException, DataLengthException,
			IllegalStateException, InvalidCipherTextException,
			URISyntaxException {
		String jsonObjectToSend = makeClubIdJson(id);

		String fullOutput = makeGetRequest(buildGetUri("/api/club/show",
				jsonObjectToSend));
		String decodedResponse = getDecodedData(fullOutput);
		Club club = gson.fromJson(decodedResponse, Club.class);

		return club;
	}

	/**
	 * Retrieves list of fields available in club with specified id via API
	 * 
	 * @param clubId
	 *            specifies club id which fields list will be retrieved
	 * @return list of available fields
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws URISyntaxException
	 */
	public List<Field> getClubFields(int clubId) throws IOException,
			DataLengthException, IllegalStateException,
			InvalidCipherTextException, URISyntaxException {
		String jsonObjectToSend = makeClubIdJson(clubId);

		String fullOutput = makeGetRequest(buildGetUri("/api/fields/index",
				jsonObjectToSend));
		String decodedResponse = getDecodedData(fullOutput);
		JsonParser parser = new JsonParser();
		JsonArray fieldsArray = parser.parse(decodedResponse).getAsJsonArray();
		List<Field> fields = new ArrayList<Field>();
		for (int i = 0; i < fieldsArray.size(); i++) {
			Field field = gson.fromJson(fieldsArray.get(i), Field.class);
			if (field != null) {
				fields.add(field);
			}
		}
		return fields;
	}

	/**
	 * Retrieves list of orders made in club with specified id
	 * 
	 * @param clubId
	 *            specifies club id which orders list will be retrieved
	 * @return list of orders made in club
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws URISyntaxException
	 */
	public List<Order> getClubOrders(int clubId) throws IOException,
			DataLengthException, IllegalStateException,
			InvalidCipherTextException, URISyntaxException {
		String jsonObjectToSend = makeClubIdJson(clubId);

		String fullOutput = makeGetRequest(buildGetUri("/api/orders/index",
				jsonObjectToSend));
		String decodedResponse = getDecodedData(fullOutput);

		JsonParser parser = new JsonParser();
		JsonArray ordersArray = parser.parse(decodedResponse).getAsJsonArray();
		List<Order> orders = new ArrayList<Order>();
		for (int i = 0; i < ordersArray.size(); i++) {
			Order order = gson.fromJson(ordersArray.get(i), Order.class);
			if (order != null) {
				orders.add(order);
			}
		}
		return orders;
	}

	/**
	 * Retrieves list of available games on field specified by fieldId
	 * 
	 * @param clubId
	 *            specifies club id which games list will be retrieved
	 * @param fieldId
	 *            specifies field id on which games take place
	 * @return list of games on specified field
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws URISyntaxException
	 */
	public List<Game> getGames(int clubId, int fieldId) throws IOException,
			DataLengthException, IllegalStateException,
			InvalidCipherTextException, URISyntaxException {
		String jsonObjectToSend = makeClubIdFieldIdJson(clubId, fieldId);

		String fullOutput = makeGetRequest(buildGetUri("/api/games/index",
				jsonObjectToSend));
		String decodedResponse = getDecodedData(fullOutput);
		JsonParser parser = new JsonParser();
		JsonArray gamesArray = parser.parse(decodedResponse).getAsJsonArray();
		List<Game> games = new ArrayList<Game>();
		for (int i = 0; i < gamesArray.size(); i++) {
			Game game = gson.fromJson(gamesArray.get(i), Game.class);
			if (game != null) {
				games.add(game);
			}
		}
		return games;
	}

	/**
	 * Retrieves list of equipment available in club with specified id
	 * 
	 * @param clubId
	 *            clubId specifies club id which equipment list will be
	 *            retrieved
	 * @return list of available equipment
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws URISyntaxException
	 */
	public List<Equipment> getClubEquipment(int clubId) throws IOException,
			DataLengthException, IllegalStateException,
			InvalidCipherTextException, URISyntaxException {
		String jsonObjectToSend = makeClubIdJson(clubId);

		String fullOutput = makeGetRequest(buildGetUri("/api/equipment/index",
				jsonObjectToSend));
		String decodedResponse = getDecodedData(fullOutput);
		JsonParser parser = new JsonParser();
		JsonArray equipmentArray = parser.parse(decodedResponse)
				.getAsJsonArray();
		List<Equipment> equipments = new ArrayList<Equipment>();
		for (int i = 0; i < equipmentArray.size(); i++) {
			Equipment equipment = gson.fromJson(equipmentArray.get(i),
					Equipment.class);
			if (equipment != null) {
				equipments.add(equipment);
			}
		}
		return equipments;
	}

	/**
	 * Retrieves list of special offers available in club with specified id
	 * 
	 * @param clubId
	 *            specifies club id which special offers list will be retrieved
	 * @return list of special offers
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws URISyntaxException
	 */
	public List<SpecialOffer> getSpecialOffers(int clubId) throws IOException,
			DataLengthException, IllegalStateException,
			InvalidCipherTextException, URISyntaxException {
		String jsonObjectToSend = makeClubIdJson(clubId);

		String fullOutput = makeGetRequest(buildGetUri(
				"/api/special_offer/index", jsonObjectToSend));
		String decodedResponse = getDecodedData(fullOutput);
		JsonParser parser = new JsonParser();
		JsonArray specialOffersArray = parser.parse(decodedResponse)
				.getAsJsonArray();
		List<SpecialOffer> specialOffers = new ArrayList<SpecialOffer>();
		for (int i = 0; i < specialOffersArray.size(); i++) {
			SpecialOffer specialOffer = gson.fromJson(
					specialOffersArray.get(i), SpecialOffer.class);
			if (specialOffer != null) {
				specialOffers.add(specialOffer);
			}
		}
		return specialOffers;
	}

	/**
	 * Retrieves list of images available in club with specified id
	 * 
	 * @param clubId
	 *            specifies club id which images list will be retrieved
	 * @return list of images available in club with specified id
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws URISyntaxException
	 */
	public List<Image> getImages(int clubId) throws IOException,
			DataLengthException, IllegalStateException,
			InvalidCipherTextException, URISyntaxException {
		String jsonObjectToSend = makeClubIdJson(clubId);

		String fullOutput = makeGetRequest(buildGetUri("/api/image/index",
				jsonObjectToSend));
		String decodedResponse = getDecodedData(fullOutput);
		JsonParser parser = new JsonParser();
		JsonArray imagesArray = parser.parse(decodedResponse).getAsJsonArray();
		List<Image> images = new ArrayList<Image>();
		for (int i = 0; i < imagesArray.size(); i++) {
			Image image = gson.fromJson(imagesArray.get(i), Image.class);
			if (image != null) {
				images.add(image);
			}
		}
		return images;
	}

	/**
	 * Retrieves information about {@link User} with specified id
	 * 
	 * @param userId
	 * @return {@link User} instance
	 * @throws DataLengthException
	 * @throws ClientProtocolException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public User getUser(int userId) throws DataLengthException,
			ClientProtocolException, UnsupportedEncodingException,
			IllegalStateException, InvalidCipherTextException, IOException,
			URISyntaxException {

		String jsonObjectToSend = makeUserIdJson(userId);
		String fullOutput = makeGetRequest(buildGetUri("/api/user/show",
				jsonObjectToSend));
		String decodedResponse = getDecodedData(fullOutput);
		User user = gson.fromJson(decodedResponse, User.class);
		return user;
	}

	/**
	 * Sends request to add {@link Club} via API
	 * 
	 * @param club
	 *            {@link Club} instance to add
	 * @return true if club was added successfully
	 */
	public boolean addClub(Club club) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdDataObjectJson(null, "club", club);
			String fullOutput = makePostRequest(buildUri("/api/club/create"),
					jsonObjectToSend);
			error = isApiError(fullOutput);
			String decodedResponse = getDecodedData(fullOutput);
			club.setId(Long.parseLong(decodedResponse));

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to update {@link Club} info via API
	 * 
	 * @param clubId
	 *            is necessary to make update request via API. Use clubId of
	 *            updated club
	 * @param club
	 *            {@link Club} instance to update
	 * @return true if club info was updated successfully
	 */
	public boolean updateClub(int clubId, Club club) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdDataObjectJson(clubId, "club", club);
			String fullOutput = makePutRequest(buildUri("/api/club/update"),
					jsonObjectToSend);
			error = isApiError(fullOutput);

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to delete club with specified id via API
	 * 
	 * @param clubId
	 *            specifies id of club to delete
	 * @return true if club was deleted successfully
	 */
	public boolean deleteClub(int clubId) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdObjectIdJson(clubId, null, null);
			String fullOutput = makeDeleteRequest(buildGetUri(
					"/api/club/delete", jsonObjectToSend));
			error = isApiError(fullOutput);
		} catch (IOException | DataLengthException | IllegalStateException
				| InvalidCipherTextException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}

		return !error;
	}

	/**
	 * Adds order via API. Sets order id to value returned from API. <b>Do not
	 * use this method to avoid errors in API</b>
	 * 
	 * @param clubId
	 * @param order
	 * @return true if order was added successfully
	 */
	@Deprecated
	public boolean addOrder(int clubId, Order order) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			if (order.getEquipmentSet() != null
					&& order.getEquipmentSet().isEmpty()) {
				order.getEquipmentSet().add(new EquipmentSetItem());
			}
			jsonObjectToSend = makeClubIdDataObjectJson(clubId, "order", order);
			String fullOutput = makePostRequest(buildUri("/api/orders/create"),
					jsonObjectToSend);
			error = isApiError(fullOutput);
			String decodedResponse = getDecodedData(fullOutput);
			order.setId(Long.parseLong(decodedResponse));

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Adds order via API. Uses {@link OrderToSend} instance to avoid errors
	 * with API.
	 * 
	 * @param clubId
	 * @param orderToSend
	 * @param orderedGames
	 * @return true if order was added successfully
	 */
	public boolean addOrderToSend(int clubId, OrderToSend orderToSend,
			List<OrderedGame> orderedGames) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			// if (orderToSend.getEquipmentSet() != null
			// && orderToSend.getEquipmentSet().isEmpty()) {
			// orderToSend.getEquipmentSet().add(new EquipmentSetItem());
			// }
			if (orderedGames == null) {
			}
			jsonObjectToSend = makeOrderJson(clubId, orderToSend, orderedGames);
			String fullOutput = makePostRequest(buildUri("/api/orders/create"),
					jsonObjectToSend);
			error = isApiError(fullOutput);
			// String decodedResponse = getDecodedData(fullOutput);
			// order.setId(Long.parseLong(decodedResponse));

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to update {@link Order} info via API
	 * 
	 * @param clubId
	 *            is necessary to make update request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param order
	 *            {@link Order} instance to update
	 * @return true if order was updated successfully
	 */
	public boolean updateOrder(int clubId, Order order) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			if (order.getEquipmentSet() != null
					&& order.getEquipmentSet().isEmpty()) {
				order.getEquipmentSet().add(new EquipmentSetItem());
			}
			// jsonObjectToSend = makeClubIdObjectIdDataObjectJson(clubId,
			// "order", order.getId().intValue(), order);
			jsonObjectToSend = makeOrderJson(clubId, order.getId().intValue(),
					order, order.getOrderedGames());
			String fullOutput = makePutRequest(buildUri("/api/orders/update"),
					jsonObjectToSend);
			error = isApiError(fullOutput);
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to delete order with specified id via API
	 * 
	 * @param clubId
	 *            is necessary to make delete request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param orderId
	 *            specifies id of order to delete
	 * @return true if order was deleted successfully
	 */
	public boolean deleteOrder(int clubId, int orderId) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdObjectIdJson(clubId, "order", orderId);
			String fullOutput = makeDeleteRequest(buildGetUri(
					"/api/orders/delete", jsonObjectToSend));
			error = isApiError(fullOutput);

		} catch (IOException | DataLengthException | IllegalStateException
				| InvalidCipherTextException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to add {@link Game} via API
	 * 
	 * @param clubId
	 *            is necessary to make add request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param fieldId
	 *            specifies id of field on which game takes place
	 * @param game
	 *            {@link Game} instance to add
	 * @return true if game was added successfully
	 */
	public boolean addGame(int clubId, int fieldId, Game game) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdFieldIdDataObjectJson(clubId, fieldId,
					"game", game);
			String fullOutput = makePostRequest(buildUri("/api/games/create"),
					jsonObjectToSend);
			error = isApiError(fullOutput);
			String decodedResponse = getDecodedData(fullOutput);
			game.setId(Long.parseLong(decodedResponse));

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to update {@link Game} info via API
	 * 
	 * @param clubId
	 *            is necessary to make update request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param fieldId
	 *            specifies id of field on which game takes place
	 * @param game
	 *            {@link Game} instance to update
	 * @return true if game was updated successfully
	 */
	public boolean updateGame(int clubId, int fieldId, Game game) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdFieldIdObjectIdDataObjectJson(clubId,
					fieldId, "game", (int) game.getId(), game);
			String fullOutput = makePutRequest(buildUri("/api/games/update"),
					jsonObjectToSend);
			error = isApiError(fullOutput);

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to delete game with specified id via API
	 * 
	 * @param clubId
	 *            is necessary to make delete request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param fieldId
	 *            specifies id of field on which game takes place
	 * @param gameId
	 *            specifies id of game to delete
	 * @return true if game was deleted successfully
	 */
	public boolean deleteGame(int clubId, int fieldId, int gameId) {
		boolean error = true;
		Writer jsonOut = new StringWriter();
		JsonWriter writer = new JsonWriter(jsonOut);
		try {
			writer.beginObject();
			writer.name("club_id").value(clubId);
			writer.name("field_id").value(fieldId);
			writer.name("game_id").value(gameId);
			writer.endObject();
			String jsonObjectToSend = jsonOut.toString();
			writer.close();
			jsonOut.close();
			// System.out.println("JSON: " + jsonObjectToSend);
			Loggers.FILE_LOGGER.debug("JSON: " + jsonObjectToSend);

			String fullOutput = makeDeleteRequest(buildGetUri(
					"/api/games/delete", jsonObjectToSend));
			error = isApiError(fullOutput);

		} catch (IOException | DataLengthException | IllegalStateException
				| InvalidCipherTextException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to add {@link Field} via API
	 * 
	 * @param clubId
	 *            is necessary to make add request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param field
	 *            {@link Field} instance to add
	 * @return true if field was added successfully
	 */
	public boolean addField(int clubId, Field field) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdDataObjectJson(clubId, "field", field);
			String fullOutput = makePostRequest(buildUri("/api/fields/create"),
					jsonObjectToSend);
			error = isApiError(fullOutput);
			String decodedResponse = getDecodedData(fullOutput);
			field.setId(Long.parseLong(decodedResponse));

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to update {@link Field} via API
	 * 
	 * @param clubId
	 *            is necessary to make update request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param field
	 *            {@link Field} instance to update
	 * @return true if field was updated successfully
	 */
	public boolean updateField(int clubId, Field field) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdObjectIdDataObjectJson(clubId,
					"field", (int) field.getId(), field);
			String fullOutput = makePutRequest(buildUri("/api/fields/update"),
					jsonObjectToSend);
			error = isApiError(fullOutput);
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to delete field with specified id via API
	 * 
	 * @param clubId
	 *            is necessary to make delete request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param fieldId
	 *            specifies id of field to delete
	 * @return true if field was deleted successfully
	 */
	public boolean deleteField(int clubId, int fieldId) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdObjectIdJson(clubId, "field", fieldId);
			String fullOutput = makeDeleteRequest(buildGetUri(
					"/api/fields/delete", jsonObjectToSend));
			error = isApiError(fullOutput);
		} catch (IOException | DataLengthException | IllegalStateException
				| InvalidCipherTextException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}

		return !error;
	}

	/**
	 * Sends request to add {@link Equipment} via API
	 * 
	 * @param clubId
	 *            is necessary to make add request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param equipment
	 *            {@link Equipment} instance to add
	 * @return true if equipment was added successfully
	 */
	public boolean addClubEquipment(int clubId, Equipment equipment) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdDataObjectJson(clubId, "equipment",
					equipment);
			String fullOutput = makePostRequest(
					buildUri("/api/equipment/create"), jsonObjectToSend);
			error = isApiError(fullOutput);
			String decodedResponse = getDecodedData(fullOutput);
			equipment.setId(Long.parseLong(decodedResponse));

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to update {@link Equipment} via API
	 * 
	 * @param clubId
	 *            is necessary to make update request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param equipment
	 *            {@link Equipment} instance to update
	 * @return true if equipment was updated successfully
	 */
	public boolean updateClubEquipment(int clubId, Equipment equipment) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdObjectIdDataObjectJson(clubId,
					"equipment", (int) equipment.getId(), equipment);
			String fullOutput = makePutRequest(
					buildUri("/api/equipment/update"), jsonObjectToSend);
			error = isApiError(fullOutput);

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to delete equipment with specified id via API
	 * 
	 * @param clubId
	 *            is necessary to make delete request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param equipmentId
	 *            specifies id of equipment to delete
	 * @return true if equipment was deleted successfully
	 */
	public boolean deleteClubEquipment(int clubId, int equipmentId) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdObjectIdJson(clubId, "equipment_set",
					equipmentId);
			String fullOutput = makeDeleteRequest(buildGetUri(
					"/api/equipment/delete", jsonObjectToSend));
			error = isApiError(fullOutput);
		} catch (IOException | DataLengthException | IllegalStateException
				| InvalidCipherTextException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to add {@link SpecialOffer} via API
	 * 
	 * @param clubId
	 *            is necessary to make add request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param specialOffer
	 *            {@link SpecialOffer} instance to add
	 * @return true if special offer was added successfully
	 */
	public boolean addSpecialOffer(int clubId, SpecialOffer specialOffer) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdDataObjectJson(clubId,
					"special_offer", specialOffer);
			String fullOutput = makePostRequest(
					buildUri("/api/special_offer/create"), jsonObjectToSend);
			error = isApiError(fullOutput);
			String decodedResponse = getDecodedData(fullOutput);
			specialOffer.setId(Long.parseLong(decodedResponse));
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to update {@link SpecialOffer} via API
	 * 
	 * @param clubId
	 *            is necessary to make update request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param specialOffer
	 *            {@link SpecialOffer} instance to update
	 * @return true if special offer was updated successfully
	 */
	public boolean updateSpecialOffer(int clubId, SpecialOffer specialOffer) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdObjectIdDataObjectJson(clubId,
					"special_offer", (int) specialOffer.getId(), specialOffer);
			String fullOutput = makePutRequest(
					buildUri("/api/special_offer/update"), jsonObjectToSend);
			error = isApiError(fullOutput);

		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Sends request to delete special offer with specified id via API
	 * 
	 * @param clubId
	 *            is necessary to make delete request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param specialOfferId
	 *            specifies id of special offer to delete
	 * @return true if special offer was deleted successfully
	 */
	public boolean deleteSpecialOffer(int clubId, int specialOfferId) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdObjectIdJson(clubId, "special_offers",
					specialOfferId);
			String fullOutput = makeDeleteRequest(buildGetUri(
					"/api/special_offer/delete", jsonObjectToSend));
			error = isApiError(fullOutput);

		} catch (IOException | DataLengthException | IllegalStateException
				| InvalidCipherTextException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}

		return !error;
	}

	/**
	 * Sends request to add image via API
	 * 
	 * @param clubId
	 *            is necessary to make add request via API. Use
	 *            {@link ApplicationConfigValues#CLUB_ID}
	 * @param name
	 *            specifies image filename
	 * @param pathToImage
	 *            specifies URL of original image
	 * @return true if image was added successfully
	 */
	public boolean addImage(int clubId, String name, String pathToImage) {
		boolean error = true;
		String jsonObjectToSend;
		try {
			Writer jsonOut = new StringWriter();
			JsonWriter writer = new JsonWriter(jsonOut);
			writer.beginObject();
			writer.name("email").value(EMAIL);
			writer.name("password").value(encrypter.encrypt(PASSWORD));
			Writer jsonDataOut = new StringWriter();
			JsonWriter dataWriter = new JsonWriter(jsonDataOut);

			dataWriter.beginObject();

			dataWriter.name("club_id").value(clubId);
			dataWriter.name("image");
			dataWriter.beginObject();
			dataWriter.name("name").value(name);
			dataWriter.name("link_to_file").value(pathToImage);
			dataWriter.endObject();
			dataWriter.endObject();
			String jsonDataObject = jsonDataOut.toString();
			dataWriter.close();
			jsonDataOut.close();
			// System.out.println("JSON data: " + jsonDataObject);
			Loggers.FILE_LOGGER.debug("JSON data: " + jsonDataObject);

			writer.name("data").value(encrypter.encrypt(jsonDataObject));
			writer.endObject();
			jsonObjectToSend = jsonOut.toString();

			writer.close();
			jsonOut.close();
			// System.out.println("JSON: " + jsonObjectToSend);
			Loggers.FILE_LOGGER.debug("JSON: " + jsonObjectToSend);

			String fullOutput = makePostRequest(buildUri("/api/image/create"),
					jsonObjectToSend);
			error = isApiError(fullOutput);
			// String decodedResponse = getDecodedData(fullOutput);
			// specialOffer.setId(Long.parseLong(decodedResponse));
		} catch (DataLengthException | IllegalStateException
				| InvalidCipherTextException | IOException | URISyntaxException e) {
			error = true;
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return !error;
	}

	/**
	 * Creates string containing JSON object with club_id field
	 * 
	 * @param clubId
	 *            specifies value for club_id field of JSON object.
	 * @return string containing JSON object
	 * @throws IOException
	 * @see #makeClubIdObjectIdJson(Integer, String, Integer)
	 */
	private String makeClubIdJson(int clubId) throws IOException {
		return makeClubIdObjectIdJson(clubId, null, null);
	}

	/**
	 * Creates string containing JSON object with club_id and field_id fields
	 * 
	 * @param clubId
	 *            specifies value for club_id field of JSON object.
	 * @param fieldId
	 *            specifies value for field_id field of JSON object.
	 * @return string containing JSON object
	 * @throws IOException
	 * @see #makeClubIdObjectIdJson(Integer, String, Integer)
	 */
	private String makeClubIdFieldIdJson(Integer clubId, Integer fieldId)
			throws IOException {
		return makeClubIdObjectIdJson(clubId, "field", fieldId);
	}

	/**
	 * Creates string containing JSON object with user_id field
	 * 
	 * @param userId
	 *            specifies value for user_id field of JSON object.
	 * @return string containing JSON object
	 * @throws IOException
	 * @see #makeClubIdObjectIdJson(Integer, String, Integer)
	 */
	private String makeUserIdJson(Integer userId) throws IOException {
		return makeClubIdObjectIdJson(null, "user", userId);
	}

	/**
	 * Creates string containing JSON object with club_id and
	 * <i>objectName</i>_id fields
	 * 
	 * @param clubId
	 *            specifies value for club_id field of JSON object. JSON object
	 *            will not contain club_id field if this parameter is null
	 * @param objectName
	 *            specifies name for field in JSON object. objectName will be
	 *            concatenated with "_id" to form JSON object field name
	 * @param objectId
	 *            specifies value for <i>objectName</i>_id field of JSON object.
	 *            JSON object will not contain <i>objectName</i>_id field if
	 *            this parameter is null
	 * @return string containing JSON object
	 * @throws IOException
	 */
	private String makeClubIdObjectIdJson(Integer clubId, String objectName,
			Integer objectId) throws IOException {
		Writer jsonOut = new StringWriter();
		JsonWriter writer = new JsonWriter(jsonOut);
		writer.beginObject();
		if (clubId != null) {
			writer.name("club_id").value(clubId);
		}
		if (objectId != null) {
			writer.name(objectName + "_id").value(objectId);
		}
		writer.endObject();
		String jsonObjectToSend = jsonOut.toString();
		writer.close();
		jsonOut.close();
		// System.out.println("JSON: " + jsonObjectToSend);
		Loggers.FILE_LOGGER.debug("JSON: " + jsonObjectToSend);
		return jsonObjectToSend;
	}

	/**
	 * <p>
	 * Creates string containing JSON object with email, password and data
	 * fields.
	 * </p>
	 * <p>
	 * email field contains {@link #EMAIL} value.<br>
	 * password field contains encrypted {@link #PASSWORD} value <br>
	 * data field contains encrypted JSON object with club_id and
	 * <i>dataParamName</i> fields
	 * </p>
	 * 
	 * @param clubId
	 *            specifies value for club_id field of data JSON object. data
	 *            JSON object will not contain club_id field if this parameter
	 *            is null
	 * @param dataParamName
	 *            specifies name for field in data JSON object. dataParamName
	 *            will be used to form <i>dataParamName</i>_id and
	 *            <i>dataParamName</i> field names
	 * @param dataObject
	 *            will be JSON encoded and used as value for
	 *            <i>dataParamName</i> field of data JSON object
	 * @return string containing JSON object
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 * @throws IOException
	 */
	private <T> String makeClubIdDataObjectJson(Integer clubId,
			String dataParamName, T dataObject) throws DataLengthException,
			IllegalStateException, InvalidCipherTextException, IOException {
		return makeClubIdFieldIdDataObjectJson(clubId, null, dataParamName,
				dataObject);
	}

	/**
	 * <p>
	 * Creates string containing JSON object with email, password and data
	 * fields.
	 * </p>
	 * <p>
	 * email field contains {@link #EMAIL} value.<br>
	 * password field contains encrypted {@link #PASSWORD} value <br>
	 * data field contains encrypted JSON object with club_id, field_id, and
	 * <i>dataParamName</i> fields
	 * </p>
	 * 
	 * @param clubId
	 *            specifies value for club_id field of data JSON object. data
	 *            JSON object will not contain club_id field if this parameter
	 *            is null
	 * @param fieldId
	 *            specifies value for field_id field of data JSON object. data
	 *            JSON object will not contain field_id field if this parameter
	 *            is null
	 * @param dataParamName
	 *            specifies name for field in data JSON object. dataParamName
	 *            will be used to form <i>dataParamName</i>_id and
	 *            <i>dataParamName</i> field names
	 * @param dataObject
	 *            will be JSON encoded and used as value for
	 *            <i>dataParamName</i> field of data JSON object
	 * @return string containing JSON object
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 */
	private <T> String makeClubIdFieldIdDataObjectJson(Integer clubId,
			Integer fieldId, String dataParamName, T dataObject)
			throws IOException, DataLengthException, IllegalStateException,
			InvalidCipherTextException {
		return makeClubIdFieldIdObjectIdDataObjectJson(clubId, fieldId,
				dataParamName, null, dataObject);
	}

	/**
	 * <p>
	 * Creates string containing JSON object with email, password and data
	 * fields.
	 * </p>
	 * <p>
	 * email field contains {@link #EMAIL} value.<br>
	 * password field contains encrypted {@link #PASSWORD} value <br>
	 * data field contains encrypted JSON object with club_id,
	 * <i>dataParamName</i>_id and <i>dataParamName</i> fields
	 * </p>
	 * 
	 * @param clubId
	 *            specifies value for club_id field of data JSON object. data
	 *            JSON object will not contain club_id field if this parameter
	 *            is null
	 * @param dataParamName
	 *            specifies name for field in data JSON object. dataParamName
	 *            will be used to form <i>dataParamName</i>_id and
	 *            <i>dataParamName</i> field names
	 * @param objectId
	 *            specifies value for <i>dataParamName</i>_id field of data JSON
	 *            object. data JSON object will not contain
	 *            <i>dataParamName</i>_id field if this parameter is null
	 * @param dataObject
	 *            will be JSON encoded and used as value for
	 *            <i>dataParamName</i> field of data JSON object
	 * @return string containing JSON object
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 */
	private <T> String makeClubIdObjectIdDataObjectJson(Integer clubId,
			String dataParamName, Integer objectId, T dataObject)
			throws IOException, DataLengthException, IllegalStateException,
			InvalidCipherTextException {
		return makeClubIdFieldIdObjectIdDataObjectJson(clubId, null,
				dataParamName, objectId, dataObject);

	}

	/**
	 * <p>
	 * Creates string containing JSON object with email, password and data
	 * fields.
	 * </p>
	 * <p>
	 * email field contains {@link #EMAIL} value.<br>
	 * password field contains encrypted {@link #PASSWORD} value <br>
	 * data field contains encrypted JSON object with club_id, field_id,
	 * <i>dataParamName</i>_id and <i>dataParamName</i> fields
	 * </p>
	 * 
	 * @param clubId
	 *            specifies value for club_id field of data JSON object. data
	 *            JSON object will not contain club_id field if this parameter
	 *            is null
	 * @param fieldId
	 *            specifies value for field_id field of data JSON object. data
	 *            JSON object will not contain field_id field if this parameter
	 *            is null
	 * @param dataParamName
	 *            specifies name for field in data JSON object. dataParamName
	 *            will be used to form <i>dataParamName</i>_id and
	 *            <i>dataParamName</i> field names
	 * @param objectId
	 *            specifies value for <i>dataParamName</i>_id field of data JSON
	 *            object. data JSON object will not contain
	 *            <i>dataParamName</i>_id field if this parameter is null
	 * @param dataObject
	 *            will be JSON encoded and used as value for
	 *            <i>dataParamName</i> field of data JSON object
	 * @return string containing JSON object
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 */
	private <T> String makeClubIdFieldIdObjectIdDataObjectJson(Integer clubId,
			Integer fieldId, String dataParamName, Integer objectId,
			T dataObject) throws IOException, DataLengthException,
			IllegalStateException, InvalidCipherTextException {
		Writer jsonOut = new StringWriter();
		JsonWriter writer = new JsonWriter(jsonOut);
		writer.beginObject();
		writer.name("email").value(EMAIL);
		writer.name("password").value(encrypter.encrypt(PASSWORD));
		Writer jsonDataOut = new StringWriter();
		JsonWriter dataWriter = new JsonWriter(jsonDataOut);

		dataWriter.beginObject();
		if (clubId != null) {
			dataWriter.name("club_id").value(clubId);
		}
		if (fieldId != null) {
			dataWriter.name("field_id").value(fieldId);
		}
		if (objectId != null) {
			dataWriter.name(dataParamName + "_id").value(objectId);
		}
		dataWriter.name(dataParamName);
		gson.toJson(gson.toJsonTree(dataObject), dataWriter);
		dataWriter.endObject();
		String jsonDataObject = jsonDataOut.toString();
		dataWriter.close();
		jsonDataOut.close();
		// System.out.println("JSON data: " + jsonDataObject);
		Loggers.FILE_LOGGER.debug("JSON data: " + jsonDataObject);

		writer.name("data").value(encrypter.encrypt(jsonDataObject));
		writer.endObject();
		String jsonObjectToSend = jsonOut.toString();
		writer.close();
		jsonOut.close();
		// System.out.println("JSON: " + jsonObjectToSend);
		Loggers.FILE_LOGGER.debug("JSON: " + jsonObjectToSend);
		return jsonObjectToSend;
	}

	/**
	 * <p>
	 * Creates string containing JSON object with email, password and data
	 * fields.
	 * </p>
	 * <p>
	 * email field contains {@link #EMAIL} value.<br>
	 * password field contains encrypted {@link #PASSWORD} value <br>
	 * data field contains encrypted JSON object with club_id, order,
	 * ordered_games fields
	 * </p>
	 * 
	 * @param clubId
	 *            specifies value for club_id field of data JSON object.
	 * @param orderId
	 * @param order
	 * @param orderedGames
	 * @return string containing JSON object
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 */
	private <T> String makeOrderJson(Integer clubId, Integer orderId, T order,
			List<OrderedGame> orderedGames) throws IOException,
			DataLengthException, IllegalStateException,
			InvalidCipherTextException {
		Writer jsonOut = new StringWriter();
		JsonWriter writer = new JsonWriter(jsonOut);
		writer.beginObject();
		writer.name("email").value(EMAIL);
		writer.name("password").value(encrypter.encrypt(PASSWORD));
		Writer jsonDataOut = new StringWriter();
		JsonWriter dataWriter = new JsonWriter(jsonDataOut);

		dataWriter.beginObject();
		dataWriter.name("club_id").value(clubId);
		if (orderId != null) {
			dataWriter.name("order_id").value(orderId);
		}
		dataWriter.name("order");
		gson.toJson(gson.toJsonTree(order), dataWriter);
		dataWriter.name("ordered_games");
		gson.toJson(gson.toJsonTree(orderedGames != null ? orderedGames
				: new ArrayList<OrderedGame>()), dataWriter);
		dataWriter.endObject();
		String jsonDataObject = jsonDataOut.toString();
		dataWriter.close();
		jsonDataOut.close();
		// System.out.println("JSON data: " + jsonDataObject);
		Loggers.FILE_LOGGER.debug("JSON data: " + jsonDataObject);

		writer.name("data").value(encrypter.encrypt(jsonDataObject));
		writer.endObject();
		String jsonObjectToSend = jsonOut.toString();
		writer.close();
		jsonOut.close();
		// System.out.println("JSON: " + jsonObjectToSend);
		Loggers.FILE_LOGGER.debug("JSON: " + jsonObjectToSend);
		return jsonObjectToSend;
	}

	/**
	 * <p>
	 * Creates string containing JSON object with email, password and data
	 * fields.
	 * </p>
	 * <p>
	 * email field contains {@link #EMAIL} value.<br>
	 * password field contains encrypted {@link #PASSWORD} value <br>
	 * data field contains encrypted JSON object with club_id, order,
	 * ordered_games fields
	 * </p>
	 * 
	 * @param clubId
	 *            specifies value for club_id field of data JSON object.
	 * @param order
	 * @param orderedGames
	 * @return string containing JSON object
	 * @throws IOException
	 * @throws DataLengthException
	 * @throws IllegalStateException
	 * @throws InvalidCipherTextException
	 */
	private <T> String makeOrderJson(Integer clubId, T order,
			List<OrderedGame> orderedGames) throws IOException,
			DataLengthException, IllegalStateException,
			InvalidCipherTextException {
		return makeOrderJson(clubId, null, order, orderedGames);
	}

	// private String getImageBase64(String pathToImage) throws IOException {
	// Path path = Paths.get(pathToImage);
	// byte[] imageBytes = Files.readAllBytes(path);
	// Base64 base64 = new Base64();
	//
	// String imageBase64 = new String(base64.encode(imageBytes), "UTF-8");
	// return imageBase64;
	// }

	/**
	 * Checks if user is authorized to use API by sending request to
	 * /api/club/show
	 * 
	 * @return true if authorization succeeded
	 */
	public boolean isAuthorized() {
		// TODO find a better way to check auth
		boolean isAuthorized = false;
		String jsonObjectToSend;
		try {
			jsonObjectToSend = makeClubIdJson(ApplicationConfigValues.CLUB_ID);
			String fullOutput = makeGetRequest(buildGetUri("/api/club/show",
					jsonObjectToSend));
			isAuthorized = !isApiError(fullOutput);
		} catch (IOException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		} catch (DataLengthException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		} catch (IllegalStateException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		} catch (URISyntaxException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e);
			// e.printStackTrace();
		}
		return isAuthorized;
	}
}
