package com.federicofeliziani.minecraftTest;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.Closeable;
import java.sql.*;
import java.util.GregorianCalendar;

/**
 * Created by f3l1x on 31/08/14.
 */
public class Database {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public Database(FileConfiguration config) throws Exception {
        try {
            String databaseName = config.get(Tokens.DATABASE_NAME.toString()).toString();
            // this will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // setup the connection with the DB.
            connect = DriverManager
                    .getConnection("jdbc:mysql://"+ config.get(Tokens.DATABASE_IP.toString()) +"/" + databaseName + "?"
                            + "user=" + config.get(Tokens.DATABASE_USER.toString()) + "&password=" + config.get(Tokens.DATABASE_PASSWORD.toString()));

            // statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // resultSet gets the result of the SQL query
            resultSet = statement
                    .executeQuery("SHOW TABLES LIKE `MEP_USERS`");
            writeResultSet(resultSet);

            preparedStatement = connect
                    .prepareStatement("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = ? and table_name = ??");
            preparedStatement.setString(1, databaseName);
            preparedStatement.setString(2, Tokens.TABLE_PREFIX.toString());
            preparedStatement.setString(3, Tokens.USERS_TABLE.toString());
            resultSet = preparedStatement.executeQuery();


            // preparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
            // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
            // parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(new GregorianCalendar(2000,0,1).getTime().getTime()));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // remove again the insert comment
            preparedStatement = connect
                    .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement
                    .executeQuery("select * from FEEDBACK.COMMENTS");
            writeMetaData(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }
    private void writeMetaData(ResultSet resultSet) throws SQLException {
        // now get some metadata from the database
        System.out.println("The columns in the table are: ");
        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // resultSet is initialised before the first data set
        while (resultSet.next()) {
            // it is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g., resultSet.getSTring(2);
            String user = resultSet.getString("myuser");
            String website = resultSet.getString("webpage");
            String summary = resultSet.getString("summary");
            Date date = resultSet.getDate("datum");
            String comment = resultSet.getString("comments");
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
            System.out.println("Summary: " + summary);
            System.out.println("Date: " + date);
            System.out.println("Comment: " + comment);
        }
    }

    // you need to close all three to make sure
    private void close() {
        try{
            resultSet.close();
            statement.close();
            connect.close();
        }
        catch(Exception ex) {

        }
    }
}