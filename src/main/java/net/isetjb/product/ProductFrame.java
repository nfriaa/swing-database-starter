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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.isetjb.config.I18N;
import org.apache.log4j.Logger;

/**
 * Class used to display products list.
 *
 * @author Nafaa Friaa (nafaa.friaa@isetjb.rnu.tn)
 */
public class ProductFrame extends JInternalFrame
{
    final static Logger log = Logger.getLogger(ProductFrame.class);

    JPanel jPanelHeader = new JPanel();
    JLabel jLabel1 = new JLabel(I18N.lang("productframe.jLabel1"));
    JButton jButtonDelete = new JButton(I18N.lang("productframe.jButtonDelete"));
    JButton jButtonAdd = new JButton(I18N.lang("productframe.jButtonAdd"));
    JButton jButtonEdit = new JButton(I18N.lang("productframe.jButtonEdit"));
    public JTable jTable1;

    /**
     * Constructor.
     */
    public ProductFrame()
    {
        log.debug("START constructor...");

        setTitle(I18N.lang("productframe.title"));
        setLocation(new Random().nextInt(100), new Random().nextInt(100));
        setSize(550, 350);
        setVisible(false);
        setClosable(true);
        setIconifiable(true);
        //setMaximizable(false);
        //setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        // header :
        jPanelHeader.setBorder(BorderFactory.createTitledBorder(I18N.lang("productframe.jPanelHeader")));

        jPanelHeader.add(jButtonDelete);
        jButtonDelete.addActionListener((ActionEvent ev) ->
        {
            jButtonDeleteActionPerformed(ev);
        });

        jPanelHeader.add(jButtonAdd);
        jButtonAdd.addActionListener((ActionEvent ev) ->
        {
            jButtonAddActionPerformed(ev);
        });

        jPanelHeader.add(jButtonEdit);
        jButtonEdit.addActionListener((ActionEvent ev) ->
        {
            jButtonEditActionPerformed(ev);
        });

        getContentPane().add(jPanelHeader, BorderLayout.NORTH);

        // Table :
        jTable1 = new JTable(this.getData());
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setDefaultEditor(Object.class, null);

        getContentPane().add(new JScrollPane(jTable1), BorderLayout.CENTER);

        log.debug("End of constructor.");
    }

    /**
     * Method to get the data from Repository and return it in DefaultTableModel
     * object. Very useful for refreshing JTable after data modification.
     *
     * @return
     */
    public DefaultTableModel getData()
    {
        log.debug("Start method...");

        // Comumns :
        String[] columns = new String[]
        {
            I18N.lang("productframe.jtable1.coloumn.id"),
            I18N.lang("productframe.jtable1.coloumn.name"),
            I18N.lang("productframe.jtable1.coloumn.price"),
            I18N.lang("productframe.jtable1.coloumn.enabled")
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // get data rows :
        ProductRepository productRepository = new ProductRepository();
        ArrayList<ProductBean> products = productRepository.findAll();

        // transform ArrayList<> to Object[][] :
        for (ProductBean product : products)
        {
            model.addRow(new Object[]
            {
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getEnabled()
            });
        }

        log.debug("End method.");

        return model;
    }

    public void jButtonDeleteActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        log.debug("selectedRowCount : " + jTable1.getSelectedRowCount());

        if (jTable1.getSelectedRowCount() > 0)
        {
            ProductRepository productRepository = new ProductRepository();
            int[] selectedRows = jTable1.getSelectedRows();
            for (int index = 0; index < selectedRows.length; index++)
            {
                log.debug("Delete row with id=" + jTable1.getValueAt(selectedRows[index], 0));
                productRepository.delete((long) jTable1.getValueAt(selectedRows[index], 0));
            }

            // refresh the Table Model :
            jTable1.setModel(this.getData());
        }
    }

    public void jButtonAddActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        new ProductDialog(null, I18N.lang("productdialog.addtitle"), true, true, null);

        // refresh the Table Model :
        jTable1.setModel(this.getData());
    }

    public void jButtonEditActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        if (jTable1.getSelectedRowCount() > 0)
        {
            long product_id = (long) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            log.debug("Trying to edit product with id : " + product_id);

            ProductRepository productRepository = new ProductRepository();

            new ProductDialog(null, I18N.lang("productdialog.edittitle"), true, false, productRepository.find(product_id));

            // refresh the Table Model :
            jTable1.setModel(this.getData());
        }
    }
}
