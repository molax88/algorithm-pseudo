package one;

/**
 * 实现一个算法，确定一个字符串 s 的所有字符是否全都不同。
 * @author molax
 * @date 2022/1/20 17:35
 */
public class One {

    public static boolean checkUnique(String s){
        long bitmap = 0L;
        for(int i = 0, size = s.length();i<size;i++){
            int position = s.charAt(i) - 'A';
            if((bitmap & (1 << position)) != 0){
                return false;
            }
            bitmap |= (1 << position);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(checkUnique("asdf"));
    }
}
