package com.sk.blog.utils;


import com.github.pagehelper.PageInfo;

import com.sk.blog.bean.Contents;
import com.sk.blog.constant.WebConst;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class Commons {

    public static String THEME = "themes/default";

    /**
     * 判断分页中是否有数据
     *
     * @param paginator
     * @return
     */
    public static boolean is_empty(PageInfo paginator) {
        return paginator == null || (paginator.getList() == null) || (paginator.getList().size() == 0);
    }

    /**
     * 网站链接
     *
     * @return
     */
    public static String site_url() {
        return site_url("/");
    }

    /**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
     * @return
     */
    public static String site_url(String sub) {
        return site_option("site_url") + sub;
    }

    /**
     * 网站标题
     *
     * @return
     */
    public static String site_title() {
        return site_option("site_title");
    }

    /**
     * 网站配置项
     *
     * @param key
     * @return
     */
    public static String site_option(String key) {
        return site_option(key, "");
    }

    /**
     * 网站配置项
     *
     * @param key
     * @param defalutValue 默认值
     * @return
     */
    public static String site_option(String key, String defalutValue) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        String str = WebConst.initConfig.get(key);
        if (StringUtils.isNotBlank(str)) {
            return str;
        } else {
            return defalutValue;
        }
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param len
     * @return
     */
    public static String substr(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }

    /**
     * 返回主题URL
     *
     * @return
     */
    public static String theme_url() {
        return site_url(Commons.THEME);
    }

    /**
     * 返回主题下的文件路径
     *
     * @param sub
     * @return
     */
    public static String theme_url(String sub) {
        return site_url(Commons.THEME + sub);
    }


    /**
     * 返回文章链接地址
     *
     * @param contents
     * @return
     */
//    public static String permalink(Contents contents) {
//        return permalink(contents.getCid(), contents.getSlug());
//    }


    /**
     * 获取随机数
     *
     * @param max
     * @param str
     * @return
     */
    public static String random(int max, String str) {
        return UUID.random(1, max) + str;
    }

    /**
     * 返回文章链接地址
     *
     * @param cid
     * @param slug 如果slug为空，则使用article/cid作为跳转页面
     * @return
     */
    public static String permalink(Integer cid, String slug) {
        return site_url("/article/" + (StringUtils.isNotBlank(slug) ? slug : cid.toString()));
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @return
     */
    public static String fmtdate(Integer unixTime) {
        return fmtdate(unixTime, "yyyy-MM-dd");
    }

    /**
     * 格式化unix时间戳为日期
     *
     * @param unixTime
     * @param patten
     * @return
     */
    public static String fmtdate(Integer unixTime, String patten) {
        if (null != unixTime && StringUtils.isNotBlank(patten)) {
            return DateKit.formatDateByUnixTime(unixTime, patten);
        }
        return "";
    }


    /**
     * 显示标签
     *
     * @param tags
     * @return
     */
    public static String show_tags(String tags) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(tags)) {
            String[] arr = tags.split(",");
            StringBuffer sbuf = new StringBuffer();
            for (String c : arr) {
                sbuf.append("<a href=\"/tag/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
            }
            return sbuf.toString();
        }
        return "";
    }

    /**
     * 截取文章摘要
     *
     * @param value 文章内容
     * @param len   要截取文字的个数
     * @return
     */
    public static String intro(String value, int len) {
        int pos = value.indexOf("<!--more-->");
        if (pos != -1) {
            String html = value.substring(0, pos);
            return TaleUtils.htmlToText(TaleUtils.mdToHtml(html));
        } else {
            String text = TaleUtils.htmlToText(TaleUtils.mdToHtml(value));
            if (text.length() > len) {
                return text.substring(0, len);
            }
            return text;
        }
    }

    /**
     * 显示文章内容，转换markdown为html,给用户展示
     *
     * @param value
     * @return
     */
    public static String article(String value) {
        if (StringUtils.isNotBlank(value)) {
            value = value.replace("<!--more-->", "\r\n");
            return TaleUtils.mdToHtml(value);
        }
        return "";
    }

    /**
     * An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!
     * <p>
     * 这种格式的字符转换为emoji表情
     *
     * @param value
     * @return
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }

    /**
     * 获取文章第一张图片
     *
     * @return
     */
    public static String show_thumb(String content) {
        content = TaleUtils.mdToHtml(content);
        if (content.contains("<img")) {
            String img = "";
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
            Matcher m_image = p_image.matcher(content);
            if (m_image.find()) {
                img = img + "," + m_image.group();
                // //匹配src
                Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(img);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        return "";
    }

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};



}
