package org.apache.cassandra.db;

import java.sql.*;
import java.io.IOException;

import org.apache.cassandra.db.filter.*;

public class MySQLInstance extends DBInstance {
    
    Connection conn;
    PreparedStatement pstInsert, pstUpdate, pstMultiInsert;
    
    int debug = 0;
    
    int multiMax = 100;
    int multiCount = 0;
    String bsql;
    final String PREFIX = "_";
    
    public MySQLInstance(String ksName, String cfName) {
        this.ksName = ksName;
        this.cfName = PREFIX + cfName;
        conn = new MySQLConfigure().connect(ksName);
        /*try {
             conn.setAutoCommit(false);
        } catch(SQLException e) {
             System.out.println(e);
    	}*/
        
        try {
            pstInsert = conn.prepareStatement("INSERT INTO "+cfName+" (Row_Key, ColumnFamily) VALUES (?,?) ON DUPLICATE KEY UPDATE ColumnFamily = ?");
            if(ksName.equals("system")) {
                pstUpdate = conn.prepareStatement("UPDATE "+cfName+" SET ColumnFamily = ? WHERE Row_Key = ?");
            } else {
                pstUpdate = conn.prepareStatement("CALL set_row(?,?)");
            }
            bsql = "INSERT INTO "+ cfName + " (Row_Key, ColumnFamily) VALUES";
            for(int i=0; i< multiMax; i++) {
                bsql += " (?, ?)";
                if(i < multiMax - 1) {
                    bsql += ",";
                }
            }
                
            pstMultiInsert = conn.prepareStatement(bsql);
        } catch (SQLException e) {
            System.err.println("db prepare state error "+ e);
        }
    }
    
    public int put(String rowKey, ColumnFamily cf) throws SQLException, IOException {
        ColumnFamily oldcf = get(rowKey, null);
        if(oldcf != null) {
            return update(rowKey, cf, oldcf);
        } else {
            return insert(rowKey, cf);
        }
    }

    
    int insert(String rowKey, ColumnFamily cf) throws SQLException {
        try {
            return doInsert(rowKey, cf.toBytes());
            // return doMultipleInsert(rowKey, cfValue);
        } catch (SQLException e) {
            System.out.println("db connection error "+ e);
            return -1;
        }
    }
    
    int update(String rowKey, ColumnFamily newcf, ColumnFamily cf) throws SQLException, IOException {
        try {
            return doUpdate(rowKey, mergeColumnFamily(cf, newcf));
        } catch (SQLException e) {
            System.out.println("db update error: "+ e);
            return -1;
        }
    }
    
    public int delete(String table, String columnName, String columnValue) throws SQLException {
        PreparedStatement pstDelete = conn.prepareStatement("DELETE FROM "+table+" WHERE Row_Key = ?");
        try {
            pstDelete.setString(1, columnValue);
            return pstDelete.executeUpdate();
        } finally {
            pstDelete.close();
        }
    }
    
    public ColumnFamily get(String rowKey, QueryFilter filter) throws SQLException, IOException {
        try {
            return bytes2ColumnFamily(doSelect(rowKey));
        } catch (SQLException e) {
            System.err.println("db get error "+ e);
            return null;
        }
    }
    
    // Init MySQL Table for Keyspaces
    public int create(int rowKeySize, int columnFamilySize, String columnFamilyType, String storageEngineType) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            
            if(debug > 0) {
                stmt.executeUpdate("TRUNCATE TABLE " + cfName);
            }
            
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            while(rs.next()) {
                if(rs.getString(1).equals(cfName)) {
                    return 0;
                }
            }
            
            String sPrepareSQL = "CREATE Table "+ cfName + "(" +
                "`Row_Key` VARCHAR(?) NOT NULL," +
                "`ColumnFamily` VARBINARY(?)," +
                "PRIMARY KEY (`Row_Key`)" +
            ") ENGINE = " + storageEngineType;
            
            PreparedStatement pst = conn.prepareStatement(sPrepareSQL);
            pst.setInt(1,rowKeySize);
            pst.setInt(2,columnFamilySize);
            
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("db connection error "+ e);
            return -1;
        }
    }
    /*
    int doMultipleInsert(String rowKey, byte[] cfValue) throws SQLException {
        if(multiCount < multiMax) {
            pstMultiInsert.setString(2*multiCount+1, rowKey);
            pstMultiInsert.setBytes(2*multiCount+2, cfValue);
            multiCount++;
        }
        if(multiCount == multiMax) {
            multiCount = 0;
            pstMultiInsert.addBatch();
            //pstMultiInsert = conn.prepareStatement(bsql);
            return 1;
        }
        return 1;
    }
    */
    synchronized int doInsert(String rowKey, byte[] cfValue) throws SQLException {
        pstInsert.setString(1, rowKey);
        pstInsert.setBytes(2, cfValue);
        pstInsert.setBytes(3, cfValue);
        return pstInsert.executeUpdate();
    }
    
    byte[] doSelect(String rowKey) throws SQLException {
        //Connection conn1 = new MySQLConfigure().connect(ksName);
        PreparedStatement pstSelect;
        if(ksName.equals("system")) {
            pstSelect = conn.prepareStatement("SELECT ColumnFamily FROM " + cfName + " WHERE Row_Key = ?");
        } else {
            pstSelect = conn.prepareStatement("CALL get_row(?)");
        }
        pstSelect.setString(1, rowKey);
        ResultSet rs = pstSelect.executeQuery();
        byte[] b = null;
        if(rs != null) {
        	while(rs.next()) {
        		b = rs.getBytes(1);
        	}
        }
        rs.close();
        pstSelect.close();
        //conn1.close();
        return b;
    }

    synchronized int doUpdate(String rowKey, byte[] cfValue) throws SQLException {
        pstUpdate.setBytes(1, cfValue);
        pstUpdate.setString(2, rowKey);
        return pstUpdate.executeUpdate();
    }
}
