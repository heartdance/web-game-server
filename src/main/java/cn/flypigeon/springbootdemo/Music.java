package cn.flypigeon.springbootdemo;

import lombok.Data;

import java.util.List;

@Data
public class Music {

    /**
     * 歌曲名称
     */
    private String title;

    /**
     * 专辑
     */
    private String album;

    /**
     * 艺人
     */
    private String artist;

    /**
     * 作曲
     */
    private String composer;

    /**
     * 作词
     */
    private String lyricist;

    /**
     * 封面
     */
    private List<String> covers;
}
