package model;

import dao.UserDAO;
import dao.CardDAO;

import java.util.List;
import java.util.Objects;

public class Function {
    public void register(String userName, String passWord, float balance, String fullName,
                         int age, String sex, String address) {
        List<Card> cardList = CardDAO.getInstance().selectAll();
        for (int index = 0; index < cardList.size(); index++) {
            if (Objects.equals(cardList.get(index).getUserName(), userName)) {
                System.out.println("This account has already existed!");
                return;
            }
        }
        User user = new User(userName, fullName, age, sex, address);
        Card card = new Card(userName, passWord, balance);
        UserDAO.getInstance().insert(user);
        CardDAO.getInstance().insert(card);
        System.out.println("Sign Up Success!");
    }

    public boolean login(String userName, String passWord) {
        List<Card> cardList = CardDAO.getInstance().selectAll();
        for (int index = 0; index < cardList.size(); index++) {
            if (Objects.equals(cardList.get(index).getUserName(), userName) &&
                    Objects.equals(cardList.get(index).getPassWord(), passWord)) {
                System.out.println("Logged in successfully!");
                return true;
            }
        }
        return false;
    }

    public void transfer(String userName, String recipientAccount, float amountOfMoney) {
        Card card = CardDAO.getInstance().selectById(userName);
        List<Card> cardList = CardDAO.getInstance().selectAll();
        for (int index = 0; index < cardList.size(); index++) {
            if (Objects.equals(cardList.get(index).getUserName(), recipientAccount)) {
                float newRecipientBalance = cardList.get(index).getBalance() + amountOfMoney;
                cardList.get(index).setBalance(newRecipientBalance);
                float newBalance = card.getBalance() - amountOfMoney;
                card.setBalance(newBalance);
                CardDAO.getInstance().update(card);
                CardDAO.getInstance().update(cardList.get(index));
                System.out.println("Transfer money successfully!");
                return;
            }
        }
        System.out.println("The recipient does not exist!");
    }

    public void viewAccountInformation(String userName) {
        User user = UserDAO.getInstance().selectById(userName);
        Card card = CardDAO.getInstance().selectById(userName);
        String information = "Name : %s\nAge: %s\nSex: %s\nAddress: %s\nUser Name: %s\n Balance: %s".
                formatted(user.getFullName(), String.valueOf(user.getAge()), user.getSex(),
                        user.getAddress(), card.getUserName(), String.valueOf(card.getBalance()));
        System.out.println(information);
    }

    public void changePassWord(String userName, String oldPassWord, String newPassWord) {
        Card card = CardDAO.getInstance().selectById(userName);
        if (Objects.equals(card.getPassWord(), oldPassWord)) {
            card.setPassWord(newPassWord);
            CardDAO.getInstance().update(card);
            System.out.println("changed password successfully!");
            return;
        }
        System.out.println("Password entered is incorrect!");
    }
}
