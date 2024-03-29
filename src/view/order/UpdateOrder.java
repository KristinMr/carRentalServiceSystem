package view.order;

import util.*;
import util.Order;
import view.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class UpdateOrder extends JDialog {
    private JLabel adminIDLabel = new JLabel("管理员编号");
    private JTextField adminIDField = new JTextField();

    private JLabel adminNameLabel = new JLabel("管理员名称");
    private JTextField adminNameField = new JTextField();

    private JLabel userIDLabel = new JLabel("用户编号");
    private JTextField userIDField = new JTextField();

    private JLabel userNameLabel = new JLabel("用户名称");
    private JTextField userNameField = new JTextField();

    private JLabel carIDLabel = new JLabel("车辆编号");
    private JTextField carIDField = new JTextField();

    private JLabel carNumberLabel = new JLabel("车牌号");
    private JTextField carNumberField = new JTextField();

    private JLabel carBrandLabel = new JLabel("车辆品牌");
    private JTextField carBrandField = new JTextField();

    private JLabel carModelLabel = new JLabel("品牌型号");
    private JTextField carModelField = new JTextField();

    private JLabel carRentLabel = new JLabel("车辆租金（元/每天）");
    private JTextField carRentField = new JTextField();

    private JLabel userMoneyLabel = new JLabel("用户余额");
    private JTextField userMoneyField = new JTextField();

    private JLabel carStateLabel = new JLabel("车辆状态");
    private JTextField carStateField = new JTextField();

    private JLabel orderStateLabel = new JLabel("订单类型");
    private JComboBox<State> orderStateBox = new JComboBox<State>();

    private JLabel startDateLabel = new JLabel("起租日期");
    private JTextField startDateField = new JTextField();

    private JLabel endDateLabel = new JLabel("预计还车日期");
    private JTextField endDateField = new JTextField();

    private JLabel orderInfoLabel = new JLabel("新增备注");
    private JTextArea orderInfoArea = new JTextArea();

    private JButton resetButton = new JButton("重置");
    private JButton confirmButton = new JButton("确认修改订单");

    String orderStateID = null;
    String orderInfo = null;

    public UpdateOrder(Admin admin, String orderID) {
        setTitle("修改用户订单");
        setSize(850, 660);
        setLocationRelativeTo(null);
        setLayout(null);
        setModal(true);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\mrcap\\IdeaProjects\\CarRentalServiceSystem\\src\\source\\main.jpg");
        JLabel bgLabel = new JLabel(imageIcon);
        this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
        bgLabel.setBounds(-200, -200, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        this.getContentPane().add(new JLabel());
        ((JPanel) getContentPane()).setOpaque(false);

        adminIDLabel.setBounds(50, 30, 120, 30);
        adminIDField.setBounds(180, 30, 180, 30);
        adminIDField.setText(admin.getAdminID());
        adminIDField.setEditable(false);

        adminNameLabel.setBounds(430, 30, 120, 30);
        adminNameField.setBounds(550, 30, 180, 30);
        adminNameField.setText(admin.getAdminName());
        adminNameField.setEditable(false);

        userIDLabel.setBounds(50, 80, 120, 30);
        userIDField.setBounds(180, 80, 180, 30);
        userIDField.setEditable(false);

        userNameLabel.setBounds(430, 80, 80, 30);
        userNameField.setBounds(550, 80, 180, 30);
        userNameField.setEditable(false);

        carIDLabel.setBounds(50, 130, 120, 30);
        carIDField.setBounds(180, 130, 180, 30);
        carIDField.setEditable(false);

        carNumberLabel.setBounds(430, 130, 80, 30);
        carNumberField.setBounds(550, 130, 180, 30);
        carNumberField.setEditable(false);

        carBrandLabel.setBounds(50, 180, 120, 30);
        carBrandField.setBounds(180, 180, 180, 30);
        carBrandField.setEditable(false);

        carModelLabel.setBounds(430, 180, 80, 30);
        carModelField.setBounds(550, 180, 180, 30);
        carModelField.setEditable(false);

        carRentLabel.setBounds(30, 230, 180, 30);
        carRentField.setBounds(180, 230, 180, 30);
        carRentField.setEditable(false);

        userMoneyLabel.setBounds(430, 230, 80, 30);
        userMoneyField.setBounds(550, 230, 180, 30);
        userMoneyField.setEditable(false);

        carStateLabel.setBounds(30, 280, 180, 30);
        carStateField.setBounds(180, 280, 180, 30);
        carStateField.setEditable(false);

        orderStateLabel.setBounds(430, 280, 80, 30);
        orderStateBox.setBounds(550, 280, 180, 30);

        startDateLabel.setBounds(50, 330, 120, 30);
        startDateField.setBounds(180, 330, 180, 30);
        Chooser chooser = Chooser.getInstance();
        chooser.register(startDateField);

        endDateLabel.setBounds(430, 330, 120, 30);
        endDateField.setBounds(550, 330, 180, 30);
        Chooser chooser1 = Chooser.getInstance();
        chooser1.register(endDateField);

        orderInfoLabel.setBounds(50, 380, 120, 30);
        orderInfoArea.setBounds(180, 380, 550, 60);

        resetButton.setBounds(50, 495, 80, 30);
        confirmButton.setBounds(560, 490, 180, 40);


        add(adminIDLabel);
        add(adminIDField);
        add(adminNameLabel);
        add(adminNameField);
        add(userIDLabel);
        add(userIDField);
        add(userNameLabel);
        add(userNameField);
        add(carIDLabel);
        add(carIDField);
        add(carNumberLabel);
        add(carNumberField);
        add(carBrandLabel);
        add(carBrandField);
        add(carModelLabel);
        add(carModelField);
        add(carRentLabel);
        add(carRentField);
        add(userMoneyLabel);
        add(userMoneyField);
        add(carStateLabel);
        add(carStateField);
        add(orderStateLabel);
        add(orderStateBox);
        add(startDateLabel);
        add(startDateField);
        add(endDateLabel);
        add(endDateField);
        add(orderInfoLabel);
        add(orderInfoArea);
        add(resetButton);
        add(confirmButton);
        add(bgLabel);

        Connection connection = DButil.getConnection();
        String sql = "select user.user_id, user.user_name, car.car_id, car.car_number, brand.brand_name, model.model_name, model.model_rent, user.user_money, state.state_name, oorder.order_state,oorder.order_stime, oorder.order_etime, oorder.order_info from oorder, user, car, model, brand, state where oorder.order_id = ? and oorder.order_user = user.user_id and oorder.order_car = car.car_id and car.car_model = model.model_id and model.model_brand = brand.brand_id and car.car_state = state.state_id";
        String sql1 = "select * from state where state_recycle_bin = 0";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                order.setOrderUserID(rs.getString(1));
//                order.setOrderUserName(rs.getString(2));
//                order.setOrderCarID(rs.getString(3));
//                order.setOrderCarNumber(rs.getString(4));
//                order.setOrderCarBrand(rs.getString(5));
//                order.setOrderCarModel(rs.getString(6));
//                order.setOrderCarRent(rs.getString(7));
//                order.setOrderInfo(rs.getString(8));
//                order.setOrderCarState(rs.getString(9));
//                order.setOrderState(rs.getString(10));

                userIDField.setText(rs.getString(1));
                userNameField.setText(rs.getString(2));
                carIDField.setText(rs.getString(3));
                carNumberField.setText(rs.getString(4));
                carBrandField.setText(rs.getString(5));
                carModelField.setText(rs.getString(6));
                carRentField.setText(rs.getString(7));
                userMoneyField.setText(rs.getString(8));
                carStateField.setText(rs.getString(9));
                orderStateID = rs.getString(10);
                startDateField.setText(rs.getString(11));
                endDateField.setText(rs.getString(12));
                orderInfo = rs.getString(13);
            }
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                if ((rs1.getInt(3) == 2) && (!rs1.getString(2).equals("已结算"))) {
//                    state.setStateID(rs.getString(1));
//                    state.setStateName(rs.getString(2));
//                    carStateBox.addItem(state);
                    State state = new State();
                    state.setStateID(rs1.getString(1));
                    state.setStateName(rs1.getString(2));
                    orderStateBox.addItem(state);
                }
                for (int k = 0; k < orderStateBox.getItemCount(); k++) {
                    State state;
                    state = orderStateBox.getItemAt(k);
                    if (state.getStateID() == orderStateID) {
                        orderStateBox.setSelectedItem(state);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButil.releaseConnection(connection);
        }

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startDateField.setText("");
                endDateField.setText("");
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                long days2 = Period.between(LocalDate.parse(startDateField.getText()), LocalDate.parse(endDateField.getText())).getDays();
                if (days2 < 0) {
                    JOptionPane.showMessageDialog(null, "起租日期应在预计还车日期之前！请重新输入。");
                } else {
                    String carStateID = "";
                    String carStateName = "";
                    State orderState = (State) orderStateBox.getSelectedItem();
                    String orderStateID = orderState.getStateID();
                    String orderStateName = orderState.getStateName();
                    if (orderStateName.equals("预约")) {
                        carStateName = "被预约";
                    } else if (orderStateName.equals("租赁")) {
                        carStateName = "租赁中";
                    }

                    String orderStartDate = startDateField.getText();

                    System.out.println(orderStartDate);
                    String orderEndDate = endDateField.getText();
                    if (orderEndDate.equals("")) {
                        orderEndDate = null;
                    }
                    String addOrderInfo = orderInfoArea.getText();
                    Date date1 = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String orderInfo1 = String.format("%s修改时间：%s，修改者编号：%s，修改者名称：%s，添加备注：%s。      ", orderInfo, dateFormat.format(date1), admin.getAdminID(), admin.getAdminName(), addOrderInfo);
                    Connection connection1 = DButil.getConnection();
                    String sql = "select state_id from state where state_name = ?";
                    String sql1 = "update oorder set order_state = ?, order_stime = ?, order_etime = ?, order_info = ?, order_update_admin = ?, order_update_time = ? where order_id = ?";
                    String sql2 = "update car set car_state = ? where car_id = ?";
                    try {
                        PreparedStatement ps = connection1.prepareStatement(sql);
                        ps.setObject(1, carStateName);

                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            carStateID = rs.getString(1);
                            PreparedStatement ps1 = connection1.prepareStatement(sql1);
                            ps1.setObject(1, orderStateID);
                            ps1.setObject(2, orderStartDate);
                            ps1.setObject(3, orderEndDate);
                            ps1.setObject(4, orderInfo1);
                            ps1.setObject(5, admin.getAdminID());
                            ps1.setObject(6, dateFormat.format(new Date()));
                            ps1.setObject(7, orderID);

                            int n1 = ps1.executeUpdate();
                            if (n1 > 0) {
                                PreparedStatement ps2 = connection1.prepareStatement(sql2);
                                ps2.setObject(1, carStateID);
                                ps2.setObject(2, carIDField.getText());

                                int n2 = ps2.executeUpdate();
                                if (n2 > 0) {
                                    UpdateOrder.this.dispose();
                                    JOptionPane.showMessageDialog(null, "管理员：" + admin.getAdminName() + " 将用户" + userNameField.getText() + "的租车订单修改成功！");
                                    Main.main.removeAll();
                                    Main.main.repaint();
                                    Main.main.updateUI();

                                    Main.main.add(new OrderList(admin));
                                } else {
                                    JOptionPane.showMessageDialog(null, "管理员：" + admin.getAdminName() + " 将用户" + userNameField.getText() + "的租车订单修改成功！   但车辆状态更新失败！");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "修改订单失败！请重新操作！");
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        DButil.releaseConnection(connection1);
                    }
                }
            }
        });

        setVisible(true);
    }
}
