package net.sf.esfinge.greenframework.mock.service.numbertest;

import net.sf.esfinge.greenframework.annotation.GreenAdjustableNumber;
import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.annotation.GreenReturnWhenSwitchOff;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileService {

    @GreenAdjustableNumber
    @GreenConfigKey("keyCountTimes")
    private final Integer countTimes = 0;

    private final Integer defaultCount = 5;

    @GreenAdjustableNumber
    @GreenConfigKey("keyFileLength")
    private final Long fileLength = 0L;

    private final Long defaultFileLength = 5L;

    public Integer tryAccessExternalFileWithGreen() {
        return tryAccessFile(countTimes);
    }

    public Integer tryAccessExternalFileWithoutGreen() {
        return tryAccessFile(defaultCount);
    }

    public Long getFileLength() {
        return fileLength;
    }

    public Long getDefaultFileLength() {
        return defaultFileLength;
    }

    private int tryAccessFile(Integer countTimes) {
        int count = 0 ;

        for (int i = 0; i < countTimes; i++) {
            count++;
            log.info("Try to access the external file {}...", i+1);
        }

        return count;
    }

    @GreenReturnWhenSwitchOff(numberValue = 6458)
    public Integer getGreenValueInsideMethodClass1() {
        return 0;
    }

    @GreenReturnWhenSwitchOff(numberValue = 6459)
    public Integer getGreenValueInsideMethodClass2() {
        return 0;
    }
}
