
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author molax
 * @date 2022/10/20 15:08
 */
public class CodeTest {

    static int[] runningSums(int[] elements){
        AtomicInteger sum = new AtomicInteger(0);
        return Arrays.stream(elements).map(v -> sum.addAndGet(v)).toArray();
    }

    static boolean canChange(String ransomNote, String magazine){
        if(ransomNote.length()>magazine.length()){
            return false;
        }
//        HashMap<Integer, Integer> nums = new HashMap<>(32);
//        ransomNote.chars().forEach(v-> nums.put(v, nums.getOrDefault(v, 0) + 1));
//        magazine.chars().forEach(v-> nums.put(v, nums.getOrDefault(v, 0) - 1));
//        return nums.values().stream().allMatch(v->v<=0);
        int[] nums = new int[26];
        for(char c : magazine.toCharArray()){
            nums[c-'a']++;
        }
        for(char c : ransomNote.toCharArray()){
            if(nums[c-'a']-- < 0){
                return false;
            }
        }
        return true;
    }

    public static List<String> fizzBuzz(int n) {
        return IntStream.range(1, n+1).mapToObj(v->{
            if(v%3==0 && v%5==0){
                return "FizzBuzz";
            }
            else if(v%3==0){
                return "Fizz";
            }
            else if(v%5==0){
                return "Buzz";
            }
            else {
                return String.valueOf(v);
            }
        }).collect(Collectors.toList());
    }

    public static ListNode middleNode(ListNode head) {
        ListNode res = head;
        int i = 1;
        while(Objects.nonNull(head.next)){
            if(++i%2==0)
                res = res.next;
            head = head.next;
        }
        return res;
    }
    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public int maximumWealth(int[][] accounts) {
//        int res = 0;
//        for(int i=0;i<accounts.length;i++){
//            int sum = 0;
//            for(int j=0;j<accounts[i].length;j++){
//                sum+=accounts[i][j];
//            }
//            if(sum>res){
//                res = sum;
//            }
//        }
//        return res;
        return Arrays.stream(accounts).map(v->Arrays.stream(v).sum()).max(Integer::compare).get();
    }

    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    public boolean checkTree(TreeNode root) {
        return root.val == root.left.val + root.right.val;
    }

    public static int removeDuplicates(int[] nums) {
        int max = nums[0];
        int index = 0;
        for(int i=1;i<nums.length;i++){
            if(nums[i]>max){
                nums[++index]=nums[i];
                max=nums[i];
            }
        }
        nums = Arrays.copyOf(nums, index);
        return index+1;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        List<Integer> all = new ArrayList();
        while(Objects.nonNull(list1)||Objects.nonNull(list2)){
            if(Objects.isNull(list1)){
                all.add(list2.val);
                list2 = list2.next;
            }
            else if(Objects.isNull(list2)){
                all.add(list1.val);
                list1 = list1.next;
            }
            else if(list1.val>list2.val){
                all.add(list2.val);
                list2 = list2.next;
            }
            else{
                all.add(list1.val);
                list1 = list1.next;
            }
        }
        ListNode result=null;
        for(int i=all.size()-1;i>=0;i--){
            if(Objects.isNull(result)){
                result = new ListNode(all.get(i));
            }
            else{
                result = new ListNode(all.get(i), result);
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(JSONUtil.toJsonStr(runningSums(new int[]{1,1,1,1,1})));
//        System.out.println(canChange("aa", "aab"));
//        System.out.println(JSONUtil.toJsonStr(fizzBuzz(3)));
        ListNode head = new ListNode(1);
        ListNode temp = head;
        for(int i=2;i<10;i++){
            temp = temp.next = new ListNode(i);
        }
        System.out.println(head.val);
        System.out.println(middleNode(head).val);
    }
}
