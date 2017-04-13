package me.ianhe.utils;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class QRCode {

    /**
     * 二维码生成工具
     *
     * @param folder  文件存放位置
     * @param content 二维码内容
     * @param format  二维码格式
     * @param width   宽度
     * @param height  高度
     * @return 二维码文件名
     */
    public static String generateQRCode(String folder, String content, String fileName, String format, int width, int height) {
        HashMap<EncodeHintType, Object> hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, format, baos);
            return FileUtil.uploadFile(baos.toByteArray(), folder + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private QRCode() {
        //工具类不允许实例化
    }

}
