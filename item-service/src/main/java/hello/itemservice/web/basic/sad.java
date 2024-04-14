package hello.itemservice.web.basic;

public class sad {
}

class Solution {
    public String solution(int[] numbers, String hand) {
        String answer = "";
        int l = 20;
        int r = 20;
        for (int i : numbers) {
            if (i == 1 || i == 4 || i == 7) {
                answer += "L";
                l = i + 2;
                i++;
            } else if (i == 3 || i == 6 || i == 9) {
                answer += "R";
                r = i;
                i++;
            } else if (i == 2 || i == 5 || i == 8) {
                if (Math.abs(r - i) > Math.abs(l - i)) {
                    answer += "L";
                    l = i;
                    i++;
                } else if (Math.abs(r - i) < Math.abs(l - i)) {
                    answer += "R";
                    r = i;
                    i++;
                } else {
                    if (hand.equals("left")) {
                        answer += "L";
                        l = i;
                        i++;
                    } else {
                        answer += "R";
                        r = i;
                        i++;
                    }
                }
            } else {
                if (l == 8) {
                    answer += "L";
                    l = 10;
                    i++;
                } else (r == 8) {
                    answer += "R";
                    r = 10;
                    i++;
                }
                if (r > l) {
                    answer += "R";
                    r = 10;
                    i++;
                } else if(r<l) {
                        answer += "L";
                l = 10;
                i++;
                }else{
                    if (hand.equals("left")) {
                        answer += "L";
                        l = i;
                        i++;
                    } else {
                        answer += "R";
                        r = i;
                        i++;
                    }
                }

            }

        }

        return answer;
    }
}
