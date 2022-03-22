import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class App extends JFrame {
    private Scanner sc = new Scanner(System.in);
    private Set<Customer> customers = new TreeSet<>();
    private List<JButton> customerButtons = new ArrayList<>();
    private JTextField tfName, tfPrice, tfQuantity, tfQuantityForCustomers;
    private JButton addItem, addToCustomer, totalButton, computeTotalPerCustomer, clearBill;
    private JTextArea bill;
    private DefaultListModel<String> allProducts = new DefaultListModel<>();
    private JList productList;
    private JPanel panel;
    private Customer temp;
    private int index, total; double tip;

    public App(){
        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new JPanel();

        tfName = new JTextField("Name");
        panel.add(tfName);
        this.setContentPane(panel);
        tfName.setPreferredSize(new Dimension(120, 25));
        tfName.setVisible(true);

        tfPrice = new JTextField("Price");
        panel.add(tfPrice);
        tfPrice.setPreferredSize(new Dimension(120, 25));
        tfPrice.setVisible(true);

        tfQuantity = new JTextField("Quantity");
        panel.add(tfQuantity);
        tfQuantity.setVisible(true);
        tfQuantity.setPreferredSize(new Dimension(120, 25));

        addItem = new JButton("Add");
        panel.add(addItem);
        addItem.setVisible(true);
        addItem.setPreferredSize(new Dimension(120, 25));

        bill = new JTextArea();
        panel.add(bill);
        bill.setVisible(true);
        bill.setPreferredSize(new Dimension(250, 300));
        bill.setText("Produs Cantitate Pret\n\n");

        productList = new JList(allProducts);
        panel.add(productList);
        productList.setVisible(true);
        productList.setPreferredSize(new Dimension(250, 300));

        int noCustomers = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < noCustomers; i++){
            String name = sc.nextLine();
            JButton b = new JButton(name);
            panel.add(b);
            b.setVisible(true);
            b.setPreferredSize(new Dimension(120, 25));
            customerButtons.add(b);
        }

        tfQuantityForCustomers = new JTextField("Quantity");
        panel.add(tfQuantityForCustomers);
        tfQuantityForCustomers.setVisible(true);
        tfQuantityForCustomers.setPreferredSize(new Dimension(120, 25));

        addToCustomer = new JButton("Add");
        panel.add(addToCustomer);
        addToCustomer.setVisible(true);
        addToCustomer.setPreferredSize(new Dimension(120, 25));

        totalButton = new JButton("Total");
        panel.add(totalButton);
        totalButton.setVisible(true);
        totalButton.setPreferredSize(new Dimension(120, 25));

        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(allProducts.isEmpty()){
                    allProducts.insertElementAt((tfName.getText() + " " + tfQuantity.getText() + " " + Integer.parseInt(tfPrice.getText())*Integer.parseInt(tfQuantity.getText())), index++);
                    bill.append(tfName.getText() + " " + tfQuantity.getText() + " " + Integer.parseInt(tfPrice.getText())*Integer.parseInt(tfQuantity.getText()) + "\n");
                    total += Integer.parseInt(tfPrice.getText())*Integer.parseInt(tfQuantity.getText());
                }
                else{
                    bill.setText("Produs Cantitate Pret\n\n");
                    allProducts.insertElementAt((tfName.getText() + " " + tfQuantity.getText() + " " + Integer.parseInt(tfPrice.getText())*Integer.parseInt(tfQuantity.getText())), index++);
                    for(int i = 0; i < productList.getModel().getSize(); i++){
                        bill.append(productList.getModel().getElementAt(i).toString().split(" ")[0] + " " + Integer.parseInt(productList.getModel().getElementAt(i).toString().split(" ")[1]) + "x " + Integer.parseInt(productList.getModel().getElementAt(i).toString().split(" ")[2]) + " lei\n");
                    }
                    total += Integer.parseInt(tfPrice.getText())*Integer.parseInt(tfQuantity.getText());
                }

            }
        });

        for(JButton b : customerButtons){
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(customers.isEmpty()){
                        Customer c = new Customer(b.getText());
                        customers.add(c);
                        temp = c;
                    }
                    else{
                        for(Customer c : customers){
                            if(b.getText().equals(c.getName())){
                                temp = c;
                                return;
                            }
                            else {
                                Customer customer = new Customer(b.getText());
                                customers.add(customer);
                                temp = customer;
                                return;
                            }
                        }
                    }
                }
            });
        }

        addToCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.add(new Product(productList.getSelectedValue().toString().split(" ")[0], Integer.parseInt(productList.getSelectedValue().toString().split(" ")[2])/Integer.parseInt(productList.getSelectedValue().toString().split(" ")[1])), Integer.parseInt(tfQuantityForCustomers.getText()));
            }
        });

        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bill.setText("");
                bill.setText("Produs Cantitate Pret\n\n");
                for(int i = 0; i < productList.getModel().getSize(); i++){
                    bill.append(productList.getModel().getElementAt(i).toString().split(" ")[0] + " " + Integer.parseInt(productList.getModel().getElementAt(i).toString().split(" ")[1]) + "x " + Integer.parseInt(productList.getModel().getElementAt(i).toString().split(" ")[2]) + " lei\n");
                }
                    bill.append("Total: " + total + " lei\n");
                    tip = (double)total * 10/100;
                    bill.append("Tip: " + Math.ceil(tip) + " lei\n");

            }
        });

        computeTotalPerCustomer = new JButton("Customer Total");
        panel.add(computeTotalPerCustomer);
        computeTotalPerCustomer.setVisible(true);
        computeTotalPerCustomer.setPreferredSize(new Dimension(120, 25));

        computeTotalPerCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bill.setText("");
                for(Customer c : customers){
                    int total = 0;
                    bill.append(c.getName() + ":" + "\n");
                    for(Map.Entry<Product, Integer> product : c.getProducts().entrySet()){
                        bill.append(product.getKey().getName() + " " + product.getValue() + "x " + product.getKey().getPrice() * product.getValue() + " lei\n");
                        total += product.getValue() * product.getKey().getPrice();
                    }
                    bill.append("Total: " + total +" lei" + "\n\n");
                }
                bill.append("Tip per customer: " + Math.ceil(tip/noCustomers) + " lei");
            }
        });

        clearBill = new JButton("Clear");
        panel.add(clearBill);
        clearBill.setVisible(true);
        clearBill.setPreferredSize(new Dimension(120, 25));
        clearBill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            bill.setText("");
            bill.setText("Produs Cantitate Pret\n\n");
            }
        });

    }

    public static void main(String[] args) {
        new App();

    }
}
