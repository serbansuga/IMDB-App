import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainPage extends JFrame implements ActionListener, ListSelectionListener,ItemListener{
    int index;
    JLabel Imagelabel, messagelabel, experienceLabel, menuLabel;
    JList<Production> prodList;
    JScrollPane scrollPane;
    JRadioButton filterByRating, filterByYear;
    JTextField search;
    JButton searchButton, actorsButton, menuButton, details, logout;

    JComboBox genreFilters;
    DefaultListModel<Production> listModel;

    Production prod;
    public MainPage(String title){
        super(title);
        IMDB.getInstance().loginPage.main = this;
        JPanel Centerpanel = new JPanel();
        JPanel Northpanel = new JPanel();
        JPanel Westpanel = new JPanel();
        JPanel Eastpanel = new JPanel();

        filterByRating = new JRadioButton("Sort by ratings");
        filterByRating.setFocusable(false);
        filterByRating.setFont(new Font(null, Font.PLAIN, 20));
        filterByRating.addItemListener(this);
        filterByRating.setBackground(new Color(221,221,221));

        filterByYear = new JRadioButton("Sort by newest");
        filterByYear.setFocusable(false);
        filterByYear.setFont(new Font(null, Font.PLAIN, 20));
        filterByYear.addItemListener(this);
        filterByYear.setBackground(new Color(221,221,221));


        Centerpanel.setLayout(new BorderLayout());
        Centerpanel.setPreferredSize(new Dimension(300,0));
        Centerpanel.setBackground(new Color(221,221,221));

        Northpanel.setLayout(null);
        Northpanel.setPreferredSize(new Dimension(200,120));
        Northpanel.setBackground(new Color( 170, 170, 170));

        Westpanel.setLayout(new BorderLayout());
        Westpanel.setPreferredSize(new Dimension(210,0));
        Westpanel.setBackground(new Color(221,221,221));

        Eastpanel.setLayout(null);
        Eastpanel.setPreferredSize(new Dimension(300,0));
        Eastpanel.setBackground(new Color(221,221,221));
        //Recomandarile
        Random random = new Random();
        Vector<Integer> v = new Vector<>();

        IMDB.getInstance().recommendations.clear();
        IMDB.getInstance().recommendations1.clear();
        listModel = new DefaultListModel<>();
        listModel.removeAllElements();

//        if(IMDB.getInstance().recommendations.isEmpty()) {
            for (int i = 0; i < 11; i++) {
                int j;
                int rand = random.nextInt(IMDB.getInstance().productions.size());
                boolean ok = false;
                while (!ok) {
                    for (j = 0; j < v.size(); j++) {
                        if (v.get(j) == rand) {
                            break;
                        }
                    }
                    if (j == v.size()) {
                        ok = true;
                        v.add(rand);
                    } else {
                        rand = random.nextInt(IMDB.getInstance().productions.size());
                    }
                }
                IMDB.getInstance().recommendations.add(IMDB.getInstance().productions.get(rand));
            }

            Collections.sort(IMDB.getInstance().recommendations);

            for (Production prod : IMDB.getInstance().recommendations) {
                listModel.addElement(prod);
                IMDB.getInstance().recommendations1.add(prod);
            }
//        }else{
//            for (Production prod : IMDB.getInstance().recommendations) {
//                listModel.addElement(prod);
//            }
//        }



        prodList = new JList<>(listModel);
        prodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prodList.addListSelectionListener(this);
        //prodList.setCellRenderer(new prodRenderer());
        scrollPane = new JScrollPane(prodList);
        scrollPane.setBounds(0,110,300, 165);

        JLabel foryou = new JLabel("For you:");
        foryou.setBounds(115,70,100,20);
        foryou.setFont(new Font("Calibri", Font.BOLD, 22));
        Eastpanel.add(scrollPane, BorderLayout.CENTER);
        Eastpanel.add(foryou);




//        for(Production prod : IMDB.getInstance().recommendations1){
//            System.out.println(prod.title);
//        }



        Imagelabel = new JLabel();
        //Imagelabel.setBounds(40,0 ,230,300);
        Imagelabel.setPreferredSize(new Dimension(230, 300));
        Imagelabel.setHorizontalAlignment(SwingConstants.CENTER);
        Production prod = IMDB.getInstance().recommendations1.get(0);
        if(prod.title.equals("Mad Max: Fury Road")){
            Imagelabel.setIcon(new ImageIcon("Images/" + "Mad Max Fury Road" +".jpg"));
        }else if(prod.title.equals("The Lord of the Rings: The Return of the King")){
            Imagelabel.setIcon(new ImageIcon("Images/" + "The Lord of the Rings The Return of the King" +".jpg"));
        }else {
            Imagelabel.setIcon(new ImageIcon("Images/" + prod.title + ".jpg"));
        }

        String text = "Genres:";
        if(IMDB.getInstance().recommendations.get(index).genres != null) {
            for (Production.Genres genre : IMDB.getInstance().recommendations.get(index).genres) {
                text += " " + genre.toString();
            }
        }else{
            text += "-";
        }
        Imagelabel.setText(text);
        Imagelabel.setHorizontalTextPosition(JLabel.CENTER);
        Imagelabel.setVerticalTextPosition(JLabel.BOTTOM);


        messagelabel = new JLabel();
        text = "Welcome back, " + IMDB.getInstance().connectedUser.information.name + "!";
        messagelabel.setText(text);
        messagelabel.setBounds(15, 95,300,30);
        messagelabel.setFont(new Font("Calibri", Font.BOLD, 19));


        experienceLabel = new JLabel();
        if(IMDB.getInstance().connectedUser.accountType != User.AccountTypes.ADMIN) {
            text = "User experience: " + IMDB.getInstance().connectedUser.experience;
        }else{
            text = "User experience: -";
        }
        experienceLabel.setText(text);
        experienceLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        experienceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        search = new JTextField();
        search.setBounds(290, 30, 390,40);
        search.setText("Search...");
        search.setFont(new Font("Calibri", Font.BOLD, 17));
        search.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                search.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                search.setText("Search...");
            }
        });

        searchButton = new JButton();
        searchButton.setIcon(new ImageIcon("search.png"));
        searchButton.setBounds(240, 30, 40,40);
        searchButton.setMnemonic(KeyEvent.VK_ENTER);
        searchButton.setFocusable(false);
        searchButton.addActionListener(this);

        menuLabel = new JLabel();
        menuLabel.setIcon(new ImageIcon("menu.png"));
        menuLabel.setBounds(750, 30,50,50);

        menuButton = new JButton();
        menuButton.setText("Menu");
        menuButton.setBounds(810, 37,110,38);
        menuButton.setFocusable(false);
        menuButton.setBorder(BorderFactory.createEtchedBorder());
        menuButton.addActionListener(this);

        logout = new JButton();
        logout.setText("Logout");
        logout.setBounds(820, 85,90,30);
        logout.setFocusable(false);
        logout.setBorder(BorderFactory.createEtchedBorder());
        logout.addActionListener(this);

        details = new JButton("View Details");
        details.setFocusable(false);
        details.setPreferredSize(new Dimension(110,40));
        details.setBorder(BorderFactory.createEtchedBorder());
        details.addActionListener(this);

        actorsButton = new JButton("View Actors");
        actorsButton.setFocusable(false);
        actorsButton.setBounds(100, 285, 110,30);
        actorsButton.setBorder(BorderFactory.createEtchedBorder());
        actorsButton.addActionListener(this);
       // Eastpanel.add(actorsButton);

        JLabel Imdb = new JLabel();
        Imdb.setIcon(new ImageIcon("imdblogoo (1).png"));
        Imdb.setBounds(15,12,200,80);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,45, 0));
        detailsPanel.setBackground(new Color(221,221,221));
        detailsPanel.setPreferredSize(new Dimension(0, 60));
        detailsPanel.setBackground(new Color(221,221,221));

        detailsPanel.add(details);

        JLabel filter = new JLabel("Filter by genre:");
        filter.setHorizontalAlignment(SwingConstants.CENTER);
        filter.setFont(new Font("Calibri", Font.PLAIN, 20));

        JPanel filterpanel = new JPanel(new BorderLayout());
        filterpanel.setPreferredSize(new Dimension(0, 120));
        filterpanel.setBackground(new Color(221,221,221));
        filterpanel.add(filter, BorderLayout.SOUTH);
        filterpanel.add(experienceLabel, BorderLayout.NORTH);

        JPanel filters = new JPanel();
        filters.setLayout(new FlowLayout());
        filters.setBackground(new Color(221,221,221));

        String[] Genres = {"All", "Action", "Adventure", "Comedy", "Drama", "Horror", "SF",
                       "Fantasy", "Romance", "Mystery", "Thriller", "Crime", "Biography", "War"};
        genreFilters = new JComboBox<>(Genres);
        genreFilters.addActionListener(this);
        filters.add(genreFilters);

        JPanel sortRatings = new JPanel();
        sortRatings.setLayout(new BorderLayout());
        sortRatings.setBackground(new Color(221,221,221));
        sortRatings.setPreferredSize(new Dimension(0, 220));
        sortRatings.setBackground(new Color(221,221,221));

        JPanel sorters = new JPanel();
        sorters.setLayout(new GridLayout(2,1));
        sorters.setBackground(new Color(221,221,221));
        sorters.add(filterByRating);


        filterByRating.setHorizontalAlignment(SwingConstants.CENTER);
        filterByYear.setHorizontalAlignment(SwingConstants.CENTER);
        sortRatings.add(sorters, BorderLayout.NORTH);


        Westpanel.add(filterpanel, BorderLayout.NORTH);
        Westpanel.add(filters, BorderLayout.CENTER);
        Westpanel.add(sortRatings, BorderLayout.SOUTH);

        Northpanel.add(Imdb);
        Northpanel.add(messagelabel);
        Northpanel.add(search);
        Northpanel.add(searchButton);
        Northpanel.add(menuLabel);
        Northpanel.add(menuButton);
        Northpanel.add(logout);

        Centerpanel.add(Imagelabel, BorderLayout.CENTER);
        Centerpanel.add(detailsPanel, BorderLayout.SOUTH);
        //Westpanel.add(prev);
      // Westpanel.add(next);

        this.setLayout(new BorderLayout());
        this.add(Centerpanel, BorderLayout.CENTER);
        this.add(Northpanel, BorderLayout.NORTH);
        this.add(Westpanel, BorderLayout.WEST);
        this.add(Eastpanel, BorderLayout.EAST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 550);
        this.setVisible(true);
        ImageIcon ImdbIcon = new ImageIcon("IMDBLogo.png");
        this.setIconImage(ImdbIcon.getImage());
        //this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuButton){
            this.dispose();
            new MenuPage("Menu");
        }
        if(e.getSource() == logout){
            this.dispose();
            new LoginPage("Login");
        }
        if (e.getSource() == genreFilters) {
            for (Production prod : IMDB.getInstance().recommendations) {
                if (IMDB.getInstance().recommendations1.contains(prod))
                    IMDB.getInstance().recommendations1.remove(prod);
            }
            listModel.removeAllElements();

            for (Production prod : IMDB.getInstance().recommendations) {
                IMDB.getInstance().recommendations1.add(prod);
                listModel.addElement(prod);
            }

//System.out.println(genreFilters.getSelectedItem());
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

            for (Production prod : IMDB.getInstance().recommendations) {
                if (prod.genres.contains(genre) == false && genre != null) {
                    IMDB.getInstance().recommendations1.remove(prod);
                    listModel.removeElement(prod);
                }
            }

        }else if(e.getSource() == details && prod != null){
            this.setVisible(false);
            ProductionDetailsPage ProdPage = new ProductionDetailsPage("Production Details", prod);
        }else if(e.getSource() == searchButton){
            if(search.getText() == null || search.getText().equals("Search...")){
                JOptionPane.showMessageDialog(null, "Type in search bar!", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                String name = search.getText().toLowerCase();
                boolean ok = false;
                for(Production prod : IMDB.getInstance().productions){
                    String title = prod.title.toLowerCase();
                    if(title.equals(name)){
                        ok = true;
                        this.setVisible(false);
                        ProductionDetailsPage ProdPage = new ProductionDetailsPage("Production Details", prod);
                        break;
                    }
                }
                if(!ok) {
                    name = search.getText().toLowerCase();
                    for (Actor actor : IMDB.getInstance().actors) {
                        String ActorName = actor.name.toLowerCase();
                        if (ActorName.equals(name)) {
                            ok = true;
                            this.setVisible(false);
                            ActorDetailsPage ActorPage = new ActorDetailsPage("Actor Details", actor);
                            break;
                        }
                    }
                }

                if(!ok){
                    JOptionPane.showMessageDialog(null, "Actor or Production not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(prodList.isSelectionEmpty()){
            return;
        }
        if(e.getValueIsAdjusting() == false){
            int indexList = prodList.getSelectedIndex();
             prod = IMDB.getInstance().recommendations1.get(indexList);
            if(prod.title.equals("Mad Max: Fury Road")){
                Imagelabel.setIcon(new ImageIcon("Images/" + "Mad Max Fury Road" +".jpg"));
            }else if(prod.title.equals("The Lord of the Rings: The Return of the King")){
                Imagelabel.setIcon(new ImageIcon("Images/" + "The Lord of the Rings The Return of the King" +".jpg"));
            }else {
                Imagelabel.setIcon(new ImageIcon("Images/" + prod.title + ".jpg"));
            }
            String text = "Genres:";
            if(prod.genres != null) {
                for (Production.Genres genre : prod.genres) {
                    text += " " + genre.toString();
                }
            }
            Imagelabel.setText(text);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(filterByRating.isSelected()){
            IMDB.getInstance().recommendations1.sort(new Production.ProductionRatingsSort());
            listModel.removeAllElements();
            for (Production prod : IMDB.getInstance().recommendations1) {
                listModel.addElement(prod);
            }
        }
    }
}

