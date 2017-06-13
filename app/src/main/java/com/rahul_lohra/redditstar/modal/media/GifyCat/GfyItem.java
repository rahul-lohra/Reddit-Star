
package com.rahul_lohra.redditstar.modal.media.GifyCat;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GfyItem {

    @SerializedName("gfyId")
    @Expose
    private String gfyId;
    @SerializedName("gfyName")
    @Expose
    private String gfyName;
    @SerializedName("gfyNumber")
    @Expose
    private String gfyNumber;
    @SerializedName("webmUrl")
    @Expose
    private String webmUrl;
    @SerializedName("gifUrl")
    @Expose
    private String gifUrl;
    @SerializedName("mobileUrl")
    @Expose
    private String mobileUrl;
    @SerializedName("mobilePosterUrl")
    @Expose
    private String mobilePosterUrl;
    @SerializedName("miniUrl")
    @Expose
    private String miniUrl;
    @SerializedName("miniPosterUrl")
    @Expose
    private String miniPosterUrl;
    @SerializedName("posterUrl")
    @Expose
    private String posterUrl;
    @SerializedName("thumb360Url")
    @Expose
    private String thumb360Url;
    @SerializedName("thumb360PosterUrl")
    @Expose
    private String thumb360PosterUrl;
    @SerializedName("thumb100PosterUrl")
    @Expose
    private String thumb100PosterUrl;
    @SerializedName("max5mbGif")
    @Expose
    private String max5mbGif;
    @SerializedName("max2mbGif")
    @Expose
    private String max2mbGif;
    @SerializedName("max1mbGif")
    @Expose
    private String max1mbGif;
    @SerializedName("gif100px")
    @Expose
    private String gif100px;
    @SerializedName("mjpgUrl")
    @Expose
    private String mjpgUrl;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("avgColor")
    @Expose
    private String avgColor;
    @SerializedName("frameRate")
    @Expose
    private Integer frameRate;
    @SerializedName("numFrames")
    @Expose
    private Integer numFrames;
    @SerializedName("mp4Size")
    @Expose
    private Integer mp4Size;
    @SerializedName("webmSize")
    @Expose
    private Integer webmSize;
    @SerializedName("gifSize")
    @Expose
    private Integer gifSize;
    @SerializedName("source")
    @Expose
    private Integer source;
    @SerializedName("createDate")
    @Expose
    private Integer createDate;
    @SerializedName("nsfw")
    @Expose
    private String nsfw;
    @SerializedName("mp4Url")
    @Expose
    private String mp4Url;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("published")
    @Expose
    private Integer published;
    @SerializedName("dislikes")
    @Expose
    private Integer dislikes;
    @SerializedName("extraLemmas")
    @Expose
    private String extraLemmas;
    @SerializedName("md5")
    @Expose
    private String md5;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("languageCategories")
    @Expose
    private Object languageCategories;
    @SerializedName("domainWhitelist")
    @Expose
    private List<Object> domainWhitelist = null;

    public String getGfyId() {
        return gfyId;
    }

    public void setGfyId(String gfyId) {
        this.gfyId = gfyId;
    }

    public String getGfyName() {
        return gfyName;
    }

    public void setGfyName(String gfyName) {
        this.gfyName = gfyName;
    }

    public String getGfyNumber() {
        return gfyNumber;
    }

    public void setGfyNumber(String gfyNumber) {
        this.gfyNumber = gfyNumber;
    }

    public String getWebmUrl() {
        return webmUrl;
    }

    public void setWebmUrl(String webmUrl) {
        this.webmUrl = webmUrl;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getMobilePosterUrl() {
        return mobilePosterUrl;
    }

    public void setMobilePosterUrl(String mobilePosterUrl) {
        this.mobilePosterUrl = mobilePosterUrl;
    }

    public String getMiniUrl() {
        return miniUrl;
    }

    public void setMiniUrl(String miniUrl) {
        this.miniUrl = miniUrl;
    }

    public String getMiniPosterUrl() {
        return miniPosterUrl;
    }

    public void setMiniPosterUrl(String miniPosterUrl) {
        this.miniPosterUrl = miniPosterUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getThumb360Url() {
        return thumb360Url;
    }

    public void setThumb360Url(String thumb360Url) {
        this.thumb360Url = thumb360Url;
    }

    public String getThumb360PosterUrl() {
        return thumb360PosterUrl;
    }

    public void setThumb360PosterUrl(String thumb360PosterUrl) {
        this.thumb360PosterUrl = thumb360PosterUrl;
    }

    public String getThumb100PosterUrl() {
        return thumb100PosterUrl;
    }

    public void setThumb100PosterUrl(String thumb100PosterUrl) {
        this.thumb100PosterUrl = thumb100PosterUrl;
    }

    public String getMax5mbGif() {
        return max5mbGif;
    }

    public void setMax5mbGif(String max5mbGif) {
        this.max5mbGif = max5mbGif;
    }

    public String getMax2mbGif() {
        return max2mbGif;
    }

    public void setMax2mbGif(String max2mbGif) {
        this.max2mbGif = max2mbGif;
    }

    public String getMax1mbGif() {
        return max1mbGif;
    }

    public void setMax1mbGif(String max1mbGif) {
        this.max1mbGif = max1mbGif;
    }

    public String getGif100px() {
        return gif100px;
    }

    public void setGif100px(String gif100px) {
        this.gif100px = gif100px;
    }

    public String getMjpgUrl() {
        return mjpgUrl;
    }

    public void setMjpgUrl(String mjpgUrl) {
        this.mjpgUrl = mjpgUrl;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getAvgColor() {
        return avgColor;
    }

    public void setAvgColor(String avgColor) {
        this.avgColor = avgColor;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public Integer getNumFrames() {
        return numFrames;
    }

    public void setNumFrames(Integer numFrames) {
        this.numFrames = numFrames;
    }

    public Integer getMp4Size() {
        return mp4Size;
    }

    public void setMp4Size(Integer mp4Size) {
        this.mp4Size = mp4Size;
    }

    public Integer getWebmSize() {
        return webmSize;
    }

    public void setWebmSize(Integer webmSize) {
        this.webmSize = webmSize;
    }

    public Integer getGifSize() {
        return gifSize;
    }

    public void setGifSize(Integer gifSize) {
        this.gifSize = gifSize;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public String getNsfw() {
        return nsfw;
    }

    public void setNsfw(String nsfw) {
        this.nsfw = nsfw;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public String getExtraLemmas() {
        return extraLemmas;
    }

    public void setExtraLemmas(String extraLemmas) {
        this.extraLemmas = extraLemmas;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getLanguageCategories() {
        return languageCategories;
    }

    public void setLanguageCategories(Object languageCategories) {
        this.languageCategories = languageCategories;
    }

    public List<Object> getDomainWhitelist() {
        return domainWhitelist;
    }

    public void setDomainWhitelist(List<Object> domainWhitelist) {
        this.domainWhitelist = domainWhitelist;
    }

}
