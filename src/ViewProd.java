import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;

public class ViewProd extends JPanel implements ActionListener, ListSelectionListener, ItemListener {
    DefaultListModel listModel;
    JList prodList;
    JScrollPane scrollPane;
    JButton viewDetails;
    JComboBox genreFilters;
    JRadioButton filterByRating;

    Production prod;
    public ViewProd(){

        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        IMDB.getInstance().filtered.clear();

        Collections.sort(IMDB.getInstance().productions);
        listModel = new DefaultListModel<>();
        for(Production prod : IMDB.getInstance().productions){
            listModel.addElement(prod);
            IMDB.getInstance().filtered.add(prod);
        }

        prodList = new JList<>(listModel);
        prodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prodList.addListSelectionListener(this);
        scrollPane = new JScrollPane(prodList);
        scrollPane.setBounds(40,40,500, 280);

        viewDetails = new JButton("View details");
        viewDetails.setBounds(40, 340,130,40);
        viewDetails.setFocusable(false);
        viewDetails.setBorder(BorderFactory.createEtchedBorder());
        viewDetails.addActionListener(this);

        String[] Genres = {"All", "Action", "Adventure", "Comedy", "Drama", "Horror", "SF",
                "Fantasy", "Romance", "Mystery", "Thriller", "Crime", "Biography", "War"};
        genreFilters = new JComboBox<>(Genres);
        genreFilters.addActionListener(this);
        genreFilters.setBounds(560,90,100,20);

        JLabel genrelabel = new JLabel("Filter by Genre:");
        genrelabel.setBounds(560, 60,150,20);
        genrelabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        filterByRating = new JRadioButton("Sort by ratings");
        filterByRating.setFocusable(false);
        filterByRating.setBounds(560,130,200,30);
        filterByRating.setFont(new Font(null, Font.PLAIN, 20));
        filterByRating.addItemListener(this);
        filterByRating.setBackground(new Color(221,221,221));

        this.add(scrollPane);
        this.add(viewDetails);
        this.add(genreFilters);
        this.add(genrelabel);
        this.add(filterByRating);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == genreFilters) {
            for (Production prod : IMDB.getInstance().productions) {
                if (IMDB.getInstance().filtered.contains(prod))
                    IMDB.getInstance().filtered.remove(prod);
            }
            listModel.removeAllElements();
            for (Production prod : IMDB.getInstance().productions) {
                IMDB.getInstance().filtered.add(prod);
                listModel.addElement(prod);
            }

            String text = (String) genreFilters.getSelectedItem();
            Production.Genres genre;
            switch (text) {
                case "Action":
                    genre = Production.Genres.Action;
                    break;
                case "Adventure":
                    genre = Production.Genres.Adventure;
                    break;
                case "Comedy":
                    genre = Production.Genres.Comedy;
                    break;
                case "Drama":
                    genre = Production.Genres.Drama;
                    break;
                case "Horror":
                    genre = Production.Genres.Horror;
                    break;
                case "SF":
                    genre = Production.Genres.SF;
                    break;
                case "Fantasy":
                    genre = Production.Genres.Fantasy;
                    break;
                case "Romance":
                    genre = Production.Genres.Romance;
                    break;
                case "Mystery":
                    genre = Production.Genres.Mystery;
                    break;
                case "Thriller":
                    genre = Production.Genres.Thriller;
                    break;
                case "Crime":
                    genre = Production.Genres.Crime;
                    break;
                case "Biography":
                    genre = Production.Genres.Biography;
                    break;
                case "War":
                    genre = Production.Genres.War;
                    break;
                default:
                    genre = null;
                    break;
            }

            for (Production prod : IMDB.getInstance().productions) {
                if (prod.genres.contains(genre) == false && genre != null) {
                    IMDB.getInstance().filtered.remove(prod);
                    listModel.removeElement(prod);
                }
            }
        }

        if (e.getSource() == viewDetails) {
            if (prodList.isSelectionEmpty() == false) {
                int indexList1 = prodList.getSelectedIndex();
                prod = IMDB.getInstance().filtered.get(indexList1);
                ProductionDetailsPageMenu prodPage = new ProductionDetailsPageMenu("Production Details", prod);
            } else {
                JOptionPane.showMessageDialog(null, "Select a production!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(filterByRating.isSelected()){
            IMDB.getInstance().filtered.sort(new Production.ProductionRatingsSort());
            listModel.removeAllElements();
            for (Production prod : IMDB.getInstance().filtered) {
                listModel.addElement(prod);
            }
        }
    }
}
