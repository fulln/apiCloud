//package me.fulln.base.common.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import net.sourceforge.tess4j.ITesseract;
//
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//
//import java.io.File;
//
///**
// * @program: api
// * @description: 图形验证码识别工具类(需要改成若快的平台)
// * @author: fulln
// * @create: 2018-09-21 09:46
// * @Version： 0.0.1
// **/
//@Slf4j
//public class ValidationCodeUtil {
//
//    public static String ValidateCodes() {
//
//
//        File imageFile = new File("eurotext.tif");
//
//
//        // JNA Interface Mapping
//        ITesseract instance = new Tesseract();
//
//
//        try {
//            return instance.doOCR(imageFile);
//        } catch (TesseractException e) {
//            return null;
//        }
//
//    }
//
//}
