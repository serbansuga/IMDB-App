import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener, ItemListener {
    JLabel Login, Email, Password, ImdbImage;
    JTextField EmailField;
    JPasswordField PasswordField;
    JPanel WestPanel;
    JPanel EastPanel;
    JCheckBox ShowPassw;
    JButton loginButton, exit;

    MainPage main;

    public LoginPage(String name){
        super(name);
        IMDB.getInstance().loginPage = this;
        WestPanel = new JPanel();
        EastPanel = new JPanel();

        EastPanel.setLayout(new BorderLayout());
        EastPanel.setPreferredSize(new Dimension(500,450));
        ImageIcon Imdbimage = new ImageIcon("IMBDLOGO (2).jpg");
        ImdbImage = new JLabel();
        ImdbImage.setIcon(Imdbimage);
        ImdbImage.setPreferredSize(new Dimension(200,200));
        EastPanel.add(ImdbImage, BorderLayout.CENTER);

        EastPanel.setBackground(new Color(159, 121, 108));
        //EastPanel.setBounds(250, 0, 400, 400);

//        JPanel Logindetails = new JPanel();
//        Logindetails.setLayout(null);
//        Logindetails.setBackground(Color.darkGray);
//        Logindetails.setBounds(50,50, 225,325);

        Login = new JLabel("IMDB - Login");
        Login.setBounds(100,50,150,50);
        //Login.setHorizontalAlignment(SwingConstants.CENTER);
        Login.setFont(new Font("Calibri", Font.PLAIN, 22));

        Email = new JLabel("Email Address");
        Email.setBounds(60,120,150,50);
       // Email.setHorizontalAlignment(SwingConstants.CENTER);
        Email.setFont(new Font("Calibri", Font.PLAIN, 15));

        EmailField = new JTextField();
        //EmailField.setMinimumSize(new Dimension(150,50));

//        EmailField.setHorizontalAlignment(SwingConstants.CENTER);
        EmailField.setBounds(60,155,210,25);
        EmailField.setFont(new Font("Calibri", Font.PLAIN, 15));

        Password = new JLabel("Password");
        Password.setBounds(60,180,150,50);
        // Email.setHorizontalAlignment(SwingConstants.CENTER);
        Password.setFont(new Font("Calibri", Font.PLAIN, 15));


        PasswordField = new JPasswordField();
        //EmailField.setMinimumSize(new Dimension(150,50));

//        EmailField.setHorizontalAlignment(SwingConstants.CENTER);
        PasswordField.setBounds(60,215,210,25);
        PasswordField.setFont(new Font("Calibri", Font.PLAIN, 15));

        ShowPassw = new JCheckBox("Show password");
        ShowPassw.setBounds(150, 245, 150, 30);
        ShowPassw.setFont(new Font(null, Font.PLAIN, 13));
        ShowPassw.setFocusable(false);
        ShowPassw.setOpaque(false);
        PasswordField.setEchoChar('*');
        ShowPassw.addItemListener(this);

        loginButton = new JButton("Login");
        loginButton.setBounds(60,285,100,35);
        // Email.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton.setFont(new Font(null, Font.PLAIN, 15));
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        loginButton.setMnemonic(KeyEvent.VK_ENTER);
        loginButton.setBorder(BorderFactory.createEtchedBorder());

        exit = new JButton("Exit");
        exit.setBounds(170,285,100,35);
        // Email.setHorizontalAlignment(SwingConstants.CENTER);
        exit.setFont(new Font(null, Font.PLAIN, 15));
        exit.setFocusable(false);
        exit.addActionListener(this);
        exit.setBorder(BorderFactory.createEtchedBorder());
        
        WestPanel.setLayout(null);
        WestPanel.setPreferredSize(new Dimension(325,1000));
        WestPanel.setBackground(new Color( 233, 229, 205));
        WestPanel.add(Login);
        // WestPanel.add(Logindetails);
        WestPanel.add(Email);
        WestPanel.add(EmailField);
        WestPanel.add(Password);
        WestPanel.add(PasswordField);
        WestPanel.add(ShowPassw);
        WestPanel.add(loginButton);
        WestPanel.add(exit);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(825, 450);
        this.setVisible(true);
        this.add(WestPanel, BorderLayout.WEST);
        this.add(EastPanel, BorderLayout.EAST);
        ImageIcon ImdbIcon = new ImageIcon("IMDBLogo.png");
        this.setIconImage(ImdbIcon.getImage());
         //this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit){
            System.exit(0);
        }else if(e.getSource() == loginButton){
            String email = EmailField.getText();
            String passw = PasswordField.getText();
            if(email.isEmpty() || passw.isEmpty()){
                JOptionPane.showMessageDialog(null, "Complete all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                boolean ok = false;
                for(User user : IMDB.getInstance().users){
                    if(user.information.credentials.email.equals(email) && user.information.credentials.password.equals(passw)){
                        ok = true;
                        IMDB.getInstance().connectedUser = user;
                        break;
                    }
                }
                if(ok){
                    JOptionPane.showMessageDialog(null, "Successfully logged in!", "Login", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                    main =  new MainPage("MainPage");
                }else{
                    JOptionPane.showMessageDialog(null, "Email or password incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            PasswordField.setEchoChar((char) 0);
        }else{
            PasswordField.setEchoChar('*');
        }
    }
}
