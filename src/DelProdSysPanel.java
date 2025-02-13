import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelProdSysPanel extends JPanel implements ActionListener, ListSelectionListener {
    DefaultListModel listModel2;
    JList prodList2;
    JScrollPane scrollPane2;

    JButton delProdSysButton;
    JLabel delProdSysLabel;
    Production prod;
    MenuPage menu;
    public DelProdSysPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        IMDB.getInstance().addedProductions.clear();
        listModel2 = new DefaultListModel<>();
        listModel2.removeAllElements();
        if(IMDB.getInstance().connectedUser instanceof Regular == false) {
            for (Object obj : ((Staff) IMDB.getInstance().connectedUser).addedItems) {
                if (obj instanceof Production) {
                    IMDB.getInstance().addedProductions.add((Production) obj);
                    listModel2.addElement((Production) obj);
                }
            }
        }
        prodList2 = new JList<>(listModel2);
        prodList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prodList2.addListSelectionListener(this);
        scrollPane2 = new JScrollPane(prodList2);
        scrollPane2.setBounds(40,80,500, 280);

        delProdSysLabel = new JLabel("Choose production you want to delete:");
        delProdSysLabel.setBounds(40,55,400,25);
        delProdSysLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        delProdSysButton = new JButton("Delete Production");
        delProdSysButton.setBounds(40, 380,140,40);
        delProdSysButton.setFocusable(false);
        delProdSysButton.setBorder(BorderFactory.createEtchedBorder());
        delProdSysButton.addActionListener(this);

        this.add(scrollPane2);
        this.add(delProdSysButton);
        this.add(delProdSysLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delProdSysButton) {
            if (prodList2.isSelectionEmpty() == false) {
                int indexList = prodList2.getSelectedIndex();
                prod = IMDB.getInstance().addedProductions.get(indexList);
                if (IMDB.getInstance().connectedUser instanceof Admin<?> == false) {
                    if (((Staff) IMDB.getInstance().connectedUser).addedItems.contains(prod) == false) {
                        JOptionPane.showMessageDialog(null, "Production is not added by you!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ((Contributor) IMDB.getInstance().connectedUser).removeProductionSystem(prod.title);
                        IMDB.getInstance().addedProductions.remove(prod);
                        JOptionPane.showMessageDialog(null, "Production deleted successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                        menu.dispose();
                        new MenuPage("Menu");
                    }
                } else {
                    ((Admin) IMDB.getInstance().connectedUser).removeProductionSystem(prod.title);
                    JOptionPane.showMessageDialog(null, "Production deleted successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
                    menu.dispose();
                    new MenuPage("Menu");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a production!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
