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
package net.isetjb.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import net.isetjb.dao.DAOConnection;
import net.isetjb.dao.Repository;
import org.apache.log4j.Logger;

/**
 * The Product repository implementation.
 *
 * @author Nafaa Friaa (nafaa.friaa@isetjb.rnu.tn)
 */
public class ProductRepository implements Repository<ProductBean>
{
    final static Logger log = Logger.getLogger(ProductRepository.class);

    /**
     * Find one item by id.
     *
     * @param id
     * @return
     */
    @Override
    public ProductBean find(long id)
    {
        log.debug("Start method...");

        ProductBean obj = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM products WHERE id=?");

            prepared.setLong(1, id);

            ResultSet result = prepared.executeQuery();

            if (result.first())
            {
                obj = map(result);
            }

        } catch (SQLException e)
        {
            log.error("Error finding product : " + e);
        }

        log.debug("End method.");

        return obj;

    }

    /**
     * Find all items.
     *
     * @return
     */
    @Override
    public ArrayList<ProductBean> findAll()
    {
        log.debug("Start method...");

        ArrayList<ProductBean> products = new ArrayList<>();

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM products");

            ResultSet result = prepared.executeQuery();

            while (result.next())
            {
                products.add(map(result));
            }

        } catch (SQLException e)
        {
            log.error("Error finding products : " + e);
        }

        log.debug("End method.");

        return products;
    }

    /**
     * Create new Object and return this new Object if success. Run only on
     * tables with auto_increment id column.
     *
     * @param obj
     * @return
     */
    @Override
    public ProductBean create(ProductBean obj)
    {
        log.debug("Start method...");

        ProductBean objectToReturn = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " INSERT INTO products (name, price, enabled) "
                    + " VALUES(?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

            prepared.setString(1, obj.getName());
            prepared.setDouble(2, obj.getPrice());
            prepared.setInt(3, obj.getEnabled());

            // execute query and get the affected rows number :
            int affectedRows = prepared.executeUpdate();
            if (affectedRows != 0)
            {
                // get the latest inserted id :
                ResultSet generatedKeys = prepared.getGeneratedKeys();
                if (generatedKeys.next())
                {
                    log.debug("Inserted id : " + generatedKeys.getLong(1));
                    objectToReturn = this.find(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e)
        {
            log.error("Error creating new product : " + e);
        }

        log.debug("End method.");

        return objectToReturn;
    }

    /**
     * Update a record.
     *
     * @param obj
     * @return
     */
    @Override
    public ProductBean update(ProductBean obj)
    {
        log.debug("Start method...");

        ProductBean objectToReturn = null;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " UPDATE products "
                    + " SET name=?, "
                    + " price=?, "
                    + " enabled=? "
                    + " WHERE id=? ");

            prepared.setString(1, obj.getName());
            prepared.setDouble(2, obj.getPrice());
            prepared.setInt(3, obj.getEnabled());
            prepared.setLong(4, obj.getId());

            // execute query and get the affected rows number :
            int affectedRows = prepared.executeUpdate();
            if (affectedRows != 0)
            {
                log.debug("Updated id : " + obj.getId());
                objectToReturn = this.find(obj.getId());
            }

        } catch (SQLException e)
        {
            log.error("Error updating product : " + e);
        }

        log.debug("End method.");

        return objectToReturn;
    }

    /**
     * Delete a single record.
     *
     * @param id
     * @return the number of affected rows.
     */
    @Override
    public int delete(long id)
    {
        log.debug("Start method...");

        int affectedRows = 0;

        try
        {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " DELETE FROM products "
                    + " WHERE id=? ");

            prepared.setLong(1, id);

            // execute query and get the affected rows number :
            affectedRows = prepared.executeUpdate();

        } catch (SQLException e)
        {
            log.error("Error deleteing product : " + e);
        }

        log.debug("End method.");

        return affectedRows;
    }

    /**
     * Map the current row of the given ResultSet to an Object.
     *
     * @param resultSet
     * @return The mapped Object from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static ProductBean map(ResultSet resultSet) throws SQLException
    {
        ProductBean obj = new ProductBean();

        obj.setId(resultSet.getLong("id"));
        obj.setName(resultSet.getString("name"));
        obj.setPrice(resultSet.getDouble("price"));
        obj.setEnabled(resultSet.getInt("enabled"));

        return obj;
    }
}
