package com.warrior.gen.database;

import com.warrior.gen.model.Config;
import com.warrior.gen.model.TableMeta;
import org.apache.commons.lang.StringUtils;
import java.sql.*;

public class DBHelper {

    public static DBHelper dbHelper;

    private Connection connection;
    private DatabaseMetaData metaData;

    private DBHelper(Config config) {
        try {
            Class.forName(config.getDriverClass());
            connection = DriverManager.getConnection(config.getUrl(), config.getUserName(), config.getPassWord());
            metaData = connection.getMetaData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static DBHelper getInstance(Config config) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(config);
        }
        return dbHelper;
    }

    public TableMeta getTableMeta(String tableName) throws SQLException {
        TableMeta tableMeta = new TableMeta();
        ResultSet rst = metaData.getTables(null, "%", tableName, new String[]{"TABLE"});
        if (rst.next()){
            tableMeta.setTableName(rst.getString("TABLE_NAME"));
        }
        rst = metaData.getPrimaryKeys(null,null,tableName);
        String primaryKey = "";
        if (rst.next()){
            primaryKey = rst.getString("COLUMN_NAME");
        }
        rst = metaData.getColumns(null, "%", tableName, "%");
        while(rst.next()){
            if(StringUtils.equals(primaryKey,rst.getString("COLUMN_NAME"))){
                tableMeta.addAttribute(true,rst.getInt("DATA_TYPE"),rst.getString("COLUMN_NAME"),rst.getString("REMARKS"));
            }else{
                tableMeta.addAttribute(false,rst.getInt("DATA_TYPE"),rst.getString("COLUMN_NAME"),rst.getString("REMARKS"));
            }
        }
        return tableMeta;
    }

}
