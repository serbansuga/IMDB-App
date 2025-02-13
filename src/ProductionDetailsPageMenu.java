import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Iterator;

public class ProductionDetailsPageMenu extends JFrame implements ActionListener, ListSelectionListener {
    JLabel imageLabel;
    JLabel title, type, actors, directors,duration, releaseYear, ratingaverage, ratings,GenresLabel;
    JTextArea plot;
    JButton back, addfavorites,addReview;

    JPanel panel;
    JList ratingsList;
    JScrollPane scrollPane;
    Production prod;

    public ProductionDetailsPageMenu(String name, Production prod){
        super(name);
        this.prod = prod;
        panel = new JPanel();
        panel.setSize(1000,550);
        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBackground(new Color(221,221,221));

        imageLabel = new JLabel();
        if(prod.title.equals("Mad Max: Fury Road")){
            imageLabel.setIcon(new ImageIcon("Images/" + "Mad Max Fury Road" +".jpg"));
        }else if(prod.title.equals("The Lord of the Rings: The Return of the King")){
            imageLabel.setIcon(new ImageIcon("Images/" + "The Lord of the Rings The Return of the King" +".jpg"));
        }else {
            imageLabel.setIcon(new ImageIcon("Images/" + prod.title + ".jpg"));
        }
        imageLabel.setBounds(30,60,230,300);

        back = new JButton("Back");
        back.setBounds(30, 20,100,30);
        back.setFocusable(false);
        back.setBorder(BorderFactory.createEtchedBorder());
        back.addActionListener(this);

        addfavorites = new JButton("Add to favorites");
        addfavorites.setBounds(30, 410,130,40);
        addfavorites.setFocusable(false);
        addfavorites.setBorder(BorderFactory.createEtchedBorder());
        addfavorites.addActionListener(this);

        addReview = new JButton("Add a review");
        addReview.setBounds(185, 410,130,40);
        addReview.setFocusable(false);
        addReview.setBorder(BorderFactory.createEtchedBorder());
        addReview.addActionListener(this);


        title = new JLabel();
        title.setText("Title: " + prod.title);
        title.setBounds(300, 60, 600,30);
        title.setFont(new Font("Calibri", Font.BOLD, 20));

        type = new JLabel();
        if(prod instanceof Movie){
            type.setText("Type: Movie");
        }else if(prod instanceof Series){
            type.setText("Type: Series");
        }

        type.setBounds(300, 95, 300,30);
        type.setFont(new Font("Calibri", Font.BOLD, 16));

        actors = new JLabel();
        String text = "Actors:";
        boolean ok = false;
        for(String txt : prod.actors){
            if(!ok) {
                text += " " + txt;
                ok = true;
            }else{
                text += ", " + txt;
            }
        }
        actors.setText(text);

        actors.setBounds(300, 125, 300,30);
        actors.setFont(new Font("Calibri", Font.BOLD, 16));

        directors = new JLabel();
        text = "Directors:";
        ok = false;
        for(String txt : prod.directors){
            if(!ok) {
                text += " " + txt;
            }else{
                ok = true;
                text += ", " + txt;
            }
        }
        directors.setText(text);
        directors.setBounds(300, 155, 300,30);
        directors.setFont(new Font("Calibri", Font.BOLD, 16));

        duration = new JLabel();
        if(prod instanceof Movie){
            duration.setText("Duration: " + ((Movie) prod).duration);
        }else{
            duration.setText("Duration: " + "-");
        }
        duration.setBounds(300, 190, 300,30);
        duration.setFont(new Font("Calibri", Font.BOLD, 16));


        releaseYear = new JLabel();
        if(prod instanceof Movie){
            releaseYear.setText("Release Year: " + ((Movie) prod).releaseYear);
        }else if(prod instanceof Series){
            releaseYear.setText("Release Year: " + ((Series) prod).releaseYear);
        }
        releaseYear.setBounds(300, 225, 300,30);
        releaseYear.setFont(new Font("Calibri", Font.BOLD, 16));


        ratingaverage = new JLabel();
        ratingaverage.setText("Rating: " + String.valueOf(prod.averageRating));
        ratingaverage.setBounds(300, 260, 300,30);
        ratingaverage.setFont(new Font("Calibri", Font.BOLD, 16));


        plot = new JTextArea();
        plot.setText("Plot: " + prod.description);
        plot.setFont(new Font("Calibri", Font.BOLD, 16));
        plot.setBackground(panel.getBackground());
        plot.setLineWrap(true);
        plot.setEditable(false);
        plot.setFocusable(false);
        plot.setBounds(300, 295, 300,110);

        Collections.sort(prod.ratings);
        DefaultListModel listModel = new DefaultListModel<>();
        for(Rating rating : prod.ratings){
            listModel.addElement(rating);
        }

        ratings = new JLabel("Ratings:");
        ratings.setBounds(620, 80, 300,30);
        ratings.setFont(new Font("Calibri", Font.BOLD, 21));

        ratingsList = new JList(listModel);
        ratingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ratingsList.addListSelectionListener(this);
        ratingsList.setCellRenderer(new RatingsRenderer());
        scrollPane = new JScrollPane(ratingsList);
        scrollPane.setPreferredSize(new Dimension(360,300));
        scrollPane.setBounds(620,110,360,250);


        text = "Genres:";
        if(prod.genres != null) {
            for (Production.Genres genre : prod.genres) {
                text += " " + genre.toString();
            }
        }

        GenresLabel = new JLabel();
        GenresLabel.setBounds(620, 375, 300,30);
        GenresLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        GenresLabel.setText(text);


        panel.add(imageLabel);
        panel.add(back);
        panel.add(title);
        panel.add(type);
        panel.add(actors);
        panel.add(directors);
        panel.add(duration);
        panel.add(releaseYear);
        panel.add(ratingaverage);
        panel.add(plot);
        panel.add(scrollPane);
        panel.add(ratings);
       // panel.add(addfavorites);
        //panel.add(addReview);
        panel.add(GenresLabel);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 550);
        this.setVisible(true);
        this.add(panel);


        ImageIcon ImdbIcon = new ImageIcon("IMDBLogo.png");
        this.setIconImage(ImdbIcon.getImage());
        this.setResizable(false);
    }
    class RatingsRenderer extends JPanel implements ListCellRenderer<Rating>{
        JLabel usernamerating, comment;
        JPanel new_panel;

        public RatingsRenderer(){
            setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(250,70));

            new_panel = new JPanel();
            new_panel.setLayout(new GridLayout(2, 1, 20,5));

            usernamerating = new JLabel();
            comment = new JLabel();

            new_panel.add(usernamerating);
            new_panel.add(comment);

            add(new_panel, BorderLayout.CENTER);
            setOpaque(true);
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends Rating> list, Rating value, int index, boolean isSelected, boolean cellHasFocus) {
            usernamerating.setText("username: " + value.username + "      Rating: " + value.rating);
            comment.setText("Comment: " + value.comment);
            if(isSelected){
                new_panel.setBackground(Color.gray);
            }else{
                new_panel.setBackground(list.getBackground());
            }
            return this;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            this.dispose();
        }else if(e.getSource() == addfavorites){
            IMDB.getInstance().connectedUser.addFavoriteProduction(this.prod.title);
            JOptionPane.showMessageDialog(null, "Successfully added!", "Info", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new MenuPage("Menu");
        }else if(e.getSource() == addReview){
            if(IMDB.getInstance().connectedUser instanceof Regular<?>) {
                this.dispose();
                AddReviewMenu addReview1 = new AddReviewMenu("Add your review", this.prod);
                new MenuPage("Menu");
            }else{
                JOptionPane.showMessageDialog(null, "Only regular users can submit a review!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
