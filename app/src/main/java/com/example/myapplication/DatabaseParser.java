package com.example.myapplication;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseParser {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/jawadrizvi/Documents/HackEd2020/data/ca_videos.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public List<DataEntry>  pieChartData() {
        List<DataEntry> data = new ArrayList<>();

//        Connection connection = connect();
//        Statement stmt = connection.createStatement();
//        String sql = "SELECT id, name, capacity FROM warehouses";

        data.add(new ValueDataEntry("Apples", 6371664));
        data.add(new ValueDataEntry("Pears", 789622));
        data.add(new ValueDataEntry("Bananas", 7216301));
        data.add(new ValueDataEntry("Grapes", 1486621));
        data.add(new ValueDataEntry("Oranges", 1200000));

        return data;
    }





    /**
     * select all rows in the warehouses table
     */
    public void selectAll(){
        String sql = "SELECT category_id from FROM ca_videos;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
