package Actions.Download;

import Services.Interfacies.IPrintService;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.io.InputStream;

/**
 * Created by Артем on 26.05.2016.
 */
public class DownloadPupilStats extends ActionSupport {
    private String docType;
    private int pupilID;
    private InputStream fileStream;
    private String fileName;
    private IPrintService printService = ServiceFactory.getPrintService();

    public String execute() throws Exception {
        try {
            if (docType.equals("pdf")) {
                fileStream = printService.PrintPDFAchivementStatistics(pupilID);
                fileName = "Stats" + pupilID + ".pdf";
                return SUCCESS;
            }
            if (docType.equals("xls")) {
                fileStream = printService.PrintXLSAchivementStatistics(pupilID);
                fileName = "Stats" + pupilID + ".xlsx";
                return SUCCESS;
            }
            if (docType.equals("csv")) {
                fileStream = printService.PrintCSVAchivementStatistics(pupilID);
                fileName = "Stats" + pupilID + ".csv";
                return SUCCESS;
            }
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public int getPupilID() {
        return pupilID;
    }

    public void setPupilID(int pupilID) {
        this.pupilID = pupilID;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

