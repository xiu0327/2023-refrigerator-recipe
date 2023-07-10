package refrigerator.back.global.s3;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class S3ImageConvertAdapter implements ImageUrlConvert{

    private final S3ImageHandler s3ImageHandler;
    private final String bucketName;

    @Override
    public String getUrl(String fileName) {
        return s3ImageHandler.getUrl(bucketName, fileName).toString();
    }
}
