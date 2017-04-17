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

import java.io.Serializable;

/**
 * The Product Entity / Model Class.
 *
 * @author Nafaa Friaa (nafaa.friaa@isetjb.rnu.tn)
 */
public class ProductBean implements Serializable
{
    private long id;
    private String Name;
    private Double price;
    private int enabled;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public int getEnabled()
    {
        return enabled;
    }

    public void setEnabled(int enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public String toString()
    {
        return "ProductBean{" + "id=" + id + ", Name=" + Name + ", price=" + price + ", enabled=" + enabled + '}';
    }

}
