package com.sinosdx.service.authentication.service;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.authentication.constants.Constants;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import com.sinosdx.starter.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

/**
 * 图片验证码工具类
 *
 * @author wendy
 * @date 2021/2/22
 */
@Service
public class ImageVerificationCodeService {

    @Autowired
    private RedisService redisService;

    /**
     * 验证码图片的长和宽
     */
    private final int weight = 100;
    private final int height = 40;
    /**
     * 获取随机数对象
     */
    private Random random = new Random();
    //private String[] fontNames = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};   //字体数组
    /**
     * 字体数组
     */
    private final String[] fontNames = {"Georgia"};
    /**
     * 验证码数组
     */
    private final String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    /**
     * 获取随机的颜色
     *
     * @return
     */
    private Color randomColor() {
        // 这里为什么是225，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
        int r = this.random.nextInt(225);
        int g = this.random.nextInt(225);
        int b = this.random.nextInt(225);
        // 返回一个随机颜色
        return new Color(r, g, b);
    }

    /**
     * 获取随机字体
     *
     * @return
     */
    private Font randomFont() {
        // 获取随机的字体
        int index = random.nextInt(fontNames.length);
        String fontName = fontNames[index];
        // 随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
        int style = random.nextInt(4);
        //随机获取字体的大小
        int size = random.nextInt(10) + 24;
        //返回一个随机的字体
        return new Font(fontName, style, size);
    }

    /**
     * 获取随机字符
     *
     * @return
     */
    private char randomChar() {
        int index = random.nextInt(codes.length());
        return codes.charAt(index);
    }

    /**
     * 画干扰线，验证码干扰线用来防止计算机解析图片
     *
     * @param image
     */
    private void drawLine(BufferedImage image) {
        //定义干扰线的数量
        int num = random.nextInt(10);
        Graphics2D g = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(weight);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(weight);
            int y2 = random.nextInt(height);
            g.setColor(randomColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 创建图片的方法
     *
     * @return
     */
    private BufferedImage createImage() {
        //创建图片缓冲区
        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
        //获取画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        //设置背景色随机
        g.setColor(new Color(255, 255, random.nextInt(245) + 10));
        g.fillRect(0, 0, weight, height);
        //返回一个图片
        return image;
    }

    /**
     * 获取验证码图片的方法
     *
     * @return
     */
    public void getImage(HttpServletResponse response) throws IOException {
        BufferedImage image = createImage();
        //获取画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        StringBuilder sb = new StringBuilder();
        //画四个字符即可
        for (int i = 0; i < 4; i++) {
            //随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
            String s = randomChar() + "";
            //添加到StringBuilder里面
            sb.append(s);
            //定义字符的x坐标
            float x = i * 1.0F * weight / 4;
            //设置字体，随机
            g.setFont(randomFont());
            //设置颜色，随机
            g.setColor(randomColor());
            g.drawString(s, x, height - 5);
        }
        drawLine(image);

        // redis缓存验证码
        String uuid = UUID.randomUUID().toString();
        redisService.set(Constants.VERIFICATION_CODE + uuid, sb.toString(), 300L);

        response.addHeader("UUID", uuid);
        g.dispose();
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
        out.close();
    }

    /**
     * 将验证码图片写出的方法
     *
     * @param image
     * @param out
     * @throws IOException
     */
    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
        out.close();
    }

    /**
     * 验证传入的验证码与缓存中的验证码是否一致
     *
     * @param code
     * @return
     */
    public R<Object> verifyCode(String code, String uuid) {
        String key = Constants.VERIFICATION_CODE + uuid;
        String originCaptcha = redisService.get(key);
        if (originCaptcha == null) {
            return R.fail(false, ResultCodeEnum.VERIFY_CODE_EXPIRED);
        }
        if (code.equalsIgnoreCase(originCaptcha)) {
            return R.success(true);
        }
        return R.fail(false, ResultCodeEnum.VERIFY_CODE_ERROR);
    }
}
