/*
 * The MIT License
 *
 * Copyright 2017 Nafaa Friaa (nafaa.friaa@isetjb.rnu.tn).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.isetjb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.isetjb.config.PROP;
import org.apache.log4j.Logger;

/**
 * Singleton with lazy init.
 *
 * @author Nafaa Friaa (nafaa.friaa@isetjb.rnu.tn)
 */
public class DAOConnection
{
    final static Logger log = Logger.getLogger(DAOConnection.class);

    private static String driver = PROP.getProperty("db.driver");

    private static String url = PROP.getProperty("db.url");

    private static String user = PROP.getProperty("db.user");

    private static String password = PROP.getProperty("db.password");

    private static Connection connection;

    /**
     * Private constructor so this class cannot be instantiated only by it self.
     */
    private DAOConnection()
    {
    }

    /**
     * Create and return the Connection if not exist.
     *
     * @return The connection object
     */
    public static Connection getInstance()
    {
        if (connection == null)
        {
            try
            {
                Class.forName(driver);
            } catch (ClassNotFoundException e)
            {
                log.error("DB Driver error : " + e);
            }

            try
            {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e)
            {
                log.error("DB Connection error : " + e);
            }
        }

        return connection;
    }
}
