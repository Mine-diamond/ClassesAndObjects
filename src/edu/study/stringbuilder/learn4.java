package edu.study.stringbuilder;

public class learn4 {
    public static void main(String[] args) {
        String str = "//1．创建stringBuilder，准备进行拼接\n" +
                "StringBuildersb=newStringBuilder(\"\")\n" +
                "/2遍历数组，获取内部元素\n" +
                "for (int i= o;i< arr.length-1;i++){\n" +
                "    /3．将获取到的元素，拼接到字符串缓冲区\n" +
                "    sb.append(arr[i).append(\"\")\n" +
                "/4．特殊处理最后一个元素\n" +
                "sb.append(arr[arr.length-1j).append(\"j\")\n" +
                "returnsb.tostringO:";
        StringBuilder builder = new StringBuilder(str);
        System.out.println(builder.toString());
    }
}
