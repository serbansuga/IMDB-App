import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddReviewMenu extends JFrame implements ActionListener {
    JTextField userRating;
    JTextArea comment;
    JLabel rating, commentLabel;

    JButton submit, back;

    JPanel panel;

    Production prod;
    public AddReviewMenu(String name, Production prod){
        super(name);
        this.prod = prod;

        panel = new JPanel();
        panel.setSize(300, 400);
        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBackground(new Color(221, 221, 221));

        rating = new JLabel("Enter your rating: ");
        rating.setBounds(30,60, 200,30);
        rating.setFont(new Font("Calibri", Font.PLAIN, 20));

        userRating = new JTextField();
        userRating.setBounds(30,95,100,25);
        userRating.setFont(new Font("Calibri", Font.PLAIN, 17));

        commentLabel = new JLabel("Enter your comment: ");
        commentLabel.setBounds(30,135, 200,30);
        commentLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        comment = new JTextArea();
        comment.setFont(new Font("Calibri", Font.BOLD, 16));
        comment.setLineWrap(true);
        comment.setBounds(30, 165, 200, 100);


        submit = new JButton("Submit review");
        submit.setBounds(30, 280, 140, 40);
        submit.setFocusable(false);
        submit.setBorder(BorderFactory.createEtchedBorder());
        submit.setFont(new Font("Calibri", Font.BOLD, 20));
        submit.addActionListener(this);

        back = new JButton("Back");
        back.setBounds(18, 13, 80, 25);
        back.setFocusable(false);
        back.setBorder(BorderFactory.createEtchedBorder());
        back.setFont(new Font("Calibri", Font.PLAIN, 20));
        back.addActionListener(this);

        panel.add(rating);
        panel.add(userRating);
        panel.add(comment);
        panel.add(commentLabel);
        panel.add(submit);
        panel.add(back);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 400);
        this.setVisible(true);
        this.add(panel);

        ImageIcon ImdbIcon = new ImageIcon("IMDBLogo.png");
        this.setIconImage(ImdbIcon.getImage());
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            if(userRating.getText().isEmpty() || comment.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Complete all information!", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                try {
                    Integer rating = Integer.parseInt(userRating.getText());
                    String comment1 = comment.getText();
                    if(IMDB.getInstance().connectedUser instanceof Regular<?>){
                        ((Regular<?>) IMDB.getInstance().connectedUser).addRating(prod.title, rating, comment1);
                    }
                    this.dispose();
                    ProductionDetailsPageMenu page = new ProductionDetailsPageMenu("Production details", prod);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a valid number for rating!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }else if(e.getSource() == back){
            ProductionDetailsPageMenu page = new ProductionDetailsPageMenu("Production details", prod);
        }
    }
}
