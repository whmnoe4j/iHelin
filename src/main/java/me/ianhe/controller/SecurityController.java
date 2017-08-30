package me.ianhe.controller;

import org.springframework.stereotype.Controller;

/**
 * @author iHelin
 * @create 2017-04-15 10:40
 */
@Controller
public class SecurityController extends BaseController {

    /*@GetMapping("/captcha-image")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
//        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
//        System.out.println("验证码: " + code );
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = RandomStringUtils.randomAlphanumeric(4);
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }*/

}
