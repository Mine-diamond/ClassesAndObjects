package edu.study.stringbuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SBshow {
    int[] numArr;

    public String showInGS(){
        StringBuilder sb = new StringBuilder();
        String processedStr = "";

        for (int i = 0; i < numArr.length; i++) {
            if (i == 0){
                sb.append("[");
            }

            sb.append(numArr[i]);

            if (i == numArr.length-1){
                sb.append("]");
            }else {
                sb.append(",");
            }
            processedStr = sb.toString();

        }
        return processedStr;
    }
}
