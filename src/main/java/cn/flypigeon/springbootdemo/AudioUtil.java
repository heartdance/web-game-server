package cn.flypigeon.springbootdemo;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.flac.metadatablock.MetadataBlockDataPicture;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.AbstractTagFrame;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AudioUtil {

    public static Music getSongInfo(String musicPath) {
        Music music = new Music();
        try {
            AudioFile audioFile = AudioFileIO.read(new File(musicPath));
            Tag tag = audioFile.getTag();
            music.setTitle(tag.getFirst(FieldKey.TITLE));
            music.setAlbum(tag.getFirst(FieldKey.ALBUM));
            music.setArtist(tag.getFirst(FieldKey.ARTIST));
            music.setComposer(tag.getFirst(FieldKey.COMPOSER));
            music.setLyricist(tag.getFirst(FieldKey.LYRICIST));
            if (audioFile instanceof MP3File) {
                music.setCovers(Collections.singletonList(new BASE64Encoder().encode(getMP3Cover((MP3File) audioFile))));
            } else if (tag instanceof FlacTag) {
                List<MetadataBlockDataPicture> images = ((FlacTag) tag).getImages();
                BASE64Encoder base64Encoder = new BASE64Encoder();
                List<String> covers = new ArrayList<>(images.size());
                for (MetadataBlockDataPicture image : images) {
                    covers.add(base64Encoder.encode(image.getImageData()));
                }
                music.setCovers(covers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return music;
    }

    private static byte[] getMP3Cover(MP3File mp3File) {
        byte[] imageData = null;
        try {
            AbstractID3v2Tag tag = mp3File.getID3v2Tag();
            AbstractTagFrame frame = (AbstractTagFrame) tag.getFrame("APIC");
            FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
            imageData = body.getImageData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageData;
    }

}
