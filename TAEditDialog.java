import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import javax.swing.border.Border;
import java.util.EventObject;
import java.util.Vector;
import java.io.*; 

/******************************
* Copyright (c) 2003,2019 Kevin Lano
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
* *****************************/

class TAEditDialog extends JDialog
{ private JPanel bottom;
  private JButton okButton, cancelButton;
  private DialogPanel dialogPanel;
  

  private String defaultName;
  private boolean defaultInit;
  private String defaultInv; 

  private String newName;
  private boolean newInit;
  private String newInv; 
   
  public TAEditDialog(JFrame owner)
  { super(owner, true);
    setTitle("Edit Time Annotation");
    okButton = new JButton("Ok");
    cancelButton = new JButton("Cancel");
    ButtonHandler bHandler = new ButtonHandler();
    okButton.addActionListener(bHandler);
    cancelButton.addActionListener(bHandler);
    bottom = new JPanel();
    bottom.add(okButton);
    bottom.add(cancelButton);
    bottom.setBorder(BorderFactory.createEtchedBorder());
    dialogPanel = new DialogPanel();
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(bottom, BorderLayout.SOUTH);
    getContentPane().add(dialogPanel, BorderLayout.CENTER); 
  }

  public void setOldFields(String ev, boolean init)
  { defaultName = ev;
    defaultInit = init; 
    dialogPanel.setOldFields(ev,init); 
  }

  public void setFields(String ev, String inv, boolean i)
  { newName = ev;
    newInit = i; 
    newInv = inv; 
  }

  public String getName() { return newName; }
  public boolean getInit() { return newInit; }
  public String getInv() { return newInv; } 

  class DialogPanel extends JPanel
  { private JLabel nameLabel;
    JTextField nameField;  /* State name */
    
    private JLabel invLabel;
    JTextField invField;  /* State invariant */

    private JLabel entryLabel;
    JTextField entryField;  /* State entry action */
   
    JCheckBox initBox, notinitBox;
    private JPanel initPanel;
    private ButtonGroup group; 

    public DialogPanel()
    { nameLabel = new JLabel("Time:");
      nameField = new JTextField();
      invLabel = new JLabel("Condition:");
      invField = new JTextField();
      initPanel = new JPanel();
      initBox = new JCheckBox("<<forall>>");
      notinitBox = new JCheckBox("<<exists>>",true);
      initPanel.add(initBox);
      initPanel.add(notinitBox);
      initPanel.setBorder(
        BorderFactory.createTitledBorder("Stereotype"));
      group = new ButtonGroup(); 
      group.add(initBox);
      group.add(notinitBox);

      add(nameLabel);
      add(nameField);
      add(invLabel);
      add(invField);
      add(initPanel); 
    }

  public void setOldFields(String nme, boolean init)
  { nameField.setText(nme);
    if (init)
    { initBox.setSelected(true); }
    else
    { notinitBox.setSelected(true); } 
  }

  public Dimension getPreferredSize()
  { return new Dimension(350,160); }

  public Dimension getMinimumSize()
  { return new Dimension(350,160); }

  public void doLayout()
  { nameLabel.setBounds(10,10,60,30);
    nameField.setBounds(70,15,270,20);
    invLabel.setBounds(10,40,60,30);
    invField.setBounds(70,45,270,20);
    initPanel.setBounds(10,80,330,50); 
  }

  public void reset()
  { nameField.setText("");
    notinitBox.setSelected(true); 
  }
 }  /* inner class */

 class ButtonHandler implements ActionListener
 { public void actionPerformed(ActionEvent ev)
   { JButton button = (JButton) ev.getSource();
     String label = button.getText();
     if ("Ok".equals(label))
     { setFields(dialogPanel.nameField.getText(),
                 dialogPanel.invField.getText(), 
                 dialogPanel.initBox.isSelected()); 
     }
     else 
     { setFields(null,null,false); }
 
     dialogPanel.reset();
     setVisible(false); 
   } 
 }
}


