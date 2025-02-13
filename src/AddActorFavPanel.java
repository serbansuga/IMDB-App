import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddActorFavPanel extends JPanel implements ActionListener, ListSelectionListener {
    DefaultListModel listModelActors;
    JList actorList;
    JScrollPane actorPane;
    JLabel addActorFavLabel;
    JButton addActorFavButton;
    Actor actor;
    MenuPage menu;
    public AddActorFavPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        listModelActors = new DefaultListModel<>();
        for(Actor actor : IMDB.getInstance().actors){
            listModelActors.addElement(actor);
        }

        actorList = new JList<>(listModelActors);
        actorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        actorList.addListSelectionListener(this);
        actorPane = new JScrollPane(actorList);
        actorPane.setBounds(40,80,500, 280);

        addActorFavLabel = new JLabel("Choose actor you want to add to favorite:");
        addActorFavLabel.setBounds(40,50,500,25);
        addActorFavLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        addActorFavButton = new JButton("Add to favorite");
        addActorFavButton.setBounds(40, 380,130,40);
        addActorFavButton.setFocusable(false);
        addActorFavButton.setBorder(BorderFactory.createEtchedBorder());
        addActorFavButton.addActionListener(this);

        this.add(actorPane);
        this.add(addActorFavButton);
        this.add(addActorFavLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addActorFavButton) {
            if (actorList.isSelectionEmpty() == false) {
                int indexList = actorList.getSelectedIndex();
                actor = IMDB.getInstance().actors.get(indexList);

                if (IMDB.getInstance().connectedUser.favorites.contains(actor) == false) {
                    IMDB.getInstance().connectedUser.addFavoriteActor(actor.name);
                    JOptionPane.showMessageDialog(null, "Actor added to favorites", "Info", JOptionPane.INFORMATION_MESSAGE);
                    menu.dispose();
                    new MenuPage("Menu");
                } else {
                    JOptionPane.showMessageDialog(null, "This actor is already to favorites", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Select an actor!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
