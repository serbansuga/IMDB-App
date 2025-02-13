import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelProdFavPanel extends JPanel implements ActionListener, ListSelectionListener {
    DefaultListModel listModelProdFav;
    JList prodFavList;
    JScrollPane scrollProdFav;
    JLabel delProdFavLabel;
    JButton delProdFavButton;
    Production prod;
    public DelProdFavPanel(){
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        IMDB.getInstance().prodFavorites.clear();
        listModelProdFav = new DefaultListModel<>();

        listModelProdFav.removeAllElements();
        for(Object obj : IMDB.getInstance().connectedUser.favorites){
            if(obj instanceof Production) {
                IMDB.getInstance().prodFavorites.add((Production) obj);
                listModelProdFav.addElement((Production)obj);
            }
        }

        prodFavList = new JList<>(listModelProdFav);
        prodFavList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prodFavList.addListSelectionListener(this);
        scrollProdFav = new JScrollPane(prodFavList);
        scrollProdFav.setBounds(40,80,500, 280);

        delProdFavLabel = new JLabel("Choose production you want to delete:");
        delProdFavLabel.setBounds(40,55,400,25);
        delProdFavLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        delProdFavButton = new JButton("Delete from favorite");
        delProdFavButton.setBounds(40, 380,130,40);
        delProdFavButton.setFocusable(false);
        delProdFavButton.setBorder(BorderFactory.createEtchedBorder());
        delProdFavButton.addActionListener(this);

        this.add(scrollProdFav);
        this.add(delProdFavButton);
        this.add(delProdFavLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delProdFavButton) {
            if (prodFavList.isSelectionEmpty() == false) {
                int nr = 0;
                int indexList = prodFavList.getSelectedIndex();
                prod = IMDB.getInstance().prodFavorites.get(indexList);
                IMDB.getInstance().connectedUser.delFavoriteProduction(prod);
                listModelProdFav.removeElement(prod);
                IMDB.getInstance().prodFavorites.remove(prod);
                JOptionPane.showMessageDialog(null, "Production deleted from favorites", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Select a production!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
