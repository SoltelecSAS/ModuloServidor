/*
 *  JCalendarDemo.java - Demonstration of JCalendar Java Bean
 *  Copyright (C) 2004 Kai Toedter
 *  kai@toedter.com
 *  www.toedter.com
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package com.soltelec.servidor.utils;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;



/**
 * A demonstration Applet for the JCalendar bean. The demo can also be started as Java application.
 *
 * @author Kai Toedter
 * @version 1.2
 */
public class JCalendarDemo extends JFrame {
    private JSplitPane splitPane;
    private JPanel calendarPanel;
    private JComponent[] beans;

    private JTitlePanel componentTitlePanel;
    private JPanel componentPanel;


    /**
     * Initializes the applet.
     */
    public void init() {
        // Set the JGoodies Plastic 3D look and feel
        initializeLookAndFeels();

        // initialize all beans to demo
        beans = new JComponent[1];
        beans[0] = new JDateChooser();


        getContentPane().setLayout(new BorderLayout());


        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        splitPane.setDividerSize(4);

        BasicSplitPaneDivider divider = ((BasicSplitPaneUI) splitPane.getUI()).getDivider();

        if (divider != null) {
            divider.setBorder(null);
        }


        componentPanel = new JPanel();



        componentTitlePanel = new JTitlePanel("Component", null, componentPanel,
                BorderFactory.createEmptyBorder(4, 4, 0, 4));


        splitPane.setTopComponent(componentTitlePanel);
        installBean(beans[0]);

        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Installs the Kunststoff and Plastic Look And Feels if available in classpath.
     */
    public final void initializeLookAndFeels() {
    	// if in classpath thry to load JGoodies Plastic Look & Feel
        try {
            UIManager.installLookAndFeel("JGoodies Plastic 3D",
                "com.jgoodies.plaf.plastic.Plastic3DLookAndFeel");
            UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.Plastic3DLookAndFeel");
        } catch (Throwable t) {
        	try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}  catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

 



    /**
     * The applet is a PropertyChangeListener for "locale" and "calendar".
     *
     * @param evt Description of the Parameter
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (calendarPanel != null) {
            if (evt.getPropertyName().equals("calendar")) {
                // calendar = (Calendar) evt.getNewValue();
                // DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,
                // jcalendar.getLocale());
                // dateField.setText(df.format(calendar.getTime()));
            }
        }
    }

    /**
     * Creates a JFrame with a JCalendarDemo inside and can be used for testing.
     *
     * @param s The command line arguments
     */
    public static void main(String[] s) {
        WindowListener l = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            };

      //  JFrame frame = new JFrame("JCalendar Demo");
      //  frame.addWindowListener(l);

        JCalendarDemo frame = new JCalendarDemo();
        frame.init();
   
        frame.pack();
        frame.setBounds(200, 200, 400,
            300);
        frame.setVisible(true);
    }

    /**
     * Installes a demo bean.
     *
     * @param bean the demo bean
     */
    private void installBean(JComponent bean) {

            componentPanel.removeAll();
            componentPanel.add(bean);



            int count = 0;





                        final JComponent currentBean = bean;

 
                                JDateChooser dateChooser = new JDateChooser();
                                dateChooser.addPropertyChangeListener((PropertyChangeListener) bean);

  

            componentTitlePanel.setTitle(bean.getName(), null);
            bean.validate();

            componentPanel.validate();

    }



}
