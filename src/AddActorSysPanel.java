import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddActorSysPanel extends JPanel implements ActionListener {
    JTextField actorName, actorBio;
    JLabel actorNameLabel, actorBioLabel;
    JButton addActorSysButton;
    MenuPage menu;
    public AddActorSysPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        actorName = new JTextField();
        actorBio = new JTextField();

        actorNameLabel = new JLabel("Enter actor name:");
        actorNameLabel.setBounds(40,95,500,25);
        actorNameLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        actorName.setBounds(40,120,300,25);
        actorName.setFont(new Font("Calibri", Font.PLAIN, 17));

        actorBioLabel= new JLabel("Enter actor biography:");
        actorBioLabel.setBounds(40,155,500,25);
        actorBioLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        actorBio.setBounds(40,180,300,25);
        actorBio.setFont(new Font("Calibri", Font.PLAIN, 17));

        addActorSysButton = new JButton("Add actor");
        addActorSysButton.setBounds(40, 220,130,40);
        addActorSysButton.setFocusable(false);
        addActorSysButton.setBorder(BorderFactory.createEtchedBorder());
        addActorSysButton.addActionListener(this);


        this.add(actorName);
        this.add(actorBio);
        this.add(addActorSysButton);
        this.add(actorBioLabel);
        this.add(actorNameLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addActorSysButton) {
            boolean ok = true;
            if (!(IMDB.getInstance().connectedUser instanceof Regular<?>)) {
                if (actorName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete actor name!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (actorBio.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Complete actor biography!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    String ActorName = actorName.getText().toLowerCase();
                    for(Actor actor : IMDB.getInstance().actors){
                        String name = actor.name.toLowerCase();
                        if(name.equals(ActorName)){
                            ok = false;
                            break;
                        }
                    }
                    if(ok) {
                        ((Staff<?>) IMDB.getInstance().connectedUser).addActorSystem(new Actor(actorName.getText(), actorBio.getText()));
                        JOptionPane.showMessageDialog(null, "Actor added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                        menu.dispose();
                        new MenuPage("Menu");
                    }else{
                        JOptionPane.showMessageDialog(null, "Actor already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
