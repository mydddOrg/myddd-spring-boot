package org.myddd.media;

public interface MediaAccess {

    /**
     * 存取一个图片，指定图片内容及文件名后缀
     * @param content 图片内容
     * @param fileName  文件名,包含后缀
     * @return  返回代表此图片的唯一ID
     */
    public String saveMediaFile(byte[] content, String fileName);

    /**
     * 根据图片ID返回此图片
     * @param imageID  传入代表图片的唯一ID
     * @return  返回图片文件
     */
    public MediaFile getMediaFile(String imageID);
}
