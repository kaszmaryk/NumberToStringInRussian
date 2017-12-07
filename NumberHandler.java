/*
 * Переводит числа до 999 миллиардов в текстовую строку
 * Например: 123 -> "сто двадцать три"
 *
 * @author: kaszmaryk
 * @version: 1.0
 */
package templator;

import java.util.ArrayList;

public class NumberHandler {
    private long number;
    private String[] units = {"","один","два","три","четыре","пять","шесть","семь","восемь","девять"};
    private String[] unitsNominat = {"", "одна", "две"};
    private String[] teens = {"", "одиннадцать", "двенадцать", "тринадцать","четырнадцать","пятнадцать","шестнадцать","семнадцать","восемнадцать","девятнадцать"};
    private String[] decades = {"","десять","двадцать","тридцать", "сорок","пятьдесят","шестьдесят","семьдесят","восемьдесят","девяносто"};
    private String[] hundreds = {"","сто","двесте","триста","четыреста","пятьсот","шестьсот","семьсот","восемьсот","девятьсот"};
    private String[] thousands = {"тысяча","тысячи","тысяч"};
    private String[] millions = {"миллион","миллиона","миллионов"};
    private String[] billions = {"миллиард","миллиарда","миллиардов"};
    private String[][] numorders = {this.thousands,this.millions,this.billions};
    
    
    public static String formString(String num) {
        NumberHandler nh = new NumberHandler();
        long a = Long.parseLong(num);
        nh.setNumber(a);
        String s = nh.formString();
        return s;
    }
    
    public void setNumber(long number) {
        this.number = number;
    }
    
    public String[] getOrders() {
        String numStr = Long.toString(this.number);
        numStr = new StringBuilder(numStr).reverse().toString();
        double numChars = numStr.length();
        int numOrders = (int)Math.ceil(numChars/3);
        ArrayList<String> finalString = new ArrayList<String>();
        for(int i = 0; i < (int)numChars; i+=3) {
            String tempString = "";            
            if(i <= numChars - 3) {
                 tempString = new StringBuilder(
                         String.valueOf(numStr.charAt(i))
                         +String.valueOf(numStr.charAt(i+1))
                         +String.valueOf(numStr.charAt(i+2))).reverse().toString();
            } else {
                int lostChars = (int) numChars - i;
                switch(lostChars){
                    case 1:
                        tempString = String.valueOf(numStr.charAt(i));
                        break;
                    case 2:
                        tempString = new StringBuilder(
                                String.valueOf(numStr.charAt(i))
                                +String.valueOf(numStr.charAt(i+1))).reverse().toString();
                        break;
                }
            }
            finalString.add(tempString);            
        }
        String[] returningArray = new String[finalString.size()];
        finalString.toArray(returningArray);
        return returningArray;
    }
    
    public String formString() {
        if(this.number > 0) {
            String[] orders = this.getOrders();
            String[] finalOrders = new String[orders.length];
            String finalString = "";
            for(int i = 0; i < orders.length; i++) {
                finalOrders[orders.length - 1 - i] = this.formTextString(orders[i], i);
            }
            return String.join(" ", finalOrders);
        } else {
            return "ноль";
        }
    }
    
    private String formTextString(String s, int i) {
        String finalString = "";
        
        int strlen = s.length();
        
        if(strlen == 3) {
            finalString = finalString+" "+this.hundreds[Character.getNumericValue(s.charAt(0))];
            finalString = finalString+" "+this.getDecimal(String.valueOf(s.charAt(1))+String.valueOf(s.charAt(2)), i);
        } else if(strlen == 2) {
            finalString = this.getDecimal(s, i);
        } else {
            if((i == 1) && Integer.parseInt(s) < 3) {
                finalString = finalString+" "+this.unitsNominat[Integer.parseInt(s)];
            } else {
                finalString = finalString+" "+this.units[Integer.parseInt(s)];
            }            
        }
        
        finalString = finalString+" "+this.getNumOrders(i, Integer.parseInt(s));
        return finalString.trim().replaceAll("\\s\\s", " ");
    }
    
    private String getDecimal(String s, int i) {
        String finalString = "";
        int v = Integer.parseInt(s);
        if(v < 10) {
            finalString = finalString+" "+this.units[v];
        } else if(v%10 == 0) {
            finalString = finalString+" "+this.decades[v/10];
        } else if(v < 20){
            finalString = finalString+" "+this.teens[v%10];
        } else if(v%10 < 3 && i == 1){
            finalString = finalString+" "+this.decades[Character.getNumericValue(s.charAt(0))]+" "+this.unitsNominat[Character.getNumericValue(s.charAt(1))];
        } else {
            finalString = finalString+" "+this.decades[Character.getNumericValue(s.charAt(0))]+" "+this.units[Character.getNumericValue(s.charAt(1))];
        }
        return finalString;
    }
    
    private String getNumOrders(int i, int num) {
        String finalString = "";
        
        if(i > 0){
            if(num%10 > 4 ||  (num%100 > 10 && num%100 < 20)|| num%10 == 0) {
                finalString = this.numorders[i-1][2];
            } else if(num%10 > 1 && num%10 < 5){
                finalString = this.numorders[i - 1][1];
            } else {
                finalString = this.numorders[i-1][0];
            }
        }
        
        return finalString;
    }
    
    public static String getCeil(double num) {
        String n = String.valueOf((int)Math.floor(num));
        return n;
    }
    public static String getFraction(double num) {
        String n = String.valueOf((int)Math.floor((num - Math.floor(num))*100));
        return n;
    }
    
}
