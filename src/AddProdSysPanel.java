import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProdSysPanel extends JPanel implements ActionListener {
    JLabel prodTitleLabel, prodDescriptionLabel, prodRatingLabel;
    JTextField prodTitle, prodDescription, prodRating;
    JButton addProdSysButton;
    JRadioButton movie, series;
    MenuPage menu;
    public AddProdSysPanel(MenuPage menu){
        this.menu = menu;
        this.setLayout(null);
        this.setVisible(false);
        this.setBackground(new Color(221,221,221));
        this.setSize(800, 600);

        prodTitleLabel = new JLabel("Production title:");
        prodTitleLabel.setBounds(30,40,210,25);
        prodTitleLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        prodDescriptionLabel = new JLabel("Production description:");
        prodDescriptionLabel.setBounds(30,110,210,25);
        prodDescriptionLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        prodRatingLabel = new JLabel("Production rating:");
        prodRatingLabel.setBounds(30,170,210,25);
        prodRatingLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        prodTitle = new JTextField();
        prodDescription = new JTextField();
        prodRating = new JTextField();
        prodTitle.setBounds(30,70,300,25);
        prodTitle.setFont(new Font("Calibri", Font.PLAIN, 17));

        prodDescription.setBounds(30,135,300,25);
        prodDescription.setFont(new Font("Calibri", Font.PLAIN, 17));

        prodRating.setBounds(30,195,300,25);
        prodRating.setFont(new Font("Calibri", Font.PLAIN, 17));

        addProdSysButton = new JButton("Add production");
        addProdSysButton.setBounds(160, 245,130,40);
        addProdSysButton.setFocusable(false);
        addProdSysButton.setBorder(BorderFactory.createEtchedBorder());
        addProdSysButton.addActionListener(this);

        movie = new JRadioButton("Movie");
        series = new JRadioButton("Series");
        movie.setBounds(30,230, 100,30);
        series.setBounds(30,265,100,30);
        this.add(prodTitle);
        this.add(prodDescription);
        this.add(prodRating);
        this.add(movie);
        this.add(series);
        this.add(addProdSysButton);
        this.add(prodTitleLabel);
        this.add(prodDescriptionLabel);
        this.add(prodRatingLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addProdSysButton) {
            boolean ok = true;
            if (!(IMDB.getInstance().connectedUser instanceof Regular<?>)) {
                if (movie.isSelected()) {
                    try {
                        Double rating = Double.parseDouble(prodRating.getText());
                        if(rating >=1 && rating <= 10){
                            String title = prodTitle.getText().toLowerCase();
                            for(Production prod : IMDB.getInstance().productions){
                                String prodTitle = prod.title.toLowerCase();
                                if(prodTitle.equals(title)){
                                    ok = false;
                                    break;
                                }
                            }
                            if(ok) {
                                ((Staff<?>) IMDB.getInstance().connectedUser).addProductionSystem(new Movie(prodTitle.getText(), prodDescription.getText(), rating, null, null));
                                JOptionPane.showMessageDialog(null, "Production added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                                menu.dispose();
                                new MenuPage("Menu");
                            }else{
                                JOptionPane.showMessageDialog(null, "Production already exists.", "Info", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Rating should be between 1 and 10.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Enter a valid number for rating!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (series.isSelected()) {
                    try{
                        Double rating = Double.parseDouble(prodRating.getText());
                        if(rating >= 1 && rating <= 10) {
                            ((Staff<?>) IMDB.getInstance().connectedUser).addProductionSystem(new Series(prodTitle.getText(), prodDescription.getText(), rating, null, null));
                            JOptionPane.showMessageDialog(null, "Production added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                            menu.dispose();
                            new MenuPage("Menu");
                        }else{
                            JOptionPane.showMessageDialog(null, "Rating should be between 1 and 10.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Enter a valid number for rating!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Choose production type!", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        }
    }
}
