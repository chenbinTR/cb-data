package book;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import utils.Utils;

import java.io.File;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-09-12
 * @see
 * @since
 */
public class CreateChat {
    private static String path = "C:\\Users\\cb\\Downloads\\绘本QA生成\\";
    public static void main(String[] args) {
        createChat();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("passage", "在大海的一个角落里,住着一群快乐的小鱼。他们都是红色的,只有一条是黑色的,黑得就像淡菜壳。");
//        String result = Utils.httpPost(jsonObject.toJSONString(), "http://192.168.10.32:19510/nlp/question-generation-srl");
//        ResultEntity resultEntity = JSON.parseObject(result, ResultEntity.class);
//        System.out.println(resultEntity.getData().get(0).getQas().get(0).get(1));

//        List<ExcelEntity> excelEntityList = new ArrayList<>(1);
//        ExcelEntity excelEntity = new ExcelEntity();
//        excelEntity.setValue0("1");
//        excelEntity.setValue1("陈大哥");
//        excelEntityList.add(excelEntity);
//
//        Utils.writeExcel(path+"text.xlsx", excelEntityList);

//        String result = Utils.httpPost("{\"passage\":\"Ditto 是一款强大的 Windows 剪贴板增强工具，它支持64位操作系统，而且完全免费，绿色开源，支持中文，而且还有免安装的绿色版本。开启 Ditto 后，不会有任何程序界面出现，它只是默默地在系统右下角弹出了一个蓝色的托盘图标，\"}","http://192.168.10.32:19510/nlp/question-generation-srl");
//        System.out.println(result);
    }
    private static void createChat(){
        File file = new File(path+"绘本数据");
        String[] files = file.list();
        List<String> fileContent;
        for(String fileName:files){
            System.out.println(fileName);
            // 单个文件处理
            String singleFilePath = path+"绘本数据\\";
            processSingleFile(singleFilePath, fileName);
        }
    }

    private static void processSingleFile(String filePath, String fileName){
        List<String> fileContents = Utils.readFileToList(filePath+fileName);
        for(String line:fileContents){
            try{
                String[] lines = line.split("\t");
                String text = lines[1];
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("passage", text);
                String result = Utils.httpPost(jsonObject.toJSONString(), "http://192.168.10.32:19510/nlp/question-generation-srl");
                ResultEntity resultEntity = JSON.parseObject(result, ResultEntity.class);
                List<Data> datas = resultEntity.getData();
                if(datas.size()<1){
                    continue;
                }
                for(Data data:datas){
                    List<List<String>> qas = data.getQas();
                    for(List<String> qa:qas){
                        String question = qa.get(0);
                        String answer = qa.get(1);
                        Utils.writeToTxt("Q:\\book\\"+fileName, line+"\t"+question+"\t"+answer);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
