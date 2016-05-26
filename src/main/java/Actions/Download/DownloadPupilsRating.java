package Actions.Download;

import Services.Interfacies.IPrintService;
import Services.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import java.io.InputStream;

/**
 * Created by Артем on 26.05.2016.
 */
public class DownloadPupilsRating extends ActionSupport {
    private String docType;
    private int classID;
    private InputStream fileStream;
    private String fileName;
    private IPrintService printService = ServiceFactory.getPrintService();

    public String execute() throws Exception {
        try {
            if (docType.equals("pdf")) {
                fileStream = printService.PrintPDFPupilsRating(classID);
                fileName = "Rating" + classID + ".pdf";
                return SUCCESS;
            }
            if (docType.equals("xls")) {
                fileStream = printService.PrintXLSPupilWeekSchedule(classID);
                fileName = "Rating" + classID + ".xlsx";
                return SUCCESS;
            }
            if (docType.equals("csv")) {
                fileStream = printService.PrintCSVPupilWeekSchedule(classID);
                fileName = "Rating" + classID + ".csv";
                return SUCCESS;
            }
            return ERROR;
        } catch (Exception ex) {
            return ERROR;
        }
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
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
