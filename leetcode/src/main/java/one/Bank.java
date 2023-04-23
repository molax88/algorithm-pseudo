package one;

/**
 * @author molax
 * @date 2023/3/15 15:57
 */
public class Bank {

    private long[] balance;

    public Bank(){

    }
    public Bank(long[] balance){
        this.balance = balance;
    }

    public boolean withdraw(int account, long money){
        if(account>balance.length){
            return false;
        }
        else if(balance[account-1]<money){
            return false;
        }
        balance[account-1] = balance[account-1] - money;
        return true;
    }

    public boolean transfer(int account1, int account2, long money){
        if(account1>balance.length || account2>balance.length){
            return false;
        }
        else if(balance[account1-1]<money){
            return false;
        }
        balance[account1-1] = balance[account1-1] - money;
        balance[account2-1] = balance[account2-1] + money;
        return true;
    }

    public boolean deposit(int account, long money){
        if(account>balance.length){
            return false;
        }
        balance[account-1] = balance[account-1] + money;
        return true;
    }

    public static void main(String[] args) {
        Bank bank = new Bank(new long[]{10, 100, 20, 50, 30});
        System.out.println(bank.withdraw(3,10));
        System.out.println(bank.transfer(5,1,20));
        System.out.println(bank.deposit(5,20));
        System.out.println(bank.transfer(3,4,15));
        System.out.println(bank.withdraw(10,50));
    }
}
