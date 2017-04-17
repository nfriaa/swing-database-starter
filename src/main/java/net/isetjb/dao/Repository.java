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

import java.util.ArrayList;

/**
 * Interface Repository to define methods to be implemented.
 *
 * @author Nafaa Friaa (nafaa.friaa@isetjb.rnu.tn)
 */
public interface Repository<T>
{
    /**
     * Find one item by id and return it if exist / else return null.
     *
     * @param id
     * @return
     */
    public T find(long id);

    /**
     * Find all items and return a list.
     *
     * @return
     */
    public ArrayList<T> findAll();

    /**
     * Create a new object and return it / else return null.
     *
     * @param obj
     * @return
     */
    public T create(T obj);

    /**
     * Update an existant object and return it / else return null.
     *
     * @param obj
     * @return
     */
    public T update(T obj);

    /**
     * Delete an item by id and return 1 on success.
     *
     * @param id
     * @return
     */
    public int delete(long id);
}
