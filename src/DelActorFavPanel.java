import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelActorFavPanel extends JPanel implements ActionListener, ListSelectionListener {
    DefaultListModel listModelActorFav;
    JList actorFavList;
    JScrollPane scrollActorFav;
    JLabel delActorFavLabel;
    JButton delActorFavButton;
    Actor actor;
    public DelActorFavPanel(){
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        IMDB.getInstance().actorFavorites.clear();
        listModelActorFav = new DefaultListModel<>();
        listModelActorFav.removeAllElements();
        for(Object obj : IMDB.getInstance().connectedUser.favorites){
            if(obj instanceof Actor) {
                IMDB.getInstance().actorFavorites.add((Actor) obj);
                listModelActorFav.addElement((Actor)obj);
            }
        }

        actorFavList = new JList<>(listModelActorFav);
        actorFavList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        actorFavList.addListSelectionListener(this);
        scrollActorFav = new JScrollPane(actorFavList);
        scrollActorFav.setBounds(40,80,500, 280);

        delActorFavLabel = new JLabel("Choose actor you want to delete:");
        delActorFavLabel.setBounds(40,55,400,25);
        delActorFavLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        delActorFavButton = new JButton("Delete from favorite");
        delActorFavButton.setBounds(40, 390,130,40);
        delActorFavButton.setFocusable(false);
        delActorFavButton.setBorder(BorderFactory.createEtchedBorder());
        delActorFavButton.addActionListener(this);

        this.add(scrollActorFav);
        this.add(delActorFavButton);
        this.add(delActorFavLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delActorFavButton) {
            if (actorFavList.isSelectionEmpty() == false) {
                int nr = 0;
                int indexList = actorFavList.getSelectedIndex();
                actor = IMDB.getInstance().actorFavorites.get(indexList);
                IMDB.getInstance().connectedUser.delFavoriteActor(actor);
                listModelActorFav.removeElement(actor);
                IMDB.getInstance().actorFavorites.remove(actor);
                JOptionPane.showMessageDialog(null, "Actor deleted from favorites!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Select an actor!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
