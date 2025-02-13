import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ViewNotificationsPanel extends JPanel implements ListSelectionListener {
    DefaultListModel listModelNotfications;
    JList notificationsList;
    JScrollPane notificationsPane;
    JLabel notificationsLabel;
    public ViewNotificationsPanel(){

        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        listModelNotfications = new DefaultListModel<>();
        for(Object notification : IMDB.getInstance().connectedUser.notifications){
            listModelNotfications.addElement(notification);
        }

        notificationsList = new JList<>(listModelNotfications);
        notificationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notificationsList.addListSelectionListener(this);
        notificationsPane = new JScrollPane(notificationsList);
        notificationsPane.setBounds(60,60,700, 200);

        notificationsLabel = new JLabel();
        notificationsLabel.setText("Notifications:");
        notificationsLabel.setBounds(60,35,210,25);
        notificationsLabel.setFont(new Font("Calibri", Font.BOLD, 20));

        this.add(notificationsPane);
        this.add(notificationsLabel);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
