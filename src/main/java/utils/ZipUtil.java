package utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @User: fengxinxin
 * @Date: 2019-06-18
 * @Time: 11:19
 * @copyright uzoo
 */
public class ZipUtil {

    /**
     * 文件压缩
     * @param zipFileName
     * @param inputFile
     * @throws Exception
     */
    public static  void zip(String zipFileName, File inputFile) throws Exception {
        System.out.println("压缩中...");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        zip(out, inputFile, inputFile.getName(), bo);
        bo.close();
        out.close(); // 输出流关闭
        System.out.println("压缩完成");
    }

    private static void zip(ZipOutputStream out, File f, String base,
                            BufferedOutputStream bo) throws Exception { // 方法重载
        if (f.isDirectory()){
            File[] fl = f.listFiles();
            if (fl.length == 0){
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();
            in.close(); // 输入流关闭
        }
    }
    /**
     * 压缩成ZIP 方法
     * @param srcFiles 需要压缩的文件列表
     * @param out 	        压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles , OutputStream out){
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[2 * 1024];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("zip error from FileUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解压文件
     * @param zipPath 要解压的目标文件
     * @param descDir 指定解压目录
     * @return 解压结果：成功，失败
     */
    @SuppressWarnings("rawtypes")
    public static boolean decompressZip(String zipPath, String descDir) {
        File zipFile = new File(zipPath);
        boolean flag = false;
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile, Charset.forName("gbk"));//防止中文目录，乱码
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //指定解压后的文件夹+当前zip文件的名称
                String outPath = (descDir+zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()){
                    continue;
                }
                //保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
                System.err.println("当前zip解压之后的路径为：" + outPath);
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
            }
            flag = true;
            //必须关闭，要不然这个zip文件一直被占用着，要删删不掉，改名也不可以，移动也不行，整多了，系统还崩了。
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 解压文件到指定目录 解压出来是图片
     */
    @SuppressWarnings("rawtypes")
    public static List<String>  unZipFilesByImg(String zipPath,String descDir){
        InputStream in =null;
        OutputStream out =null;
        try {
            List<String> fileNameList=new ArrayList<>();
            File zipFile= new File(zipPath);
            File pathFile = new File(descDir);
            if(!pathFile.exists())
            {
                pathFile.mkdirs();
            }
            //解决zip文件中有中文目录或者中文文件
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
            String fileParentDir="";
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();)
            {
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                if (StringUtils.isNotBlank(fileParentDir)){
                    zipEntryName=zipEntryName.substring(fileParentDir.length());
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(zipEntryName.indexOf(".jpg")<0 && zipEntryName.indexOf(".JPG")<0 )
                {
                    fileParentDir=zipEntryName;
                    continue;
                }
                in = zip.getInputStream(entry);
                String outPath = (descDir+File.separator+zipEntryName).replaceAll("\\*", "/");
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath);
                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while((len=in.read(buf1))>0)
                {
                    out.write(buf1,0,len);
                }
                fileNameList.add(file.getPath());
            }

            fileNameList=sortBaseTypeByIDefineMode(fileNameList);

            return fileNameList;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  new ArrayList<>();
    }

    public static List<String> sortBaseTypeByIDefineMode(List<String> list) {
        String separator=System.getProperty("file.separator");
        Collections.sort(list,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                if (o1.lastIndexOf("_")>=0){
                    Integer i1=Integer.valueOf(o1.substring(o1.lastIndexOf("_")+1,o1.lastIndexOf(".")));
                    Integer i2=Integer.valueOf(o2.substring(o2.lastIndexOf("_")+1,o2.lastIndexOf(".")));
                    return i1-i2;
                }else{
                    Integer i1=Integer.valueOf(o1.substring(o1.lastIndexOf(separator)+1,o1.lastIndexOf(".")));
                    Integer i2=Integer.valueOf(o2.substring(o2.lastIndexOf(separator)+1,o2.lastIndexOf(".")));
                    return i1-i2;
                }
            }
        });
        return list;
    }


    public static void main(String[] args) throws Exception {
        decompressZip("D:\\教材\\ceshi.zip","ceshi123");

    }


}
