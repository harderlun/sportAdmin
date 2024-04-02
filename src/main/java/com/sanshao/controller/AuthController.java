package com.sanshao.controller;

import com.google.code.kaptcha.Producer;
import com.sanshao.common.lang.Const;
import com.sanshao.common.lang.Result;
import com.sanshao.entity.SysUser;
import com.sanshao.service.SysUserService;
import com.sanshao.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

/**
 * 验证码校验
 */
@RestController
public class AuthController {

    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取验证码
     * @return
     * @throws IOException
     */
    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        String key = UUID.randomUUID().toString();

        String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        ImageIO.write(image,"jpg",outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray());
        redisUtil.hset(Const.CAPTCHA_KEY,key, code,120);
        return Result.ok().data("key",key).data("captchaImg",base64Img);
    }

    /**
     * 获取用户信息
     * @param principal
     * @return
     */
    @GetMapping("/sys/userInfo")
    public Result userInfo(Principal principal){
        SysUser user = sysUserService.getByUsername(principal.getName());
        return Result.ok().data("id",user.getId()).data("username",user.getUsername()).data("avatar",user.getAvatar());
    }
}
