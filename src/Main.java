import academy.investmais.enums.AccountType;
import academy.investmais.model.people.User;

void main() {
    System.out.println("Invest Mais");

    User user1 = new User(AccountType.PERSONAL, "Julia", "julia@gmail.com");
    System.out.println(user1.getName());
    System.out.println(user1.getAccountType());
    System.out.println(user1.getUsers().toString());

    User user2 = new User(AccountType.BUSINESS, "JuliaTech", "juliatech@gmail.com");
    System.out.println(user2.getName());
    System.out.println(user2.getAccountType());
    System.out.println(user2.getUsers().toString());
    user2.addUser(user1);
    System.out.println(user2.getUsers().toString());
}