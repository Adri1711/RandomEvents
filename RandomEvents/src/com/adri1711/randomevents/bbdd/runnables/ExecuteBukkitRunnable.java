package com.adri1711.randomevents.bbdd.runnables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Nullable;
import javax.sql.DataSource;

import org.bukkit.scheduler.BukkitRunnable;

import com.adri1711.randomevents.bbdd.callback.Callback;

public class ExecuteBukkitRunnable extends BukkitRunnable {
    private final DataSource dataSource;
    private final String statement;
    private final Callback<Boolean, SQLException> callback;
    
    public ExecuteBukkitRunnable(DataSource dataSource, String statement, @Nullable Callback<Boolean, SQLException> callback) {
        if (dataSource == null) {
            //TODO: IllegalArgumentException
        }
        
        if (statement == null) {
            //TODO: IllegalArgumentException
        }
        
        this.dataSource = dataSource;
        this.statement = statement;
        this.callback = callback;
    }
    
    @Override
    public void run() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(statement);
            
            if (callback != null) {
                callback.call(preparedStatement.execute(), null);
            }
        } catch (SQLException e) {
            if (callback != null) {
                callback.call(null, e);
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}