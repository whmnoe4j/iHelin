package me.ianhe.utils;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import me.ianhe.model.MatrixToImageWriter;
import org.apache.commons.lang3.CharEncoding;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class QrCodeUtil {

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
        hints.put(EncodeHintType.CHARACTER_SET, CharEncoding.UTF_8);
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

    /**
     * 二维码生成
     * 大小默认300像素
     *
     * @author iHelin
     * @since 2017/6/1 15:12
     */
    public static String generateQRCode(String folder, String content, String fileName, String format) {
        return generateQRCode(folder, content, fileName, format, 300, 300);
    }

    private QrCodeUtil() {
        //工具类不允许实例化
    }

}
