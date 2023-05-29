package com.blog.springbootinit;

import com.blog.springbootinit.config.WxOpenConfig;
import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 *
 
 */
@SpringBootTest
class MainApplicationTests {

    @Resource
    private WxOpenConfig wxOpenConfig;

    @Test
    void contextLoads() {
        System.out.println(wxOpenConfig);
    }

//    public static void main(String[] args) {
//        long startTime = System.currentTimeMillis();
//        String text = "#接龙\n" +
//                "九联双元制人才培养特色班讲座报名接龙\n" +
//                "例 专业班级姓名\n" +
//                "\n" +
//                "1. 20电信1班某某某\n" +
//                "2. 20电信一班陈培劲\n" +
//                "3. 20电信一班陈海源\n" +
//                "4. 20电信二班王彬\n" +
//                "5. 20计科一班吴春晓\n" +
//                "6. 20计科3班郭根鹏\n" +
//                "7. 20软工二班金泂枞\n" +
//                "8. 20计科一班谢浩\n" +
//                "9. 20计科3班刘关平\n" +
//                "10. 20网工1班容超亮\n" +
//                "11. 20网工1班吴弘基\n" +
//                "12. 20网工1班王恒练\n" +
//                "13. 20电信三班陈源辉\n" +
//                "14. 20电信3班陈文彬\n" +
//                "15. 20电信3班 吴嘉伟\n" +
//                "16. 20电信1班陈煜平\n" +
//                "17. 20网工1班刘嘉达\n" +
//                "18. 20计科3班 吴志辉\n" +
//                "19. 20电信三班陈明霖\n" +
//                "20. 20电信3班杨荣华\n" +
//                "21. 20电信3班 许益玮\n" +
//                "22. 20电信3班肖建在\n" +
//                "23. 20电信三班杨海志\n" +
//                "24. 20电信4班吴佳敏\n" +
//                "25. 20计科1班聂志宇\n" +
//                "26. 20电信4班杨志雄\n" +
//                "27. 20计科1班谢梓凯\n" +
//                "28. 20电信3班黄向繁\n" +
//                "29. 20 计科2班洪德峰\n" +
//                "30. 20网工2班李晓林\n" +
//                "31. 20计科2班黄少华\n" +
//                "32. 20电信三班彭超炼\n" +
//                "33. 20电信3班钟佳南\n" +
//                "34. 20计科1班李奕旺\n" +
//                "35. 20电信3班陈秋清\n" +
//                "36. 20电信2班段智睿\n" +
//                "37. 20电信2班李佳桦\n" +
//                "38. 20网工2温培桓\n" +
//                "39. 20计科1王天润\n" +
//                "40. 20计科3班谢立城\n" +
//                "41. 20计科1班倪嘉敏\n" +
//                "42. 20电信2班刘旷\n" +
//                "43. 20计科1班林钊滢\n" +
//                "44. 20计科1班 吕炜仪\n" +
//                "45. 20机电1班丘文杰\n" +
//                "46. 20计科1班苏晓格\n" +
//                "47. 20大数据1班陈彩珍\n" +
//                "48. 20电信3班冯森\n" +
//                "49. 20电信1班黄杨锋\n" +
//                "50. 20电信1班 刁棋标\n" +
//                "51. 20电信1班李杭臻\n" +
//                "52. 20计科1班范慧琳\n" +
//                "53. 20计科1 陈冠翔\n" +
//                "54. 20电信3 蔡振鑫\n" +
//                "55. 20软工2班王栋民\n" +
//                "56. 20电气3班陈思林\n" +
//                "57. 20电信3班张永东\n" +
//                "58. 20电信3班吴思颖\n" +
//                "59. 20计科1班陈平\n" +
//                "60. 20网工1班杨俊浩\n" +
//                "61. 20电信1班朱培凯\n" +
//                "62. 20电信2班 区立棋\n" +
//                "63. 20电信2班 王伟杰\n" +
//                "64. 20电信2班 易铭杰\n" +
//                "65. 20电信3班 李炜烽\n" +
//                "66. 20电信3班 香伟成\n" +
//                "67. 20电信3班 彭德斌\n" +
//                "68. 20电气4班 林思艾\n" +
//                "69. 20网工1班 林东龙\n" +
//                "70. 20网工2班 林康炫\n" +
//                "71. 20大数据1班 赵芝泰\n" +
//                "72. 20大数据1班 何梓涛\n" +
//                "73. 20大数据1班 陈胜源\n" +
//                "74. 20电信3班 何荣佳\n" +
//                "75. 20电信3班 丁政豪\n" +
//                "76. 20电信3班 黄依劲\n" +
//                "77. 20大数据1班 陈灿粤\n" +
//                "78. 20大数据1班 黄群\n" +
//                "79. 20电信3班 吴熠瑶\n" +
//                "80. 20电信3班 康国泰\n" +
//                "81. 20电信3班  翁志侠\n" +
//                "82. 20大数据1班 陈怡瑶\n" +
//                "83. 20软工2班 陈镇杰\n" +
//                "84. 20大数据1班 尹艳明\n" +
//                "85. 20计科1班 陈恩\n" +
//                "86. 20电信1班朱俊慧\n" +
//                "87. 20电信1班陈映君\n" +
//                "88. 20大数据1班 吴绮薇\n" +
//                "89. 20计科1班 蔡育鑫\n" +
//                "90. 20计科1班 林晓群";
//        Result analysisedResult = BaseAnalysis.parse(text);
//        long endTime = System.currentTimeMillis();
//        long time = endTime - startTime;
//        int x=0;
//        for(int i=0;i<analysisedResult.size();i++){
//            String name = analysisedResult.get(i).getName();
//            if(name.equals("网")||name.equals("软")||name.equals("计"))
//                x++;
//            System.out.println(name);
//        }
//        System.out.println(x);
////        System.out.println("基本分词: " + analysisedResult + "(" + time + "ms)");
//
//    }
    public static void quicksort(int[] nums ,int l,int r){
        if(r<=l){
            return;
        }
        int tmp=nums[l];
        int start =l;
        int end=r;
        while(start<end){
            while(start<end&&nums[end]>=tmp){
                end--;
            }
            nums[start]=nums[end];
            while(start<end&&nums[start]<=tmp){
                start++;
            }
            nums[end]=nums[start];
        }
        nums[start]=tmp;
        quicksort(nums,l,start-1);
        quicksort(nums,start+1,r);
    }
    public  static void merge(int[]nums ,int l,int r){
        if(r<=l){
            return ;
        }
        int m=(l+r)/2;
        merge(nums,l,m);
        merge(nums,m+1,r);
        int[] tmp=new int[r-l+1];
        int index1=l;
        int index2=m+1;
        int index=0;
        while(index1<=m&&index2<=r){
            if(nums[index1]<=nums[index2]){
                tmp[index++]=nums[index1++];
            }else{
                tmp[index++]=nums[index2++];
            }
        }
        while(index1<=m){
            tmp[index++]=nums[index1++];
        }
        while(index2<=r){
            tmp[index++]=nums[index2++];
        }
        for(int i=l;i<=r;i++){
            nums[i]=tmp[i-l];
        }
    }
    public  static void quick(int[]nums ,int l,int r){
        if(r<=l)
        {
            return ;
        }
        int x=nums[l];
        int start =l;
        int end=r;
        while(end>start){

            while(end>start&&nums[end]>x){
                end--;
            }
            nums[start]=nums[end];
            while(end>start&&nums[start]<x){
                start++;
            }
            nums[end]=nums[start];
        }
        nums[start]=x;
        quick(nums,l,start-1);
        quick(nums,start+1,r);
    }
    public static void main(String[] args) {
        int[] nums=new int[]{3,2,1,5,6,4};
//        quicksort(nums,0,nums.length-1);

        quick(nums, 0, nums.length - 1);
        for(int i=0;i<nums.length;i++){
            System.out.println(nums[i]);
        }
    }
}
