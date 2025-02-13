import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProdFavPanel extends JPanel implements ActionListener, ListSelectionListener {
    DefaultListModel listModel;
    JList prodList;
    JScrollPane scrollPane;
    JLabel addProdFavLabel;
    JButton addProdFavButton;
    Production prod;
    MenuPage menu;
    public AddProdFavPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        listModel = new DefaultListModel<>();
        for(Production prod : IMDB.getInstance().productions){
            listModel.addElement(prod);
        }

        prodList = new JList<>(listModel);
        prodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prodList.addListSelectionListener(this);
        scrollPane = new JScrollPane(prodList);
        scrollPane.setBounds(40,80,500, 280);

        addProdFavLabel = new JLabel("Choose production you want to add to favorite:");
        addProdFavLabel.setBounds(40,50,500,25);
        addProdFavLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        addProdFavButton = new JButton("Add to favorite");
        addProdFavButton.setBounds(40, 385,130,40);
        addProdFavButton.setFocusable(false);
        addProdFavButton.setBorder(BorderFactory.createEtchedBorder());
        addProdFavButton.addActionListener(this);

        this.add(scrollPane);
        this.add(addProdFavButton);
        this.add(addProdFavLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addProdFavButton) {
            if (prodList.isSelectionEmpty() == false) {
                int indexList = prodList.getSelectedIndex();
                prod = IMDB.getInstance().productions.get(indexList);

                if (IMDB.getInstance().connectedUser.favorites.contains(prod) == false) {
                    IMDB.getInstance().connectedUser.addFavoriteProduction(prod.title);
                    JOptionPane.showMessageDialog(null, "Production added to favorites", "Info", JOptionPane.INFORMATION_MESSAGE);
                    menu.dispose();
                    new MenuPage("Menu");
                } else {
                    JOptionPane.showMessageDialog(null, "This production is already to favorites", "Info", JOptionPane.INFORMATION_MESSAGE);
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
