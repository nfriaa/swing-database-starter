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

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.isetjb.config.I18N;
import org.apache.log4j.Logger;

/**
 * Class used to display a dialog with add / edit form.
 *
 * @author Nafaa Friaa (nafaa.friaa@isetjb.rnu.tn)
 */
public class ProductDialog extends JDialog
{
    final static Logger log = Logger.getLogger(ProductDialog.class);

    JLabel jLabelId = new JLabel(I18N.lang("productframe.jLabelId"));
    JLabel jLabelName = new JLabel(I18N.lang("productframe.jLabelName"));
    JLabel jLabelPrice = new JLabel(I18N.lang("productframe.jLabelPrice"));
    JLabel jLabelEnabled = new JLabel(I18N.lang("productframe.jLabelEnabled"));

    JTextField jTextFieldId = new JTextField(40);
    JTextField jTextFieldName = new JTextField(40);
    JTextField jTextFieldPrice = new JTextField(40);
    JCheckBox jCheckBoxEnabled = new JCheckBox();

    JButton jButtonSave = new JButton(I18N.lang("productframe.jButtonSave"));
    JButton jButtonCancel = new JButton(I18N.lang("productframe.jButtonCancel"));

    boolean isNew;
    ProductBean productToEdit;

    /**
     * Constructor to call super first and then init isNew and productToEdit
     * attributes.
     *
     * @param owner
     * @param title
     * @param modal
     * @param isNew
     * @param productToEdit
     */
    public ProductDialog(Frame owner, String title, boolean modal, boolean isNew, ProductBean productToEdit)
    {
        super(owner, title, modal);

        log.debug("START constructor...");

        // set this param globals to use in other methods :
        this.isNew = isNew;
        this.productToEdit = productToEdit;

        setLocation(new Random().nextInt(150), new Random().nextInt(150));
        setSize(350, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // new :
        if (isNew)
        {
            setLayout(new GridLayout(4, 2));
        } // modification :
        else
        {
            setLayout(new GridLayout(5, 2));
            getContentPane().add(jLabelId);
            getContentPane().add(jTextFieldId);
            jTextFieldId.setEditable(false);

            // write values in form :
            jTextFieldId.setText("" + productToEdit.getId());
            jTextFieldName.setText(productToEdit.getName());
            jTextFieldPrice.setText("" + productToEdit.getPrice());
            jCheckBoxEnabled.setSelected((productToEdit.getEnabled() == 1) ? true : false);
        }

        getContentPane().add(jLabelName);
        getContentPane().add(jTextFieldName);

        getContentPane().add(jLabelPrice);
        getContentPane().add(jTextFieldPrice);

        getContentPane().add(jLabelEnabled);
        getContentPane().add(jCheckBoxEnabled);

        getContentPane().add(jButtonSave);
        jButtonSave.addActionListener((ActionEvent ev) ->
        {
            jButtonSaveActionPerformed(ev);
        });

        getContentPane().add(jButtonCancel);
        jButtonCancel.addActionListener((ActionEvent ev) ->
        {
            jButtonCancelActionPerformed(ev);
        });

        log.debug("End of constructor.");

        setVisible(true);
    }

    public void jButtonSaveActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        ProductRepository productRepository = new ProductRepository();
        ProductBean productBean = new ProductBean();

        productBean.setName(jTextFieldName.getText());
        productBean.setPrice(Double.parseDouble(jTextFieldPrice.getText()));
        productBean.setEnabled(jCheckBoxEnabled.isSelected() ? 1 : 0);

        if (this.isNew)
        {
            if (productRepository.create(productBean) != null)
            {
                this.dispose();
            }
        } else
        {
            productBean.setId(this.productToEdit.getId());
            if (productRepository.update(productBean) != null)
            {
                this.dispose();
            }
        }
    }

    public void jButtonCancelActionPerformed(ActionEvent ev)
    {
        log.debug("ActionEvent on " + ev.getActionCommand());

        this.dispose();
    }

}
