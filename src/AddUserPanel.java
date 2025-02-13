import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddUserPanel extends JPanel implements ActionListener {
    JLabel firstNameLabel, lastNameLabel, emailLabel, usernameLabel, userTypeLabel, passUserLabel, countryLabel, ageLabel,
            genderChooseLabel, birthDateLabel;
    JTextField firstName, lastName, emailUser, username, countryUser, ageUser, birthDate;
    JPasswordField passUser;
    JComboBox genderChoose, userType;
    JButton addUserButton;
    MenuPage menu;
    public AddUserPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(40,30,100,25);
        firstNameLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        firstName = new JTextField();
        firstName.setBounds(145,30,300,25);
        firstName.setFont(new Font("Calibri", Font.PLAIN, 17));

        lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(40,60,100,25);
        lastNameLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        lastName = new JTextField();
        lastName.setBounds(145,60,300,25);
        lastName.setFont(new Font("Calibri", Font.PLAIN, 17));

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(40,90,100,25);
        emailLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        emailUser = new JTextField();
        emailUser.setBounds(145,90,300,25);
        emailUser.setFont(new Font("Calibri", Font.PLAIN, 17));

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(40,120,100,25);
        usernameLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        username = new JTextField();
        username.setBounds(145,120,300,25);
        username.setFont(new Font("Calibri", Font.PLAIN, 17));

        passUserLabel = new JLabel("Password:");
        passUserLabel.setBounds(40,150,100,25);
        passUserLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        passUser = new JPasswordField();
        passUser.setBounds(145,150,300,25);
        passUser.setFont(new Font("Calibri", Font.PLAIN, 17));

        countryLabel = new JLabel("Country:");
        countryLabel.setBounds(40,180,100,25);
        countryLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        countryUser = new JTextField();
        countryUser.setBounds(145,180,150,25);
        countryUser.setFont(new Font("Calibri", Font.PLAIN, 17));

        ageLabel = new JLabel("Age:");
        ageLabel.setBounds(40,210,100,25);
        ageLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        ageUser = new JTextField();
        ageUser.setBounds(145,210,150,25);
        ageUser.setFont(new Font("Calibri", Font.PLAIN, 17));


        genderChooseLabel = new JLabel("Gender:");
        genderChooseLabel.setBounds(40,240,100,25);
        genderChooseLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        String[] genders = {"Male", "Female"};
        genderChoose = new JComboBox<>(genders);
        genderChoose.addActionListener(this);
        genderChoose.setBounds(145,240,150,25);

        birthDateLabel = new JLabel("BirthDate:");
        birthDateLabel.setBounds(40,270,100,25);
        birthDateLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        birthDate = new JTextField();
        birthDate.setBounds(145,270,150,25);
        birthDate.setFont(new Font("Calibri", Font.PLAIN, 17));

        userTypeLabel = new JLabel("Type:");
        userTypeLabel.setBounds(40,300,100,25);
        userTypeLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        String[] usertypes = {"Regular", "Contributor", "Admin"};
        userType = new JComboBox<>(usertypes);
        userType.addActionListener(this);
        userType.setBounds(145,300,150,25);

        addUserButton = new JButton("Add user");
        addUserButton.setBounds(40, 340,130,40);
        addUserButton.setFocusable(false);
        addUserButton.setBorder(BorderFactory.createEtchedBorder());
        addUserButton.addActionListener(this);

        this.add(firstNameLabel);
        this.add(firstName);
        this.add(lastNameLabel);
        this.add(lastName);
        this.add(emailLabel);
        this.add(emailUser);
        this.add(usernameLabel);
        this.add(username);
        this.add(countryUser);
        this.add(ageUser);
        this.add(countryLabel);
        this.add(ageLabel);
        this.add(genderChooseLabel);
        this.add(genderChoose);
        this.add(birthDateLabel);
        this.add(birthDate);
        this.add(userTypeLabel);
        this.add(userType);
        this.add(addUserButton);
        this.add(passUser);
        this.add(passUserLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addUserButton) {
            String username1 = username.getText().toLowerCase();
            if (username1.equals("") == false) {
                String firstname = firstName.getText().toLowerCase();
                String lastname = lastName.getText().toLowerCase();
                boolean ok = true;
                for (User user : IMDB.getInstance().users) {
                    if (user.username.equals(username1)) {
                        ok = false;
                    }
                }
                if (ok) {
                    if (username1.contains(firstname) && username1.contains(lastname)) {
                        try {
                            Integer age = Integer.parseInt(ageUser.getText());
                            String email = emailUser.getText();
                            String country = countryUser.getText();
                            String name = firstName.getText() + " " + lastName.getText();
                            String gender = (String) genderChoose.getSelectedItem();
                            String password = passUser.getText();
                            if(!password.equals("")) {
                                if (birthDate.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "Enter a valid birthDate", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    String date = (String) birthDate.getText();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    LocalDate birthDate1 = LocalDate.parse(date, formatter);

                                    String userType1 = (String) userType.getSelectedItem();
                                    User.AccountTypes accountType;
                                    int experience;
                                    if (userType1.equals("Regular")) {
                                        accountType = User.AccountTypes.REGULAR;
                                        experience = 0;
                                    } else if (userType1.equals("Contributor")) {
                                        accountType = User.AccountTypes.CONTRIBUTOR;
                                        experience = 0;
                                    } else {
                                        accountType = User.AccountTypes.ADMIN;
                                        experience = 10000;
                                    }
                                    Credentials credentials = new Credentials(email, password);
                                    User.Information information = new User.Information.InformationBuilder(credentials, name)
                                            .country(country)
                                            .gender(gender)
                                            .age(age)
                                            .birthDate(birthDate1)
                                            .build();

                                    User new_user = UserFactory.factory(accountType, information, username.getText(), experience);
                                    ((Admin) IMDB.getInstance().connectedUser).addUser(new_user);
                                    menu.dispose();
                                    new MenuPage("Menu");

                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "Enter a password.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Enter a valid number for age.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username should include User's name.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "This username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Enter username!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
