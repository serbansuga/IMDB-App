import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelUserPanel extends JPanel implements ActionListener, ListSelectionListener {
    JLabel delUserLabel;
    DefaultListModel listModeldelUser;
    JList delUserList;
    JScrollPane delUserPane;
    JButton delUserButton;
    MenuPage menu;
    public DelUserPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        delUserLabel = new JLabel("Choose user to delete:");
        delUserLabel.setBounds(40,40,400,25);
        delUserLabel.setFont(new Font("Calibri", Font.BOLD, 18));


        listModeldelUser = new DefaultListModel<>();
        for(User user : IMDB.getInstance().users){
            listModeldelUser.addElement(user);
        }

        delUserList = new JList<>(listModeldelUser);
        delUserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        delUserList.addListSelectionListener(this);
        delUserPane = new JScrollPane(delUserList);
        delUserPane.setBounds(40,65,350, 280);


        delUserButton = new JButton("Delete user");
        delUserButton.setBounds(40, 365,130,40);
        delUserButton.setFocusable(false);
        delUserButton.setBorder(BorderFactory.createEtchedBorder());
        delUserButton.addActionListener(this);

        this.add(delUserLabel);
        this.add(delUserPane);
        this.add(delUserButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == delUserButton){
            if(delUserList.isSelectionEmpty() == false){
                int indexList = delUserList.getSelectedIndex();
                User user = IMDB.getInstance().users.get(indexList);
                if(user == IMDB.getInstance().connectedUser){
                    ((Admin)IMDB.getInstance().connectedUser).delUser(user);
                    JOptionPane.showMessageDialog(null, "Your account has been deleted.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    menu.dispose();
                    new LoginPage("Login");
                }else {
                    ((Admin) IMDB.getInstance().connectedUser).delUser(user);
                    JOptionPane.showMessageDialog(null, "User deleted", "Info", JOptionPane.INFORMATION_MESSAGE);
                    menu.dispose();
                    new MenuPage("Menu");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Select an user!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
