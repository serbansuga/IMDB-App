import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateProdPanel extends JPanel implements ListSelectionListener, ActionListener {
    DefaultListModel listModelUpdateProd;
    JList prodUpdateList;
    JScrollPane prodUpdatePane;
    JLabel updateProdLabel,addDirectorProdLabel, addActorProdLabel, addGenreProdLabel, prodDescriptionUpdateLabel, updateReleaseYearLabel,updateDurationProdLabel;
    JTextField addDirectorProd,addActorProd,addGenreProd, updateDurationProd,updateReleaseYear;
    JTextArea prodDescriptionUpdate;
    JButton updateProdButton, updateProdDetails;
    Production prod;
    MenuPage menu;
    public UpdateProdPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        IMDB.getInstance().addedProductions.clear();
        listModelUpdateProd = new DefaultListModel<>();
        if(IMDB.getInstance().connectedUser instanceof Regular == false) {
            for (Object obj : ((Staff) IMDB.getInstance().connectedUser).addedItems) {
                if (obj instanceof Production) {
                    IMDB.getInstance().addedProductions.add((Production) obj);
                    listModelUpdateProd.addElement((Production) obj);
                }
            }
        }

        prodUpdateList = new JList<>(listModelUpdateProd);
        prodUpdateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prodUpdateList.addListSelectionListener(this);
        prodUpdatePane = new JScrollPane(prodUpdateList);
        prodUpdatePane.setBounds(40,80,260, 280);

        updateProdLabel = new JLabel("Choose production you want to edit:");
        updateProdLabel.setBounds(40,55,400,25);
        updateProdLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        addDirectorProdLabel = new JLabel("Add director");
        addDirectorProdLabel.setBounds(330,55,300,25);
        addDirectorProdLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        addDirectorProd = new JTextField();
        addDirectorProd.setBounds(330,80,300,25);
        addDirectorProd.setFont(new Font("Calibri", Font.PLAIN, 17));


        addActorProdLabel = new JLabel("Add actor");
        addActorProdLabel.setBounds(330,115,300,25);
        addActorProdLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        addActorProd = new JTextField();
        addActorProd.setBounds(330,140,300,25);
        addActorProd.setFont(new Font("Calibri", Font.PLAIN, 17));

        addGenreProdLabel = new JLabel("Add genre");
        addGenreProdLabel.setBounds(330,175,300,25);
        addGenreProdLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        addGenreProd = new JTextField();
        addGenreProd.setBounds(330,200,300,25);
        addGenreProd.setFont(new Font("Calibri", Font.PLAIN, 17));

        prodDescriptionUpdateLabel = new JLabel("Modify description");
        prodDescriptionUpdateLabel.setBounds(330,235,300,25);
        prodDescriptionUpdateLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        prodDescriptionUpdate = new JTextArea();

        prodDescriptionUpdate.setFont(new Font("Calibri", Font.BOLD, 16));
        prodDescriptionUpdate.setLineWrap(true);
        prodDescriptionUpdate.setEditable(true);
        prodDescriptionUpdate.setBounds(340, 260, 400, 150);

        updateReleaseYearLabel = new JLabel("Release Year");
        updateReleaseYearLabel.setBounds(330,410,300,25);
        updateReleaseYearLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        updateReleaseYear = new JTextField();
        updateReleaseYear.setBounds(330,435,300,25);
        updateReleaseYear.setFont(new Font("Calibri", Font.PLAIN, 17));

        updateDurationProdLabel = new JLabel("Duration");
        updateDurationProdLabel.setVisible(false);
        updateDurationProdLabel.setBounds(330,470,300,25);
        updateDurationProdLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        updateDurationProd = new JTextField();
        updateDurationProd.setVisible(false);
        updateDurationProd.setBounds(330,495,300,25);
        updateDurationProd.setFont(new Font("Calibri", Font.PLAIN, 17));

        updateProdDetails = new JButton("See details");
        updateProdDetails.setBounds(40, 380,140,40);
        updateProdDetails.setFocusable(false);
        updateProdDetails.setBorder(BorderFactory.createEtchedBorder());
        updateProdDetails.addActionListener(this);

        updateProdButton = new JButton("Update Production");
        updateProdButton.setBounds(40, 430,140,40);
        updateProdButton.setFocusable(false);
        updateProdButton.setBorder(BorderFactory.createEtchedBorder());
        updateProdButton.addActionListener(this);

        this.add(prodUpdatePane);
        this.add(updateProdLabel);
        this.add(updateProdDetails);
        this.add(addDirectorProdLabel);
        this.add(addDirectorProd);
        this.add(addActorProdLabel);
        this.add(addActorProd);
        this.add(addGenreProdLabel);
        this.add(addGenreProd);
        this.add(prodDescriptionUpdateLabel);
        this.add(prodDescriptionUpdate);
        this.add(updateReleaseYearLabel);
        this.add(updateReleaseYear);
        this.add(updateDurationProdLabel);
        this.add(updateDurationProd);
        this.add(updateProdButton);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateProdDetails) {
            if (prodUpdateList.isSelectionEmpty() == false) {
                int indexList = prodUpdateList.getSelectedIndex();
                prod = IMDB.getInstance().addedProductions.get(indexList);
                prodDescriptionUpdate.setText(prod.description);
                addDirectorProd.setText("");
                addActorProd.setText("");
                addGenreProd.setText("");
                if (prod instanceof Movie) {
                    updateReleaseYear.setText(String.valueOf(((Movie) prod).releaseYear));
                    updateDurationProdLabel.setVisible(true);
                    updateDurationProd.setVisible(true);
                    updateDurationProd.setText(((Movie) prod).duration);
                } else if (prod instanceof Series) {
                    updateDurationProdLabel.setVisible(false);
                    updateDurationProd.setVisible(false);
                    updateReleaseYear.setText(String.valueOf(((Series) prod).releaseYear));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a production!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

        if (e.getSource() == updateProdButton) {
            if (prodUpdateList.isSelectionEmpty() == false) {
                String director, actor, genre, description, releaseYear, duration;
                Production new_prod = null;
//                int indexList = prodUpdateList.getSelectedIndex();
//                prod = IMDB.getInstance().productions.get(indexList);
                if (((Staff) IMDB.getInstance().connectedUser).addedItems.contains(prod)) {
                    director = addDirectorProd.getText();
                    actor = addActorProd.getText();
                    genre = addGenreProd.getText();

                    description = prodDescriptionUpdate.getText();
                    releaseYear = updateReleaseYear.getText();
                    if (prod instanceof Movie) {
                        duration = updateDurationProd.getText();
                    } else {
                        duration = null;
                    }
                    if (prod instanceof Movie) {
                        new_prod = new Movie(prod.title, description, prod.averageRating, duration, Long.valueOf(releaseYear));
                    } else if (prod instanceof Series) {
                        new_prod = new Series(prod.title, description, prod.averageRating, Long.valueOf(releaseYear), ((Series) prod).numSeasons);
                    }
                    new_prod.directors = prod.directors;

                    if (director.equals("") == false && !new_prod.directors.contains(director)) {
                        new_prod.directors.add(director);
                    }

                    new_prod.actors = prod.actors;
                    if (actor.equals("") == false && !new_prod.actors.contains(actor)) {
                        new_prod.actors.add(actor);
                    }

                    Production.Genres genre1;
                    switch (genre) {
                        case "Action":
                            genre1 = Production.Genres.Action;
                            break;
                        case "Adventure":
                            genre1 = Production.Genres.Adventure;
                            break;
                        case "Comedy":
                            genre1 = Production.Genres.Comedy;
                            break;
                        case "Drama":
                            genre1 = Production.Genres.Drama;
                            break;
                        case "Horror":
                            genre1 = Production.Genres.Horror;
                            break;
                        case "SF":
                            genre1 = Production.Genres.SF;
                            break;
                        case "Fantasy":
                            genre1 = Production.Genres.Fantasy;
                            break;
                        case "Romance":
                            genre1 = Production.Genres.Romance;
                            break;
                        case "Mystery":
                            genre1 = Production.Genres.Mystery;
                            break;
                        case "Thriller":
                            genre1 = Production.Genres.Thriller;
                            break;
                        case "Crime":
                            genre1 = Production.Genres.Crime;
                            break;
                        case "Biography":
                            genre1 = Production.Genres.Biography;
                            break;
                        case "War":
                            genre1 = Production.Genres.War;
                            break;
                        default:
                            genre1 = null;
                            break;
                    }

                    new_prod.genres = prod.genres;
                    if (genre1 != null && !new_prod.genres.contains(genre1)) {
                        new_prod.genres.add(genre1);
                    }
                    ((Staff) IMDB.getInstance().connectedUser).updateProduction(new_prod);
                    JOptionPane.showMessageDialog(null, "Production updated successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    menu.dispose();
                    new MenuPage("Menu");
                } else {
                    JOptionPane.showMessageDialog(null, "Production is not added by you!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select a production!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
}
