import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class ActorDetailsPageMenu extends JFrame implements ActionListener {
    JLabel imageLabel, nameLabel, biographyLabel, performancesLabel;
    JTextArea biography;
    JPanel panel;
    JScrollPane scrollPane;
    JList<Actor.Pair> performances;
    JButton favorites, back;
    Actor actor;

    public ActorDetailsPageMenu(String name, Actor actor) {
        super(name);

        this.actor = actor;
        panel = new JPanel();
        panel.setSize(700, 550);
        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBackground(new Color(221, 221, 221));

        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("Images/" + actor.name + ".jpg"));
        imageLabel.setBounds(30, 60, 230, 300);


        favorites = new JButton("Add to favorites");
        favorites.setBounds(50, 390, 180, 30);
        favorites.setFocusable(false);
        favorites.setBorder(BorderFactory.createEtchedBorder());
        favorites.addActionListener(this);

        back = new JButton("Back");
        back.setBounds(30, 15, 90, 30);
        back.setFocusable(false);
        back.setBorder(BorderFactory.createEtchedBorder());
        back.addActionListener(this);


        nameLabel = new JLabel();
        nameLabel.setText("Name: " + actor.name);
        nameLabel.setFont(new Font("Calibri", Font.BOLD, 22));
        nameLabel.setBounds(290, 60, 300, 30);

        biographyLabel = new JLabel();
        biographyLabel.setText("Biography:");
        biographyLabel.setFont(new Font("Calibri", Font.BOLD, 22));
        biographyLabel.setBounds(290, 55, 300, 130);

        biography = new JTextArea();
        biography.setText(actor.biography);
        biography.setFocusable(false);
        biography.setBackground(panel.getBackground());
        biography.setFont(new Font("Calibri", Font.BOLD, 16));
        biography.setLineWrap(true);
        biography.setEditable(false);
        biography.setBounds(290, 135, 340, 200);

        String title, type;
        DefaultListModel<Actor.Pair> listModel = new DefaultListModel<>();
        for (Actor.Pair pair : actor.performances) {
            listModel.addElement(pair);
        }

        performancesLabel = new JLabel("Performances:");
        performancesLabel.setFont(new Font("Calibri", Font.BOLD, 22));
        performancesLabel.setBounds(290, 330, 300, 30);

        performances = new JList<>(listModel);
        performances.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(performances);
        scrollPane.setBounds(290, 360, 260, 100);
        scrollPane.setBackground(panel.getBackground());


        panel.add(imageLabel);
        panel.add(nameLabel);
        panel.add(biography);
        panel.add(biographyLabel);
        panel.add(scrollPane);
        panel.add(performancesLabel);
       // panel.add(favorites);
        panel.add(back);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 550);
        this.setVisible(true);
        this.add(panel);


        ImageIcon ImdbIcon = new ImageIcon("IMDBLogo.png");
        this.setIconImage(ImdbIcon.getImage());
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            this.dispose();
        } else if (e.getSource() == favorites) {
            IMDB.getInstance().connectedUser.addFavoriteActor(actor.name);
            JOptionPane.showMessageDialog(null, "Successfully added!", "Info", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
