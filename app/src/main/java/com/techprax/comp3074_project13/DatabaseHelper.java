package com.techprax.comp3074_project13;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "TRAVELAPP";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    //requires API level 16 and above
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + "AIRLINE");
        db.execSQL("DROP TABLE IF EXISTS " + "FLIGHT");
        db.execSQL("DROP TABLE IF EXISTS " + "SEAT");
        db.execSQL("DROP TABLE IF EXISTS " + "FLIGHTCLASS");
        db.execSQL("DROP TABLE IF EXISTS " + "CLIENT");
        db.execSQL("DROP TABLE IF EXISTS " + "ACCOUNT");
        db.execSQL("DROP TABLE IF EXISTS " + "ITINERARY");

        updateDatabase(db, oldVersion, newVersion);

    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 1) {

            //create tables here
            db.execSQL(createAirline());
            db.execSQL(createFlight());
            db.execSQL(createSeat());
            db.execSQL(createFlightClass());
            db.execSQL(createClient());
            db.execSQL(createAccount());
            db.execSQL(createItinerary());

            //insert default data fro testing

            insertAirline(db, "Air Canada");
            insertAirline(db, "Air France");
            insertAirline(db, "Air Transat");
            insertAirline(db, "Alitalia");
            insertAirline(db, "Austrian");
            insertAirline(db, "Delta");
            insertAirline(db, "Emirates");
            insertAirline(db, "InterJet");
            insertAirline(db, "Lufthansa");
            insertAirline(db, "United");
            insertAirline(db, "WestJet");

            insertFlight(db, "Toronto", "Ottawa", "2017-12-20", "2017-12-20", "10:10", "12:10", 200.00, 1);
            insertFlight(db, "Toronto", "Ottawa", "2017-12-20", "2017-12-20", "10:10", "12:10", 150.00, 2);
            insertFlight(db, "Toronto", "Ottawa", "2017-12-20", "2017-12-20", "11:10", "12:10", 350.00, 3);
            insertFlight(db, "Toronto", "Ottawa", "2017-12-20", "2017-12-20", "09:10", "12:10", 250.00, 4);
            insertFlight(db, "Toronto", "Ottawa", "2017-12-20", "2017-12-20", "10:10", "12:10", 100.00, 5);
            insertFlight(db, "Ottawa", "Toronto", "2017-12-21", "2017-12-20", "10:10", "12:10", 120.00, 6);
            insertFlight(db, "Edmonton", "Winnipeg", "2017-12-20", "2017-12-20", "10:10", "12:10", 300.00, 3);
            insertFlight(db, "Montreal", "Edmonton", "2017-12-20", "2017-12-20", "10:10", "12:10", 350.00, 4);
            insertFlight(db, "NewYork", "Edmonton", "2017-12-20", "2017-12-20", "09:10", "12:10", 185.00, 5);
            insertFlight(db, "Quebec City", "NewYork", "2017-12-20", "2017-12-20", "11:10", "12:10", 250.00, 6);
            insertFlight(db, "British Colombia", "Nova Scotia", "2017-12-20", "2017-12-20", "10:10", "12:10", 360.00, 7);
            insertFlight(db, "Los Angeles", "Ottawa", "2017-12-20", "2017-12-20", "10:10", "12:10", 350.00, 8);
            insertFlight(db, "Winnipeg", "Toronto", "2017-12-20", "2017-12-20", "09:10", "12:10", 350.00, 9);
            insertFlight(db, "Nova Scotia", "NewYork", "2017-12-20", "2017-12-20", "10:10", "12:10", 350.00, 10);

            insertSeat(db, "F", 1, 1);
            insertSeat(db, "F", 2, 1);
            insertSeat(db, "F", 3, 1);
            insertSeat(db, "F", 4, 1);
            insertSeat(db, "F", 5, 1);
            insertSeat(db, "F", 6, 1);

            insertFlightClass(db, "Economy");
            insertFlightClass(db, "Business");

            insertClient(db, "John", "Doe", "4164121000", "5412547854125963");

            insertAccount(db, "john@gmail.com", "password", 1);

            db.execSQL(updateFlight());
            db.execSQL(updateSeatNumber());

        }


    }

    public String createAirline() {
        return "CREATE TABLE AIRLINE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "AIRLINENAME TEXT);";
    }

    public String createFlight() {
        return "CREATE TABLE FLIGHT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHTNUMBER INTEGER, " +
                "ORIGIN TEXT, " +
                "DESTINATION TEXT, " +
                "DEPARTUREDATE DATE, " +
                "ARRIVALDATE DATE, " +
                "DEPARTURETIME TIME, " +
                "ARRIVALTIME TIME, " +
                "FLIGHTDURATION TIME, " +
                "FARE REAL, " +
                "FLIGHT_AIRLINE INTEGER, " +
                "FOREIGN KEY(FLIGHT_AIRLINE) REFERENCES AIRLINE(_id));";
    }

    public String createSeat() {
        return "CREATE TABLE SEAT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SEATNUMBER INTEGER, " +
                "SEAT_FLIGHT INTEGER, " +
                "STATUS TEXT, " +
                "SEAT_FLIGHTCLASS INTEGER, " +
                "FOREIGN KEY(SEAT_FLIGHT) REFERENCES FLIGHT(_id)," +
                "FOREIGN KEY(SEAT_FLIGHTCLASS) REFERENCES FLIGHTCLASS(_id));";
    }

    public String createFlightClass() {
        return "CREATE TABLE FLIGHTCLASS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHTCLASSNAME TEXT);";
    }

    public String createClient() {
        return "CREATE TABLE CLIENT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FIRSTNAME TEXT, " +
                "LASTNAME TEXT, " +
                "PHONE TEXT, " +
                "CREDITCARD TEXT, " +
                "IMAGE BLOB);";
    }

    public String createAccount() {
        return "CREATE TABLE ACCOUNT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, " +
                "PASSWORD TEXT, " +
                "ACCOUNT_CLIENT INTEGER, " +
                "FOREIGN KEY (ACCOUNT_CLIENT) REFERENCES CLIENT(_id));";
    }

    public String createItinerary() {
        return "CREATE TABLE ITINERARY (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TIMESTAMP DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')), " +
                "ITINERARY_CLIENT INTEGER, " +
                "ITINERARY_FLIGHT INTEGER, " +
                "TRAVELLER INTEGER, " +
                "FOREIGN KEY(ITINERARY_CLIENT) REFERENCES CLIENT(_id), " +
                "FOREIGN KEY(ITINERARY_FLIGHT) REFERENCES FLIGHT(_id));";
    }

    public String updateFlight() {
        return "UPDATE FLIGHT SET FLIGHTDURATION = ((strftime('%s',ARRIVALTIME) - strftime('%s', DEPARTURETIME)) / 60)/60, " +
                "FLIGHTNUMBER = _id + 10000";
    }

    public String updateSeatNumber(){
        return "UPDATE SEAT SET SEATNUMBER = _id + 100";
    }

    public void insertAirline(SQLiteDatabase db, String airlineName) {
        ContentValues airlineValues = new ContentValues();
        airlineValues.put("AIRLINENAME", airlineName);
        db.insert("AIRLINE", null, airlineValues);
    }

    public void insertFlight(SQLiteDatabase db, String origin, String destination, String departureDate, String arrivalDate, String departureTime, String arrivalTime, Double fare, int airlineID) {
        ContentValues flightValues = new ContentValues();
        flightValues.put("ORIGIN", origin);
        flightValues.put("DESTINATION", destination);
        flightValues.put("DEPARTUREDATE", departureDate);
        flightValues.put("ARRIVALDATE", arrivalDate);
        flightValues.put("DEPARTURETIME", departureTime);
        flightValues.put("ARRIVALTIME", arrivalTime);
        flightValues.put("FARE", fare);
        flightValues.put("FLIGHT_AIRLINE", airlineID);
        db.insert("FLIGHT", null, flightValues);
    }

    public void insertSeat(SQLiteDatabase db, String status, int flightID, int flightClassID) {
        ContentValues seatValues = new ContentValues();
        seatValues.put("STATUS", status);
        seatValues.put("SEAT_FLIGHT", flightID);
        seatValues.put("SEAT_FLIGHTCLASS", flightClassID);
        db.insert("SEAT", null, seatValues);
    }

    public void insertFlightClass(SQLiteDatabase db, String flightClassName) {
        ContentValues flightClassValues = new ContentValues();
        flightClassValues.put("FLIGHTCLASSNAME", flightClassName);
        db.insert("FLIGHTCLASS", null, flightClassValues);
    }

    public static void insertClient(SQLiteDatabase db, String firstName, String lastName, String phone, String creditCard) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRSTNAME", HelperUtilities.capitalize(firstName.toLowerCase()));
        clientValues.put("LASTNAME", HelperUtilities.capitalize(lastName.toLowerCase()));
        clientValues.put("PHONE", phone);
        clientValues.put("CREDITCARD", creditCard);
        db.insert("CLIENT", null, clientValues);
    }

    public static void insertAccount(SQLiteDatabase db, String email, String password, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        accountValues.put("PASSWORD", password);
        accountValues.put("ACCOUNT_CLIENT", clientID);
        db.insert("ACCOUNT", null, accountValues);
    }

    public static void insertItinerary(SQLiteDatabase db, int flightID, int clientID, int traveller) {
        ContentValues itineraryValues = new ContentValues();
        itineraryValues.put("ITINERARY_FLIGHT", flightID);
        itineraryValues.put("ITINERARY_CLIENT", clientID);
        itineraryValues.put("TRAVELLER", traveller);
        db.insert("ITINERARY", null, itineraryValues);
    }

    public static Cursor selectFlight(SQLiteDatabase db, int flightID) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, ORIGIN, DESTINATION, DEPARTUREDATE, ARRIVALDATE, DEPARTURETIME, " +
                " ARRIVALTIME, FLIGHTDURATION, FARE, AIRLINENAME, SEATNUMBER, FLIGHTCLASSNAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHTCLASS " +
                "ON SEAT.SEAT_FLIGHTCLASS = FLIGHTCLASS._id " +
                "WHERE FLIGHT._id = '" + flightID + "'", null);
    }

    public static Cursor getItineraryDetail(SQLiteDatabase db, int flightID) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, ORIGIN, DESTINATION, DEPARTUREDATE, ARRIVALDATE, DEPARTURETIME, " +
                " ARRIVALTIME, FLIGHTDURATION, FARE, AIRLINENAME, SEATNUMBER, FLIGHTCLASSNAME, TRAVELLER " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHTCLASS " +
                "ON SEAT.SEAT_FLIGHTCLASS = FLIGHTCLASS._id " +
                "JOIN ITINERARY " +
                "ON ITINERARY.ITINERARY_FLIGHT = FLIGHT._id " +
                "WHERE FLIGHT._id = '" + flightID + "'", null);
    }

    public static Cursor selectFlight(SQLiteDatabase db, String origin, String destination,
                                      String departureDate, String flightClass) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, ORIGIN, DESTINATION, DEPARTUREDATE, ARRIVALDATE, DEPARTURETIME, " +
                " ARRIVALTIME, FLIGHTDURATION, FARE, AIRLINENAME, SEATNUMBER, FLIGHTCLASSNAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHTCLASS " +
                "ON SEAT.SEAT_FLIGHTCLASS = FLIGHTCLASS._id " +
                "WHERE ORIGIN = '" + origin +
                "' AND DESTINATION = '" + destination +
                "' AND DEPARTUREDATE = '" + departureDate +
                "' AND FLIGHTCLASSNAME = '" + flightClass +
                "' AND SEAT.STATUS = 'F' ", null);
    }


    public static Cursor selectFlight(SQLiteDatabase db, String origin, String destination,
                                      String departureDate, String flightClass, String orderBy) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, ORIGIN, DESTINATION, DEPARTUREDATE, ARRIVALDATE, DEPARTURETIME, " +
                " ARRIVALTIME, FLIGHTDURATION, FARE, AIRLINENAME, SEATNUMBER, FLIGHTCLASSNAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHTCLASS " +
                "ON SEAT.SEAT_FLIGHTCLASS = FLIGHTCLASS._id " +
                "WHERE ORIGIN = '" + origin +
                "' AND DESTINATION = '" + destination +
                "' AND DEPARTUREDATE = '" + departureDate +
                "' AND FLIGHTCLASSNAME = '" + flightClass +
                "' AND SEAT.STATUS = 'F' " +
                "ORDER BY " + orderBy + " ASC", null);
    }

    public static Cursor selectItinerary(SQLiteDatabase db) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, ORIGIN, DESTINATION, DEPARTUREDATE, ARRIVALDATE, DEPARTURETIME, " +
                " ARRIVALTIME, FLIGHTDURATION, FARE, AIRLINENAME, SEATNUMBER, FLIGHTCLASSNAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN ITINERARY " +
                "ON  FLIGHT._id = ITINERARY.ITINERARY_FLIGHT " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHTCLASS " +
                "ON SEAT.SEAT_FLIGHTCLASS = FLIGHTCLASS._id ", null);
    }

    public static void deleteItinerary(SQLiteDatabase db, int itineraryID) {
        db.delete("ITINERARY", " _id = ? ", new String[]{String.valueOf(itineraryID)});
    }

    public static Cursor selectItinerary(SQLiteDatabase db, int flightID) {
        return db.query("ITINERARY", null, " ITINERARY.ITINERARY_FLIGHT = ? ",
                new String[]{String.valueOf(flightID)}, null, null, null, null);
    }

    public static Cursor login(SQLiteDatabase db, String email, String password) {
        return db.query("ACCOUNT", new String[]{"_id", "EMAIL", "PASSWORD", "ACCOUNT_CLIENT"},
                "EMAIL = ? AND PASSWORD = ? ", new String[]{email, password},
                null, null, null, null);
    }

    public static void deleteAccount(SQLiteDatabase db, String clientID) {
        db.delete("CLIENT", "_id = ? ", new String[]{clientID});
        db.delete("ACCOUNT", "_id = ? ", new String[]{clientID});
        db.delete("ITINERARY", "_id = ? ", new String[]{clientID});
    }

    public static void updateClientImage(SQLiteDatabase db, byte[] image, String id) {
        ContentValues employeeValues = new ContentValues();
        employeeValues.put("IMAGE", image);
        db.update("CLIENT", employeeValues, " _id = ? ", new String[]{id});
    }

    public static void updatePassword(SQLiteDatabase db, String password, String id) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("PASSWORD", password);
        db.update("ACCOUNT", clientValues, " _id = ? ", new String[]{id});
    }

    public static Cursor selectImage(SQLiteDatabase db, int clientID) {
        return db.query("CLIENT", new String[]{"IMAGE"}, "_id = ? ",
                new String[]{Integer.toString(clientID)}, null, null,
                null, null);
    }


    public static Cursor selectClientPassword(SQLiteDatabase db, int clientID) {
        return db.query("ACCOUNT", new String[]{"PASSWORD"}, "_id = ? ",
                new String[]{Integer.toString(clientID)}, null, null,
                null, null);
    }

    public static void updateClient(SQLiteDatabase db, String firstName, String lastName,
                                    String phone, String creditCard, int clientID) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRSTNAME", HelperUtilities.capitalize(firstName.toLowerCase()));
        clientValues.put("LASTNAME", HelperUtilities.capitalize(lastName.toLowerCase()));
        clientValues.put("PHONE", phone);
        clientValues.put("CREDITCARD", creditCard);
        db.update("CLIENT", clientValues, "_id = ?", new String[]{String.valueOf(clientID)});
    }

    public static void updateAccount(SQLiteDatabase db, String email, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        db.update("ACCOUNT", accountValues, " ACCOUNT_CLIENT = ?",
                new String[]{String.valueOf(clientID)});
    }

    public static Cursor selectClientID(SQLiteDatabase db, String firstName, String lastName,
                                        String phone, String creditCard) {
        return db.query("CLIENT", new String[]{"_id"},
                "FIRSTNAME = ? AND LASTNAME = ? AND PHONE = ? AND CREDITCARD = ? ",
                new String[]{firstName, lastName, phone, creditCard},
                null, null, null, null);
    }

    public static Cursor selectClientJoinAccount(SQLiteDatabase db, int clientID) {
        return db.rawQuery("SELECT FIRSTNAME, LASTNAME, PHONE, CREDITCARD, EMAIL FROM CLIENT " +
                "JOIN ACCOUNT " +
                "ON CLIENT._id = ACCOUNT.ACCOUNT_CLIENT " +
                "WHERE " +
                "CLIENT._id = '" + clientID + "'", null);
    }

    public static Cursor selectClient(SQLiteDatabase db, int clientID) {
        return db.query("CLIENT", null, " _id = ? ",
                new String[]{String.valueOf(clientID)}, null, null, null, null);
    }

    public String dateDifference() {
        return "SELECT " +
                "cast( " +
                "         (" +
                "                  strftime('%s',t.ARRIVALTIME)-strftime('%s',t.DEPARTURETIME)" +
                "         ) AS real " +
                "      )/60/60 AS elapsed " +
                "FROM FLIGHT AS t;";
    }

}
