/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

/**
 *
 * @author tiarn
 */

import DTOs.Movie;
import DTOs.Watched;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;


public class MySqlWatchedDao extends Daos.MySqlDao implements WatchedDaoInterface{
    
    @Override
    public Watched watchMovie(String username, int id) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
            try {
            con = this.getConnection();
            
       
            
            String query = "INSERT INTO watched(username,movieId) VALUES(?,?) ";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setInt(2, id);
            
            ps.execute();
            }
            catch (SQLException e) 
            {
                throw new DaoException("watchMovie() " + e.getMessage());
            }
            return null;
    }
    @Override
    public List<String> recommendMovie(String username) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> d = new ArrayList<>();
        
        
        try {
            con = this.getConnection();
            
           String query = "SELECT director, COUNT(director) AS director_count FROM movies,watched WHERE movies.id = watched.movieid and username = ? GROUP BY director ORDER BY director_count DESC LIMIT 1;";
             ps = con.prepareStatement(query);
             ps.setString(1, username);
             
             rs = ps.executeQuery();
             while (rs.next()) 
            {
                String director = rs.getString("director");
                
                d.add(director);
            }
         
            }
            catch (SQLException e) 
            {
                throw new DaoException("recommendMovie() " + e.getMessage());
            }
        return d;
    }

}
